package com.primafx.client.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Technical on 11/11/2016.
 */

public class ParseHistory {
    // Post Parameter
    private String akun;
    private String login_hash;
    // Reply Parameter
    @SerializedName("error")
    private Boolean error;
    @SerializedName("message")
    private String message;
    @Expose
    private List<ParseDataHistory> data;

    public ParseHistory(String akun, String login_hash) {
        this.akun = akun;
        this.login_hash = login_hash;
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

    public List<ParseDataHistory> getData() {
        return data;
    }
}
