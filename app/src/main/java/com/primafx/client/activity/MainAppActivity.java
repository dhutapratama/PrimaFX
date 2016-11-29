package com.primafx.client.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.primafx.client.R;
import com.primafx.client.database.DatabaseSQL;
import com.primafx.client.database.GData;
import com.primafx.client.model.ShakeDetector;

import java.util.HashMap;
import java.util.List;
public class MainAppActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {
    private SliderLayout mDemoSlider;
    private String[] accounts;

    // The following are used for the shake detection
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app);

        mDemoSlider = (SliderLayout)findViewById(R.id.slider);

        HashMap<String, String> url_maps = new HashMap<>();
        url_maps.put("PrimaFX APPS", "https://scontent-sin6-1.xx.fbcdn.net/v/t1.0-9/15241870_10209160350859993_290611551922635755_n.jpg?oh=76a352e177017662d53a49aa7d5a991d&oe=58F846B3");
        url_maps.put("Shake Me to get surprize", "https://scontent-sin6-1.xx.fbcdn.net/v/t1.0-9/15178091_10209160351019997_151380428978250262_n.jpg?oh=ab47f3d07a8fc37eceb785960d431f59&oe=58C0A8F4");
        url_maps.put("Please review to us", "https://scontent-sin6-1.xx.fbcdn.net/v/t1.0-9/15267980_10209160350779991_159181351555252594_n.jpg?oh=73f600449433e7e8d6e52ee9cec2e15f&oe=58FB97B6");

        for(String name : url_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);

        // Variable Initialization
        List<String> my_accounts = DatabaseSQL.getAccountData(this);
        this.accounts = my_accounts.toArray(new String[my_accounts.size()]);

        LinearLayout kelola_account = (LinearLayout) findViewById(R.id.menu_kelola_akun);
        kelola_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch_account();
            }
        });

        LinearLayout quotes = (LinearLayout) findViewById(R.id.menu_quotes);
        quotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainAppActivity.this, QuotesActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout support = (LinearLayout) findViewById(R.id.menu_support);
        support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainAppActivity.this, CallMeActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout kalender = (LinearLayout) findViewById(R.id.menu_kalender);
        kalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainAppActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });

        // ShakeDetector initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {
				/*
				 * The following method, "handleShakeEvent(count):" is a stub //
				 * method you would use to setup whatever you want done once the
				 * device has been shook.
				 */

                Log.i("SHAKE", Integer.toString(count));

                if (count > 4) {
                    Intent intent = new Intent(MainAppActivity.this, TestingApiActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    // Account Switcher
    private void switch_account() {
        Dialog d = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT)
                .setTitle("Pilih Account")
                .setNegativeButton("Batal", null)
                .setPositiveButton("Tambah Account", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainAppActivity.this, AddAccountActivity.class);
                        startActivity(intent);
                    }
                })
                .setItems(this.accounts, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dlg, int position)
                    {
                        GData.CURRENT_ACCOUNT = accounts[position];

                        Intent i = new Intent(MainAppActivity.this, MainManageActivity.class);
                        startActivity(i);
                    }
                })
                .create();
        d.show();
    }

    @Override
    protected void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        //Toast.makeText(this,slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
        //Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {}
    @Override
    public void onResume() {
        super.onResume();
        // Add the following line to register the Session Manager Listener onResume
        mSensorManager.registerListener(mShakeDetector, mAccelerometer,	SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        // Add the following line to unregister the Sensor Manager onPause
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }

}
