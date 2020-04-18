package com.lpoo.redstonetools.graphics.lanterna;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.lpoo.redstonetools.core.tiles.*;
import com.lpoo.redstonetools.core.utils.Position;
import com.lpoo.redstonetools.core.utils.Side;

public class LanternaTileRenderer {

    private TextGraphics graphics;

    public void setGraphics(TextGraphics graphics) {
        this.graphics = graphics;
    }

    public void renderTile(Tile tile, int row, int collumn) {
        java.lang.Object c = tile.getClass();
        if (NullTile.class.equals(c)) {
            renderTile((NullTile) tile, row, collumn);
        }
        else if (WireTile.class.equals(c)) {
            renderTile((WireTile) tile, row, collumn);
        }
        else if (ConstantSourceTile.class.equals(c)) {
            renderTile((ConstantSourceTile) tile, row, collumn);
        }
    }

    public void renderTile(NullTile tile, int row, int collumn) {
    }

    public void renderTile(WireTile tile, int row, int collumn) {
        // TODO: Improve power to color conversion
        String red_component = Integer.toHexString(tile.getPower(Side.UP) * 14 + 40);
        if (red_component.length() == 1)
            red_component += '0';
        String color = "#" + red_component + "0000";
        graphics.setForegroundColor(TextColor.Factory.fromString(color));
        graphics.setCharacter(collumn + 1, row + 1, 'W');
    }

    public void renderTile(ConstantSourceTile tile, int row, int collumn) {
        graphics.setForegroundColor(TextColor.Factory.fromString("#ff0000"));
        graphics.setCharacter(collumn + 1, row + 1, 'S');
    }

}
