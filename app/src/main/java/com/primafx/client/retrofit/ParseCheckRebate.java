package com.primafx.client.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Technical on 11/22/2016.
 */

public class ParseCheckRebate {

    // Post Parameter
    private String akun;
    private String authKey;
    private String periode;
    private String app;

    // Reply Parameter
    @SerializedName("error")
    private Boolean error;
    @SerializedName("message")
    private String message;


    @Expose
    private ResponseData data;

    public ParseCheckRebate(String akun, String authKey, String periode) {
        this.akun = akun;
        this.authKey = authKey;
        this.periode = periode;
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

    public class ResponseData {
        @Expose
        private SummaryData summary;
        @Expose
        private List<ParseDataCheckRebate> detail;

        public SummaryData getSummary() {
            return summary;
        }

        public List<ParseDataCheckRebate> getDetail() {
            return detail;
        }

        public class SummaryData {
            @SerializedName("periode")
            private String periode;
            @SerializedName("credit")
            private String credit;
            @SerializedName("debit")
            private String debit;
            @SerializedName("sisa")
            private String sisa;

            public String getCredit() {
                return credit;
            }

            public String getDebit() {
                return debit;
            }

            public String getPeriode() {
                return periode;
            }

            public String getSisa() {
                return sisa;
            }

            public void setCredit(String credit) {
                this.credit = credit;
            }

            public void setDebit(String debit) {
                this.debit = debit;
            }

            public void setPeriode(String periode) {
                this.periode = periode;
            }

            public void setSisa(String sisa) {
                this.sisa = sisa;
            }
        }

    }

}
