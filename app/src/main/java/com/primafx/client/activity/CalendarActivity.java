package com.primafx.client.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.primafx.client.R;
import com.primafx.client.database.GData;
import com.primafx.client.dialog.ShowDialog;
import com.primafx.client.retrofit.ParseCalendar;
import com.primafx.client.retrofit.RequestLibrary;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        retrofitCalendar();
    }

    private void retrofitCalendar() {
        final Dialog loading = new ShowDialog().loading(this);
        loading.show();

        String host = GData.API_ADDRESS;

        Retrofit retrofit = new Retrofit.Builder().baseUrl(host)
                .addConverterFactory(GsonConverterFactory.create()).build();
        RequestLibrary requestLibrary = retrofit.create(RequestLibrary.class);
        Call<ParseCalendar> callData = requestLibrary.getCalendar();

        callData.enqueue(new Callback<ParseCalendar>() {
            @Override
            public void onResponse(Call<ParseCalendar> call, Response<ParseCalendar> response) {
                loading.dismiss();
                if (response.isSuccessful()) {
                    ParseCalendar response_body = response.body();
                    if (response_body.getError()) {
                        new ShowDialog().error(CalendarActivity.this, response_body.getMessage()).setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                finish();
                            }
                        });
                    } else {
                        setData(response_body);
                    }
                } else {
                    new ShowDialog().error(CalendarActivity.this, response.message());
                }
            }

            @Override
            public void onFailure(Call<ParseCalendar> call, Throwable t) {
                loading.dismiss();
                Log.e("Network", "ParseAddAccount" + t.getMessage());
                new ShowDialog().error(CalendarActivity.this, "Tidak dapat terhubung, terjadi masalah jaringan.");
                finish();
            }
        });
    }

    public void setData(final ParseCalendar data) {
        final List<HashMap<String, String>> aList = new ArrayList<>();

        for (int i = 0; i < data.getData().size(); i++) {
            Log.i("id", data.getData().get(i).getIdNews());
            Log.i("symbol", data.getData().get(i).getSymbol());
            Log.i("time", data.getData().get(i).getHourMinute());
            Log.i("date", data.getData().get(i).getDate());
            Log.i("title", data.getData().get(i).getTitle());
            Log.i("actual", data.getData().get(i).getActual());
            Log.i("forecast", data.getData().get(i).getForecast());
            Log.i("previous", data.getData().get(i).getPrevious());

            Log.i("Sep ", "-------------------------------------------");

            HashMap<String, String> hm = new HashMap<>();
            hm.put("id", data.getData().get(i).getIdNews());
            hm.put("symbol", data.getData().get(i).getSymbol());
            hm.put("date", data.getData().get(i).getDate());
            hm.put("time", data.getData().get(i).getHourMinute());
            hm.put("title", data.getData().get(i).getTitle());
            hm.put("actual", "Act : " + data.getData().get(i).getActual());
            hm.put("forecast", "Fore : " + data.getData().get(i).getForecast());
            hm.put("previous", "Prev : " + data.getData().get(i).getPrevious());
            aList.add(hm);
        }

        String[] from = { "symbol", "date", "time", "title", "actual", "forecast", "previous" };
        int[] to = { R.id.textSymbol, R.id.textDate, R.id.textTime, R.id.textTitle, R.id.textActual, R.id.textForecast, R.id.textPrevious };
        SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList, R.layout.list_calendar, from, to)
        {
            @Override
            public View getView (final int position, View convertView, ViewGroup parent)
            {
                View v = super.getView(position, convertView, parent);

                data.getData().get(position).getPrevious();
                LinearLayout importance = (LinearLayout) v.findViewById(R.id.importance);
                if (data.getData().get(position).getImportance().equals("High")) {
                    importance.setBackgroundColor(getResources().getColor(R.color.colorHigh));
                } else if (data.getData().get(position).getImportance().equals("Medium")) {
                    importance.setBackgroundColor(getResources().getColor(R.color.colorMedium));
                } else if (data.getData().get(position).getImportance().equals("Low")) {
                    importance.setBackgroundColor(getResources().getColor(R.color.colorLow));
                }
                return v;
            }
        };

        ListView listView = (ListView) findViewById(R.id.listCalendar);
        listView.setAdapter(adapter);
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
