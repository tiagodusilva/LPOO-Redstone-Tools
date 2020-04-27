package com.lpoo.redstonetools.view.lanterna.tile;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.lpoo.redstonetools.model.tile.Tile;
import com.lpoo.redstonetools.model.tile.WireTile;
import com.lpoo.redstonetools.model.utils.Side;

public class LanternaWireTileView extends LanternaTileView {

    private final Character[] characters = new Character[]{
            '\u2588',    // No connections
            '\u2551',    // UP
            '\u2551',    // DOWN
            '\u2551',    // UP & DOWN
            '\u2550',    // RIGHT
            '\u255A',    // RIGHT & UP
            '\u2554',    // RIGHT & DOWN
            '\u2560',    // RIGHT & DOWN & UP
            '\u2550',    // LEFT
            '\u255D',    // LEFT & UP
            '\u2557',    // LEFT & DOWN
            '\u2563',    // LEFT & DOWN & UP
            '\u2550',    // LEFT & RIGHT
            '\u2569',    // LEFT & RIGHT & UP
            '\u2566',    // LEFT & RIGHT & DOWN
            '\u256C'     // LEFT & RIGHT & DOWN  & UP
    };

    @Override
    public void render(Tile tile, int row, int column, TextGraphics graphics) {
        graphics.setForegroundColor(TextColor.Factory.fromString(getPowerColor(tile.getPower(Side.UP))));

        int sides = 0;
        if (tile.isConnected(Side.UP)) {
            graphics.setCharacter(column + 1, row, '\u2551');
            sides += 1;
        }
        if (tile.isConnected(Side.DOWN)) {
            graphics.setCharacter(column + 1, row + 2, '\u2551');
            sides += 2;
        }
        if (tile.isConnected(Side.RIGHT)) {
            graphics.setCharacter(column + 2, row + 1, '\u2550');
            sides += 4;
        }
        if (tile.isConnected(Side.LEFT)) {
            graphics.setCharacter(column, row + 1, '\u2550');
            sides += 8;
        }

        graphics.setCharacter(column + 1, row + 1, characters[sides]);
    }
}
