package com.primafx.client.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.primafx.client.R;
import com.primafx.client.dialog.ShowDialog;
import com.primafx.client.retrofit.ParseDataDeposit;
import com.primafx.client.retrofit.ParseDeposit;
import com.primafx.client.retrofit.RequestLibrary;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DepositResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit_result);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        retrofitDeposit("1521161", "passwordku", intent.getStringExtra("pay_to"), intent.getStringExtra("usd"), intent.getStringExtra("idr"));
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

    private void retrofitDeposit(String akun, String authKey, String pay_to, String usd, String idr) {
        final Dialog loading = new ShowDialog().loading(this);
        loading.show();

        String host = "http://api.primafx.com/";

        ParseDeposit jsonSend = new ParseDeposit(akun, authKey, pay_to, usd, idr);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(host)
                .addConverterFactory(GsonConverterFactory.create()).build();
        RequestLibrary requestLibrary = retrofit.create(RequestLibrary.class);
        Call<ParseDeposit> callData = requestLibrary.deposit(jsonSend);

        callData.enqueue(new Callback<ParseDeposit>() {
            @Override
            public void onResponse(Call<ParseDeposit> call, Response<ParseDeposit> response) {
                loading.hide();
                if (response.isSuccessful()) {
                    ParseDeposit response_body = response.body();
                    if (response_body.getError()) {
                        new ShowDialog().error(DepositResultActivity.this, response_body.getMessage()).setOnDismissListener(new DialogInterface.OnDismissListener() {
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
                    new ShowDialog().error(DepositResultActivity.this, response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<ParseDeposit> call, Throwable t) {
                loading.hide();
                Log.e("Network", "ParseDeposit" + t.getMessage());
                new ShowDialog().error(DepositResultActivity.this, "Tidak dapat terhubung, terjadi masalah jaringan.");
            }
        });
    }

    public void setData(final ParseDataDeposit data) {
        TextView textAccount = (TextView) findViewById(R.id.textAccount);
        TextView textName = (TextView) findViewById(R.id.textName);
        TextView textUsd = (TextView) findViewById(R.id.textUsd);
        TextView textRekening = (TextView) findViewById(R.id.textRekening);
        TextView textNamaRekening = (TextView) findViewById(R.id.textNama);
        TextView textBank = (TextView) findViewById(R.id.textBank);
        TextView textSubtotal = (TextView) findViewById(R.id.textSubTotal);
        TextView textUnik = (TextView) findViewById(R.id.textUnik);
        TextView textTotalTransfer = (TextView) findViewById(R.id.textTotalTransfer);

        textAccount.setText("#" + data.getAkun());
        textName.setText(data.getNama());
        textUsd.setText("$" + data.getUsd_s());
        textRekening.setText(data.getPay_number());
        textNamaRekening.setText(data.getPay_name());
        textBank.setText(data.getPay_to());
        textSubtotal.setText("Rp " + data.getIdr());
        textUnik.setText(data.getUnik());
        textTotalTransfer.setText("Rp " + data.getTotal());


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
        Log.i("IDR", data.getIdr());
        Log.i("Unik", data.getUnik());
        Log.i("Total", data.getTotal());
        Log.i("Status", data.getStatus());

        Log.i("Sep ", "-------------------------------------------");
    }
}
