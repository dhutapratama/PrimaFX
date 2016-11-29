package com.primafx.client.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.primafx.client.R;
import com.primafx.client.database.GData;

public class TransferRebateResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_rebate_result);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        TextView textAccount = (TextView) findViewById(R.id.textAccount);
        TextView textUsd = (TextView) findViewById(R.id.textUsd);
        TextView textAccountTujuan = (TextView) findViewById(R.id.textBank);
        TextView textNama = (TextView) findViewById(R.id.textNama);

        textAccount.setText(intent.getStringExtra("akun"));
        textUsd.setText(intent.getStringExtra("usd"));
        textAccountTujuan.setText(intent.getStringExtra("pay_number"));
        textNama.setText(intent.getStringExtra("pay_name"));
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
}
