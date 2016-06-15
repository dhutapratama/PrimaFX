package com.primafx.client.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ParseGetProduct {
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
        @Expose
        private List<ParseSubGetProduct> products = new ArrayList<>();
        public List<ParseSubGetProduct> getProducts() {
            return products;
        }
        public void setProducts(List<ParseSubGetProduct> transactions) {
            this.products = transactions;
        }
    }
    private String login_key;

    public ParseGetProduct(String login_key) {
        this.login_key = login_key;
    }
}
