package com.primafx.client.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.primafx.client.R;
import com.primafx.client.dialog.ShowDialog;
import com.primafx.client.retrofit.ParseCheckRebateInquiry;
import com.primafx.client.retrofit.ParseDataRebateInquiry;
import com.primafx.client.retrofit.RequestLibrary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainManageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private FirebaseAnalytics mFirebaseAnalytics;
    private String[] accounts;
    private String[] transactionHistory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_manage);

        // Inisialisasi UI
        uiInit();

        // Variable Initialization
        this.accounts = new String[] {"#0981231 (GPBUSD)", "#0981231 (GPBUSD)","#0981231 (GPBUSD)","#0981231 (GPBUSD)"};
        this.transactionHistory = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "7", "2", "3", "4", "5", "6", "7", "8", "9", "0", "7"};

        // Method Call
        setTransactionHistory();
        setMenuCounter(R.id.nav_notification, 5);

        /* Error Reporting */
        // Firebase Analytics
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        analyticsPage();

        // Manual error reporting manual, send to server
        // FirebaseCrash.report(new Exception("Error Name"));
    }

    // UI Init
    private void uiInit() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        LinearLayout menuButton = (LinearLayout)findViewById(R.id.layout_click_sidebar);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.openDrawer(GravityCompat.START);
            }
        });

        LinearLayout deposit = (LinearLayout)findViewById(R.id.LLDeposit);
        deposit.setOnClickListener(this);

        LinearLayout withdrawal = (LinearLayout)findViewById(R.id.LLWithdrawal);
        withdrawal.setOnClickListener(this);

        LinearLayout rebate = (LinearLayout)findViewById(R.id.LLRebate);
        rebate.setOnClickListener(this);

        TextView textAccName = (TextView)findViewById(R.id.textAccName);
        textAccName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch_account();
            }
        });
    }

    // Account Switcher
    private void switch_account() {
        Dialog d = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT)
                .setTitle("Pilih Account")
                .setNegativeButton("Batal", null)
                .setPositiveButton("Tambah Account", null)
                .setItems(this.accounts, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dlg, int position)
                    {

                    }
                })
                .create();
        d.show();
    }

    // Method Menu Counter
    private void setMenuCounter(@IdRes int itemId, int count) {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        TextView view = (TextView) navigationView.getMenu().findItem(itemId).getActionView();
        view.setText(count > 0 ? String.valueOf(count) : null);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_accounts) {
            Intent intent = new Intent(this, AccountsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_notification) {
            Intent intent = new Intent(this, NotificationActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_call_me) {
            Intent intent = new Intent(this, CallMeActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_feedback) {
            Intent intent = new Intent(this, FeedbackActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_about) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void analyticsPage() {
        // [START image_view_event]
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "main_activity");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "MainManageActivity");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "activity");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        // [END image_view_event]
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
    public void onClick(View v) {
        if (v.getId() == R.id.LLDeposit) {
            Intent intent = new Intent(this, DepositActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.LLWithdrawal) {
            Intent intent = new Intent(this, WithdrawalActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.LLRebate) {
            retrofitRebateInquiry("3652724", "passwordku");


        }
    }

    private void retrofitRebateInquiry(String akun, String authKey) {
        final Dialog loading = new ShowDialog().loading(this);
        loading.show();

        String host = "http://api.primafx.com/";

        ParseCheckRebateInquiry jsonSend = new ParseCheckRebateInquiry(akun, authKey);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(host)
                .addConverterFactory(GsonConverterFactory.create()).build();
        RequestLibrary requestLibrary = retrofit.create(RequestLibrary.class);
        Call<ParseCheckRebateInquiry> callData = requestLibrary.rebateInquiry(jsonSend);

        callData.enqueue(new Callback<ParseCheckRebateInquiry>() {
            @Override
            public void onResponse(Call<ParseCheckRebateInquiry> call, Response<ParseCheckRebateInquiry> response) {
                loading.hide();
                if (response.isSuccessful()) {
                    ParseCheckRebateInquiry response_body = response.body();
                    if (response_body.getError()) {
                        finish();
                    } else {
                        new ShowDialog().error(MainManageActivity.this, "Message : " + response_body.getMessage());
                        setRebateData(response_body.getData());
                    }
                } else {
                    Log.e("Server Problem", "Server Responding but error callback : " + response.body().toString());
                    new ShowDialog().error(MainManageActivity.this, response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<ParseCheckRebateInquiry> call, Throwable t) {
                loading.hide();
                Log.e("Network", "ParseCheckRebateInquiry" + t.getMessage());
                new ShowDialog().error(MainManageActivity.this, "Tidak dapat terhubung, terjadi masalah jaringan.");
            }
        });
    }

    public void setRebateData(final List<ParseDataRebateInquiry> data) {

        for (int i = 0; i < data.size(); i++) {
            Log.i("Periode", data.get(i).getPeriode());
            Log.i("Credit", data.get(i).getCredit());
            Log.i("Debit", data.get(i).getDebit());
            Log.i("Sisa", data.get(i).getSisa());

            Log.i("Sep ", "-------------------------------------------");
        }


        Dialog rebate = ShowDialog.rebate(this, "$" + data.get(0).getSisa());

        Button btnBank = (Button) rebate.findViewById(R.id.buttonToBank);
        btnBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainManageActivity.this, RebateBankActivity.class);
                startActivity(intent);
            }
        });

        Button btnAccount = (Button) rebate.findViewById(R.id.buttonToAccount);
        btnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainManageActivity.this, RebateAccountActivity.class);
                startActivity(intent);
            }
        });

    }
}
