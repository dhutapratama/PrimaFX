package com.primafx.client.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Technical on 11/29/2016.
 */

public class ParseUpdateGCM {
    // Post Parameter
    private String login_hash;
    private String MsgAddrs;
    // Reply Parameter
    @SerializedName("error")
    private Boolean error;
    @SerializedName("message")
    private String message;

    public ParseUpdateGCM(String login_hash, String MsgAddrs) {
        this.login_hash = login_hash;
        this.MsgAddrs = MsgAddrs;
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