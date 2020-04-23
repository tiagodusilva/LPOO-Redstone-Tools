package com.lpoo.redstonetools.graphics.lanterna.tiles;

import com.googlecode.lanterna.screen.Screen;
import com.lpoo.redstonetools.core.tiles.NullTile;
import com.lpoo.redstonetools.graphics.lanterna.LanternaTileRenderer;

public class LanternaNullTileRenderer extends LanternaTileRenderer<NullTile> {

    public LanternaNullTileRenderer(Screen screen) {
        super(screen);
    }

    @Override
    public void render(NullTile tile, int row, int column) {    }
}
