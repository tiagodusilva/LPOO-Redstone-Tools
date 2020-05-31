package com.lpoo.redstonetools.view.lanterna.tile;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.lpoo.redstonetools.model.tile.DigitTile;
import com.lpoo.redstonetools.model.tile.Tile;

public class LanternaDigitTileView extends LanternaTileView {

    @Override
    public void render(Tile tile, int row, int column, TextGraphics graphics) {
        renderFrame(graphics, column, row);

        int power = ((DigitTile) tile).getPowerLevel();

        TextColor oldBg = graphics.getBackgroundColor();
        graphics.setBackgroundColor(TextColor.ANSI.BLACK);
        graphics.setForegroundColor(getPowerColor(power));

        String powerLevel = Integer.toHexString(power).toUpperCase();
        graphics.putString(column + 1, row + 1, powerLevel);

        graphics.setBackgroundColor(oldBg);
    }
}
