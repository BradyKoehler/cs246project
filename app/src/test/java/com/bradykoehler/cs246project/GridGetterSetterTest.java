package com.bradykoehler.cs246project;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class GridGetterSetterTest {
    private static Grid grid;

    @BeforeClass
    public static void grid_initialize() {
        grid = new Grid();
    }

    @Test
    public void name_isSet() {
        assertEquals(null, grid.getName());
        grid.setName("My Grid");
        assertEquals("My Grid", grid.getName());
    }

    @Test
    public void width_isSet() {
        assertEquals(0, grid.getWidth());
        grid.setWidth(3);
        assertEquals(3, grid.getWidth());
    }

    @Test
    public void height_isSet() {
        assertEquals(0, grid.getHeight());
        grid.setHeight(4);
        assertEquals(4, grid.getHeight());
    }
}