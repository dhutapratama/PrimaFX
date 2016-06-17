package com.primafx.client.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
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
import com.google.firebase.crash.FirebaseCrash;
import com.primafx.client.R;
import com.primafx.client.model.ShowDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private FirebaseAnalytics mFirebaseAnalytics;
    DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout menuButton = (LinearLayout)findViewById(R.id.layout_click_sidebar);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.openDrawer(GravityCompat.START);
            }
        });

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        analyticsPage();

        FirebaseCrash.report(new Exception("Error Boongan"));

        setTransactionHistory();

        LinearLayout deposit = (LinearLayout)findViewById(R.id.LLDeposit);
        deposit.setOnClickListener(this);

        LinearLayout withdrawal = (LinearLayout)findViewById(R.id.LLWithdrawal);
        withdrawal.setOnClickListener(this);

        LinearLayout rebate = (LinearLayout)findViewById(R.id.LLRebate);
        rebate.setOnClickListener(this);

        setMenuCounter(R.id.nav_notification, 5);

        TextView textAccName = (TextView)findViewById(R.id.textAccName);
        textAccName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch_account();
            }
        });
    }

    private void switch_account() {
        Dialog d = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT)
                .setTitle("Pilih Account")
                .setNegativeButton("Batal", null)
                .setPositiveButton("Tambah Aacount", null)
                .setItems(new String[]{"#0981231 (GPBUSD)", "#0981231 (GPBUSD)","#0981231 (GPBUSD)","#0981231 (GPBUSD)"}, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dlg, int position)
                    {
                        if ( position == 0 )
                        {
                        }
                        else if(position == 1){
                        }
                        else if(position == 2){
                        }
                    }
                })
                .create();
        d.show();
    }

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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == 0) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void analyticsPage() {
        // [START image_view_event]
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "main_activity");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "MainActivity");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "activity");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        // [END image_view_event]
    }

    public void setTransactionHistory() {
        String[] data = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "7"};

        final List<HashMap<String, String>> aList = new ArrayList<>();

        for (int i = 0; i < data.length; i++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("data", data[i]);
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
                Picasso.with(MainActivity.this).load(transactions.get(position).getPicture())
                        .error(MainActivity.this.getResources().getDrawable(R.mipmap.no_image_square))
                        .into(imagePerson);
                        */
                return v;
            }
        };

        // Getting a reference to listview of main.xml layout file
        ListView listView = (ListView) findViewById(R.id.listView);

        // Setting the adapter to the listView
        listView.setAdapter(adapter);

        /*
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, TransactionDetail.class);
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
            Dialog rebate = ShowDialog.rebate(this, "$12.00");

            Button btnBank = (Button) rebate.findViewById(R.id.buttonToBank);
            btnBank.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, RebateBankActivity.class);
                    startActivity(intent);
                }
            });

            Button btnAccount = (Button) rebate.findViewById(R.id.buttonToAccount);
            btnAccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, RebateAccountActivity.class);
                    startActivity(intent);
                }
            });

        }
    }

    private static final String TAG = MainActivity.class.getSimpleName();
}
