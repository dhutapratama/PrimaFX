package com.primafx.client.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.annotations.SerializedName;
import com.primafx.client.R;
import com.primafx.client.database.DatabaseSQL;
import com.primafx.client.database.GData;
import com.primafx.client.dialog.ShowDialog;
import com.primafx.client.retrofit.ParseAddAccount;
import com.primafx.client.retrofit.ParseDataQuotes;
import com.primafx.client.retrofit.ParseQuotes;
import com.primafx.client.retrofit.ParseWithdrawal;
import com.primafx.client.retrofit.RequestLibrary;

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

        retrofitAddAccount();


        //DatabaseSQL.removeAllAccount(this);
        //DatabaseSQL.addAccount(this, "123456", "test123");
        //DatabaseSQL.deleteAccount(this, "123456");

        //List<String> my_accounts = DatabaseSQL.getAccountData(this);

        //for(int i = 0; i<my_accounts.size(); i++){
        //    Log.i("Account", my_accounts.get(i));
        //}
    }

    private void retrofitAddAccount() {
        final Dialog loading = new ShowDialog().loading(this);
        loading.show();

        String host = GData.API_ADDRESS;

        ParseAddAccount jsonSend = new ParseAddAccount("3652724",
                "e1b3b0121ba60319ab4b6ec5fd9e52e0f1080de9882e02526b9e572b8939d930bb0f707b2818f4f909e2bc8a3b7a5177c964a3bdc7c376cd4b1ef97573b5ba30",
                "123456a");
        Retrofit retrofit = new Retrofit.Builder().baseUrl(host)
                .addConverterFactory(GsonConverterFactory.create()).build();
        RequestLibrary requestLibrary = retrofit.create(RequestLibrary.class);
        Call<ParseAddAccount> callData = requestLibrary.addAccount(jsonSend);

        callData.enqueue(new Callback<ParseAddAccount>() {
            @Override
            public void onResponse(Call<ParseAddAccount> call, Response<ParseAddAccount> response) {
                loading.dismiss();
                if (response.isSuccessful()) {
                    ParseAddAccount response_body = response.body();
                    if (response_body.getError()) {
                        new ShowDialog().error(TestingApiActivity.this, response_body.getMessage()).setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                finish();
                            }
                        });
                    } else {
                        setData(response_body);
                    }
                } else {
                    Log.e("Server Problem", "Server Responding but error callback : " + response.message());
                    //new ShowDialog().error(TestingApiActivity.this, response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<ParseAddAccount> call, Throwable t) {
                loading.dismiss();
                Log.e("Network", "ParseAddAccount" + t.getMessage());
                new ShowDialog().error(TestingApiActivity.this, "Tidak dapat terhubung, terjadi masalah jaringan.");
            }
        });
    }

    public void setData(ParseAddAccount data) {
/*
        for (int i = 0; i < data.size(); i++) {
            Log.i("id", data.get(i).getId());
            Log.i("symbol", data.get(i).getSymbol());
            Log.i("datetime", data.get(i).getDatetime());
            Log.i("open", data.get(i).getOpen());
            Log.i("ask", data.get(i).getAsk());
            Log.i("bid", data.get(i).getBid());
            Log.i("high", data.get(i).getHigh());
            Log.i("low", data.get(i).getLow());
            Log.i("ranges", data.get(i).getRanges());
            Log.i("company", data.get(i).getCompany());

            Log.i("Sep ", "-------------------------------------------");
        }
*/
        Log.i("akun", data.getData().getAkun());
        Log.i("nama", data.getData().getNama());
        Log.i("phone", data.getData().getPhone());
        Log.i("email", data.getData().getEmail());
        Log.i("kode_agen", data.getData().getKode_agen());
        Log.i("trader_id", data.getData().getTrader_id());
        Log.i("best_regard", data.getData().getBest_regard());
        Log.i("app", data.getData().getApp());


    }
}