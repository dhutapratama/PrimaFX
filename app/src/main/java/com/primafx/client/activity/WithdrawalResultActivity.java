package com.primafx.client.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.primafx.client.R;
import com.primafx.client.database.GData;

public class WithdrawalResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawal_result);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        TextView textAccount = (TextView) findViewById(R.id.textAccount);
        TextView textName = (TextView) findViewById(R.id.textName);
        TextView textUsd = (TextView) findViewById(R.id.textUsd);

        TextView textRekening = (TextView) findViewById(R.id.textRekening);
        TextView textNama = (TextView) findViewById(R.id.textNama);
        TextView textBank = (TextView) findViewById(R.id.textFromTo);
        TextView textKurs = (TextView) findViewById(R.id.textKurs);

        TextView textTotalTransfer = (TextView) findViewById(R.id.textTotalTransfer);

        textAccount.setText(intent.getStringExtra("akun"));
        textName.setText(intent.getStringExtra("nama"));
        textUsd.setText("$" + intent.getStringExtra("usd"));

        textRekening.setText(intent.getStringExtra("pay_number"));
        textNama.setText(intent.getStringExtra("pay_name"));
        textBank.setText(intent.getStringExtra("pay_to"));
        textKurs.setText("Rp " + intent.getStringExtra("kurs"));
        textTotalTransfer.setText("Rp " + intent.getStringExtra("idr"));
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

        if (GData.LOGIN_CODE.equals(null)) {
            Intent intent = new Intent(this, SplashActivity.class);
            startActivity(intent);
        }
    }
}
