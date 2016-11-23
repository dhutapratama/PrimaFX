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
    @SerializedName("error")
    private Boolean error;
    @SerializedName("message")
    private String message;
    @Expose
    private ResponseData data;

    public ParseAddAccount(String akun, String authKey, String phone_pass) {
        this.akun = akun;
        this.authKey = authKey;
        this.phone_pass = phone_pass;
        this.app = "android";
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

    public ResponseData getData() {
        return data;
    }

    public void setData(ResponseData data) {
        this.data = data;
    }

    public class ResponseData {
        @SerializedName("akun")
        private String akun;
        @SerializedName("nama")
        private String nama;
        @SerializedName("phone")
        private String phone;
        @SerializedName("email")
        private String email;
        @SerializedName("kode_agen")
        private String kode_agen;
        @SerializedName("trader_id")
        private String trader_id;
        @SerializedName("best_regard")
        private String best_regard;
        @SerializedName("app")
        private String app;

        public String getAkun() {
            return akun;
        }

        public void setAkun(String akun) {
            this.akun = akun;
        }

        public String getNama() {
            return nama;
        }

        public void setNama(String nama) {
            this.nama = nama;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPhone() {
            return phone;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getEmail() {
            return email;
        }

        public String getKode_agen() {
            return kode_agen;
        }

        public void setKode_agen(String kode_agen) {
            this.kode_agen = kode_agen;
        }

        public String getTrader_id() {
            return trader_id;
        }

        public void setTrader_id(String trader_id) {
            this.trader_id = trader_id;
        }

        public String getBest_regard() {
            return best_regard;
        }

        public void setBest_regard(String best_regard) {
            this.best_regard = best_regard;
        }

        public String getApp() {
            return app;
        }

        public void setApp(String app) {
            this.app = app;
        }
    }
}
