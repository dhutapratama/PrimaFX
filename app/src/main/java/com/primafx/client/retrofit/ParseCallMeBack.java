package com.primafx.client.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Technical on 11/15/2016.
 */

public class ParseCallMeBack {

    // Post Parameter
    private String nama;
    private String phone;
    private String email;
    private String support;
    private String date;
    private String time;
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

    public ParseCallMeBack(String nama, String phone, String email, String support, String date, String time) {
        this.nama = nama;
        this.phone = phone;
        this.email = email;
        this.support = support;
        this.date = date;
        this.time = time;
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
