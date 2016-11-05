package com.primafx.client.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.primafx.client.R;
import com.primafx.client.dialog.ShowDialog;
import com.primafx.client.retrofit.ParseDataDeposit;
import com.primafx.client.retrofit.ParseDataTransferRebate;
import com.primafx.client.retrofit.ParseDataWithdrawalRebate;
import com.primafx.client.retrofit.ParseDeposit;
import com.primafx.client.retrofit.ParseTransferRebate;
import com.primafx.client.retrofit.ParseWithdrawalRebate;
import com.primafx.client.retrofit.RequestLibrary;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TestingApiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing_api);

        retrofitWithdrawalRebate("7597802", "passwordku", "0.10", "true");
    }

    private void retrofitWithdrawalRebate(String akun, String authKey, String usd, String preview) {
        final Dialog loading = new ShowDialog().loading(this);
        loading.show();

        String host = "http://apis.primafx.com/";

        ParseWithdrawalRebate jsonSend = new ParseWithdrawalRebate(akun, authKey, usd, preview);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(host)
                .addConverterFactory(GsonConverterFactory.create()).build();
        RequestLibrary requestLibrary = retrofit.create(RequestLibrary.class);
        Call<ParseWithdrawalRebate> callData = requestLibrary.withdrawalRebate(jsonSend);

        callData.enqueue(new Callback<ParseWithdrawalRebate>() {
            @Override
            public void onResponse(Call<ParseWithdrawalRebate> call, Response<ParseWithdrawalRebate> response) {
                loading.hide();
                if (response.isSuccessful()) {
                    ParseWithdrawalRebate response_body = response.body();
                    if (response_body.getError()) {
                        new ShowDialog().success(TestingApiActivity.this, response_body.getMessage()).setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                finish();
                            }
                        });
                    } else {
                        setData(response_body.getData());
                    }
                } else {
                    Log.e("Server Problem", "Server Responding but error callback : " + response.body().toString());
                    new ShowDialog().error(TestingApiActivity.this, response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<ParseWithdrawalRebate> call, Throwable t) {
                loading.hide();
                Log.e("Network", "ParseWithdrrawalRebate" + t.getMessage());
                new ShowDialog().error(TestingApiActivity.this, "Tidak dapat terhubung, terjadi masalah jaringan.");
            }
        });
    }

    public void setData(final ParseDataWithdrawalRebate data) {
        Log.i("Type Order", data.getType_order());
        Log.i("Akun", data.getAkun());
        Log.i("Nama", data.getNama());
        Log.i("Phone", data.getPhone());
        Log.i("Email", data.getEmail());
        Log.i("Kode Agen", data.getKode_agen());
        Log.i("Best Regard", data.getBestRegard());
        Log.i("Pay To", data.getPay_to());
        Log.i("Pay Number", data.getPay_number());
        Log.i("Pay Name", data.getPay_name());
        Log.i("USD", data.getUsd());

        Log.i("Sep ", "-------------------------------------------");


    }
}