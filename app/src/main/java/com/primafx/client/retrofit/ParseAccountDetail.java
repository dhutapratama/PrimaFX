package com.primafx.client.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Technical on 11/23/2016.
 */

public class ParseAccountDetail {
    // Post Parameter
    private String akun;
    private String login_hash;
    private String phone_pass;
    private String app;
    // Reply Parameter
    @SerializedName("error")
    private Boolean error;
    @SerializedName("message")
    private String message;
    @Expose
    private ResponseData data;

    public ParseAccountDetail(String akun, String login_hash) {
        this.akun = akun;
        this.login_hash = login_hash;
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

    public class ResponseData {

        @Expose
        private DataAkun dAkun;
        @Expose
        private DataAkunRebate dAkunRbt;

        public class DataAkun {
            @SerializedName("tgl_input")
            private String tgl_input;
            @SerializedName("akun")
            private String akun;
            @SerializedName("Date_Register")
            private String Date_Register;
            @SerializedName("nama")
            private String nama;
            @SerializedName("phone")
            private String phone;
            @SerializedName("email")
            private String email;
            @SerializedName("kode_agen")
            private String kode_agen;
            @SerializedName("pay_to")
            private String pay_to;
            @SerializedName("pay_number")
            private String pay_number;
            @SerializedName("pay_name")
            private String pay_name;
            @SerializedName("Address")
            private String Address;
            @SerializedName("City")
            private String City;
            @SerializedName("State")
            private String State;
            @SerializedName("Country")
            private String Country;
            @SerializedName("Currency")
            private String Currency;
            @SerializedName("Server")
            private String Server;
            @SerializedName("sisa_rebate")
            private String sisa_rebate;

            public String getSisa_rebate() {
                return sisa_rebate;
            }

            public String getAddress() {
                return Address;
            }

            public String getAkun() {
                return akun;
            }

            public String getCity() {
                return City;
            }

            public String getCountry() {
                return Country;
            }

            public String getCurrency() {
                return Currency;
            }

            public String getDate_Register() {
                return Date_Register;
            }

            public String getEmail() {
                return email;
            }

            public String getKode_agen() {
                return kode_agen;
            }

            public String getNama() {
                return nama;
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

            public String getPhone() {
                return phone;
            }

            public String getServer() {
                return Server;
            }

            public String getState() {
                return State;
            }

            public String getTgl_input() {
                return tgl_input;
            }
        }

        public class DataAkunRebate {
            @SerializedName("tgl_input")
            private String tgl_input;
            @SerializedName("pay_to")
            private String pay_to;
            @SerializedName("pay_number")
            private String pay_number;
            @SerializedName("pay_name")
            private String pay_name;
            @SerializedName("email")
            private String email;
            @SerializedName("phone")
            private String phone;

            public String getTgl_input() {
                return tgl_input;
            }

            public String getPhone() {
                return phone;
            }

            public String getPay_to() {
                return pay_to;
            }

            public String getEmail() {
                return email;
            }

            public String getPay_name() {
                return pay_name;
            }

            public String getPay_number() {
                return pay_number;
            }
        }

        public DataAkun getdAkun() {
            return dAkun;
        }

        public DataAkunRebate getdAkunRbt() {
            return dAkunRbt;
        }
    }
}