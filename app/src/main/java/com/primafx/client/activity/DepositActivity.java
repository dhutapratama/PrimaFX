package com.primafx.client.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.primafx.client.R;
import com.primafx.client.database.GData;
import com.primafx.client.dialog.ShowDialog;
import com.primafx.client.retrofit.ParseDataDeposit;
import com.primafx.client.retrofit.ParseDataDepositInquiry;
import com.primafx.client.retrofit.ParseDeposit;
import com.primafx.client.retrofit.ParseDepositInquiry;
import com.primafx.client.retrofit.RequestLibrary;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DepositActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText editTotal;
    Spinner spinnerBank;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialization
        initUI();
    }

    private void initUI() {

        spinnerBank = (Spinner) findViewById(R.id.spinnerBank);
        ArrayAdapter<CharSequence> adapterBank = ArrayAdapter.createFromResource(this,
                R.array.listBank, android.R.layout.simple_spinner_item);
        adapterBank.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBank.setAdapter(adapterBank);
        spinnerBank.setOnItemSelectedListener(this);
        editTotal = (EditText) findViewById(R.id.editTotal);

        TextView account = (TextView) findViewById(R.id.textAccount);
        account.setText("#"+GData.CURRENT_ACCOUNT);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present
        getMenuInflater().inflate(R.menu.button_send, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_send:
                EditText editTotal = (EditText) findViewById(R.id.editTotal);
                if (editTotal.getText().toString().equals("")) {
                    new ShowDialog().error(DepositActivity.this, "Mohon isikan deposit anda!");
                } else {
                    Log.i("Bank", spinnerBank.getSelectedItem().toString());
                    //retrofitDepositInquiry(GData.CURRENT_ACCOUNT, GData.LOGIN_CODE, spinnerBank.getSelectedItem().toString(), editTotal.getText().toString());
                    retrofitDeposit(GData.CURRENT_ACCOUNT, GData.LOGIN_CODE, spinnerBank.getSelectedItem().toString(), editTotal.getText().toString(), "");
                }
                return true;

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

        String host = GData.API_ADDRESS;

        ParseDeposit jsonSend = new ParseDeposit(akun, authKey, pay_to, usd, idr, "true");
        Retrofit retrofit = new Retrofit.Builder().baseUrl(host)
                .addConverterFactory(GsonConverterFactory.create()).build();
        RequestLibrary requestLibrary = retrofit.create(RequestLibrary.class);
        Call<ParseDeposit> callData = requestLibrary.deposit(jsonSend);

        callData.enqueue(new Callback<ParseDeposit>() {
            @Override
            public void onResponse(Call<ParseDeposit> call, Response<ParseDeposit> response) {
                loading.dismiss();
                if (response.isSuccessful()) {
                    ParseDeposit response_body = response.body();
                    if (response_body.getError()) {
                        new ShowDialog().error(DepositActivity.this, response_body.getMessage()).setOnDismissListener(new DialogInterface.OnDismissListener() {
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
                    new ShowDialog().error(DepositActivity.this, response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<ParseDeposit> call, Throwable t) {
                loading.dismiss();
                Log.e("Network", "ParseDeposit" + t.getMessage());
                new ShowDialog().error(DepositActivity.this, "Tidak dapat terhubung, terjadi masalah jaringan.");
            }
        });
    }

    public void setData(final ParseDataDeposit data) {
        Log.i("Type Order", data.getType_order());
        Log.i("Akun", data.getAkun());
        Log.i("Pay To", data.getPay_to());
        Log.i("Pay Number", data.getPay_number());
        Log.i("Pay Name", data.getPay_name());
        Log.i("USD", data.getUsd());
        Log.i("USD Normal", data.getUsd_n());
        Log.i("USD Spesial", data.getUsd_s());
        Log.i("Kurs", data.getKurs());
        Log.i("IDR", data.getIdr());
        Log.i("Sep ", "-------------------------------------------");

        Intent intent = new Intent(this, DepositResultActivity.class);
        intent.putExtra("akun", data.getAkun());
        intent.putExtra("usd", data.getUsd());
        intent.putExtra("pay_to", data.getPay_to());
        intent.putExtra("kurs", data.getKurs());
        intent.putExtra("idr", data.getIdr());
        intent.putExtra("total", data.getTotal());
        intent.putExtra("idr_n", data.getIdr_n());
        intent.putExtra("idr_s", data.getIdr_s());

        startActivity(intent);
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
}