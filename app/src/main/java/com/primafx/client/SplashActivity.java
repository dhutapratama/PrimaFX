package com.primafx.client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread timer = new Thread()
        {
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                finally
                {
                    Intent intent = new Intent(SplashActivity.this, IntroActivity.class);
                    startActivity(intent);
                }
            }
        };
        timer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
