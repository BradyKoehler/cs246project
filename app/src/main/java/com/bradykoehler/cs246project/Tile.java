package com.bradykoehler.cs246project;

import com.google.gson.annotations.SerializedName;

/**
 * The Tile class holds data retrieved from the API.
 * A Tile belongs to a Grid.
 */
public class Tile {
    @SerializedName("tile_id")
    private int id;
    private String name;
    private int position;
    public Image image;
    private String data;
    private String sound;

    /**
     * Returns the ID of the Tile
     * @return id
     */
    public int getId() { return id; }

    /**
     * Set the ID of the Tile
     * @param id Tile id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the name of the Tile
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the Tile
     * @param name New name for the Tile
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the position of the Tile
     * @return position
     */
    public int getPosition() { return position; }

    /**
     * Sets the position of the Tile
     * @param position New name for the Tile
     */
    public void setPosition(int position) { this.position = position; }

    /**
     * Returns the image URL for the Tile
     * @return image url
     */
    public String getData() { return data; }

    /**
     * Returns the audio URL for the Tile
     * @return audio url
     */
    public String getSound() { return sound; }
}
