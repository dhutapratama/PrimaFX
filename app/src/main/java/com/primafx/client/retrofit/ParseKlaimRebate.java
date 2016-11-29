package com.primafx.client.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Technical on 11/15/2016.
 */

public class ParseKlaimRebate {

    // Post Parameter
    private String mode;
    private String akun;
    private String authKey;
    private String app;
    private String pay_to;
    private String pay_number;
    private String pay_name;
    private String email;
    private String phone;
    private String preview;
    // Reply Parameter
    @SerializedName("code")
    private Integer code;
    @SerializedName("error")
    private Boolean error;
    @SerializedName("message")
    private String message;
    @Expose
    private ParseDataKlaimRebate data;

    public ParseKlaimRebate(String akun, String authKey, String pay_to, String pay_number, String pay_name, String email, String phone) {
        this.mode = "new";
        this.akun = akun;
        this.authKey = authKey;
        this.app = "android";
        this.pay_to = pay_to;
        this.pay_number = pay_number;
        this.pay_name = pay_name;
        this.email = email;
        this.phone = phone;
        this.preview = "true";
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

    public ParseDataKlaimRebate getData() {
        return data;
    }
}
