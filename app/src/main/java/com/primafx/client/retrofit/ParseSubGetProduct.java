package com.primafx.client.retrofit;

import com.google.gson.annotations.SerializedName;

public class ParseSubGetProduct {
    @SerializedName("product_key")
    private String product_key;
    @SerializedName("product_name")
    private String product_name;
    @SerializedName("price")
    private String price;
    @SerializedName("image")
    private String image;

    public String getProduct_key() {
        return product_key;
    }
    public void setProduct_key(String product_key) {
        this.product_key = product_key;
    }
    public String getProduct_name() {
        return product_name;
    }
    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
}
