package com.primafx.client.retrofit;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Technical on 10/19/2016.
 */

public class ParseDataDeposit {
    @SerializedName("type_order")
    private String type_order;
    @SerializedName("akun")
    private String akun;
    @SerializedName("pay_to")
    private String pay_to;
    @SerializedName("pay_number")
    private String pay_number;
    @SerializedName("pay_name")
    private String pay_name;
    @SerializedName("usd")
    private String usd;
    @SerializedName("usd_n")
    private String usd_n;
    @SerializedName("usd_s")
    private String usd_s;
    @SerializedName("kurs")
    private String kurs;
    @SerializedName("idr")
    private String idr;
    @SerializedName("unik")
    private String unik;
    @SerializedName("total")
    private String total;
    @SerializedName("status")
    private String status;

    public String getType_order() {
        return type_order;
    }

    public void setType_order(String type_order) {
        this.type_order = type_order;
    }

    public String getAkun() {
        return akun;
    }

    public void setAkun(String akun) {
        this.akun = akun;
    }

    public String getPay_to() {
        return pay_to;
    }

    public void setPay_to(String pay_to) {
        this.pay_to = pay_to;
    }

    public String getPay_number() {
        return pay_number;
    }

    public void setPay_number(String pay_number) {
        this.pay_number = pay_number;
    }

    public String getPay_name() {
        return pay_name;
    }

    public void setPay_name(String pay_name) {
        this.pay_name = pay_name;
    }

    public String getUsd() {
        return usd;
    }

    public void setUsd(String usd) {
        this.usd = usd;
    }

    public String getUsd_n() {
        return usd_n;
    }

    public void setUsd_n(String usd_n) {
        this.usd_n = usd_n;
    }

    public String getUsd_s() {
        return usd_s;
    }

    public void setUsd_s(String usd_s) {
        this.usd_s = usd_s;
    }

    public String getKurs() {
        return kurs;
    }

    public void setKurs(String kurs) {
        this.kurs = kurs;
    }

    public String getIdr() {
        return idr;
    }

    public void setIdr(String idr) {
        this.idr = idr;
    }

    public String getUnik() {
        return unik;
    }

    public void setUnik(String unik) {
        this.unik = unik;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
