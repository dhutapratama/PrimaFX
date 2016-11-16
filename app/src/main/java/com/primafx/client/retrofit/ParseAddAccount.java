package com.primafx.client.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Technical on 11/15/2016.
 */

public class ParseAddAccount {
    // Post Parameter
    private String akun;
    private String authKey;
    private String phone_pass;
    private String app;
    // Reply Parameter
    @SerializedName("code")
    private Integer code;
    @SerializedName("error")
    private Boolean error;
    @SerializedName("message")
    private String message;
    @Expose
    private ParseDataDeposit data;

    public ParseAddAccount(String akun, String authKey, String phone_pass) {
        this.akun = akun;
        this.authKey = authKey;
        this.phone_pass = phone_pass;
        this.app = "android";
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

    public ParseDataDeposit getData() {
        return data;
    }
}
