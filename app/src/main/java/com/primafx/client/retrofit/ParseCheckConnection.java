package com.primafx.client.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ParseCheckConnection {
    @SerializedName("error")
    private Boolean error;
    @SerializedName("code")
    private String code;
    @SerializedName("error_message")
    private String error_message;

    public Boolean getError() {
        return error;
    }
    public void setError(Boolean error) {
        this.error = error;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getError_message() {
        return error_message;
    }
    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    @Expose
    private ResponseData data;
    public ResponseData getData() {
        return data;
    }

    public class ResponseData {
        @SerializedName("type")
        private String type;

        public String getType() {
            return type;
        }
        public void setType(String type) {
            this.type = type;
        }
    }

    private String login_key;

    public ParseCheckConnection(String login_key) {
        this.login_key = login_key;
    }
}
