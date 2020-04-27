package com.lpoo.redstonetools.view.lanterna.tile;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.lpoo.redstonetools.model.tile.NullTile;
import com.lpoo.redstonetools.model.tile.Tile;

public class LanternaNullTileView extends LanternaTileView {

    @Override
    public void render(Tile tile, int row, int column, TextGraphics graphics) {
        graphics.setCharacter(column + 1, row + 1, ' ');
    }

}
