package com.primafx.client.activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.primafx.client.R;
import com.primafx.client.database.GData;
import com.primafx.client.dialog.ShowDialog;
import com.primafx.client.retrofit.ParseChangePhone;
import com.primafx.client.retrofit.RequestLibrary;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PhoneRegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_registration);

        final EditText prefix = (EditText)findViewById(R.id.editPrefix);
        final EditText phone = (EditText)findViewById(R.id.editPhone);

        Button btnSave = (Button)findViewById(R.id.buttonAddPhone) ;
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retrofitSavePhone(GData.LOGIN_CODE, prefix.getText().toString()+phone.getText().toString());
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
    }

    private void retrofitSavePhone(String login_hash, String phone) {
        final Dialog loading = new ShowDialog().loading(this);
        loading.show();

        String host = GData.API_ADDRESS;

        ParseChangePhone jsonSend = new ParseChangePhone(login_hash, phone);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(host)
                .addConverterFactory(GsonConverterFactory.create()).build();
        RequestLibrary requestLibrary = retrofit.create(RequestLibrary.class);
        Call<ParseChangePhone> callData = requestLibrary.changePhone(jsonSend);

        callData.enqueue(new Callback<ParseChangePhone>() {
            @Override
            public void onResponse(Call<ParseChangePhone> call, Response<ParseChangePhone> response) {
                loading.dismiss();
                if (response.isSuccessful()) {
                    ParseChangePhone response_body = response.body();
                    if (response_body.getError()) {
                        new ShowDialog().error(PhoneRegistrationActivity.this, response_body.getMessage());
                    } else {
                        Intent i = new Intent(PhoneRegistrationActivity.this, PhoneConfirmationActivity.class);
                        startActivity(i);
                        finish();
                    }
                } else {
                    Log.e("Server Problem", "Server Responding but error callback : " + response.message());
                    new ShowDialog().error(PhoneRegistrationActivity.this, response.message());
                }
            }

            @Override
            public void onFailure(Call<ParseChangePhone> call, Throwable t) {
                loading.dismiss();
                Log.e("Network", "ParseChangePhone" + t.getMessage());
                new ShowDialog().error(PhoneRegistrationActivity.this, "Tidak dapat terhubung, terjadi masalah jaringan.");
            }
        });
    }
}
