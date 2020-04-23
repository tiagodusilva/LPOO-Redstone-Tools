package com.lpoo.redstonetools.graphics.lanterna;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.lpoo.redstonetools.core.Circuit;
import com.lpoo.redstonetools.core.tiles.Tile;
import com.lpoo.redstonetools.graphics.CircuitRenderer;
import com.lpoo.redstonetools.graphics.TileRenderer;
import com.lpoo.redstonetools.graphics.lanterna.tiles.LanternaConstantSourceTileRenderer;
import com.lpoo.redstonetools.graphics.lanterna.tiles.LanternaNullTileRenderer;
import com.lpoo.redstonetools.graphics.lanterna.tiles.LanternaWireTileRenderer;

public class LanternaCircuitRenderer implements CircuitRenderer {

    private final Screen screen;
    private TextGraphics graphics;
    private LanternaNullTileRenderer nullTileRenderer;
    private LanternaConstantSourceTileRenderer constantSourceRenderer;
    private LanternaWireTileRenderer wireTileRenderer;

    public LanternaCircuitRenderer(Screen screen, TextGraphics graphics) {
        this.screen = screen;
        this.graphics = graphics;
        this.nullTileRenderer = new LanternaNullTileRenderer(screen);
    }

    @Override
    public void render(Circuit circuit, int row, int column) {
        graphics.setBackgroundColor(TextColor.ANSI.BLACK);
        graphics.setForegroundColor(TextColor.ANSI.WHITE);
        for (int i = 0; i < screen.getTerminalSize().getColumns() / 3; i++) {
            for (int j = 0; j < screen.getTerminalSize().getRows() / 3; j++) {
                circuit.getTile(i, j).getRenderer().render(circuit.getTile(i, j), j * 3, i * 3);
            }
        }
    }

    @Override
    public TileRenderer<? extends Tile> getNullTileRenderer() {
        return nullTileRenderer;
    }
}
