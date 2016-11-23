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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.primafx.client.R;
import com.primafx.client.database.GData;
import com.primafx.client.dialog.ShowDialog;
import com.primafx.client.retrofit.ParseDataTransferRebate;
import com.primafx.client.retrofit.ParseTransferRebate;
import com.primafx.client.retrofit.RequestLibrary;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RebateAccountActivity extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rebate_account);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Initialization
        initUI();
    }

    private void initUI() {
        final EditText editTotal = (EditText)findViewById(R.id.editTotal);
        editTotal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                editTotal.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String valDollar = charSequence.toString();

                if (valDollar.length() > 4) {
                    valDollar = valDollar.substring(0, 4);
                    editTotal.setText(valDollar);
                    editTotal.setError("Deposit anda melebihi batas maksimum");
                } else if (valDollar.length() == 0) {
                    valDollar = "0";
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

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
                transferRebate();
                return true;

            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void transferRebate() {
        EditText editAccountTujuan = (EditText) findViewById(R.id.editAccountTujuan);
        EditText editTotal = (EditText) findViewById(R.id.editTotal);


        retrofitTransferRebate(GData.CURRENT_ACCOUNT, GData.LOGIN_CODE, editTotal.getText().toString(), editAccountTujuan.getText().toString(), "true");
    }

    private void retrofitTransferRebate(String akun, String authKey, String usd, String pay_number, String preview) {
        final Dialog loading = new ShowDialog().loading(this);
        loading.show();

        String host = GData.API_ADDRESS;

        ParseTransferRebate jsonSend = new ParseTransferRebate(akun, authKey, usd, pay_number);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(host)
                .addConverterFactory(GsonConverterFactory.create()).build();
        RequestLibrary requestLibrary = retrofit.create(RequestLibrary.class);
        Call<ParseTransferRebate> callData = requestLibrary.transferRebate(jsonSend);

        callData.enqueue(new Callback<ParseTransferRebate>() {
            @Override
            public void onResponse(Call<ParseTransferRebate> call, Response<ParseTransferRebate> response) {
                loading.dismiss();
                if (response.isSuccessful()) {
                    ParseTransferRebate response_body = response.body();
                    if (response_body.getError()) {
                        new ShowDialog().error(RebateAccountActivity.this, response_body.getMessage()).setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                finish();
                            }
                        });
                    } else {
                        //Log.i(response_body.getCode(), response_body.getMessage());
                        //new ShowDialog().error(TestingApiActivity.this, "Message : " + response_body.getMessage());
                        setData(response_body.getData());
                    }
                } else {
                    Log.e("Server Problem", "Server Responding but error callback : " + response.body().toString());
                    new ShowDialog().error(RebateAccountActivity.this, response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<ParseTransferRebate> call, Throwable t) {
                loading.dismiss();
                Log.e("Network", "ParseTransferRebate" + t.getMessage());
                new ShowDialog().error(RebateAccountActivity.this, "Tidak dapat terhubung, terjadi masalah jaringan.");
            }
        });
    }

    public void setData(final ParseDataTransferRebate data) {
        Log.i("Type Order", data.getType_order());
        Log.i("Akun", data.getAkun());
        Log.i("Nama", data.getNama());
        Log.i("Pay To", data.getPay_to());
        Log.i("Pay Number", data.getPay_number());
        Log.i("Pay Name", data.getPay_name());
        Log.i("USD", data.getUsd());

        Log.i("Sep ", "-------------------------------------------");

        Intent intent = new Intent(this, TransferRebateResultActivity.class);
        intent.putExtra("akun", data.getAkun());
        intent.putExtra("usd", data.getUsd());
        intent.putExtra("pay_number", data.getPay_number());
        intent.putExtra("pay_name", data.getPay_name());
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
