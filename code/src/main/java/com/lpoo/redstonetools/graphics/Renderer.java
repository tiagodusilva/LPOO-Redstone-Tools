package com.lpoo.redstonetools.graphics;

public interface Renderer<T> {
    void render(T object, int row, int column);
}
