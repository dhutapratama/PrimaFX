package com.primafx.client.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ParseCheckUser {
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
        @SerializedName("search_id")
        private String search_id;
        @SerializedName("name")
        private String name;
        @SerializedName("email")
        private String email;
        @SerializedName("picture")
        private String picture;

        public String getType() {
            return type;
        }
        public void setType(String type) {
            this.type = type;
        }
        public String getSearch_id() {
            return search_id;
        }
        public void setSearch_id(String search_id) {
            this.search_id = search_id;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getEmail() {
            return email;
        }
        public void setEmail(String email) {
            this.email = email;
        }
        public String getPicture() {
            return picture;
        }
        public void setPicture(String picture) {
            this.picture = picture;
        }
    }

    private String login_key;
    private String search_id;

    public ParseCheckUser(String login_key, String search_id) {
        this.login_key = login_key;
        this.search_id = search_id;
    }
}
