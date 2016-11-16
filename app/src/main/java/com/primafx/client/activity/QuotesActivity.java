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

import com.primafx.client.R;
import com.primafx.client.database.GData;
import com.primafx.client.dialog.ShowDialog;
import com.primafx.client.retrofit.ParseDataQuotes;
import com.primafx.client.retrofit.ParseQuotes;
import com.primafx.client.retrofit.RequestLibrary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuotesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        retrofitQuotes();
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


    private void retrofitQuotes() {
        final Dialog loading = new ShowDialog().loading(this);
        loading.show();

        String host = GData.API_ADDRESS;

        ParseQuotes jsonSend = new ParseQuotes();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(host)
                .addConverterFactory(GsonConverterFactory.create()).build();
        RequestLibrary requestLibrary = retrofit.create(RequestLibrary.class);
        Call<ParseQuotes> callData = requestLibrary.getQuotes();

        callData.enqueue(new Callback<ParseQuotes>() {
            @Override
            public void onResponse(Call<ParseQuotes> call, Response<ParseQuotes> response) {
                loading.dismiss();
                if (response.isSuccessful()) {
                    ParseQuotes response_body = response.body();
                    if (response_body.getError()) {
                        new ShowDialog().success(QuotesActivity.this, response_body.getMessage()).setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                finish();
                            }
                        });
                    } else {
                        setData(response_body.getData());
                    }
                } else {
                    Log.e("Server Problem", "Server Responding but error callback : " + response.body());
                    //new ShowDialog().error(TestingApiActivity.this, response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<ParseQuotes> call, Throwable t) {
                loading.dismiss();
                Log.e("Network", "ParseWithdrawal" + t.getMessage());
                new ShowDialog().error(QuotesActivity.this, "Tidak dapat terhubung, terjadi masalah jaringan.");
            }
        });
    }

    public void setData(final List<ParseDataQuotes> data) {

        final List<HashMap<String, String>> aList = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            Log.i("id", data.get(i).getId());
            Log.i("symbol", data.get(i).getSymbol());
            Log.i("datetime", data.get(i).getDatetime());
            Log.i("open", data.get(i).getOpen());
            Log.i("ask", data.get(i).getAsk());
            Log.i("bid", data.get(i).getBid());
            Log.i("high", data.get(i).getHigh());
            Log.i("low", data.get(i).getLow());
            Log.i("ranges", data.get(i).getRanges());
            Log.i("company", data.get(i).getCompany());

            Log.i("Sep ", "-------------------------------------------");

            HashMap<String, String> hm = new HashMap<>();
            hm.put("symbol", data.get(i).getSymbol());
            hm.put("open", "Open : "+data.get(i).getOpen());
            hm.put("ask", data.get(i).getAsk());
            hm.put("bid", data.get(i).getBid());
            hm.put("high", "High :"+data.get(i).getHigh());
            hm.put("low", "Low : "+data.get(i).getLow());
            hm.put("ranges", "Ranges : "+data.get(i).getRanges());
            aList.add(hm);
        }

        String[] from = {"symbol", "open", "ask", "bid", "high", "low", "ranges"};
        int[] to = {R.id.symbol, R.id.open, R.id.ask, R.id.bid, R.id.high, R.id.low, R.id.ranges};
        SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList, R.layout.list_quotes, from, to)
        {
            @Override
            public View getView (final int position, View convertView, ViewGroup parent)
            {
                View v = super.getView(position, convertView, parent);
                /*
                ImageView imagePerson = (ImageView)v.findViewById(R.id.imagePerson);
                Picasso.with(MainManageActivity.this).load(transactions.get(position).getPicture())
                        .error(MainManageActivity.this.getResources().getDrawable(R.mipmap.no_image_square))
                        .into(imagePerson);
                        */
                return v;
            }
        };


        ListView listView = (ListView) findViewById(R.id.listQuotes);
        listView.setAdapter(adapter);
    }
}


