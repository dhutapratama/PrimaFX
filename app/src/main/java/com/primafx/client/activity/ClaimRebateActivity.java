package com.primafx.client.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.primafx.client.R;
import com.primafx.client.database.GData;
import com.primafx.client.dialog.ShowDialog;
import com.primafx.client.retrofit.ParseKlaimRebate;
import com.primafx.client.retrofit.RequestLibrary;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClaimRebateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim_rebate);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Spinner spinnerPayTo = (Spinner)findViewById(R.id.spinnerKe);
        final EditText editNomorTujuan = (EditText)findViewById(R.id.editNomorTujuan);
        final EditText editNamaPemegangRekening = (EditText)findViewById(R.id.editNamaPemegangRekening);
        final EditText editEmail = (EditText)findViewById(R.id.editEmail);
        final EditText editPhone = (EditText)findViewById(R.id.editPhone);

        final ArrayList<String> pay_to =new ArrayList<String>(){};
        pay_to.add("AKUN");
        pay_to.add("BCA");
        pay_to.add("BNI");
        pay_to.add("BRI");
        pay_to.add("MANDIRI");

        ArrayAdapter<String> list_pay_to= new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, pay_to);
        list_pay_to.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPayTo.setAdapter(list_pay_to);


        Button buttonKlaimSekarang = (Button)findViewById(R.id.buttonKlaimSekarang);
        buttonKlaimSekarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retrofitKlaimRebate(
                        GData.CURRENT_ACCOUNT,
                        GData.LOGIN_CODE,
                        spinnerPayTo.getSelectedItem().toString(),
                        editNomorTujuan.getText().toString(),
                        editNamaPemegangRekening.getText().toString(),
                        editEmail.getText().toString(),
                        editPhone.getText().toString());
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

    private void retrofitKlaimRebate(String akun, String login_hash, String pay_to, String pay_number, String pay_name, String email, String phone ) {
        final Dialog loading = new ShowDialog().loading(this);
        loading.show();

        String host = GData.API_ADDRESS;

        ParseKlaimRebate jsonSend = new ParseKlaimRebate(akun, login_hash, pay_to, pay_number, pay_name, email, phone);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(host)
                .addConverterFactory(GsonConverterFactory.create()).build();
        RequestLibrary requestLibrary = retrofit.create(RequestLibrary.class);
        Call<ParseKlaimRebate> callData = requestLibrary.klaimRebate(jsonSend);

        callData.enqueue(new Callback<ParseKlaimRebate>() {
            @Override
            public void onResponse(Call<ParseKlaimRebate> call, Response<ParseKlaimRebate> response) {
                loading.dismiss();
                if (response.isSuccessful()) {
                    ParseKlaimRebate response_body = response.body();
                    if (response_body.getError()) {
                        new ShowDialog().error(ClaimRebateActivity.this, response_body.getMessage()).setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {

                            }
                        });
                    } else {
                        setData(response_body);

                    }
                } else {
                    Log.e("Server Problem", "Server Responding but error callback : " + response.message());
                    new ShowDialog().error(ClaimRebateActivity.this, response.message());
                }
            }

            @Override
            public void onFailure(Call<ParseKlaimRebate> call, Throwable t) {
                loading.dismiss();
                Log.e("Network", "ParseCallMeBack" + t.getMessage());
                new ShowDialog().error(ClaimRebateActivity.this, "Tidak dapat terhubung, terjadi masalah jaringan.");
            }
        });
    }

    public void setData(ParseKlaimRebate data) {
        Log.i("nama", data.getData().getNama());
        Log.i("phone", data.getData().getPhone());
        Log.i("email", data.getData().getEmail());
        Log.i("pay_to", data.getData().getPay_to());
        Log.i("pay_name", data.getData().getPay_name());
        Log.i("pay_number", data.getData().getPay_number());

        new ShowDialog().success(this, "Klaim Rebate telah berhasil.")
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        Intent i = getBaseContext().getPackageManager()
                                .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }
                });
    }
}
