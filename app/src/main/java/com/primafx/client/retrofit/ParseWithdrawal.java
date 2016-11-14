package com.primafx.client.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Technical on 11/7/2016.
 */

public class ParseWithdrawal {

    // Post Parameter
    private String akun;
    private String authKey;
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
    private ParseDataWithdrawal data;

    public ParseWithdrawal(String akun, String authKey, String usd, String preview) {
        this.akun = akun;
        this.authKey = authKey;
        this.usd = usd;
        this.preview = preview;
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

    public ParseDataWithdrawal getData() {
        return data;
    }
}
