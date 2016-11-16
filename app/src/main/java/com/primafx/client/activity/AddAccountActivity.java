package com.primafx.client.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.primafx.client.R;
import com.primafx.client.database.DatabaseSQL;
import com.primafx.client.database.GData;
import com.primafx.client.dialog.ShowDialog;
import com.primafx.client.retrofit.ParseAddAccount;
import com.primafx.client.retrofit.RequestLibrary;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final EditText account = (EditText)findViewById(R.id.editAccount);
        final EditText phone_password = (EditText)findViewById(R.id.editPhonePassword);

        Button tambahkan = (Button)findViewById(R.id.buttonAddAccount);
        tambahkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrofitAddAccount(account.getText().toString(), phone_password.getText().toString());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void retrofitAddAccount(final String account, String phone_password) {
        final Dialog loading = new ShowDialog().loading(this);
        loading.show();

        String host = GData.API_ADDRESS;

        ParseAddAccount jsonSend = new ParseAddAccount(account,
                GData.LOGIN_CODE,
                phone_password);
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
                        new ShowDialog().error(AddAccountActivity.this, response_body.getMessage()).setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                finish();
                            }
                        });
                    } else {
                        setData(response_body);
                        DatabaseSQL.addAccount(AddAccountActivity.this, account);
                        new ShowDialog().success(AddAccountActivity.this, "Akun anda berhasil ditambahkan.").setOnDismissListener(
                                new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialog) {
                                        Intent intent = new Intent(AddAccountActivity.this, MainAppActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                        );

                    }
                } else {
                    Log.e("Server Problem", "Server Responding but error callback : " + response.message());
                    new ShowDialog().error(AddAccountActivity.this, response.message());
                }
            }

            @Override
            public void onFailure(Call<ParseAddAccount> call, Throwable t) {
                loading.dismiss();
                Log.e("Network", "ParseAddAccount" + t.getMessage());
                new ShowDialog().error(AddAccountActivity.this, "Tidak dapat terhubung, terjadi masalah jaringan.");
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
