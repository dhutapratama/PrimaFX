package com.primafx.client.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.primafx.client.R;
import com.primafx.client.database.GData;
import com.primafx.client.dialog.ShowDialog;
import com.primafx.client.model.DatePickerFragment;
import com.primafx.client.model.TimePickerFragment;
import com.primafx.client.retrofit.ParseCallMeBack;
import com.primafx.client.retrofit.RequestLibrary;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CallMeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_me);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialization
        initUI();

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

    private void initUI() {
        Spinner spinnerBank = (Spinner) findViewById(R.id.spinnerSupport);
        ArrayAdapter<CharSequence> adapterBank = ArrayAdapter.createFromResource(this,
                R.array.listCallSupport, android.R.layout.simple_spinner_item);
        adapterBank.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBank.setAdapter(adapterBank);
        spinnerBank.setOnItemSelectedListener(this);

        Button btnPickDate = (Button) findViewById(R.id.btnPickDate);
        btnPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment dialog = new DatePickerFragment(mDateSetListener);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                dialog.show(ft, "DatePicker");

            }
        });

        Button btnPickHour = (Button) findViewById(R.id.btnPickHour);
        btnPickHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerFragment dialog = new TimePickerFragment(mTimeSetListener);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                dialog.show(ft, "TimePicker");
            }
        });


        TextView textContactUs = (TextView) findViewById(R.id.textContactUs);
        textContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CallMeActivity.this, ContactUsActivity.class);
                startActivity(intent);
            }
        });

        Button buttonCallMe = (Button) findViewById(R.id.buttonCallMe);
        buttonCallMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editNama = (EditText)findViewById(R.id.editNama);
                EditText editEmail = (EditText)findViewById(R.id.editEmail);
                EditText editPhone = (EditText)findViewById(R.id.editPhone);

                Spinner spinnerSupport = (Spinner)findViewById(R.id.spinnerSupport);

                TextView textDate = (TextView)findViewById(R.id.textDate);
                TextView textTime = (TextView)findViewById(R.id.textTime);

                retrofitCallMeBack(
                        editNama.getText().toString(),
                        editPhone.getText().toString(),
                        editEmail.getText().toString(),
                        spinnerSupport.getSelectedItem().toString(),
                        textDate.getText().toString(),
                        textTime.getText().toString()
                );
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void retrofitCallMeBack(String nama, String phone, String email, String support, String date, String time) {
        final Dialog loading = new ShowDialog().loading(this);
        loading.show();

        String host = GData.API_ADDRESS;
        ParseCallMeBack jsonSend = new ParseCallMeBack(
               nama,
               phone,
               email,
               support,
               date,
               time
        );
        Retrofit retrofit = new Retrofit.Builder().baseUrl(host)
                .addConverterFactory(GsonConverterFactory.create()).build();
        RequestLibrary requestLibrary = retrofit.create(RequestLibrary.class);
        Call<ParseCallMeBack> callData = requestLibrary.callMeBack(jsonSend);

        callData.enqueue(new Callback<ParseCallMeBack>() {
            @Override
            public void onResponse(Call<ParseCallMeBack> call, Response<ParseCallMeBack> response) {
                loading.dismiss();
                if (response.isSuccessful()) {
                    ParseCallMeBack response_body = response.body();
                    if (response_body.getError()) {
                        new ShowDialog().error(CallMeActivity.this, response_body.getMessage()).setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                            }
                        });
                    } else {
                        setData(response_body);
                        new ShowDialog().success(CallMeActivity.this, "Call Me Back telah berhasil dikirimkan. Support kami akan segera menghubungi anda sesuai dengan jadwal yang anda kirimkan.");
                    }
                } else {
                    Log.e("Server Problem", "Server Responding but error callback : " + response.message());
                    //new ShowDialog().error(TestingApiActivity.this, response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<ParseCallMeBack> call, Throwable t) {
                loading.dismiss();
                Log.e("Network", "ParseCallMeBack" + t.getMessage());
                new ShowDialog().error(CallMeActivity.this, "Tidak dapat terhubung, terjadi masalah jaringan.");
            }
        });
    }

    public void setData(ParseCallMeBack data) {
        Log.i("nama", data.getData().getNama());
        Log.i("phone", data.getData().getPhone());
        Log.i("email", data.getData().getEmail());
        Log.i("support", data.getData().getSupport());
        Log.i("time", data.getData().getTime());
        Log.i("date", data.getData().getDate());
    }

    TimePickerDialog.OnTimeSetListener mTimeSetListener =
            new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(android.widget.TimePicker view,
                                      int hourOfDay, int minute) {
                    Log.i("", "" + hourOfDay + ":" + minute);
                    String hour = "";
                    if (hourOfDay < 10) {
                        hour = "0"+Integer.toString(hourOfDay);
                    } else {
                        hour = Integer.toString(hourOfDay);
                    }
                    String menit = "";
                    if (minute < 10) {
                        menit = "0"+Integer.toString(minute);
                    } else {
                        menit = Integer.toString(minute);
                    }

                    TextView textTime = (TextView) findViewById(R.id.textTime);
                    textTime.setText(hour + ":" + menit);
                }
            };

    DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    TextView textDate = (TextView) findViewById(R.id.textDate);
                    textDate.setText(i + "-" + i1 + "-" + i2);
                }
            };
}
