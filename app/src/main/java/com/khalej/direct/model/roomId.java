package com.khalej.direct.model;

import com.google.gson.annotations.SerializedName;

public class roomId {
    @SerializedName("room_id")
    int room_id;

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }
}

