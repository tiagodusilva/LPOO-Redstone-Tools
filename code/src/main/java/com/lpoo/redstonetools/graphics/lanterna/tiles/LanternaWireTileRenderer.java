package com.lpoo.redstonetools.graphics.lanterna.tiles;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.lpoo.redstonetools.core.tiles.Tile;
import com.lpoo.redstonetools.core.tiles.WireTile;
import com.lpoo.redstonetools.core.utils.Side;
import com.lpoo.redstonetools.graphics.lanterna.LanternaTileRenderer;

public class LanternaWireTileRenderer extends LanternaTileRenderer<WireTile> {

    /**
     * LEFT | RIGHT | DOWN | UP
     * &#x29C9;  &#x29C8;  &#xD83D;&#xDF55;  &#x233E; &#x26CB;
     * &#x2550;
     * &#x2551;
     * &#x2560;
     * &#x2563;
     * &#x2566;
     * &#x2569;
     * &#x256C;
     * &#x255D;
     * &#x255A;
     * &#x2557;
     * &#x2554;
     */
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

    public LanternaWireTileRenderer(Screen screen) {
        super(screen);
    }

    @Override
    public void render(WireTile tile, int row, int column) {
        TextGraphics graphics = screen.newTextGraphics();
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
