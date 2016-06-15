package com.primafx.client.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.primafx.client.R;
import com.primafx.client.retrofit.ParseCheckConnection;
import com.primafx.client.retrofit.RequestLibrary;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Log.d("Firebase", "InstanceID token: " + FirebaseInstanceId.getInstance().getToken());
        retroParsing("lpioijoijoij");

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
    private void retroParsing(String message_token) {
        String host = "http://rufi.hol.es/v2_server/";

        ParseCheckConnection jsonSend = new ParseCheckConnection(message_token);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(host)
                .addConverterFactory(GsonConverterFactory.create()).build();
        RequestLibrary requestLibrary = retrofit.create(RequestLibrary.class);
        Call<ParseCheckConnection> callData = requestLibrary.checkConnection(jsonSend);

        callData.enqueue(new Callback<ParseCheckConnection>() {
            @Override
            public void onResponse(Call<ParseCheckConnection> call, Response<ParseCheckConnection> response) {
                if (response.isSuccessful()) {
                    // Nothing to do
                } else {
                    Log.e("Server Problem", "Server Responding but error callback : " + response.body());
                }
            }

            @Override
            public void onFailure(Call<ParseCheckConnection> call, Throwable t) {
                Log.e("Network Failure", t.getCause().toString());
            }
        });
    }
}
