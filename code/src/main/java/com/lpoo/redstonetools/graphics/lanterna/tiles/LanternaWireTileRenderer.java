package com.lpoo.redstonetools.graphics.lanterna.tiles;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.lpoo.redstonetools.core.tiles.Tile;
import com.lpoo.redstonetools.core.tiles.WireTile;
import com.lpoo.redstonetools.core.utils.Side;
import com.lpoo.redstonetools.graphics.lanterna.LanternaTileRenderer;

public class LanternaWireTileRenderer extends LanternaTileRenderer<WireTile> {

    public LanternaWireTileRenderer(Screen screen) {
        super(screen);
    }

    @Override
    public void render(WireTile tile, int row, int column) {
        TextGraphics graphics = screen.newTextGraphics();
        String red_component = Integer.toHexString(tile.getPower(Side.UP) * 14 + 40);
        if (red_component.length() == 1)
            red_component += '0';
        String color = "#" + red_component + "0000";
        graphics.setForegroundColor(TextColor.Factory.fromString(color));
        graphics.setCharacter(column + 1, row + 1, 'W');
    }
}
