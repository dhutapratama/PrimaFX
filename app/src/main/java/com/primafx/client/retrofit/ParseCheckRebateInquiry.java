package com.primafx.client.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Technical on 10/4/2016.
 */

public class ParseCheckRebateInquiry {

    // Post Parameter
    private String akun;
    private String authKey;
    private String app;


    // Reply Parameter
    @SerializedName("error")
    private Boolean error;
    @SerializedName("message")
    private String message;
    @Expose
    private List<ParseDataRebateInquiry> data;

    public ParseCheckRebateInquiry(String akun, String authKey) {
        this.akun = akun;
        this.authKey = authKey;
        this.app = "android";
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

    public List<ParseDataRebateInquiry> getData() {
        return data;
    }
}
