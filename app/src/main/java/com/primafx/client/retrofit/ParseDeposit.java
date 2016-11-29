package com.primafx.client.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Technical on 10/18/2016.
 */

public class ParseDeposit {

    // Post Parameter
    private String akun;
    private String authKey;
    private String app;
    private String pay_to;
    private String usd;
    private String idr;
    private String preview;
    // Reply Parameter
    @SerializedName("error")
    private Boolean error;
    @SerializedName("message")
    private String message;
    @Expose
    private ParseDataDeposit data;

    public ParseDeposit(String akun, String authKey, String pay_to, String usd, String idr, String preview) {
        this.akun = akun;
        this.authKey = authKey;
        this.pay_to = pay_to;
        this.app = "android";
        this.usd = usd;
        this.idr = idr;
        this.preview = preview;
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

    public ParseDataDeposit getData() {
        return data;
    }
}
