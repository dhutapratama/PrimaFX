package com.primafx.client.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.primafx.client.R;
import com.primafx.client.database.DatabaseSQL;
import com.primafx.client.database.GData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainAppActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {
    private SliderLayout mDemoSlider;
    private String[] accounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app);

        mDemoSlider = (SliderLayout)findViewById(R.id.slider);

        HashMap<String, String> url_maps = new HashMap<>();
        url_maps.put("PrimaPAY", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
        url_maps.put("Kelola Akun Trading", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
        url_maps.put("Obrolan", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");

        for(String name : url_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
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


}
