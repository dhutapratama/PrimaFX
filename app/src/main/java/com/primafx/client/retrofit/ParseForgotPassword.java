package com.primafx.client.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ParseForgotPassword {
    // Post Parameter
    private String email;

    // Reply Parameter
    @SerializedName("code")
    private String code;
    @SerializedName("message")
    private String message;

    public ParseForgotPassword(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
