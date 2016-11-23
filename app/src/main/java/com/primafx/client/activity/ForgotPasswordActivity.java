package com.primafx.client.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.primafx.client.R;
import com.primafx.client.database.DatabaseSQL;
import com.primafx.client.database.GData;
import com.primafx.client.dialog.ShowDialog;
import com.primafx.client.retrofit.ParseEmailLogin;
import com.primafx.client.retrofit.ParseForgotPassword;
import com.primafx.client.retrofit.ParseGoogleSignin;
import com.primafx.client.retrofit.RequestLibrary;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A login screen that offers login via email/password.
 */
public class ForgotPasswordActivity extends AppCompatActivity implements OnClickListener {

    private ProgressDialog mProgressDialog;

    // UI references.
    private AutoCompleteTextView mEmailView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        Button btnRequestPassword = (Button) findViewById(R.id.btnRequestPassword);
        btnRequestPassword.setOnClickListener(this);
    }

    private void attemptRequestPassword() {
        // Reset errors.
        mEmailView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            retrofitForgotPassword(mEmailView.getText().toString());
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@") && email.contains(".");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 7;
    }

    /**
     * Shows the progress UI and hides the login form.
     */

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRequestPassword:
                attemptRequestPassword();
                break;
        }
    }

    private void retrofitForgotPassword(String email) {
        final Dialog loading = new ShowDialog().loading(this);
        loading.show();

        String host = GData.API_ADDRESS;

        ParseForgotPassword jsonSend = new ParseForgotPassword(email);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(host)
                .addConverterFactory(GsonConverterFactory.create()).build();
        RequestLibrary requestLibrary = retrofit.create(RequestLibrary.class);
        Call<ParseForgotPassword> callData = requestLibrary.forgotPassword(jsonSend);

        callData.enqueue(new Callback<ParseForgotPassword>() {
            @Override
            public void onResponse(Call<ParseForgotPassword> call, Response<ParseForgotPassword> response) {
                loading.dismiss();
                if (response.isSuccessful()) {
                    ParseForgotPassword response_body = response.body();
                    if (response_body.getCode().equals("200")) {
                        Log.i("200", response_body.getMessage());
                        Dialog dialog = new ShowDialog().success(ForgotPasswordActivity.this, response_body.getMessage());
                        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialogInterface) {
                                finish();
                            }
                        });

                    } else {
                        Log.i(response_body.getCode(), response_body.getMessage());
                        new ShowDialog().error(ForgotPasswordActivity.this, response_body.getMessage());
                    }
                } else {
                    String errorMessage = "Kesalahan tidak diketahui.";
                    if (response.body() != null) {
                        errorMessage = response.body().toString();
                    }

                    Log.e("Server Problem", "Server Responding but error callback : " + errorMessage);
                    new ShowDialog().error(ForgotPasswordActivity.this, errorMessage);
                }
            }

            @Override
            public void onFailure(Call<ParseForgotPassword> call, Throwable t) {
                loading.dismiss();
                Log.e("Network", "ParseEmailLogin");
                new ShowDialog().error(ForgotPasswordActivity.this, "Tidak dapat terhubung, terjadi masalah jaringan.");
            }
        });
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

