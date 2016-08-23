package com.primafx.client.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ParseGoogleSignin {
    // Post Parameter
    private String google_token;
    // Reply Parameter
    @SerializedName("code")
    private String code;
    @SerializedName("message")
    private String message;
    @Expose
    private ResponseData data;

    public ParseGoogleSignin(String google_token) {
        this.google_token = google_token;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
        @SerializedName("login_code")
        private String login_code;

        public String getLogin_code() {
            return login_code;
        }

        public void setLogin_code(String login_code) {
            this.login_code = login_code;
        }
    }
}