package com.primafx.client.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.primafx.client.R;
import com.primafx.client.database.GData;
import com.primafx.client.dialog.ShowDialog;
import com.primafx.client.retrofit.ParseCheckRebate;
import com.primafx.client.retrofit.RequestLibrary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CheckRebateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_rebate);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        retrofitCheckRebate(
                GData.CURRENT_ACCOUNT,
                GData.LOGIN_CODE,
                "201609");
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

    private void retrofitCheckRebate(String akun, String authKey, String periode) {
        final Dialog loading = new ShowDialog().loading(this);
        loading.show();

        String host = GData.API_ADDRESS;

        ParseCheckRebate jsonSend = new ParseCheckRebate(akun, authKey, periode);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(host)
                .addConverterFactory(GsonConverterFactory.create()).build();
        RequestLibrary requestLibrary = retrofit.create(RequestLibrary.class);
        Call<ParseCheckRebate> callData = requestLibrary.checkRebate(jsonSend);

        callData.enqueue(new Callback<ParseCheckRebate>() {
            @Override
            public void onResponse(Call<ParseCheckRebate> call, Response<ParseCheckRebate> response) {
                loading.dismiss();
                if (response.isSuccessful()) {
                    ParseCheckRebate response_body = response.body();
                    if (response_body.getError()) {
                        new ShowDialog().error(CheckRebateActivity.this, response_body.getMessage()).setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                finish();
                            }
                        });
                    } else {
                        setData(response_body);
                    }
                } else {
                    Log.e("Server Problem", "Server Responding but error callback : " + response.message());
                    new ShowDialog().error(CheckRebateActivity.this, response.message());
                }
            }

            @Override
            public void onFailure(Call<ParseCheckRebate> call, Throwable t) {
                loading.dismiss();
                Log.e("Network", "ParseCallMeBack" + t.getMessage());
                new ShowDialog().error(CheckRebateActivity.this, "Tidak dapat terhubung, terjadi masalah jaringan.");
            }
        });
    }

    public void setData(final ParseCheckRebate data) {
        final List<HashMap<String, String>> aList = new ArrayList<>();

        Log.i("periode", data.getData().getSummary().getPeriode());
        Log.i("credit", data.getData().getSummary().getCredit());
        Log.i("debit", data.getData().getSummary().getDebit());
        Log.i("sisa", data.getData().getSummary().getSisa());
        Log.i("Sep ", "-------------------------------------------");
        Log.i("Sep ", "-------------------------------------------");

        for (int i = 0; i < data.getData().getDetail().size(); i++) {
            Log.i("id", data.getData().getDetail().get(i).getId());
            Log.i("ticket", data.getData().getDetail().get(i).getTicket());
            Log.i("Date", data.getData().getDetail().get(i).getDate());
            Log.i("InputDateTime", data.getData().getDetail().get(i).getInputDateTime());
            Log.i("InstaDateTime", data.getData().getDetail().get(i).getInstaDateTime());
            Log.i("akun", data.getData().getDetail().get(i).getAkun());
            Log.i("profit", data.getData().getDetail().get(i).getProfit());
            Log.i("type", data.getData().getDetail().get(i).getType());
            Log.i("detail", data.getData().getDetail().get(i).getDetail());
            Log.i("info", data.getData().getDetail().get(i).getInfo());
            Log.i("Sep ", "-------------------------------------------");

            HashMap<String, String> hm = new HashMap<>();
            hm.put("id", data.getData().getDetail().get(i).getId());
            hm.put("ticket", data.getData().getDetail().get(i).getTicket());
            hm.put("Date", data.getData().getDetail().get(i).getDate());
            hm.put("InputDateTime", data.getData().getDetail().get(i).getInputDateTime());
            hm.put("InstaDateTime", data.getData().getDetail().get(i).getInstaDateTime());
            hm.put("akun", data.getData().getDetail().get(i).getAkun());
            hm.put("profit", data.getData().getDetail().get(i).getProfit());
            hm.put("type", data.getData().getDetail().get(i).getType());
            hm.put("detail", data.getData().getDetail().get(i).getDetail());
            hm.put("info", data.getData().getDetail().get(i).getInfo());
            aList.add(hm);
        }

        String[] from = { "Date", "ticket", "type", "profit",  "InputDateTime",  "detail", "info" };
        int[] to = { R.id.textDate, R.id.textTicket, R.id.textType, R.id.textProfit, R.id.textInputDate, R.id.textDetail, R.id.textInfo };
        SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList, R.layout.list_rebate, from, to){
            @Override
            public View getView (final int position, View convertView, ViewGroup parent)
            {
                View v = super.getView(position, convertView, parent);

                if (data.getData().getDetail().get(position).getInfo().equals("")){
                    TextView textInfo = (TextView)v.findViewById(R.id.textInfo);
                    textInfo.setVisibility(View.GONE);
                }

                return v;
            }
        };

        ListView listView = (ListView) findViewById(R.id.listRebate);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                new ShowDialog().success(CheckRebateActivity.this, Integer.toString(position));
            }
        });

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
