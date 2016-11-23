package com.primafx.client.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.primafx.client.R;
import com.primafx.client.database.GData;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
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
