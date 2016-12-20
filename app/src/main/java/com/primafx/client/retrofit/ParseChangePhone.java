package com.primafx.client.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Technical on 12/12/2016.
 */

public class ParseChangePhone {

    // Post Parameter
    private String login_hash;
    private String phone;
    // Reply Parameter
    @SerializedName("error")
    private Boolean error;
    @SerializedName("message")
    private String message;

    public ParseChangePhone(String login_hash, String phone) {
        this.login_hash = login_hash;
        this.phone = phone;
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
