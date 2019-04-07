package com.bradykoehler.cs246project;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * The Image class holds data about images retrieved from
 * the API
 */
public class Image implements Serializable {
    @SerializedName("image_id")
    private int id;
    private String name;
    private String data;
    private String sound;

    /**
     * Default constructor for an Image
     * @param name the name of the Image
     * @param data the url of the image file
     * @param sound the url of the sound file
     */
    public Image(String name, String data, String sound) {
        this.name = name;
        this.data = data;
        this.sound = sound;
    }

    /**
     * Returns the ID of the Image
     * @return id
     */
    public int getId() { return id; }

    /**
     * Returns the name of the Image
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the Image
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the file URL of the Image
     * @return
     */
    public String getData() {
        return data;
    }

    /**
     * Sets the file URL of the Image
     * @param data
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * Returns the URL of the sound file for the Image
     * @return
     */
    public String getSound() {
        return sound;
    }

    /**
     * Sets the URL of the Image sound file
     * @param sound
     */
    public void setSound(String sound) {
        this.sound = sound;
    }
}
