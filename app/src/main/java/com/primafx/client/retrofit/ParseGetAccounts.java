package com.primafx.client.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Technical on 11/25/2016.
 */

public class ParseGetAccounts {

    // Post Parameter
    private String login_hash;

    // Reply Parameter
    @SerializedName("error")
    private Boolean error;
    @SerializedName("message")
    private String message;
    @Expose
    private List<ResponseData> data;

    public ParseGetAccounts(String login_hash) {
        this.login_hash = login_hash;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public Boolean getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ResponseData> getData() {
        return data;
    }

    public class ResponseData {
        // Reply Parameter
        @SerializedName("akun")
        private String akun;
        @SerializedName("nama")
        private String nama;

        public String getAkun() {
            return akun;
        }

        public String getNama() {
            return nama;
        }
    }

}
