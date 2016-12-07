package com.primafx.client.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.google.firebase.iid.FirebaseInstanceId;
import com.primafx.client.R;
import com.primafx.client.database.GData;
import com.primafx.client.dialog.ShowDialog;
import com.primafx.client.model.SquareFragment;
import com.primafx.client.retrofit.ParseAccountDetail;
import com.primafx.client.retrofit.ParseAddAccount;
import com.primafx.client.retrofit.ParseCalendar;
import com.primafx.client.retrofit.ParseCallMeBack;
import com.primafx.client.retrofit.ParseCheckRebate;
import com.primafx.client.retrofit.ParseGetAccounts;
import com.primafx.client.retrofit.ParseHistory;
import com.primafx.client.retrofit.ParseKlaimRebate;
import com.primafx.client.retrofit.ParseUpdateGCM;
import com.primafx.client.retrofit.RequestLibrary;

import java.util.ArrayList;
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

        //retrofitgetAccounts("e1b3b0121ba60319ab4b6ec5fd9e52e0f1080de9882e02526b9e572b8939d930bb0f707b2818f4f909e2bc8a3b7a5177c964a3bdc7c376cd4b1ef97573b5ba30");



        //DatabaseSQL.removeAllAccount(this);
        //DatabaseSQL.addAccount(this, "123456", "test123");
        //DatabaseSQL.deleteAccount(this, "123456");

        //List<String> my_accounts = DatabaseSQL.getAccountData(this);

        //for(int i = 0; i<my_accounts.size(); i++){
        //    Log.i("Account", my_accounts.get(i));
        //}
    }

    private void retrofitgetAccounts() {
        final Dialog loading = new ShowDialog().loading(this);
        loading.show();

        String host = GData.API_ADDRESS;

        ParseUpdateGCM jsonSend = new ParseUpdateGCM(GData.LOGIN_CODE, FirebaseInstanceId.getInstance().getToken());
        Retrofit retrofit = new Retrofit.Builder().baseUrl(host)
                .addConverterFactory(GsonConverterFactory.create()).build();
        RequestLibrary requestLibrary = retrofit.create(RequestLibrary.class);
        Call<ParseUpdateGCM> callData = requestLibrary.updateGCM(jsonSend);

        callData.enqueue(new Callback<ParseUpdateGCM>() {
            @Override
            public void onResponse(Call<ParseUpdateGCM> call, Response<ParseUpdateGCM> response) {
                loading.dismiss();
                if (response.isSuccessful()) {
                    Log.d("Firebase", "InstanceID token: " + FirebaseInstanceId.getInstance().getToken());
                } else {
                    Log.e("Server Problem", "Server Responding but error callback : " + response.message());
                    new ShowDialog().error(TestingApiActivity.this, response.message());
                }
            }

            @Override
            public void onFailure(Call<ParseUpdateGCM> call, Throwable t) {
                loading.dismiss();
                Log.e("Network", "ParseCallMeBack" + t.getMessage());
                new ShowDialog().error(TestingApiActivity.this, "Tidak dapat terhubung, terjadi masalah jaringan.");
            }
        });
    }

    public void setData(ParseGetAccounts data) {
        for (int i = 0; i < data.getData().size(); i++) {
            Log.i("akun", data.getData().get(i).getAkun());
            Log.i("nama", data.getData().get(i).getNama());

            Log.i("Sep ", "-------------------------------------------");
        }
/*
        Log.i("nama", data.getData().getNama());
        Log.i("phone", data.getData().getPhone());
        Log.i("email", data.getData().getEmail());
        Log.i("pay_to", data.getData().getPay_to());
        Log.i("pay_name", data.getData().getPay_name());
        Log.i("pay_number", data.getData().getPay_number());

        */

    }

}