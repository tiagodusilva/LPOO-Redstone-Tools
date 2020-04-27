package com.lpoo.redstonetools.view.lanterna.tile;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.lpoo.redstonetools.model.tile.ConstantSourceTile;
import com.lpoo.redstonetools.model.tile.Tile;
import com.lpoo.redstonetools.model.utils.Power;
import com.lpoo.redstonetools.model.utils.Side;

public class LanternaConstantSourceTileView extends LanternaTileView {

    @Override
    public void render(Tile tile, int row, int column, TextGraphics graphics) {
        renderFrame(graphics, row, column);

        graphics.setForegroundColor(TextColor.Factory.fromString(getPowerColor(Power.getMax())));
        graphics.setCharacter(column + 1, row + 1, '\u0424');
    }
}
