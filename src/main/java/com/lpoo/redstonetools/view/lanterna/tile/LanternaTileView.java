package com.lpoo.redstonetools.view.lanterna.tile;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.lpoo.redstonetools.model.tile.Tile;
import com.lpoo.redstonetools.model.utils.Power;
import com.lpoo.redstonetools.model.utils.Side;

public abstract class LanternaTileView {

    public abstract void render(Tile tile, int row, int column, TextGraphics graphics);

    private final TextColor foregroundColor = TextColor.ANSI.WHITE;
    private final TextColor backgroundColor = TextColor.ANSI.BLACK;
    private final TextColor outputColor = TextColor.Factory.fromString("#CC6E1C");
    private final TextColor inputColor = TextColor.Factory.fromString("#3497E2");
    private final TextColor baseRedstoneColor = TextColor.Factory.fromString("#500000");
    private final TextColor brokenColor = TextColor.Factory.fromString("#f9ed04");

    public TextColor getOutputColor() {
        return outputColor;
    }

    public TextColor getInputColor() {
        return inputColor;
    }

    public TextColor getBrokenColor() {
        return brokenColor;
    }

    public TextColor getForegroundColor() {
        return foregroundColor;
    }

    public TextColor getBackgroundColor() {
        return backgroundColor;
    }

    public TextColor getPowerColor(int power) {
        if (power == Power.getMin())
            return baseRedstoneColor;

        String red_component = Integer.toHexString((int) (120 + Power.normalize(power) * (240 - 120)));
        if (red_component.length() == 1)
            red_component += '0';
        return TextColor.Factory.fromString("#" + red_component + "0000");
    }

    public void renderBrokenFrame(TextGraphics graphics, int column, int row) {
        TextColor color = graphics.getForegroundColor();

        graphics.setForegroundColor(getBrokenColor());
        graphics.putString(column, row, "\u250F\u254D\u2513");
        graphics.putString(column, row + 1, "\u254F \u254F");
        graphics.putString(column, row + 2, "\u2517\u254D\u251B");

        graphics.setForegroundColor(color);
    }

    public void renderFrame(TextGraphics graphics, int column, int row) {
        graphics.putString(column, row, "\u250F\u2501\u2513");
        graphics.putString(column, row + 1, "\u2503 \u2503");
        graphics.putString(column, row + 2, "\u2517\u2501\u251B");
    }

    public void renderPowerSensitiveFrame(TextGraphics graphics, Tile tile, int column, int row, TextColor frameColor) {
        TextColor color = graphics.getForegroundColor();
        graphics.setForegroundColor(frameColor);
        renderPowerSensitiveFrame(graphics, tile, column, row);
        graphics.setForegroundColor(color);
    }

    public void renderPowerSensitiveFrame(TextGraphics graphics, Tile tile, int column, int row) {
        TextColor color = graphics.getForegroundColor();

        graphics.putString(column, row, "\u250F\u2501\u2513");
        graphics.putString(column, row + 1, "\u2503 \u2503");
        graphics.putString(column, row + 2, "\u2517\u2501\u251B");

        // Code smell, but the alternative would be quite more costly
        if (tile.acceptsPower(Side.UP)) {
            graphics.setForegroundColor(getInputColor());
            graphics.setCharacter(column + 1, row, '\u2568');
        } else if (tile.outputsPower(Side.UP)) {
            graphics.setForegroundColor(getOutputColor());
            graphics.setCharacter(column + 1, row, '\u2568');
        }

        if (tile.acceptsPower(Side.DOWN)) {
            graphics.setForegroundColor(getInputColor());
            graphics.setCharacter(column + 1, row + 2, '\u2565');
        } else if (tile.outputsPower(Side.DOWN)) {
            graphics.setForegroundColor(getOutputColor());
            graphics.setCharacter(column + 1, row + 2, '\u2565');
        }

        if (tile.acceptsPower(Side.RIGHT)) {
            graphics.setForegroundColor(getInputColor());
            graphics.setCharacter(column + 2, row + 1, '\u255e');
        } else if (tile.outputsPower(Side.RIGHT)) {
            graphics.setForegroundColor(getOutputColor());
            graphics.setCharacter(column + 2, row + 1, '\u255e');
        }

        if (tile.acceptsPower(Side.LEFT)) {
            graphics.setForegroundColor(getInputColor());
            graphics.setCharacter(column, row + 1, '\u2561');
        } else if (tile.outputsPower(Side.LEFT)) {
            graphics.setForegroundColor(getOutputColor());
            graphics.setCharacter(column, row + 1, '\u2561');
        }

        graphics.setForegroundColor(color);
    }
}
