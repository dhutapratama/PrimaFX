package com.primafx.client.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.primafx.client.R;
import com.primafx.client.database.DatabaseSQL;
import com.primafx.client.database.GData;
import com.primafx.client.dialog.ShowDialog;
import com.primafx.client.retrofit.ParseAuthenticate;
import com.primafx.client.retrofit.RequestLibrary;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SplashActivity extends AppCompatActivity {
    Dialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Log.d("Firebase", "InstanceID token: " + FirebaseInstanceId.getInstance().getToken());
        loading = new ShowDialog().loading(this);
        DatabaseSQL.getInitialData(this);
        if (GData.FIRST_TIME.equals("true")) {
            Intent i = new Intent(this, IntroActivity.class);
            startActivity(i);
        } else {
            if (GData.LOGIN_CODE.equals(null) || GData.LOGIN_CODE.equals("null")) {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
            } else {
                retrofitCheckLoginCode();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    private void retrofitCheckLoginCode() {
        loading.show();

        String host = GData.API_ADDRESS;

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .header("Login-Code", GData.LOGIN_CODE)
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });

        OkHttpClient client = httpClient.build();

        ParseAuthenticate jsonSend = new ParseAuthenticate();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(host)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create()).build();
        RequestLibrary requestLibrary = retrofit.create(RequestLibrary.class);
        Call<ParseAuthenticate> callData = requestLibrary.authenticate(jsonSend);

        callData.enqueue(new Callback<ParseAuthenticate>() {
            @Override
            public void onResponse(Call<ParseAuthenticate> call, Response<ParseAuthenticate> response) {
                loading.dismiss();
                if (response.isSuccessful()) {
                    ParseAuthenticate response_body = response.body();
                    if (response_body.getCode().equals("200")) {
                        Log.i("200", response_body.getMessage());
                        //new ShowDialog().success(SplashActivity.this, response_body.getData().getEmail());
                        //DatabaseSQL.updateSecurityData(LoginActivity.this, DatabaseSQL.FIELD_LOGIN_CODE, response_body.getData().getLogin_code());

                        Intent i = new Intent(SplashActivity.this, MainAppActivity.class);
                        startActivity(i);

                        finish();
                    } else {
                        Log.i(response_body.getCode(), response_body.getMessage());

                        final Dialog errorDialog = new ShowDialog().error(SplashActivity.this, response_body.getMessage());

                        errorDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialogInterface) {
                                finish();
                            }
                        });
                    }
                } else {
                    String errorMessage = "Kesalahan tidak diketahui";
                    if (response.body().toString() != null) {
                        errorMessage = response.body().toString();
                    }
                    Log.e("Server Problem", "Respond error : " + errorMessage);
                    final Dialog errorDialog = new ShowDialog().error(SplashActivity.this, errorMessage);
                    errorDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            finish();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ParseAuthenticate> call, Throwable t) {
                loading.dismiss();
                Log.e("Network", "ParseEmailLogin");
                final Dialog errorDialog = new ShowDialog().error(SplashActivity.this, "Tidak dapat terhubung, terjadi masalah jaringan.");
                errorDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        finish();
                    }
                });
            }
        });
    }
}
