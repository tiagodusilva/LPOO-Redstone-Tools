package com.lpoo.redstonetools.graphics.lanterna;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.lpoo.redstonetools.core.tiles.Tile;
import com.lpoo.redstonetools.core.utils.Side;
import com.lpoo.redstonetools.graphics.TileRenderer;
import com.lpoo.redstonetools.core.utils.Power;

public abstract class LanternaTileRenderer<T extends Tile> extends TileRenderer<T> {

    protected Screen screen;

    public LanternaTileRenderer(Screen screen) {
        this.screen = screen;
    }

    public String getOutputColor() {
        return "#CC6E1C";
    }

    public String getInputColor() {
        return "#3497E2";
    }

    public String getPowerColor(int power) {
//        int minPowerColor = 120;
//        int maxPowerColor = 240;

        if (power == Power.getMin())
            return "#500000";

        String red_component = Integer.toHexString((int) (120 + Power.normalize(power) * (240 - 120)));
        if (red_component.length() == 1)
            red_component += '0';
        return "#" + red_component + "0000";
    }

    public void renderFrame(TextGraphics graphics, int column, int row) {
        graphics.setForegroundColor(TextColor.ANSI.WHITE);
        graphics.putString(column, row, "\u250F\u2501\u2513");
        graphics.putString(column, row + 1, "\u2503 \u2503");
        graphics.putString(column, row + 2, "\u2517\u2501\u251B");
    }

    public void renderPowerSensitiveFrame(TextGraphics graphics, Tile tile, int column, int row) {
        graphics.setForegroundColor(TextColor.ANSI.WHITE);
        graphics.putString(column, row, "\u250F\u2501\u2513");
        graphics.putString(column, row + 1, "\u2503 \u2503");
        graphics.putString(column, row + 2, "\u2517\u2501\u251B");

        // Code smell, but the alternative would be quite more costly
        if (tile.acceptsPower(Side.UP)) {
            graphics.setForegroundColor(TextColor.Factory.fromString(getInputColor()));
            graphics.setCharacter(column + 1, row, '\u2565');
        } else if (tile.outputsPower(Side.UP)) {
            graphics.setForegroundColor(TextColor.Factory.fromString(getOutputColor()));
            graphics.setCharacter(column + 1, row, '\u2568');
        }

        if (tile.acceptsPower(Side.DOWN)) {
            graphics.setForegroundColor(TextColor.Factory.fromString(getInputColor()));
            graphics.setCharacter(column + 1, row + 2, '\u2568');
        } else if (tile.outputsPower(Side.DOWN)) {
            graphics.setForegroundColor(TextColor.Factory.fromString(getOutputColor()));
            graphics.setCharacter(column + 1, row + 2, '\u2565');
        }

        if (tile.acceptsPower(Side.RIGHT)) {
            graphics.setForegroundColor(TextColor.Factory.fromString(getInputColor()));
            graphics.setCharacter(column + 2, row + 1, '\u2561');
        } else if (tile.outputsPower(Side.RIGHT)) {
            graphics.setForegroundColor(TextColor.Factory.fromString(getOutputColor()));
            graphics.setCharacter(column + 2, row + 1, '\u255e');
        }

        if (tile.acceptsPower(Side.LEFT)) {
            graphics.setForegroundColor(TextColor.Factory.fromString(getInputColor()));
            graphics.setCharacter(column, row + 1, '\u255e');
        }
        else if (tile.outputsPower(Side.LEFT)) {
            graphics.setForegroundColor(TextColor.Factory.fromString(getOutputColor()));
            graphics.setCharacter(column, row + 1, '\u2561');
        }
    }

}
