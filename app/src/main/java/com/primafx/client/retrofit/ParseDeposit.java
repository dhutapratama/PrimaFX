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
    private String pay_to;
    private String usd;
    private String idr;
    // Reply Parameter
    @SerializedName("code")
    private Integer code;
    @SerializedName("error")
    private Boolean error;
    @SerializedName("message")
    private String message;
    @Expose
    private ParseDataDepositInquiry data;

    public ParseDeposit(String akun, String authKey, String pay_to, String usd, String idr) {
        this.akun = akun;
        this.authKey = authKey;
        this.pay_to = pay_to;
        this.usd = usd;
        this.idr = idr;
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
