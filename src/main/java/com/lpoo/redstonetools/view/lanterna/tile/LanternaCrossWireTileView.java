package com.lpoo.redstonetools.view.lanterna.tile;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.lpoo.redstonetools.model.tile.Tile;
import com.lpoo.redstonetools.model.tile.WireTile;
import com.lpoo.redstonetools.model.utils.Side;

public class LanternaCrossWireTileView extends LanternaTileView {

    public LanternaCrossWireTileView() {
        super();
    }

    @Override
    public void render(Tile tile, int row, int column, TextGraphics graphics) {

        if (tile.isConnected(Side.UP)) {
            graphics.setForegroundColor(getPowerColor(tile.getPower(Side.UP)));
            graphics.setCharacter(column + 1, row, '\u2551');
        }
        if (tile.isConnected(Side.DOWN)) {
            graphics.setForegroundColor(getPowerColor(tile.getPower(Side.DOWN)));
            graphics.setCharacter(column + 1, row + 2, '\u2551');
        }
        if (tile.isConnected(Side.RIGHT)) {
            graphics.setForegroundColor(getPowerColor(tile.getPower(Side.RIGHT)));
            graphics.setCharacter(column + 2, row + 1, '\u2550');
        }
        if (tile.isConnected(Side.LEFT)) {
            graphics.setForegroundColor(getPowerColor(tile.getPower(Side.LEFT)));
            graphics.setCharacter(column, row + 1, '\u2550');
        }

        graphics.setForegroundColor(TextColor.Factory.fromString("#AFAFAF"));
        graphics.setCharacter(column + 1, row + 1, '\u256C');
    }
}
