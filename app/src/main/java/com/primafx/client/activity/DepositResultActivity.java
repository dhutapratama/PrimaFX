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
import com.primafx.client.database.GData;
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

        TextView textAccount = (TextView) findViewById(R.id.textAccount);
        TextView textUsd = (TextView) findViewById(R.id.textUsd);
        TextView textRekening = (TextView) findViewById(R.id.textRekening);
        TextView textNamaRekening = (TextView) findViewById(R.id.textNama);
        TextView textBank = (TextView) findViewById(R.id.textBank);
        TextView textSubtotal = (TextView) findViewById(R.id.textSubTotal);
        TextView textUnik = (TextView) findViewById(R.id.textUnik);
        TextView textTotalTransfer = (TextView) findViewById(R.id.textTotalTransfer);

        textAccount.setText("#" + intent.getStringExtra("akun"));
        textUsd.setText("$" + intent.getStringExtra("usd"));
        textRekening.setText(intent.getStringExtra("pay_number"));
        textNamaRekening.setText(intent.getStringExtra("pay_name"));
        textBank.setText(intent.getStringExtra("pay_to"));
        textSubtotal.setText("Rp " + intent.getStringExtra("idr"));

        if (intent.getStringExtra("unik")!=null) {
            textUnik.setText(intent.getStringExtra("unik"));
            textTotalTransfer.setText("Rp " + intent.getStringExtra("total"));
        }

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


}
