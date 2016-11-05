package com.primafx.client.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ParseEmailLogin {
    // Post Parameter
    private String email;
    private String password;

    // Reply Parameter
    @SerializedName("code")
    private String code;
    @SerializedName("message")
    private String message;
    @Expose
    private ResponseData data;

    public ParseEmailLogin(String email, String password) {
        this.email = email;
        this.password = password;
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