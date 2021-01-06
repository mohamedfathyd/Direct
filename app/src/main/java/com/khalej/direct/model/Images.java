package com.khalej.direct.model;

import com.google.gson.annotations.SerializedName;

public class Images {
    @SerializedName("id")
    int id;

    @SerializedName("image")
    String image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
