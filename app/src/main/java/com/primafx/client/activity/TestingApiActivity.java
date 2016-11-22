package com.primafx.client.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.primafx.client.R;
import com.primafx.client.database.GData;
import com.primafx.client.dialog.ShowDialog;
import com.primafx.client.model.SquareFragment;
import com.primafx.client.retrofit.ParseAddAccount;
import com.primafx.client.retrofit.ParseCalendar;
import com.primafx.client.retrofit.ParseCallMeBack;
import com.primafx.client.retrofit.ParseCheckRebate;
import com.primafx.client.retrofit.ParseHistory;
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

        retrofitCheckRebate(
                "2450553",
                "88f200b77cccee4a6e95c383d33e0f22",
        "201609");

        //DatabaseSQL.removeAllAccount(this);
        //DatabaseSQL.addAccount(this, "123456", "test123");
        //DatabaseSQL.deleteAccount(this, "123456");

        //List<String> my_accounts = DatabaseSQL.getAccountData(this);

        //for(int i = 0; i<my_accounts.size(); i++){
        //    Log.i("Account", my_accounts.get(i));
        //}
    }

    private void retrofitCheckRebate(String akun, String authKey, String periode) {
        final Dialog loading = new ShowDialog().loading(this);
        loading.show();

        String host = GData.API_ADDRESS;

        //ParseAddAccount jsonSend = new ParseAddAccount("3652724",
        //        "e1b3b0121ba60319ab4b6ec5fd9e52e0f1080de9882e02526b9e572b8939d930bb0f707b2818f4f909e2bc8a3b7a5177c964a3bdc7c376cd4b1ef97573b5ba30",
        //        "123456a");

        ParseCheckRebate jsonSend = new ParseCheckRebate(akun, authKey, periode);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(host)
                .addConverterFactory(GsonConverterFactory.create()).build();
        RequestLibrary requestLibrary = retrofit.create(RequestLibrary.class);
        Call<ParseCheckRebate> callData = requestLibrary.checkRebate(jsonSend);

        callData.enqueue(new Callback<ParseCheckRebate>() {
            @Override
            public void onResponse(Call<ParseCheckRebate> call, Response<ParseCheckRebate> response) {
                loading.dismiss();
                if (response.isSuccessful()) {
                    ParseCheckRebate response_body = response.body();
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
            public void onFailure(Call<ParseCheckRebate> call, Throwable t) {
                loading.dismiss();
                Log.e("Network", "ParseCallMeBack" + t.getMessage());
                new ShowDialog().error(TestingApiActivity.this, "Tidak dapat terhubung, terjadi masalah jaringan.");
            }
        });
    }

    public void setData(ParseCheckRebate data) {
        Log.i("periode", data.getData().getSummary().getPeriode());
        Log.i("credit", data.getData().getSummary().getCredit());
        Log.i("debit", data.getData().getSummary().getDebit());
        Log.i("sisa", data.getData().getSummary().getSisa());
        Log.i("Sep ", "-------------------------------------------");
        Log.i("Sep ", "-------------------------------------------");

        for (int i = 0; i < data.getData().getDetail().size(); i++) {
            Log.i("id", data.getData().getDetail().get(i).getId());
            Log.i("ticket", data.getData().getDetail().get(i).getTicket());
            Log.i("Date", data.getData().getDetail().get(i).getDate());
            Log.i("InputDateTime", data.getData().getDetail().get(i).getInputDateTime());
            Log.i("InstaDateTime", data.getData().getDetail().get(i).getInstaDateTime());

            Log.i("akun", data.getData().getDetail().get(i).getAkun());
            Log.i("profit", data.getData().getDetail().get(i).getProfit());
            Log.i("detail", data.getData().getDetail().get(i).getDetail());
            Log.i("info", data.getData().getDetail().get(i).getInfo());

            Log.i("Sep ", "-------------------------------------------");
        }

        /*
        Log.i("nama", data.getData().getNama());
        Log.i("phone", data.getData().getPhone());
        Log.i("email", data.getData().getEmail());
        Log.i("support", data.getData().getSupport());
        Log.i("time", data.getData().getTime());
        Log.i("date", data.getData().getDate());
*/

    }

}