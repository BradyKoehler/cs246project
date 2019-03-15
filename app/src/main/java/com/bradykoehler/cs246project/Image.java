package com.bradykoehler.cs246project;

import com.google.gson.annotations.SerializedName;

public class Image {
    @SerializedName("image_id")
    private int id;
    private String name;
    private String data;
    private String sound;

    public Image(String name, String data, String sound) {
        this.name = name;
        this.data = data;
        this.sound = sound;
    }

    public int getId() { return id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }
}
