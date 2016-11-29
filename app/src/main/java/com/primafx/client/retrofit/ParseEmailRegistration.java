package com.primafx.client.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ParseEmailRegistration {
    // Post Parameter
    private String email;
    private String password;
    private String name;
    // Reply Parameter
    @SerializedName("error")
    private Boolean error;
    @SerializedName("message")
    private String message;

    public ParseEmailRegistration(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
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
}
