package com.primafx.client.retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RequestLibrary {
    String headerContent = "User-Agent: IMME 1.5-Lite";

    // Check Connection
    @Headers(headerContent)
    @POST("check")
    Call<ParseCheckConnection> checkConnection(@Body ParseCheckConnection value);


    // Initial user
    @Headers(headerContent)
    @POST("check_user")
    Call<ParseCheckUser> checkUser(@Body ParseCheckUser value);

    // Product
    @Headers(headerContent)
    @POST("get_products")
    Call<ParseGetProduct> getProduct(@Body ParseGetProduct value);

}
