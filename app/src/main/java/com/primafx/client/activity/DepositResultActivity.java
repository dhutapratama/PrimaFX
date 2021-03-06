package com.primafx.client.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.primafx.client.R;
import com.primafx.client.database.GData;
import com.primafx.client.dialog.ShowDialog;
import com.primafx.client.retrofit.ParseDataDeposit;
import com.primafx.client.retrofit.ParseDeposit;
import com.primafx.client.retrofit.RequestLibrary;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DepositResultActivity extends AppCompatActivity {
    Button buttonOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit_result);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Intent intent = getIntent();

        TextView textAccount = (TextView) findViewById(R.id.textTypeOrder);
        TextView textUsd = (TextView) findViewById(R.id.textUsd);
        TextView textBank = (TextView) findViewById(R.id.textTglProses);
        TextView textSubtotal = (TextView) findViewById(R.id.textSubTotal);


        TextView textNormalRate = (TextView) findViewById(R.id.textNormalRate);
        TextView textSpecialRate = (TextView) findViewById(R.id.textSpecialRate);

        textAccount.setText("#" + intent.getStringExtra("akun"));
        textUsd.setText("$" + intent.getStringExtra("usd"));
        textBank.setText(intent.getStringExtra("pay_to"));
        textSubtotal.setText("Rp " + intent.getStringExtra("idr"));

        textNormalRate.setText("Rp " + intent.getStringExtra("idr_n"));
        textSpecialRate.setText("Rp " + intent.getStringExtra("idr_s"));
        buttonOrder = (Button)findViewById(R.id.buttonOrder);
        buttonOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retrofitDeposit(GData.CURRENT_ACCOUNT, GData.LOGIN_CODE, intent.getStringExtra("pay_to"), intent.getStringExtra("usd"), "");
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

    private void retrofitDeposit(String akun, String authKey, String pay_to, String usd, String idr) {
        final Dialog loading = new ShowDialog().loading(this);
        loading.show();

        String host = GData.API_ADDRESS;

        ParseDeposit jsonSend = new ParseDeposit(akun, authKey, pay_to, usd, idr, "false");
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
                loading.dismiss();
                Log.e("Network", "ParseDeposit" + t.getMessage());
                new ShowDialog().error(DepositResultActivity.this, "Tidak dapat terhubung, terjadi masalah jaringan.");
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
        Log.i("Kurs", data.getKurs());
        Log.i("IDR", data.getIdr());

        Log.i("Unik", data.getUnik());
        Log.i("Total", data.getTotal());
        Log.i("Status", data.getStatus());

        Log.i("Sep ", "-------------------------------------------");


        TextView textRekening = (TextView) findViewById(R.id.textRekening);
        TextView textNamaRekening = (TextView) findViewById(R.id.textNama);
        TextView textUnik = (TextView) findViewById(R.id.textUnik);
        TextView textTotalTransfer = (TextView) findViewById(R.id.textTotalTransfer);

        TextView textView6 = (TextView) findViewById(R.id.textView6);
        TextView textView7 = (TextView) findViewById(R.id.textView7);
        TextView textView18 = (TextView) findViewById(R.id.textView18);
        TextView textView26 = (TextView) findViewById(R.id.textView26);


        textRekening.setVisibility(View.VISIBLE);
        textNamaRekening.setVisibility(View.VISIBLE);
        textUnik.setVisibility(View.VISIBLE);
        textTotalTransfer.setVisibility(View.VISIBLE);
        textView6.setVisibility(View.VISIBLE);
        textView7.setVisibility(View.VISIBLE);
        textView18.setVisibility(View.VISIBLE);
        textView26.setVisibility(View.VISIBLE);
        buttonOrder.setVisibility(View.GONE);

        textRekening.setText(data.getPay_number());
        textNamaRekening.setText(data.getPay_name());
        textUnik.setText(data.getUnik());
        textTotalTransfer.setText("Rp "+data.getTotal());
        takeScreenshot();
    }

    private void takeScreenshot() {
        Date now = new Date();
        android.text.format.DateFormat.format("MM-dd_hhmmss", now);

        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/PFXDEPO_" + now + ".jpg";

            // create bitmap screen capture
            View v1 = getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Throwable e) {
            // Several error may come out with file handling or OOM
            e.printStackTrace();
        }
    }
}
