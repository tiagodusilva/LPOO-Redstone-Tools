package com.lpoo.redstonetools.graphics.lanterna;

import com.googlecode.lanterna.screen.Screen;
import com.lpoo.redstonetools.core.tiles.Tile;
import com.lpoo.redstonetools.graphics.TileRenderer;

public abstract class LanternaTileRenderer<T extends Tile> extends TileRenderer<T> {

    protected Screen screen;

    public LanternaTileRenderer(Screen screen) {
        this.screen = screen;
    }
}
