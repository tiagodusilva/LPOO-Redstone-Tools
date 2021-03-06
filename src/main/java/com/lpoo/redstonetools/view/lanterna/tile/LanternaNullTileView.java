package com.lpoo.redstonetools.view.lanterna.tile;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.lpoo.redstonetools.model.tile.NullTile;
import com.lpoo.redstonetools.model.tile.Tile;

public class LanternaNullTileView extends LanternaTileView {

    @Override
    public void render(Tile tile, int row, int column, TextGraphics graphics) {
        if (((NullTile) tile).isBroken()) {
            renderBrokenFrame(graphics, column, row);
            graphics.setCharacter(column + 1, row + 1, 'F');
        }
        else {
            graphics.setCharacter(column + 1, row + 1, ' ');
        }
    }

}
