package com.lpoo.redstonetools.view.lanterna.tile;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.lpoo.redstonetools.model.tile.RepeaterTile;
import com.lpoo.redstonetools.model.tile.Tile;
import com.lpoo.redstonetools.model.utils.Power;

public class LanternaNotGateTileView extends LanternaTileView {

    @Override
    public void render(Tile tile, int row, int column, TextGraphics graphics) {
        renderPowerSensitiveFrame(graphics, tile, column, row);

        graphics.setForegroundColor(getPowerColor(Power.getMax()));
        graphics.setCharacter(column + 1, row + 1, '\u00AC');
    }
}
