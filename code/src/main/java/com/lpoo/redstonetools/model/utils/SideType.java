package com.lpoo.redstonetools.model.utils;

/**
 * <h1>Side Type</h1>
 * Type of the side of the tile
 *
 * Default type defines a side that doesn't interact with others
 * Input type defines a side that accepts power
 * Output type defines a side that outputs power
 */
public enum SideType {
    DEFAULT, INPUT, OUTPUT;

    /**
     * <h1>Check if side is input type</h1>
     *
     * @return  true if side is input, false otherwise
     */
    public boolean isInput() {
        return this == SideType.INPUT;
    }

    /**
     * <h1>Check if side is output type</h1>
     *
     * @return  true if side is output, false otherwise
     */
    public boolean isOutput() {
        return this == SideType.OUTPUT;
    }
}
