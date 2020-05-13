package com.lpoo.redstonetools.model.utils;

/**
 *  <h1>Side</h1>
 *  Possible sides in 2D coordinate system
 *
 * @author g79
 */
public enum Side {
    UP, DOWN, LEFT, RIGHT;

    /**
     * <h1>Get opposite side of the side</h1>
     *
     * @return  Opposite side
     */
    public Side opposite() {
        switch (this) {
            case UP:
                return Side.DOWN;
            case DOWN:
                return Side.UP;
            case LEFT:
                return Side.RIGHT;
            case RIGHT:
                return Side.LEFT;
        }
        return Side.UP;
    }

    /**
     * <h1>Get side at the right of the side</h1>
     */
    public Side atRight() {
        switch (this) {
            case UP:
                return Side.RIGHT;
            case DOWN:
                return Side.LEFT;
            case LEFT:
                return Side.UP;
            case RIGHT:
                return Side.DOWN;
        }
        return Side.UP;
    }

    /**
     * <h1>Get side at the left of the side</h1>
     */
    public Side atLeft() {
        return atRight().opposite();
    }

}
