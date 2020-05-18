package com.lpoo.redstonetools.view.lanterna.tile;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.lpoo.redstonetools.model.tile.Tile;
import com.lpoo.redstonetools.model.utils.Power;

public class LanternaCounterTileView extends LanternaTileView {
    @Override
    public void render(Tile tile, int row, int column, TextGraphics graphics) {
        renderPowerSensitiveFrame(graphics, tile, column, row);

        graphics.setForegroundColor(TextColor.Factory.fromString(getPowerColor(Power.getMax())));
        graphics.setCharacter(column + 1, row + 1, 'C');
    }
}
