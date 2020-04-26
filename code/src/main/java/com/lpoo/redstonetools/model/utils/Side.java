package com.lpoo.redstonetools.model.utils;

public enum Side {
    UP, DOWN, LEFT, RIGHT;

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
        return Side.UP; // << Compiler go BBRRRRRR
    }

}
