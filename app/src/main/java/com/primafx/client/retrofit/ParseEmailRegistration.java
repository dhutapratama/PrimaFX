package com.primafx.client.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ParseEmailRegistration {
    // Post Parameter
    private String email;
    private String password;
    // Reply Parameter
    @SerializedName("code")
    private String code;
    @SerializedName("message")
    private String message;

    public ParseEmailRegistration(String email, String password) {
        this.email = email;
        this.password = password;
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
