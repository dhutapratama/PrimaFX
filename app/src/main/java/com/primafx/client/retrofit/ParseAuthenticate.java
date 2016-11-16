package com.primafx.client.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ParseAuthenticate {
    // Reply Parameter
    @SerializedName("error")
    private Boolean error;
    @SerializedName("message")
    private String message;
    @Expose
    private List<ResponseData> data;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
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
        @SerializedName("id")
        private String id;
        @SerializedName("google_id")
        private String google_id;
        @SerializedName("email")
        private String email;
        @SerializedName("password")
        private String password;
        @SerializedName("pin")
        private String pin;
        @SerializedName("email_verification_code")
        private String email_verification_code;
        @SerializedName("phone")
        private String phone;
        @SerializedName("phone_verification_code")
        private String phone_verification_code;
        @SerializedName("is_phone_verified")
        private String is_phone_verified;
        @SerializedName("is_activated")
        private String is_activated;
        @SerializedName("registration_date")
        private String registration_date;
        @SerializedName("MsgAddrs")
        private String MsgAddrs;
        @SerializedName("name")
        private String name;
        @SerializedName("address")
        private String address;
        @SerializedName("profile_image_url")
        private String profile_image_url;
        @SerializedName("ktp_image_url")
        private String ktp_image_url;
        @SerializedName("status")
        private String status;

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public String getGoogle_id() {
            return google_id;
        }

        public void setGoogle_id(String google_id) {
            this.google_id = google_id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public String getPin() {
            return pin;
        }

        public String getEmail_verification_code() {
            return email_verification_code;
        }

        public void setEmail_verification_code(String email_verification_code) {
            this.email_verification_code = email_verification_code;
        }

        public String getIs_phone_verified() {
            return is_phone_verified;
        }

        public void setIs_phone_verified(String is_phone_verified) {
            this.is_phone_verified = is_phone_verified;
        }

        public String getPhone() {
            return phone;
        }

        public String getPhone_verification_code() {
            return phone_verification_code;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public void setPhone_verification_code(String phone_verification_code) {
            this.phone_verification_code = phone_verification_code;
        }

        public String getIs_activated() {
            return is_activated;
        }

        public void setIs_activated(String is_activated) {
            this.is_activated = is_activated;
        }

        public String getRegistration_date() {
            return registration_date;
        }

        public void setRegistration_date(String registration_date) {
            this.registration_date = registration_date;
        }

        public String getMsgAddrs() {
            return MsgAddrs;
        }

        public void setMsgAddrs(String msgAddrs) {
            MsgAddrs = msgAddrs;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getProfile_image_url() {
            return profile_image_url;
        }

        public void setProfile_image_url(String profile_image_url) {
            this.profile_image_url = profile_image_url;
        }

        public String getKtp_image_url() {
            return ktp_image_url;
        }

        public void setKtp_image_url(String ktp_image_url) {
            this.ktp_image_url = ktp_image_url;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setPin(String pin) {
            this.pin = pin;
        }
    }
}
