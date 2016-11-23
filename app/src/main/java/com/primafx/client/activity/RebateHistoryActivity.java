package com.primafx.client.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.primafx.client.R;
import com.primafx.client.database.GData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RebateHistoryActivity extends AppCompatActivity {
    private String[] transactionHistory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rebate_history);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.transactionHistory = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "7", "2", "3", "4", "5", "6", "7", "8", "9", "0", "7"};

        setTransactionHistory();
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

    public void setTransactionHistory() {
        final List<HashMap<String, String>> aList = new ArrayList<>();

        for (int i = 0; i < this.transactionHistory.length; i++) {
            HashMap<String, String> hm = new HashMap<>();
            hm.put("data", this.transactionHistory[i]);
            aList.add(hm);
        }

        String[] from = {};
        int[] to = {};
        SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList, R.layout.list_transaction_history, from, to)
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

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        /*
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainManageActivity.this, TransactionDetail.class);
                intent.putExtra("transaction_refference", transactions.get(position).getTransaction_refference());
                startActivity(intent);
            }
        });
        */
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
