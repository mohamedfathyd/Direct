package com.khalej.direct.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Companys {
    @SerializedName("id")
    int id;
    @SerializedName("name")
    String name;
    @SerializedName("logo")
    String logo;
    @SerializedName("images")
    List<Images> images;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public List<Images> getImages() {
        return images;
    }

    public void setImages(List<Images> images) {
        this.images = images;
    }
}
