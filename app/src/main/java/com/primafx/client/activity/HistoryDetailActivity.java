package com.primafx.client.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.primafx.client.R;
import com.primafx.client.database.GData;
import com.primafx.client.dialog.ShowDialog;

public class HistoryDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        TextView textTypeOrder = (TextView)findViewById(R.id.textTypeOrder);
        TextView textDate = (TextView)findViewById(R.id.textDate);
        TextView textTglOrder = (TextView)findViewById(R.id.textTglOrder);
        TextView textTglTransfer = (TextView)findViewById(R.id.textTglTransfer);
        TextView textTglProses = (TextView)findViewById(R.id.textTglProses);
        TextView textTglAudit = (TextView)findViewById(R.id.textTglAudit);
        TextView textAkun = (TextView)findViewById(R.id.textAkun);
        TextView textUSD = (TextView)findViewById(R.id.textUSD);
        TextView textKurs = (TextView)findViewById(R.id.textKurs);
        TextView textTotal = (TextView)findViewById(R.id.textTotal);
        TextView textPayTo = (TextView)findViewById(R.id.textPayTo);
        TextView textPayNumber = (TextView)findViewById(R.id.textPayNumber);
        TextView textPayName = (TextView)findViewById(R.id.textPayName);
        TextView textStatus = (TextView)findViewById(R.id.textStatus);
        TextView textKeterangan = (TextView)findViewById(R.id.textKeterangan);

        textTypeOrder.setText(intent.getStringExtra("type_order"));
        textDate.setText(intent.getStringExtra("date"));
        textTglOrder.setText(intent.getStringExtra("tgl_order"));
        textTglTransfer.setText(intent.getStringExtra("tgl_transfer"));
        textTglProses.setText(intent.getStringExtra("tgl_proses"));
        textTglAudit.setText(intent.getStringExtra("tgl_audit"));
        textAkun.setText(intent.getStringExtra("akun"));
        textUSD.setText(intent.getStringExtra("usd"));
        textKurs.setText(intent.getStringExtra("kurs"));
        textTotal.setText(intent.getStringExtra("total"));
        textPayTo.setText(intent.getStringExtra("pay_to"));
        textPayNumber.setText(intent.getStringExtra("pay_number"));
        textPayName.setText(intent.getStringExtra("pay_name"));
        textStatus.setText(intent.getStringExtra("status"));
        textKeterangan.setText(intent.getStringExtra("keterangan"));
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
