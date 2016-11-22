package com.primafx.client.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Technical on 11/4/2016.
 */

public class ParseTransferRebate {
    // Post Parameter
    private String akun;
    private String authKey;
    private String app;
    private String usd;
    private String pay_number;
    private String preview;
    // Reply Parameter
    @SerializedName("error")
    private Boolean error;
    @SerializedName("message")
    private String message;
    @Expose
    private ParseDataTransferRebate data;

    public ParseTransferRebate(String akun, String authKey, String usd, String pay_number) {
        this.akun = akun;
        this.authKey = authKey;
        this.app = "android";
        this.usd = usd;
        this.pay_number = pay_number;
        this.preview = "true";
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

    public ParseDataTransferRebate getData() {
        return data;
    }
}
