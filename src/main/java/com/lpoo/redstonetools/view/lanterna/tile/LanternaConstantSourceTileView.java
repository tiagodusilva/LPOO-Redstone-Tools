package com.lpoo.redstonetools.view.lanterna.tile;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.lpoo.redstonetools.model.tile.Tile;
import com.lpoo.redstonetools.model.utils.Power;

public class LanternaConstantSourceTileView extends LanternaTileView {

    @Override
    public void render(Tile tile, int row, int column, TextGraphics graphics) {
        renderFrame(graphics, column, row);

        graphics.setForegroundColor(getPowerColor(Power.getMax()));
        graphics.setCharacter(column + 1, row + 1, '\u0424');
    }
}
