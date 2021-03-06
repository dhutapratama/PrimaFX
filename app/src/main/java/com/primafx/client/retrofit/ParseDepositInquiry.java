package com.primafx.client.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Technical on 10/18/2016.
 */

public class ParseDepositInquiry {
    // Post Parameter
    private String akun;
    private String authKey;
    private String pay_to;
    private String usd;
    private String app;
    // Reply Parameter
    @SerializedName("code")
    private Integer code;
    @SerializedName("error")
    private Boolean error;
    @SerializedName("message")
    private String message;
    @Expose
    private ParseDataDepositInquiry data;

    public ParseDepositInquiry(String akun, String authKey, String pay_to, String usd) {
        this.akun = akun;
        this.authKey = authKey;
        this.pay_to = pay_to;
        this.usd = usd;
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

    public ParseDataDepositInquiry getData() {
        return data;
    }

}