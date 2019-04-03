package com.bradykoehler.cs246project;

public class Tile {

    private String audioFileName;
    private String imageFileName;
    private String name;
    private int position;
    public Image image;
    private String data;
    private String sound;


    public String getAudioFileName() {
        return audioFileName;
    }

    public void setAudioFileName(String audioFileName) {
        this.audioFileName = audioFileName;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

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
