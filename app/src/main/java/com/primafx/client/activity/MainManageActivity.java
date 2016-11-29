package com.primafx.client.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.primafx.client.R;
import com.primafx.client.database.DatabaseSQL;
import com.primafx.client.database.GData;
import com.primafx.client.dialog.ShowDialog;
import com.primafx.client.model.DatePickerFragment;
import com.primafx.client.retrofit.ParseAccountDetail;
import com.primafx.client.retrofit.ParseCheckRebateInquiry;
import com.primafx.client.retrofit.ParseDataRebateInquiry;
import com.primafx.client.retrofit.ParseHistory;
import com.primafx.client.retrofit.ParseHistoryFind;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_AUTO);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_manage);

        // Inisialisasi UI
        uiInit();

        // Variable Initialization
        List<String> my_accounts = DatabaseSQL.getAccountData(this);
        this.accounts = my_accounts.toArray(new String[my_accounts.size()]);


       // setMenuCounter(R.id.nav_notification, 5);

        /* Error Reporting */
        // Firebase Analytics
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        analyticsPage();

        // Manual error reporting manual, send to server
        // FirebaseCrash.report(new Exception("Error Name"));
    }

    // UI Init
    private void uiInit() {
        LinearLayout deposit = (LinearLayout)findViewById(R.id.LLDeposit);
        deposit.setOnClickListener(this);

        LinearLayout withdrawal = (LinearLayout)findViewById(R.id.LLWithdrawal);
        withdrawal.setOnClickListener(this);

        LinearLayout rebateTransfer = (LinearLayout)findViewById(R.id.LLTransferRebate);
        rebateTransfer.setOnClickListener(this);

        LinearLayout rebateWithdrawal = (LinearLayout)findViewById(R.id.LLWithdrawalRebate);
        rebateWithdrawal.setOnClickListener(this);

        LinearLayout checkRebate = (LinearLayout)findViewById(R.id.LLCheckRebate);
        checkRebate.setOnClickListener(this);

        TextView textAccName = (TextView)findViewById(R.id.textAccName);
        textAccName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch_account();
            }
        });

        TextView textAccNumber = (TextView)findViewById(R.id.textAccNumber);
        textAccNumber.setText("#"+ GData.CURRENT_ACCOUNT);

        final String[] colors = {"#EF233C", "#EF233C", "#EF233C"};
        AHBottomNavigation bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);

        AHBottomNavigationItem item1 = new AHBottomNavigationItem("Keuangan", R.drawable.shield, Color.parseColor(colors[0]));
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("Profil", R.drawable.profile, Color.parseColor(colors[1]));
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("Histori", R.drawable.history, Color.parseColor(colors[2]));

        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);

        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#FEFEFE"));

        bottomNavigation.setAccentColor(Color.parseColor("#F63D2B"));
        bottomNavigation.setInactiveColor(Color.parseColor("#747474"));

        //  Enables Reveal effect
        bottomNavigation.setColored(true);

        bottomNavigation.setCurrentItem(0);

        final ViewFlipper vf = (ViewFlipper) findViewById( R.id.viewFlipper );
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, boolean wasSelected) {
                vf.setDisplayedChild(position);
            }
        });

        final Spinner spinnerType = (Spinner)findViewById(R.id.spinnerType);
        final Spinner spinnerData = (Spinner)findViewById(R.id.spinnerData);

        final ArrayList<String> find_type =new ArrayList<String>(){};
        find_type.add("Semua");
        find_type.add("Tipe");
        find_type.add("Tanggal");
        find_type.add("Status");

        final ArrayAdapter<String> listType= new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, find_type);
        listType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(listType);

        final ArrayList<String> find_data_type =new ArrayList<String>(){};
        find_data_type.add("deposit");
        find_data_type.add("withdrawal");
        find_data_type.add("transfer_rebate");

        final ArrayList<String> find_data_status =new ArrayList<String>(){};
        find_data_status.add("input");
        find_data_status.add("proses");
        find_data_status.add("pending");
        find_data_status.add("sukses");
        find_data_status.add("gagal");

        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spinnerType.getSelectedItem().toString().equals("Semua")) {
                    spinnerData.setVisibility(View.GONE);
                    retrofitOrderHistory(GData.CURRENT_ACCOUNT, GData.LOGIN_CODE);
                } else if (spinnerType.getSelectedItem().toString().equals("Tipe")) {
                    spinnerData.setVisibility(View.VISIBLE);
                    ArrayAdapter < String > listDataType = new ArrayAdapter<>(MainManageActivity.this, android.R.layout.simple_spinner_item, find_data_type);
                    listDataType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerData.setAdapter(listDataType);
                } else if (spinnerType.getSelectedItem().toString().equals("Tanggal")) {
                    spinnerData.setVisibility(View.GONE);
                    DatePickerFragment dialog = new DatePickerFragment();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    dialog.show(ft, "DatePicker");
                    dialog.setCallBack(mDateSetListener);
                } else if (spinnerType.getSelectedItem().toString().equals("Status")) {
                    spinnerData.setVisibility(View.VISIBLE);
                    ArrayAdapter < String > listDataStatus = new ArrayAdapter<>(MainManageActivity.this, android.R.layout.simple_spinner_item, find_data_status);
                    listDataStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerData.setAdapter(listDataStatus);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        spinnerData.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spinnerType.getSelectedItem().toString().equals("Tipe")) {
                    retrofitOrderHistoryFind(GData.CURRENT_ACCOUNT, GData.LOGIN_CODE, "type_order", spinnerData.getSelectedItem().toString());
                } else {
                    retrofitOrderHistoryFind(GData.CURRENT_ACCOUNT, GData.LOGIN_CODE, "status", spinnerData.getSelectedItem().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        retrofitDetailAccount(
                GData.CURRENT_ACCOUNT,
                GData.LOGIN_CODE);
    }

    // Account Switcher
    private void switch_account() {
        Dialog d = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT)
                .setTitle("Pilih Account")
                .setNegativeButton("Batal", null)
                .setPositiveButton("Tambah Account", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainManageActivity.this, AddAccountActivity.class);
                        startActivity(intent);
                    }
                })
                .setItems(this.accounts, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dlg, int position)
                    {
                        GData.CURRENT_ACCOUNT = accounts[position];

                        Intent i = new Intent(MainManageActivity.this, MainManageActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                    }
                })
                .create();
        d.show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
            Intent i = new Intent(MainManageActivity.this, MainAppActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
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
        } else if (id == R.id.Logout) {
            DatabaseSQL.Logout(this);
            DatabaseSQL.removeAllAccount(this);

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.LLDeposit) {
            Intent intent = new Intent(this, DepositActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.LLWithdrawal) {
            Intent intent = new Intent(this, WithdrawalActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.LLWithdrawalRebate) {
            retrofitRebateInquiry(GData.CURRENT_ACCOUNT, GData.LOGIN_CODE, "withdrawal");
        } else if (v.getId() == R.id.LLTransferRebate) {
            retrofitRebateInquiry(GData.CURRENT_ACCOUNT, GData.LOGIN_CODE, "transfer");
        } else if (v.getId() == R.id.LLCheckRebate) {
            Intent intent = new Intent(this, CheckRebateActivity.class);
            startActivity(intent);
        }
    }

    private void retrofitRebateInquiry(String akun, String authKey, final String type) {
        final Dialog loading = new ShowDialog().loading(this);
        loading.show();

        String host = GData.API_ADDRESS;

        ParseCheckRebateInquiry jsonSend = new ParseCheckRebateInquiry(akun, authKey);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(host)
                .addConverterFactory(GsonConverterFactory.create()).build();
        RequestLibrary requestLibrary = retrofit.create(RequestLibrary.class);
        Call<ParseCheckRebateInquiry> callData = requestLibrary.rebateInquiry(jsonSend);

        callData.enqueue(new Callback<ParseCheckRebateInquiry>() {
            @Override
            public void onResponse(Call<ParseCheckRebateInquiry> call, Response<ParseCheckRebateInquiry> response) {
                loading.dismiss();
                if (response.isSuccessful()) {
                    ParseCheckRebateInquiry response_body = response.body();
                    if (response_body.getError()) {
                        finish();
                    } else {
                        setRebateData(response_body.getData(), type);
                    }
                } else {
                    Log.e("Server Problem", "Server Responding but error callback : " + response.body().toString());
                    new ShowDialog().error(MainManageActivity.this, response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<ParseCheckRebateInquiry> call, Throwable t) {
                loading.dismiss();
                Log.e("Network", "ParseCheckRebateInquiry" + t.getMessage());
                new ShowDialog().error(MainManageActivity.this, "Tidak dapat terhubung, terjadi masalah jaringan.");
            }
        });
    }

    public void setRebateData(final List<ParseDataRebateInquiry> data, String type) {

        for (int i = 0; i < data.size(); i++) {
            Log.i("Periode", data.get(i).getPeriode());
            Log.i("Credit", data.get(i).getCredit());
            Log.i("Debit", data.get(i).getDebit());
            Log.i("Sisa", data.get(i).getSisa());

            Log.i("Sep ", "-------------------------------------------");
        }

        if (type.equals("withdrawal")) {
            Dialog rebate = ShowDialog.rebateWithdrawal(this, "$" + data.get(0).getSisa());

            Button btnBank = (Button) rebate.findViewById(R.id.buttonToBank);
            btnBank.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainManageActivity.this, RebateBankActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            Dialog rebate = ShowDialog.rebateTransfer(this, "$" + data.get(0).getSisa());
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

    private void retrofitOrderHistory(String akun, String login_hash) {
        final Dialog loading = new ShowDialog().loading(this);
        loading.show();

        String host = GData.API_ADDRESS;

        ParseHistory jsonSend = new ParseHistory(akun, login_hash);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(host)
                .addConverterFactory(GsonConverterFactory.create()).build();
        RequestLibrary requestLibrary = retrofit.create(RequestLibrary.class);
        Call<ParseHistory> callData = requestLibrary.history(jsonSend);

        callData.enqueue(new Callback<ParseHistory>() {
            @Override
            public void onResponse(Call<ParseHistory> call, Response<ParseHistory> response) {
                loading.dismiss();
                if (response.isSuccessful()) {
                    ParseHistory response_body = response.body();
                    if (response_body.getError()) {
                        new ShowDialog().error(MainManageActivity.this, response_body.getMessage()).setOnDismissListener(new DialogInterface.OnDismissListener() {
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
                    new ShowDialog().error(MainManageActivity.this, response.message());
                }
            }

            @Override
            public void onFailure(Call<ParseHistory> call, Throwable t) {
                loading.dismiss();
                Log.e("Network", "ParseHistory" + t.getMessage());
                new ShowDialog().error(MainManageActivity.this, "Tidak dapat terhubung, terjadi masalah jaringan.");
            }
        });
    }

    public void setData(ParseHistory data) {
        final List<HashMap<String, String>> aList = new ArrayList<>();
        for (int i = 0; i < data.getData().size(); i++) {
            Log.i("listCode", data.getData().get(i).getListCode());
            Log.i("type_order", data.getData().get(i).getType_order());
            Log.i("date", data.getData().get(i).getDate());
            Log.i("tgl_order", data.getData().get(i).getTgl_order());
            Log.i("tgl_transfer", data.getData().get(i).getTgl_transfer());
            Log.i("tgl_proses", data.getData().get(i).getTgl_proses());
            Log.i("tgl_audit", data.getData().get(i).getTgl_audit());
            Log.i("akun", data.getData().get(i).getAkun());
            Log.i("usd", data.getData().get(i).getUsd());
            Log.i("kurs", data.getData().get(i).getKurs());
            Log.i("total", data.getData().get(i).getTotal());
            Log.i("pay_to", data.getData().get(i).getPay_to());
            Log.i("pay_number", data.getData().get(i).getPay_number());
            Log.i("pay_name", data.getData().get(i).getPay_name());
            Log.i("status", data.getData().get(i).getStatus());

            Log.i("Sep ", "-------------------------------------------");

            HashMap<String, String> hm = new HashMap<>();
            hm.put("listCode", data.getData().get(i).getListCode());
            hm.put("type_order", data.getData().get(i).getType_order());
            hm.put("date", data.getData().get(i).getDate());
            hm.put("tgl_order", data.getData().get(i).getTgl_order());
            hm.put("tgl_transfer", data.getData().get(i).getTgl_transfer());
            hm.put("tgl_proses", data.getData().get(i).getTgl_proses());
            hm.put("tgl_audit", data.getData().get(i).getTgl_audit());
            hm.put("akun", data.getData().get(i).getAkun());
            hm.put("usd", "/ "+data.getData().get(i).getUsd()+" USD");
            hm.put("kurs", data.getData().get(i).getKurs());
            hm.put("total", "Rp " + data.getData().get(i).getTotal());
            hm.put("pay_to", data.getData().get(i).getPay_to());
            hm.put("pay_number", data.getData().get(i).getPay_number());
            hm.put("pay_name", data.getData().get(i).getPay_name());
            hm.put("status", data.getData().get(i).getStatus());
            hm.put("result", data.getData().get(i).getResult());
            aList.add(hm);
        }

        String[] from = { "tgl_order", "pay_to", "type_order", "status",  "total",  "usd" };
        int[] to = { R.id.textPassword, R.id.textRekening, R.id.textOrderType, R.id.textStatus, R.id.textIDR, R.id.textUSD };
        SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList, R.layout.list_transaction_history, from, to);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                new ShowDialog().success(MainManageActivity.this, Integer.toString(position));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (GData.LOGIN_CODE== null) {
            Intent intent = new Intent(this, SplashActivity.class);
            startActivity(intent);
        }
    }

    DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    String month = "";
                    if (i1 < 10) {
                        month = "0" + Integer.toString(i1+1);
                    } else {
                        month = Integer.toString(i1+1);
                    }

                    String date = "";
                    if (i2 < 10) {
                        date = "0" + Integer.toString(i2);
                    } else {
                        date = Integer.toString(i2);
                    }

                    Log.i("DATE", i + "-" + month + "-" + date);

                    retrofitOrderHistoryFind(GData.CURRENT_ACCOUNT, GData.LOGIN_CODE, "date", i + "-" + month + "-" + date);
                }
            };

    private void retrofitOrderHistoryFind(String akun, String login_hash, String find_type, String find_data) {
        final Dialog loading = new ShowDialog().loading(this);
        loading.show();

        String host = GData.API_ADDRESS;

        ParseHistoryFind jsonSend = new ParseHistoryFind(akun, login_hash, find_type, find_data);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(host)
                .addConverterFactory(GsonConverterFactory.create()).build();
        RequestLibrary requestLibrary = retrofit.create(RequestLibrary.class);
        Call<ParseHistoryFind> callData = requestLibrary.findHistory(jsonSend);

        callData.enqueue(new Callback<ParseHistoryFind>() {
            @Override
            public void onResponse(Call<ParseHistoryFind> call, Response<ParseHistoryFind> response) {
                loading.dismiss();
                if (response.isSuccessful()) {
                    ParseHistoryFind response_body = response.body();
                    if (response_body.getError()) {
                        new ShowDialog().error(MainManageActivity.this, response_body.getMessage());
                    } else {
                        setData(response_body);
                    }
                } else {
                    Log.e("Server Problem", "Server Responding but error callback : " + response.message());
                    new ShowDialog().error(MainManageActivity.this, response.message());
                }
            }

            @Override
            public void onFailure(Call<ParseHistoryFind> call, Throwable t) {
                loading.dismiss();
                Log.e("Network", "ParseHistory" + t.getMessage());
                new ShowDialog().error(MainManageActivity.this, "Tidak dapat terhubung, terjadi masalah jaringan.");
            }
        });
    }

    public void setData(ParseHistoryFind data) {
        final List<HashMap<String, String>> aList = new ArrayList<>();
        for (int i = 0; i < data.getData().size(); i++) {
            Log.i("listCode", data.getData().get(i).getListCode());
            Log.i("type_order", data.getData().get(i).getType_order());
            Log.i("date", data.getData().get(i).getDate());
            Log.i("tgl_order", data.getData().get(i).getTgl_order());
            Log.i("tgl_transfer", data.getData().get(i).getTgl_transfer());
            Log.i("tgl_proses", data.getData().get(i).getTgl_proses());
            Log.i("tgl_audit", data.getData().get(i).getTgl_audit());
            Log.i("akun", data.getData().get(i).getAkun());
            Log.i("usd", data.getData().get(i).getUsd());
            Log.i("kurs", data.getData().get(i).getKurs());
            Log.i("total", data.getData().get(i).getTotal());
            Log.i("pay_to", data.getData().get(i).getPay_to());
            Log.i("pay_number", data.getData().get(i).getPay_number());
            Log.i("pay_name", data.getData().get(i).getPay_name());
            Log.i("status", data.getData().get(i).getStatus());

            Log.i("Sep ", "-------------------------------------------");

            HashMap<String, String> hm = new HashMap<>();
            hm.put("listCode", data.getData().get(i).getListCode());
            hm.put("type_order", data.getData().get(i).getType_order());
            hm.put("date", data.getData().get(i).getDate());
            hm.put("tgl_order", data.getData().get(i).getTgl_order());
            hm.put("tgl_transfer", data.getData().get(i).getTgl_transfer());
            hm.put("tgl_proses", data.getData().get(i).getTgl_proses());
            hm.put("tgl_audit", data.getData().get(i).getTgl_audit());
            hm.put("akun", data.getData().get(i).getAkun());
            hm.put("usd", "/ "+data.getData().get(i).getUsd()+" USD");
            hm.put("kurs", data.getData().get(i).getKurs());
            hm.put("total", "Rp " + data.getData().get(i).getTotal());
            hm.put("pay_to", data.getData().get(i).getPay_to());
            hm.put("pay_number", data.getData().get(i).getPay_number());
            hm.put("pay_name", data.getData().get(i).getPay_name());
            hm.put("status", data.getData().get(i).getStatus());
            hm.put("result", data.getData().get(i).getResult());
            aList.add(hm);
        }

        String[] from = { "tgl_order", "pay_to", "type_order", "status",  "total",  "usd" };
        int[] to = { R.id.textPassword, R.id.textRekening, R.id.textOrderType, R.id.textStatus, R.id.textIDR, R.id.textUSD };
        SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList, R.layout.list_transaction_history, from, to);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                new ShowDialog().success(MainManageActivity.this, Integer.toString(position));
            }
        });
    }

    private void retrofitDetailAccount(String akun, String login_hash ) {
        final Dialog loading = new ShowDialog().loading(this);
        loading.show();

        String host = GData.API_ADDRESS;
        ParseAccountDetail jsonSend = new ParseAccountDetail(akun, login_hash);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(host)
                .addConverterFactory(GsonConverterFactory.create()).build();
        RequestLibrary requestLibrary = retrofit.create(RequestLibrary.class);
        Call<ParseAccountDetail> callData = requestLibrary.accountDetail(jsonSend);

        callData.enqueue(new Callback<ParseAccountDetail>() {
            @Override
            public void onResponse(Call<ParseAccountDetail> call, Response<ParseAccountDetail> response) {
                loading.dismiss();
                if (response.isSuccessful()) {
                    ParseAccountDetail response_body = response.body();
                    if (response_body.getError()) {
                        new ShowDialog().error(MainManageActivity.this, response_body.getMessage()).setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                finish();
                            }
                        });
                    } else {
                        setDataAccount(response_body);
                    }
                } else {
                    Log.e("Server Problem", "Server Responding but error callback : " + response.message());
                    //new ShowDialog().error(TestingApiActivity.this, response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<ParseAccountDetail> call, Throwable t) {
                loading.dismiss();
                Log.e("Network", "ParseCallMeBack" + t.getMessage());
                new ShowDialog().error(MainManageActivity.this, "Tidak dapat terhubung, terjadi masalah jaringan.");
            }
        });
    }

    public void setDataAccount(ParseAccountDetail data) {
        Log.i("Sep ", "Akun -------------------------------------------");
        Log.i("tgl_input", data.getData().getdAkun().getTgl_input());
        Log.i("Date_Register", data.getData().getdAkun().getDate_Register());
        Log.i("nama", data.getData().getdAkun().getNama());
        Log.i("phone", data.getData().getdAkun().getPhone());
        Log.i("email", data.getData().getdAkun().getEmail());
        Log.i("kode_agen", data.getData().getdAkun().getKode_agen());
        Log.i("pay_to", data.getData().getdAkun().getPay_to());
        Log.i("pay_number", data.getData().getdAkun().getPay_number());
        Log.i("pay_name", data.getData().getdAkun().getPay_name());
        Log.i("Address", data.getData().getdAkun().getAddress());
        Log.i("City", data.getData().getdAkun().getCity());
        Log.i("State", data.getData().getdAkun().getState());
        Log.i("Country", data.getData().getdAkun().getCountry());
        Log.i("Currency", data.getData().getdAkun().getCurrency());
        Log.i("sisa_rebate", data.getData().getdAkun().getSisa_rebate());
        Log.i("Sep ", "Akun Rebate -------------------------------------------");
        if (data.getData().getdAkunRbt() != null) {
            Log.i("tgl_input", data.getData().getdAkunRbt().getTgl_input());
            Log.i("pay_to", data.getData().getdAkunRbt().getPay_to());
            Log.i("pay_number", data.getData().getdAkunRbt().getPay_number());
            Log.i("pay_name", data.getData().getdAkunRbt().getPay_name());
            Log.i("email", data.getData().getdAkunRbt().getEmail());
            Log.i("phone", data.getData().getdAkunRbt().getPhone());
        }

        TextView textAccount = (TextView)findViewById(R.id.textAccount);
        TextView textName = (TextView)findViewById(R.id.textName);
        TextView textPhone = (TextView)findViewById(R.id.textPhone);
        TextView textEmail = (TextView)findViewById(R.id.textEmail);
        TextView textBank = (TextView)findViewById(R.id.textBank);
        TextView textRekening = (TextView)findViewById(R.id.textBank);
        TextView textAtasNama = (TextView)findViewById(R.id.textAtasNama);
        TextView textRegistrationDate = (TextView)findViewById(R.id.textRegistrationDate);
        TextView textValidationDate = (TextView)findViewById(R.id.textValidationDate);
        TextView textAgen = (TextView)findViewById(R.id.textAgen);
        TextView textProvinsi = (TextView)findViewById(R.id.textProvinsi);
        TextView textCity = (TextView)findViewById(R.id.textCity);
        TextView textAddress = (TextView)findViewById(R.id.textAddress);

        TextView textRbtBank = (TextView)findViewById(R.id.textRbtBank);
        TextView textRbtRekening = (TextView)findViewById(R.id.textRbtRekening);
        TextView textRbtAtasNama = (TextView)findViewById(R.id.textRbtAtasNama);
        TextView textRbtEmail = (TextView)findViewById(R.id.textRbtEmail);
        TextView textRbtPhone = (TextView)findViewById(R.id.textRbtPhone);

        TextView textLabelRbtBank = (TextView)findViewById(R.id.textRbtView31);
        TextView textLabelRbtRekening = (TextView)findViewById(R.id.textRbtView34);
        TextView textLabelRbtAtasNama = (TextView)findViewById(R.id.textRbtView46);
        TextView textLabelRbtEmail = (TextView)findViewById(R.id.textLabelEmail);
        TextView textLabelRbtPhone = (TextView)findViewById(R.id.textRbtView24);

        Button buttonKlaimRebate = (Button)findViewById(R.id.buttonKlaimRebate);
        TextView textWarningRebate = (TextView)findViewById(R.id.textWarningRebate);

        textAccount.setText(GData.CURRENT_ACCOUNT + "/" + data.getData().getdAkun().getCurrency() + "\n" + data.getData().getdAkun().getServer());
        textName.setText(data.getData().getdAkun().getNama());
        textPhone.setText(data.getData().getdAkun().getPhone());
        textEmail.setText(data.getData().getdAkun().getEmail());
        textBank.setText(data.getData().getdAkun().getPay_to());
        textRekening.setText(data.getData().getdAkun().getPay_number());
        textAtasNama.setText(data.getData().getdAkun().getPay_name());
        textRegistrationDate.setText(data.getData().getdAkun().getDate_Register());
        textValidationDate.setText(data.getData().getdAkun().getTgl_input());
        textAgen.setText(data.getData().getdAkun().getKode_agen());
        textProvinsi.setText(data.getData().getdAkun().getState());
        textCity.setText(data.getData().getdAkun().getCity());
        textAddress.setText(data.getData().getdAkun().getAddress());

        if (data.getData().getdAkunRbt() == null) {
            textRbtBank.setVisibility(View.GONE);
            textRbtRekening.setVisibility(View.GONE);
            textRbtAtasNama.setVisibility(View.GONE);
            textRbtEmail.setVisibility(View.GONE);
            textRbtPhone.setVisibility(View.GONE);
            textLabelRbtBank.setVisibility(View.GONE);
            textLabelRbtRekening.setVisibility(View.GONE);
            textLabelRbtAtasNama.setVisibility(View.GONE);
            textLabelRbtEmail.setVisibility(View.GONE);
            textLabelRbtPhone.setVisibility(View.GONE);

            buttonKlaimRebate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainManageActivity.this, ClaimRebateActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            buttonKlaimRebate.setVisibility(View.GONE);
            textWarningRebate.setVisibility(View.GONE);

            textRbtBank.setText(data.getData().getdAkunRbt().getPay_to());
            textRbtRekening.setText(data.getData().getdAkunRbt().getPay_number());
            textRbtAtasNama.setText(data.getData().getdAkunRbt().getPay_name());
            textRbtEmail.setText(data.getData().getdAkunRbt().getEmail());
            textRbtPhone.setText(data.getData().getdAkunRbt().getPhone());
        }

    }
}
