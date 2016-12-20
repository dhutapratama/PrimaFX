package com.primafx.client.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Technical on 12/12/2016.
 */

public class ParseVerifyChangePhone {

    // Post Parameter
    private String login_hash;
    private String verifycode;
    // Reply Parameter
    @SerializedName("error")
    private Boolean error;
    @SerializedName("message")
    private String message;

    public ParseVerifyChangePhone(String login_hash, String verifycode) {
        this.login_hash = login_hash;
        this.verifycode = verifycode;
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
