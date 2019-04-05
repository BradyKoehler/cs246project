package com.bradykoehler.cs246project;

public class Tile {

    private String name;
    private int position;
    public Image image;
    private String data;
    private String sound;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPosition() { return position; }

    public void setPosition(int position) { this.position = position; }

    public String getData() { return data; }

    public String getSound() { return sound; }
}
