package com.lpoo.redstonetools.graphics.lanterna.tiles;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.lpoo.redstonetools.core.tiles.ConstantSourceTile;
import com.lpoo.redstonetools.graphics.lanterna.LanternaTileRenderer;

public class LanternaConstantSourceTileRenderer extends LanternaTileRenderer<ConstantSourceTile> {

    public LanternaConstantSourceTileRenderer(Screen screen) {
        super(screen);
    }

    @Override
    public void render(ConstantSourceTile tile, int row, int column) {
        TextGraphics graphics = screen.newTextGraphics();
        graphics.setForegroundColor(TextColor.Factory.fromString("#ff0000"));
        graphics.setCharacter(column + 1, row + 1, 'S');
    }
}
