package com.primafx.client.retrofit;

import android.provider.SyncStateContract;

import com.primafx.client.database.GData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RequestLibrary {
    String headerContent = "User-Agent: PrimaFX 1.0";
    public static final String LOGIN_CODE = GData.LOGIN_CODE;

    // Product
    @Headers({"User-Agent: PrimaFX 1.0", "Content-Type: application/json"})
    @POST("trader/register-email")
    Call<ParseEmailRegistration> emailRegistration(@Body ParseEmailRegistration value);

    // Google Signin
    @Headers({"User-Agent: PrimaFX 1.0", "Content-Type: application/json"})
    @POST("trader/auth-google")
    Call<ParseGoogleSignin> googleSignin(@Body ParseGoogleSignin value);

    // Email Login
    @Headers({"User-Agent: PrimaFX 1.0", "Content-Type: application/json"})
    @POST("trader/login-email")
    Call<ParseEmailLogin> emailLogin(@Body ParseEmailLogin value);

    // Email Login
    @Headers({"User-Agent: PrimaFX 1.0", "Content-Type: application/json"})
    @POST("trader/authenticate")
    Call<ParseAuthenticate> authenticate(@Body ParseAuthenticate value);

    // Forgot Password
    @Headers({"User-Agent: PrimaFX 1.0", "Content-Type: application/json"})
    @POST("forgot_password")
    Call<ParseForgotPassword> forgotPassword(@Body ParseForgotPassword value);

    // Rebate Inquiry
    @Headers({"User-Agent: PrimaFX 1.0", "Content-Type: application/json"})
    @POST("client/check-rebate/inquiry")
    Call<ParseCheckRebateInquiry> rebateInquiry(@Body ParseCheckRebateInquiry value);

    // Deposit Inquiry
    @Headers({"User-Agent: PrimaFX 1.0", "Content-Type: application/json"})
    @POST("client/deposit/inquiry")
    Call<ParseDepositInquiry> depositInquiry(@Body ParseDepositInquiry value);

    // Deposit
    @Headers({"User-Agent: PrimaFX 1.0", "Content-Type: application/json"})
    @POST("client/deposit")
    Call<ParseDeposit> deposit(@Body ParseDeposit value);

    // Withdrawal
    @Headers({"User-Agent: PrimaFX 1.0", "Content-Type: application/json"})
    @POST("client/withdrawal")
    Call<ParseWithdrawal> withdrawal(@Body ParseWithdrawal value);

    // Transfer Rebate
    @Headers({"User-Agent: PrimaFX 1.0", "Content-Type: application/json"})
    @POST("client/transfer-rebate")
    Call<ParseTransferRebate> transferRebate(@Body ParseTransferRebate value);

    // Transfer Rebate
    @Headers({"User-Agent: PrimaFX 1.0", "Content-Type: application/json"})
    @POST("client/withdrawal-rebate")
    Call<ParseWithdrawalRebate> withdrawalRebate(@Body ParseWithdrawalRebate value);

    // Get Quotes
    @Headers({"User-Agent: PrimaFX 1.0", "Content-Type: application/json"})
    @GET("tools/quotes")
    Call<ParseQuotes> getQuotes();
}

