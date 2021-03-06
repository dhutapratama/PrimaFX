package com.primafx.client.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.primafx.client.R;
import com.primafx.client.database.GData;
import com.primafx.client.dialog.ShowDialog;
import com.primafx.client.model.ParalaxScrollView;
import com.primafx.client.retrofit.ParseEmailRegistration;
import com.primafx.client.retrofit.RequestLibrary;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistrationActivity extends AppCompatActivity implements ParalaxScrollView.OnScrollChangedListener{
    EditText email;
    EditText nama;
    private ParalaxScrollView mScrollView;
    private View imgContainer;

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Button registration = (Button) findViewById(R.id.buttonAddAccount);
        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation();
            }
        });

        nama = (EditText) findViewById(R.id.editNama);

        email = (EditText) findViewById(R.id.editEmail);
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!isValidEmail(charSequence)) {
                    email.setError("Gunakan email yang benar!");
                } else {
                    email.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onScrollChanged(int deltaX, int deltaY) {
        int scrollY = mScrollView.getScrollY();
        // Add parallax effect
        imgContainer.setTranslationY(scrollY * 0.5f);
    }

    private void validation() {
        EditText password = (EditText) findViewById(R.id.editPassword);
        EditText password2 = (EditText) findViewById(R.id.editPhone);

        password.setError(null);
        password2.setError(null);

        if (!password.getText().toString().equals(password2.getText().toString())) {
            password2.setError("Password tidak sama!");
        }

        if (password.getText().length() == 0) {
            password.setError("Password tidak boleh kosong!");
        }

        if (password.getText().length() < 8) {
            password.setError("Panjang password minimal 8 karakter.");
        }

        if (email.getText().length() == 0) {
            email.setError("Email tidak boleh kosong!");
        }

        if (email.getError() == null && password.getError() == null && password2.getError() == null) {
            retrofitRegistration(email.getText().toString(), password.getText().toString(), nama.getText().toString());
        }
    }

    private void retrofitRegistration(String email, String password, String name) {
        final Dialog loading = new ShowDialog().loading(this);
        loading.show();

        String host = GData.API_ADDRESS;

        ParseEmailRegistration jsonSend = new ParseEmailRegistration(email, password, name);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(host)
                .addConverterFactory(GsonConverterFactory.create()).build();
        RequestLibrary requestLibrary = retrofit.create(RequestLibrary.class);
        Call<ParseEmailRegistration> callData = requestLibrary.emailRegistration(jsonSend);

        callData.enqueue(new Callback<ParseEmailRegistration>() {
            @Override
            public void onResponse(Call<ParseEmailRegistration> call, Response<ParseEmailRegistration> response) {
                loading.dismiss();
                if (response.isSuccessful()) {
                    ParseEmailRegistration response_body = response.body();
                    if (!response_body.getError()) {
                        new ShowDialog().success(RegistrationActivity.this, "Registrasi berhasil, cek email untuk mengaktifkan account anda.").setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialogInterface) {
                                finish();
                            }
                        });
                    } else {
                        new ShowDialog().error(RegistrationActivity.this, response_body.getMessage());
                    }
                } else {
                    Log.e("Server Problem", "Server Responding but error callback : " + response.body().toString());
                    new ShowDialog().error(RegistrationActivity.this, response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<ParseEmailRegistration> call, Throwable t) {
                loading.dismiss();
                Log.e("Network", "ParseEmailRegistration");
                new ShowDialog().error(RegistrationActivity.this, "Tidak dapat terhubung, terjadi masalah jaringan.");
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
