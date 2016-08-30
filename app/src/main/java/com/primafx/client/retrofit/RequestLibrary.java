package com.primafx.client.retrofit;

import android.provider.SyncStateContract;

import com.primafx.client.database.GData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RequestLibrary {
    String headerContent = "User-Agent: PrimaFX 1.0";
    public static final String LOGIN_CODE = GData.LOGIN_CODE;

    // Product
    @Headers({"User-Agent: PrimaFX 1.0", "Content-Type: application/json"})
    //@Headers(headerContent)
    @POST("email_registration")
    Call<ParseEmailRegistration> emailRegistration(@Body ParseEmailRegistration value);

    // Google Signin
    @Headers({"User-Agent: PrimaFX 1.0", "Content-Type: application/json"})
    //@Headers(headerContent)
    @POST("google_registration")
    Call<ParseGoogleSignin> googleSignin(@Body ParseGoogleSignin value);

    // Email Login
    @Headers({"User-Agent: PrimaFX 1.0", "Content-Type: application/json"})
    //@Headers(headerContent)
    @POST("email_login")
    Call<ParseEmailLogin> emailLogin(@Body ParseEmailLogin value);

    // Email Login
    @Headers({"User-Agent: PrimaFX 1.0", "Content-Type: application/json"})
    //@Headers(headerContent)
    @POST("authenticate")
    Call<ParseAuthenticate> authenticate(@Body ParseAuthenticate value);

    // Forgot Password
    @Headers({"User-Agent: PrimaFX 1.0", "Content-Type: application/json"})
    //@Headers(headerContent)
    @POST("forgot_password")
    Call<ParseForgotPassword> forgotPassword(@Body ParseForgotPassword value);
}

