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
        String red_component = Integer.toHexString(tile.getPower(Side.UP) * 14 + 40);
        if (red_component.length() == 1)
            red_component += '0';
        String color = "#" + red_component + "0000";

        graphics.putString(column, row, "\u250F\u2501\u2513");
        graphics.putString(column, row + 1, "\u2503 \u2503");
        graphics.putString(column, row + 2, "\u2517\u2501\u251B");

        graphics.setForegroundColor(TextColor.Factory.fromString(color));
        graphics.setCharacter(column + 1, row + 1, tile.isActivated() ? '\u2584' : '\u2580');
    }
}
