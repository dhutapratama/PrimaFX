package com.primafx.client.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ParseAuthenticate {
    // Reply Parameter
    @SerializedName("code")
    private String code;
    @SerializedName("message")
    private String message;
    @Expose
    private ResponseData data;

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
        @SerializedName("email")
        private String email;

        @SerializedName("name")
        private String name;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
