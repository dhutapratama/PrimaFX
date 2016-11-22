package com.primafx.client.retrofit;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Technical on 11/22/2016.
 */

public class ParseDataHistory {

    @SerializedName("listCode")
    private String listCode;
    @SerializedName("type_order")
    private String type_order;
    @SerializedName("date")
    private String date;
    @SerializedName("tgl_order")
    private String tgl_order;
    @SerializedName("tgl_transfer")
    private String tgl_transfer;
    @SerializedName("tgl_proses")
    private String tgl_proses;
    @SerializedName("tgl_audit")
    private String tgl_audit;

    @SerializedName("akun")
    private String akun;
    @SerializedName("usd")
    private String usd;
    @SerializedName("kurs")
    private String kurs;
    @SerializedName("total")
    private String total;
    @SerializedName("pay_to")
    private String pay_to;
    @SerializedName("pay_number")
    private String pay_number;
    @SerializedName("pay_name")
    private String pay_name;
    @SerializedName("status")
    private String status;
    @SerializedName("result")
    private String result;

    public String getDate() {
        return date;
    }

    public String getAkun() {
        return akun;
    }

    public String getKurs() {
        return kurs;
    }

    public String getListCode() {
        return listCode;
    }

    public String getPay_name() {
        return pay_name;
    }

    public String getPay_number() {
        return pay_number;
    }

    public String getPay_to() {
        return pay_to;
    }

    public String getResult() {
        return result;
    }

    public String getStatus() {
        return status;
    }

    public String getTgl_audit() {
        return tgl_audit;
    }

    public String getTgl_order() {
        return tgl_order;
    }

    public String getTgl_proses() {
        return tgl_proses;
    }

    public String getTgl_transfer() {
        return tgl_transfer;
    }

    public String getTotal() {
        return total;
    }

    public String getType_order() {
        return type_order;
    }

    public String getUsd() {
        return usd;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setAkun(String akun) {
        this.akun = akun;
    }

    public void setKurs(String kurs) {
        this.kurs = kurs;
    }

    public void setListCode(String listCode) {
        this.listCode = listCode;
    }

    public void setPay_name(String pay_name) {
        this.pay_name = pay_name;
    }

    public void setPay_number(String pay_number) {
        this.pay_number = pay_number;
    }

    public void setPay_to(String pay_to) {
        this.pay_to = pay_to;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTgl_audit(String tgl_audit) {
        this.tgl_audit = tgl_audit;
    }

    public void setTgl_order(String tgl_order) {
        this.tgl_order = tgl_order;
    }

    public void setTgl_proses(String tgl_proses) {
        this.tgl_proses = tgl_proses;
    }

    public void setTgl_transfer(String tgl_transfer) {
        this.tgl_transfer = tgl_transfer;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public void setType_order(String type_order) {
        this.type_order = type_order;
    }

    public void setUsd(String usd) {
        this.usd = usd;
    }
}
