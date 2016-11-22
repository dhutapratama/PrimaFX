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
    @SerializedName("error")
    private Boolean error;
    @SerializedName("message")
    private String message;
    @Expose
    private ResponseData data;

    public ParseCallMeBack(String nama, String phone, String email, String support, String date, String time) {
        this.nama = nama;
        this.phone = phone;
        this.email = email;
        this.support = support;
        this.date = date;
        this.time = time;
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

    public ResponseData getData() {
        return data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class ResponseData {
        private String nama;
        private String phone;
        private String email;
        private String support;
        private String date;
        private String time;

        public String getDate() {
            return date;
        }

        public String getEmail() {
            return email;
        }

        public String getNama() {
            return nama;
        }

        public String getPhone() {
            return phone;
        }

        public String getSupport() {
            return support;
        }

        public String getTime() {
            return time;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setNama(String nama) {
            this.nama = nama;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public void setSupport(String support) {
            this.support = support;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
