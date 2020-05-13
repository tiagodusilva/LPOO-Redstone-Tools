package com.lpoo.redstonetools.model.utils;

import java.io.Serializable;
import java.util.Objects;

/**
 *  <h1>Position</h1>
 *  Position in 2D discrete coordinate system
 *
 * @author g79
 */
public class Position implements Serializable {

    /**
     * <h1>Coordinate on the X-axis</h1>
     */
    private int x;

    /**
     * <h1>Coordinate on the Y-axis</h1>
     */
    private int y;

    /**
     * <h1>Position constructor</h1>
     *
     * @param x     Coordinate on the x-axis
     * @param y     Coordinate on the y-axis
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * <h1>Get value of the <code>x</code> coordinate</h1>
     *
     * @return  X coordinate value
     */
    public int getX() {
        return x;
    }

    /**
     * <h1>Sets value of the <code>x</code> coordinate</h1>
     *
     * @param  x    Value of X coordinate to be set to
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * <h1>Get value of the <code>y</code> coordinate</h1>
     *
     * @return  Y coordinate value
     */
    public int getY() {
        return y;
    }

    /**
     * <h1>Sets value of the <code>y</code> coordinate</h1>
     *
     * @param  y    Value of Y coordinate to be set to
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * <h1>Get adjacent position on the side specified</h1>
     *
     * @param side  Side wanted
     * @return  Adjacent position on the side specified
     */
    public Position getNeighbour(Side side) {
        switch (side) {
            case UP:
                return new Position(x, y - 1);
            case DOWN:
                return new Position(x, y + 1);
            case LEFT:
                return new Position(x - 1, y);
            case RIGHT:
                return new Position(x + 1, y);
        }
        return new Position(-1, -1);
    }

    /**
     * <h1>Tests if positions are equal</h1>
     * Position are equal if the X coordinate and Y coordinate are equal
     *
     * @param o Object to be compared to
     * @return true if position are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x &&
                y == position.y;
    }

    /**
     * <h1>Create hash from the values of the coordinate</h1>
     *
     * @return  Value of the hash
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * <h1>Transform position to String</h1>
     *
     * @return  Position in String form
     */
    @Override
    public String toString() {
        return "Position(" + x + ", " + y + ")";
    }

    /**
     * <h1>Create a copy of a position</h1>
     *
     * @return  Clone of the position
     */
    public Position clone() {
        return new Position(this.x, this.y);
    }

}
