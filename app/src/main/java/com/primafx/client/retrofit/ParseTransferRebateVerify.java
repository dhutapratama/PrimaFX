package com.primafx.client.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ParseTransferRebateVerify {
    // Post Parameter
    private String akun;
    private String authKey;
    private String verify_code;

    // Reply Parameter
    @SerializedName("error")
    private Boolean error;
    @SerializedName("message")
    private String message;
    @Expose
    private VerifyCode data;

    public ParseTransferRebateVerify(String akun, String authKey, String verify_code) {
        this.akun = akun;
        this.authKey = authKey;
        this.verify_code = verify_code;

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

    public VerifyCode getData() {
        return data;
    }

    public class VerifyCode{

    }
}
