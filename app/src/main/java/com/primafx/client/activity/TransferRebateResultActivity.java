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
import android.widget.TextView;
import android.widget.Toast;

import com.primafx.client.R;
import com.primafx.client.database.GData;
import com.primafx.client.dialog.ShowDialog;
import com.primafx.client.retrofit.ParseDataTransferRebate;
import com.primafx.client.retrofit.ParseTransferRebate;
import com.primafx.client.retrofit.RequestLibrary;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TransferRebateResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_rebate_result);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Intent intent = getIntent();

        TextView textAccount = (TextView) findViewById(R.id.textTypeOrder);
        TextView textUsd = (TextView) findViewById(R.id.textUsd);
        TextView textAccountTujuan = (TextView) findViewById(R.id.textTglProses);
        TextView textNama = (TextView) findViewById(R.id.textNama);

        textAccount.setText(intent.getStringExtra("akun"));
        textUsd.setText(intent.getStringExtra("usd"));
        textAccountTujuan.setText(intent.getStringExtra("pay_number"));
        textNama.setText(intent.getStringExtra("pay_name"));

        Button buttonLanjutkan = (Button)findViewById(R.id.buttonLanjutkan);
        buttonLanjutkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retrofitTransferRebate(GData.CURRENT_ACCOUNT, GData.LOGIN_CODE, intent.getStringExtra("usd"), intent.getStringExtra("pay_number"), "false");
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

    private void retrofitTransferRebate(String akun, String authKey, String usd, String pay_number, String preview) {
        final Dialog loading = new ShowDialog().loading(this);
        loading.show();

        String host = GData.API_ADDRESS;

        ParseTransferRebate jsonSend = new ParseTransferRebate(akun, authKey, usd, pay_number, preview);
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
                        new ShowDialog().error(TransferRebateResultActivity.this, response_body.getMessage()).setOnDismissListener(new DialogInterface.OnDismissListener() {
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
                    new ShowDialog().error(TransferRebateResultActivity.this, response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<ParseTransferRebate> call, Throwable t) {
                loading.dismiss();
                Log.e("Network", "ParseTransferRebate" + t.getMessage());
                new ShowDialog().error(TransferRebateResultActivity.this, "Tidak dapat terhubung, terjadi masalah jaringan.");
            }
        });
    }

    public void setData(final ParseDataTransferRebate data) {
        if (data.getStatus().equals("verify")) {
            Intent intent = new Intent(TransferRebateResultActivity.this, VerifikasiRebate.class);
            startActivity(intent);
        } else {
            Toast toast = Toast.makeText(TransferRebateResultActivity.this, "Sukses! Order anda akan segera kami proses.", Toast.LENGTH_LONG);
            toast.show();
            finish();
        }
    }

}
