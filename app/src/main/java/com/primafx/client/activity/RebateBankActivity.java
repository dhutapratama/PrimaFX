package com.primafx.client.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.primafx.client.R;
import com.primafx.client.database.GData;
import com.primafx.client.dialog.ShowDialog;
import com.primafx.client.retrofit.ParseDataWithdrawalRebate;
import com.primafx.client.retrofit.ParseWithdrawalRebate;
import com.primafx.client.retrofit.RequestLibrary;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RebateBankActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rebate_bank);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Initialization
        initUI();
    }

    private void initUI() {

        final EditText editTotal = (EditText)findViewById(R.id.editTotal);
        editTotal.addTextChangedListener(new TextWatcher() {
            String amount;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                editTotal.setError(null);
                amount = charSequence.toString();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String valDollar = charSequence.toString();

                if (valDollar.length() > 4) {
                    editTotal.setText(amount);
                    editTotal.setError("Withdrawal anda melebihi batas maksimum");
                    return;
                } else if (valDollar.length() == 0) {
                    valDollar = "0";
                }
                return;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        TextView account = (TextView) findViewById(R.id.textAccount);
        account.setText("#"+GData.CURRENT_ACCOUNT);
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
                retrofitWithdrawalRebate(GData.CURRENT_ACCOUNT, GData.LOGIN_CODE, editTotal.getText().toString());
                return true;

            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void retrofitWithdrawalRebate(String akun, String authKey, String usd) {
        final Dialog loading = new ShowDialog().loading(this);
        loading.show();

        String host = GData.API_ADDRESS;

        ParseWithdrawalRebate jsonSend = new ParseWithdrawalRebate(akun, authKey, usd);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(host)
                .addConverterFactory(GsonConverterFactory.create()).build();
        RequestLibrary requestLibrary = retrofit.create(RequestLibrary.class);
        Call<ParseWithdrawalRebate> callData = requestLibrary.withdrawalRebate(jsonSend);

        callData.enqueue(new Callback<ParseWithdrawalRebate>() {
            @Override
            public void onResponse(Call<ParseWithdrawalRebate> call, Response<ParseWithdrawalRebate> response) {
                loading.dismiss();
                if (response.isSuccessful()) {
                    ParseWithdrawalRebate response_body = response.body();
                    if (response_body.getError()) {
                        new ShowDialog().error(RebateBankActivity.this, response_body.getMessage()).setOnDismissListener(new DialogInterface.OnDismissListener() {
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
                    new ShowDialog().error(RebateBankActivity.this, response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<ParseWithdrawalRebate> call, Throwable t) {
                loading.dismiss();
                Log.e("Network", "ParseWithdrrawalRebate" + t.getMessage());
                new ShowDialog().error(RebateBankActivity.this, "Tidak dapat terhubung, terjadi masalah jaringan.");
            }
        });
    }

    public void setData(final ParseDataWithdrawalRebate data) {
        Log.i("Type Order", data.getType_order());
        Log.i("Akun", data.getAkun());
        Log.i("Nama", data.getNama());
        Log.i("Pay To", data.getPay_to());
        Log.i("Pay Number", data.getPay_number());
        Log.i("Pay Name", data.getPay_name());
        Log.i("USD", data.getUsd());
        Log.i("Kurs", data.getKurs());
        Log.i("IDR", data.getIdr());

        Log.i("Sep ", "-------------------------------------------");

        Intent intent = new Intent(this, WithdrawalRebateResultActivity.class);
        intent.putExtra("akun", data.getAkun());
        intent.putExtra("nama", data.getNama());
        intent.putExtra("usd", data.getUsd());
        intent.putExtra("pay_number", data.getPay_number());
        intent.putExtra("pay_name", data.getPay_name());
        intent.putExtra("pay_to", data.getPay_to());
        intent.putExtra("kurs", data.getKurs());
        intent.putExtra("idr", data.getIdr());
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (GData.LOGIN_CODE.equals(null)) {
            Intent intent = new Intent(this, SplashActivity.class);
            startActivity(intent);
        }
    }
}
