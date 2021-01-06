package com.khalej.direct.model;

import com.google.gson.annotations.SerializedName;

public class notificationData {
    @SerializedName("ar_notification_title")
    String name_ar;
    @SerializedName("en_notification_title")
    String name_en;
    @SerializedName("ar_notification_body")
    String body_ar;
    @SerializedName("en_notification_body")
    String body_en;

    @SerializedName("created_at")
    String date;

    public String getName_ar() {
        return name_ar;
    }

    public void setName_ar(String name_ar) {
        this.name_ar = name_ar;
    }

    public String getName_en() {
        return name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public String getBody_ar() {
        return body_ar;
    }

    public void setBody_ar(String body_ar) {
        this.body_ar = body_ar;
    }

    public String getBody_en() {
        return body_en;
    }

    public void setBody_en(String body_en) {
        this.body_en = body_en;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
