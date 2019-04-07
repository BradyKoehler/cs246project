package com.bradykoehler.cs246project;

import com.google.gson.annotations.SerializedName;

/**
 * The Grid class is a container for Tiles.
 */
public class Grid {
    @SerializedName("grid_id")
    private int id;
    private String name;
    private int width;
    private int height;
    private int count;

    /**
     * Default constructor for a Grid
     * @param name the name of the Grid
     */
    Grid(String name) {
        this.name = name;
        this.width = 3;
        this.height = 3;
    }

    /**
     * Returns the ID of the Grid
     * @return id
     */
    public int getId() { return id; }

    /**
     * Set the ID of the Grid
     * @param id Grid id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the name of the Grid
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the Grid
     * @param name New name for the Grid
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the width of the Grid
     * @return width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Sets the width of the Grid
     * @param width Grid width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Gets the height of the Grid
     * @return height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Set the height of the Grid
     * @param height Grid height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Returns the number of tiles contained by the grid
     * @return count
     */
    int getCount() { return count; }

    /**
     * Set the number of tiles contained by the grid
     * @param count Tiles count
     */
    public void setCount(int count) {
        this.count = count;
    }
}
