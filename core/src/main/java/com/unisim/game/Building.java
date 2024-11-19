package com.unisim.game;

/**
 *Represents a building, with a specified file path and size.
 *
 * <p>This class provides a deepCopy method, and the additional methods for implementation of this.</p>
 */
public class Building {
    /** The file path for the building's texture. */
    private String filepath;
    /** Size of building - can only be 2 or 3. */
    private int size;

    public Building(String filepath, int size) {
        this.filepath = filepath;
        if (size == 2 || size == 3) this.size = size;
        else this.size = 2;
    }

    /**Returns the filepath of the building image*/
    public String getName() {
        return filepath;
    }

    public int getSize() {
        return size;
    }

    /**
     * @return A new {@link Building} instance with the same attributes.
     */
    public Building deepCopy() {
        return new Building(getName(), getSize());
    }
}
