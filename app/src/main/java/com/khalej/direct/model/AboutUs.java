package com.khalej.direct.model;

import com.google.gson.annotations.SerializedName;

public class AboutUs {
    @SerializedName("id")
    int id;
    @SerializedName("content")
    String ar_content;
    @SerializedName("en_content")
    String en_content;
    @SerializedName("link")
    String link;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAr_content() {
        return ar_content;
    }

    public void setAr_content(String ar_content) {
        this.ar_content = ar_content;
    }

    public String getEn_content() {
        return en_content;
    }

    public void setEn_content(String en_content) {
        this.en_content = en_content;
    }
}
