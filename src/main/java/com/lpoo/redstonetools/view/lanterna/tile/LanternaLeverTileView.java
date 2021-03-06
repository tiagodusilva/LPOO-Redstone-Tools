package com.lpoo.redstonetools.view.lanterna.tile;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.lpoo.redstonetools.model.tile.Tile;
import com.lpoo.redstonetools.model.utils.Power;
import com.lpoo.redstonetools.model.utils.Side;

public class LanternaLeverTileView extends LanternaTileView {

    @Override
    public void render(Tile tile, int row, int column, TextGraphics graphics) {
        renderFrame(graphics, column, row);
        int power = tile.getPower(Side.UP);
        TextColor oldBg = graphics.getBackgroundColor();
        graphics.setBackgroundColor(TextColor.ANSI.BLACK);
        graphics.setForegroundColor(getPowerColor(power));
        graphics.setCharacter(column + 1, row + 1, Power.isOn(power) ? '\u2584' : '\u2580');
        graphics.setBackgroundColor(oldBg);
    }
}
