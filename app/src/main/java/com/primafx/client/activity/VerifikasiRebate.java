package com.primafx.client.activity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.primafx.client.R;
import com.primafx.client.database.GData;
import com.primafx.client.dialog.ShowDialog;
import com.primafx.client.retrofit.ParseDataWithdrawal;
import com.primafx.client.retrofit.ParseTransferRebateVerify;
import com.primafx.client.retrofit.ParseWithdrawal;
import com.primafx.client.retrofit.RequestLibrary;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VerifikasiRebate extends AppCompatActivity {
    private BroadcastReceiver mIntentReceiver;
    private Boolean canRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifikasi_rebate);


        Button buttonConfirm = (Button)findViewById(R.id.buttonConfirm);
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editKodeSMS = (EditText)findViewById(R.id.editKodeSMS);
                retrofitVerifyTransfer(GData.CURRENT_ACCOUNT, GData.LOGIN_CODE, editKodeSMS.getText().toString());
            }
        });

        /*
        final Button buttonRequest = (Button)findViewById(R.id.buttonRequest);
        buttonRequest.setClickable(false);

        buttonRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDialog.error(VerifikasiRebate.this, "di klik");
            }
        });

        new CountDownTimer(120000, 1000) {

            public void onTick(long millisUntilFinished) {
                buttonRequest.setText("KIRIM ULANG (" + millisUntilFinished / 1000 + ")");
            }

            public void onFinish() {
                buttonRequest.setClickable(true);
                buttonRequest.setTextColor(getResources().getColor(R.color.colorBlue));
            }
        }.start();
        */
    }
    @Override
    protected void onResume() {
        super.onResume();

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

    private void retrofitVerifyTransfer(String akun, String authKey, String verify_code) {
        final Dialog loading = new ShowDialog().loading(this);
        loading.show();

        String host = GData.API_ADDRESS;

        ParseTransferRebateVerify jsonSend = new ParseTransferRebateVerify(akun, authKey, verify_code);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(host)
                .addConverterFactory(GsonConverterFactory.create()).build();
        RequestLibrary requestLibrary = retrofit.create(RequestLibrary.class);
        Call<ParseTransferRebateVerify> callData = requestLibrary.verifyCode(jsonSend);

        callData.enqueue(new Callback<ParseTransferRebateVerify>() {
            @Override
            public void onResponse(Call<ParseTransferRebateVerify> call, Response<ParseTransferRebateVerify> response) {
                loading.dismiss();
                if (response.isSuccessful()) {
                    ParseTransferRebateVerify response_body = response.body();
                    if (response_body.getError()) {
                        new ShowDialog().error(VerifikasiRebate.this, response_body.getMessage()).setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                            }
                        });
                    } else {
                        new ShowDialog().success(VerifikasiRebate.this, response_body.getMessage()).setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                finish();
                            }
                        });
                    }
                } else {
                    Log.e("Server Problem", "Server Responding but error callback : " + response.body().toString());
                    new ShowDialog().error(VerifikasiRebate.this, response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<ParseTransferRebateVerify> call, Throwable t) {
                loading.dismiss();
                Log.e("Network", "ParseWithdrawal" + t.getMessage());
                new ShowDialog().error(VerifikasiRebate.this, "Tidak dapat terhubung, terjadi masalah jaringan.");
            }
        });
    }

}
