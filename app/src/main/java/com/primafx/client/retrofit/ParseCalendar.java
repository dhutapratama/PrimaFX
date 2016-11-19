package com.primafx.client.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Technical on 11/18/2016.
 */

public class ParseCalendar {

    // Callback Parameter
    @SerializedName("error")
    private Boolean error;
    @SerializedName("message")
    private String message;
    @Expose
    private List<ResponseData> data;

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
        @SerializedName("IdNews")
        private String IdNews;
        @SerializedName("Date")
        private String Date;
        @SerializedName("Time")
        private String Time;
        @SerializedName("HourMinute")
        private String HourMinute;
        @SerializedName("Week")
        private String Week;
        @SerializedName("Symbol")
        private String Symbol;
        @SerializedName("Country")
        private String Country;
        @SerializedName("Title")
        private String Title;
        @SerializedName("Description")
        private String Description;
        @SerializedName("Importance")
        private String Importance;
        @SerializedName("Actual")
        private String Actual;
        @SerializedName("Forecast")
        private String Forecast;
        @SerializedName("Previous")
        private String Previous;
        @SerializedName("Status")
        private String Status;

        public String getIdNews() {
            return IdNews;
        }


        public String getTime() {
            return Time;
        }

        public String getActual() {
            return Actual;
        }

        public String getCountry() {
            return Country;
        }

        public String getDescription() {
            return Description;
        }

        public String getForecast() {
            return Forecast;
        }

        public String getHourMinute() {
            return HourMinute;
        }

        public String getImportance() {
            return Importance;
        }

        public String getPrevious() {
            return Previous;
        }

        public String getStatus() {
            return Status;
        }

        public String getSymbol() {
            return Symbol;
        }

        public String getTitle() {
            return Title;
        }

        public String getWeek() {
            return Week;
        }


        public String getDate() {
            return Date;
        }

    }
}
