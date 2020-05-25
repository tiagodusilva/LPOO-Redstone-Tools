package com.lpoo.redstonetools.view.lanterna.tile;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.lpoo.redstonetools.model.tile.ComparatorTile;
import com.lpoo.redstonetools.model.tile.Tile;
import com.lpoo.redstonetools.model.utils.Power;

public class LanternaComparatorTileView extends LanternaTileView {

    @Override
    public void render(Tile tile, int row, int column, TextGraphics graphics) {
        renderPowerSensitiveFrame(graphics, tile, column, row);

        graphics.setForegroundColor(getPowerColor(Power.getMax()));
        graphics.setCharacter(column + 1, row + 1, ((ComparatorTile)tile).getSubtractMode() ? '\u236D' : '\u2994');
    }
}
