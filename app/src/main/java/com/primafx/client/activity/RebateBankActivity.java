package com.primafx.client.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.primafx.client.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

public class RebateBankActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Integer currency = 13500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rebate_bank);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Initialization
        initUI();
    }

    private void initUI() {

        Spinner spinnerBank = (Spinner) findViewById(R.id.spinnerBank);
        ArrayAdapter<CharSequence> adapterBank = ArrayAdapter.createFromResource(this,
                R.array.listBank, android.R.layout.simple_spinner_item);
        adapterBank.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBank.setAdapter(adapterBank);
        spinnerBank.setOnItemSelectedListener(this);

        final TextView textDollarCurr = (TextView)findViewById(R.id.textDollarCurr);
        final TextView textRupiahCurr = (TextView)findViewById(R.id.textRupiahCurr);

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

                Integer converter = Integer.parseInt(valDollar);
                Integer rupiah = converter * currency;

                DecimalFormat decimalFormat = (DecimalFormat) DecimalFormat.getCurrencyInstance();
                decimalFormat.setGroupingUsed(true);

                DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
                decimalFormatSymbols.setCurrencySymbol("Rp ");
                decimalFormatSymbols.setGroupingSeparator('.');
                decimalFormatSymbols.setMonetaryDecimalSeparator(',');
                decimalFormat.setDecimalFormatSymbols(decimalFormatSymbols);

                String formatedMoney = decimalFormat.format(rupiah);

                String formatedDollar = "$ " + valDollar + ".00";
                String formatedRupiah = formatedMoney.replace(",00", "");

                textDollarCurr.setText(formatedDollar);
                textRupiahCurr.setText(formatedRupiah);
                return;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
