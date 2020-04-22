package com.lpoo.redstonetools.graphics.lanterna.tiles;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.lpoo.redstonetools.core.tiles.LeverTile;
import com.lpoo.redstonetools.core.utils.Side;
import com.lpoo.redstonetools.graphics.lanterna.LanternaTileRenderer;

public class LanternaLeverTileRenderer extends LanternaTileRenderer<LeverTile> {


    public LanternaLeverTileRenderer(Screen screen) {
        super(screen);
    }

    @Override
    public void render(LeverTile tile, int row, int column) {
        TextGraphics graphics = screen.newTextGraphics();

        renderFrame(graphics, column, row);

        graphics.setForegroundColor(TextColor.Factory.fromString(getPowerColor(tile.getPower(Side.UP))));
        graphics.setCharacter(column + 1, row + 1, tile.isActivated() ? '\u2584' : '\u2580');
    }
}
