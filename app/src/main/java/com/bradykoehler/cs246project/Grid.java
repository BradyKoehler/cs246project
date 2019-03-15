package com.bradykoehler.cs246project;

import com.google.gson.annotations.SerializedName;

public class Grid {
    @SerializedName("grid_id")
    private int id;
    private String name;
    private int width;
    private int height;

    public Grid(String name) {
        this.name = name;
        this.width = 3;
        this.height = 3;
    }

    public int getId() { return id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
