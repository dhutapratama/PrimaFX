package com.primafx.client.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Technical on 11/4/2016.
 */

public class ParseWithdrawalRebate {
    // Post Parameter
    private String akun;
    private String authKey;
    private String app;
    private String usd;
    private String preview;
    // Reply Parameter
    @SerializedName("code")
    private Integer code;
    @SerializedName("error")
    private Boolean error;
    @SerializedName("message")
    private String message;
    @Expose
    private ParseDataWithdrawalRebate data;

    public ParseWithdrawalRebate(String akun, String authKey, String usd) {
        this.akun = akun;
        this.authKey = authKey;
        this.app = "android";
        this.usd = usd;
        this.preview = "false";
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
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

    public ParseDataWithdrawalRebate getData() {
        return data;
    }
}
