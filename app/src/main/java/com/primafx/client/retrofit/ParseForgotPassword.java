package com.primafx.client.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ParseForgotPassword {
    // Post Parameter
    private String email;

    // Reply Parameter
    @SerializedName("error")
    private Boolean error;
    @SerializedName("message")
    private String message;
    @Expose
    private ResponseData data;

    public ParseForgotPassword(String email) {
        this.email = email;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(ResponseData data) {
        this.data = data;
    }

    public class ResponseData {

    }

}
