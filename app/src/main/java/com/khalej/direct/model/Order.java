package com.khalej.direct.model;

import com.google.gson.annotations.SerializedName;

public class Order {
    @SerializedName("id")
    int id;
    @SerializedName("name_to")
    String name;
    @SerializedName("des")
    String details;
    @SerializedName("created_at")
    String date;
    @SerializedName("total")
    String Price;
    @SerializedName("latitude_from")
    double latfrom;
    @SerializedName("latitude_to")
    double latTo;
    @SerializedName("longitude_from")
    double lngFrom;
    @SerializedName("longitude_to")
    double lngTo;
    @SerializedName("phone_to")
    String phone;
    @SerializedName("address_from")
    String address_from;
    @SerializedName("address_to")
    String address_to;
    @SerializedName("representative_name")
    String representative_name;
    @SerializedName("representative_phone")
    String representative_phone;
    @SerializedName("representative_image")
    String representative_image;

    public String getAddress_from() {
        return address_from;
    }

    public void setAddress_from(String address_from) {
        this.address_from = address_from;
    }

    public String getAddress_to() {
        return address_to;
    }

    public void setAddress_to(String address_to) {
        this.address_to = address_to;
    }

    public String getRepresentative_name() {
        return representative_name;
    }

    public void setRepresentative_name(String representative_name) {
        this.representative_name = representative_name;
    }

    public String getRepresentative_phone() {
        return representative_phone;
    }

    public void setRepresentative_phone(String representative_phone) {
        this.representative_phone = representative_phone;
    }

    public String getRepresentative_image() {
        return representative_image;
    }

    public void setRepresentative_image(String representative_image) {
        this.representative_image = representative_image;
    }

    public double getLatfrom() {
        return latfrom;
    }

    public void setLatfrom(double latfrom) {
        this.latfrom = latfrom;
    }

    public double getLatTo() {
        return latTo;
    }

    public void setLatTo(double latTo) {
        this.latTo = latTo;
    }

    public double getLngFrom() {
        return lngFrom;
    }

    public void setLngFrom(double lngFrom) {
        this.lngFrom = lngFrom;
    }

    public double getLngTo() {
        return lngTo;
    }

    public void setLngTo(double lngTo) {
        this.lngTo = lngTo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

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

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }
}


