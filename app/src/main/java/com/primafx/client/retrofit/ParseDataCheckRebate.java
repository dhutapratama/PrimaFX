package com.primafx.client.retrofit;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Technical on 11/22/2016.
 */

public class ParseDataCheckRebate {

    @SerializedName("id")
    private String id;
    @SerializedName("ticket")
    private String ticket;
    @SerializedName("Date")
    private String Date;
    @SerializedName("InputDateTime")
    private String InputDateTime;
    @SerializedName("InstaDateTime")
    private String InstaDateTime;
    @SerializedName("akun")
    private String akun;
    @SerializedName("profit")
    private String profit;
    @SerializedName("type")
    private String type;
    @SerializedName("detail")
    private String detail;
    @SerializedName("info")
    private String info;

    public String getDate() {
        return Date;
    }

    public String getAkun() {
        return akun;
    }

    public String getDetail() {
        return detail;
    }

    public String getId() {
        return id;
    }

    public String getInfo() {
        return info;
    }

    public String getInputDateTime() {
        return InputDateTime;
    }

    public String getInstaDateTime() {
        return InstaDateTime;
    }

    public String getProfit() {
        return profit;
    }

    public String getTicket() {
        return ticket;
    }

    public String getType() {
        return type;
    }
}
