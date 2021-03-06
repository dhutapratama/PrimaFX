package com.primafx.client.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.primafx.client.R;
import com.primafx.client.database.GData;
import com.primafx.client.model.SquareFragment;

public class MenusActivity extends AppCompatActivity {
        Toolbar toolbar;
        @Override
        protected void onCreate(Bundle savedInstanceState) {

            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_AUTO);

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_menus);

            //        int[] colors = {R.color.bottomtab_0, R.color.bottomtab_1, R.color.bottomtab_2};
            final String[] colors = {"#96CC7A", "#EA705D", "#66BBCC"};

            final SquareFragment fragment = new SquareFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("color", Color.parseColor(colors[0]));
            fragment.setArguments(bundle);

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.frame, fragment, "square")
                    .commit();


            AHBottomNavigation bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);

            AHBottomNavigationItem item1 = new AHBottomNavigationItem(getString(R.string.tab_1), R.drawable.ic_map_24dp, Color.parseColor(colors[0]));
            AHBottomNavigationItem item2 = new AHBottomNavigationItem(getString(R.string.tab_2), R.drawable.ic_local_restaurant_24dp, Color.parseColor(colors[1]));
            AHBottomNavigationItem item3 = new AHBottomNavigationItem(getString(R.string.tab_3), R.drawable.ic_store_mall_directory_24dp, Color.parseColor(colors[2]));

            bottomNavigation.addItem(item1);
            bottomNavigation.addItem(item2);
            bottomNavigation.addItem(item3);

            bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#FEFEFE"));

            bottomNavigation.setAccentColor(Color.parseColor("#F63D2B"));
            bottomNavigation.setInactiveColor(Color.parseColor("#747474"));

            //  Enables Reveal effect
            bottomNavigation.setColored(true);

            bottomNavigation.setCurrentItem(0);

            bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
                @Override
                public void onTabSelected(int position, boolean wasSelected) {
                    // Do something cool here...

                    fragment.updateColor(Color.parseColor(colors[position]));
                }
            });

        }

    @Override
    protected void onResume() {
        super.onResume();

        if (GData.LOGIN_CODE== null) {
            Intent i = getBaseContext().getPackageManager()
                    .getLaunchIntentForPackage( getBaseContext().getPackageName() );
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
    }
    }

