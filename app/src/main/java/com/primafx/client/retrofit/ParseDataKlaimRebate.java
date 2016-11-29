package com.primafx.client.retrofit;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Technical on 11/15/2016.
 */

public class ParseDataKlaimRebate {
    @SerializedName("akun")
    private String akun;
    @SerializedName("nama")
    private String nama;
    @SerializedName("phone")
    private String phone;
    @SerializedName("email")
    private String email;
    @SerializedName("pay_to")
    private String pay_to;
    @SerializedName("pay_number")
    private String pay_number;
    @SerializedName("pay_name")
    private String pay_name;


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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

}
