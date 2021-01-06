package com.khalej.direct.model;

import com.google.gson.annotations.SerializedName;

public class chatRoom {
    @SerializedName("data")
    roomId data;

    public roomId getData() {
        return data;
    }

    public void setData(roomId data) {
        this.data = data;
    }
}
