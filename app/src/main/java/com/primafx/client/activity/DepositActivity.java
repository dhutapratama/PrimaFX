package com.primafx.client.activity;

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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.primafx.client.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;

public class DepositActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialization
        initUI();
    }

    private void initUI() {
        ArrayList<String> valueAccount = new ArrayList<>();
        valueAccount.add("#12312313 (GPBUSD)");
        valueAccount.add("#12312313 (GPBUSD)");
        valueAccount.add("#12312313 (GPBUSD)");
        valueAccount.add("#12312313 (GPBUSD)");
        valueAccount.add("#12312313 (GPBUSD)");

        Spinner spinnerAccount = (Spinner) findViewById(R.id.spinnerAccount);
        ArrayAdapter<String> adapterAccount = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, valueAccount);
        adapterAccount.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAccount.setAdapter(adapterAccount);
        spinnerAccount.setOnItemSelectedListener(this);

        Spinner spinnerBank = (Spinner) findViewById(R.id.spinnerBank);
        ArrayAdapter<CharSequence> adapterBank = ArrayAdapter.createFromResource(this,
                R.array.listBank, android.R.layout.simple_spinner_item);
        adapterBank.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBank.setAdapter(adapterBank);
        spinnerBank.setOnItemSelectedListener(this);
        final EditText editTotal = (EditText)findViewById(R.id.editTotal);

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
                return true;

            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

}
