package com.primafx.client.activity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.primafx.client.R;
import com.primafx.client.database.DatabaseSQL;
import com.primafx.client.database.GData;
import com.primafx.client.dialog.ShowDialog;
import com.primafx.client.retrofit.ParseAuthenticate;
import com.primafx.client.retrofit.ParseChangePhone;
import com.primafx.client.retrofit.ParseHistoryFind;
import com.primafx.client.retrofit.ParseVerifyChangePhone;
import com.primafx.client.retrofit.RequestLibrary;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PhoneConfirmationActivity extends AppCompatActivity {
    private BroadcastReceiver mIntentReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_confirmation);

        final EditText code = (EditText)findViewById(R.id.editKodeSMS);

        Button btnSave = (Button)findViewById(R.id.buttonConfirm) ;
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retrofitVerifyPhone(GData.LOGIN_CODE, code.getText().toString());
            }
        });
    }

    private void retrofitVerifyPhone(String login_hash, String code) {
        final Dialog loading = new ShowDialog().loading(this);
        loading.show();

        String host = GData.API_ADDRESS;

        ParseVerifyChangePhone jsonSend = new ParseVerifyChangePhone(login_hash, code);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(host)
                .addConverterFactory(GsonConverterFactory.create()).build();
        RequestLibrary requestLibrary = retrofit.create(RequestLibrary.class);
        Call<ParseVerifyChangePhone> callData = requestLibrary.verifyChangePhone(jsonSend);

        callData.enqueue(new Callback<ParseVerifyChangePhone>() {
            @Override
            public void onResponse(Call<ParseVerifyChangePhone> call, Response<ParseVerifyChangePhone> response) {
                loading.dismiss();
                if (response.isSuccessful()) {
                    ParseVerifyChangePhone response_body = response.body();
                    if (response_body.getError()) {
                        new ShowDialog().error(PhoneConfirmationActivity.this, response_body.getMessage());
                    } else {
                        Intent i = getBaseContext().getPackageManager()
                                .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        finish();
                    }
                } else {
                    Log.e("Server Problem", "Server Responding but error callback : " + response.message());
                    new ShowDialog().error(PhoneConfirmationActivity.this, response.message());
                }
            }

            @Override
            public void onFailure(Call<ParseVerifyChangePhone> call, Throwable t) {
                loading.dismiss();
                Log.e("Network", "ParseVerifyChangePhone" + t.getMessage());
                new ShowDialog().error(PhoneConfirmationActivity.this, "Tidak dapat terhubung, terjadi masalah jaringan.");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (GData.LOGIN_CODE== null) {
            Intent i = getBaseContext().getPackageManager()
                    .getLaunchIntentForPackage( getBaseContext().getPackageName() );
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }

        IntentFilter intentFilter = new IntentFilter("SmsMessage.intent.MAIN");
        mIntentReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String code = intent.getStringExtra("code");
                EditText editKodeSMS = (EditText)findViewById(R.id.editKodeSMS);
                editKodeSMS.setText(code);
            }
        };
        this.registerReceiver(mIntentReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.unregisterReceiver(this.mIntentReceiver);
    }
}
