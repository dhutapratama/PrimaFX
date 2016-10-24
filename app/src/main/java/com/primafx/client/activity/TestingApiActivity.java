package com.primafx.client.activity;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.primafx.client.R;
import com.primafx.client.dialog.ShowDialog;
import com.primafx.client.retrofit.ParseCheckRebateInquiry;
import com.primafx.client.retrofit.ParseDataDepositInquiry;
import com.primafx.client.retrofit.ParseDataRebateInquiry;
import com.primafx.client.retrofit.ParseDepositInquiry;
import com.primafx.client.retrofit.RequestLibrary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

        retrofitDepositInquiry("3652724", "passwordku", "bca", "5.00");
    }

    private void retrofitDepositInquiry(String akun, String authKey, String pay_to, String usd) {
        final Dialog loading = new ShowDialog().loading(this);
        loading.show();

        String host = "http://api.primafx.com/";

        ParseDepositInquiry jsonSend = new ParseDepositInquiry(akun, authKey, pay_to, usd);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(host)
                .addConverterFactory(GsonConverterFactory.create()).build();
        RequestLibrary requestLibrary = retrofit.create(RequestLibrary.class);
        Call<ParseDepositInquiry> callData = requestLibrary.depositInquiry(jsonSend);

        callData.enqueue(new Callback<ParseDepositInquiry>() {
            @Override
            public void onResponse(Call<ParseDepositInquiry> call, Response<ParseDepositInquiry> response) {
                loading.hide();
                if (response.isSuccessful()) {
                    ParseDepositInquiry response_body = response.body();
                    if (response_body.getError()) {
                        //new ShowDialog().success(TestingApiActivity.this, response_body.getMessage());
                        finish();
                    } else {
                        //Log.i(response_body.getCode(), response_body.getMessage());
                        new ShowDialog().error(TestingApiActivity.this, "Message : " + response_body.getMessage());
                        setData(response_body.getData());
                    }
                } else {
                    Log.e("Server Problem", "Server Responding but error callback : " + response.body().toString());
                    new ShowDialog().error(TestingApiActivity.this, response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<ParseDepositInquiry> call, Throwable t) {
                loading.hide();
                Log.e("Network", "ParseCheckRebateInquiry" + t.getMessage());
                new ShowDialog().error(TestingApiActivity.this, "Tidak dapat terhubung, terjadi masalah jaringan.");
            }
        });
    }

    public void setData(final ParseDataDepositInquiry data) {
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
        Log.i("USD Normal", data.getUsd_n());
        Log.i("USD Spesial", data.getUsd_s());
        Log.i("Kurs", data.getKurs());
        Log.i("Kurs Normal", data.getKurs_n());
        Log.i("Kurs Spesial", data.getKurs_s());
        Log.i("IDR", data.getIdr());
        Log.i("IDR Normal", data.getIdr_n());
        Log.i("IDR Spesial", data.getIdr_s());

        Log.i("Sep ", "-------------------------------------------");

    }
}

/*
    @SerializedName("kurs")
    private String kurs;
    @SerializedName("kurs_n")
    private String kurs_n;
    @SerializedName("kurs_s")
    private String kurs_s;
    @SerializedName("idr")
    private String idr;
    @SerializedName("idr_n")
    private String idr_n;
    @SerializedName("idr_s")
    private String idr_s;
 */
