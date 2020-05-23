package com.lpoo.redstonetools.view.lanterna.tile;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.lpoo.redstonetools.model.tile.IOTile;
import com.lpoo.redstonetools.model.tile.Tile;
import com.lpoo.redstonetools.model.utils.Power;
import com.lpoo.redstonetools.model.utils.Side;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class LanternaIOTileView extends LanternaTileView{

    private Map<Side, Character> characterMap;

    public LanternaIOTileView() {
        characterMap = new HashMap<>();
        characterMap.put(Side.UP, '\u2191');
        characterMap.put(Side.LEFT, '\u2190');
        characterMap.put(Side.DOWN, '\u2193');
        characterMap.put(Side.RIGHT, '\u2192');
    }

    @Override
    public void render(Tile tile, int row, int column, TextGraphics graphics) {
        TextColor color = tile.acceptsPower(Side.UP) ? getOutputColor() : tile.outputsPower(Side.UP) ? getInputColor() : getForegroundColor();
        renderPowerSensitiveFrame(graphics, tile, column, row, color);

        graphics.setForegroundColor(color);
        graphics.setCharacter(column + 1, row + 1, characterMap.getOrDefault(((IOTile)tile).getIOSide(), '\u262D'));
    }
}
