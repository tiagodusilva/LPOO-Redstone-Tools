package com.lpoo.redstonetools.view.lanterna.circuit;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.model.tile.Tile;
import com.lpoo.redstonetools.model.utils.TileType;
import com.lpoo.redstonetools.view.View;
import com.lpoo.redstonetools.view.lanterna.tile.*;

import java.util.HashMap;
import java.util.Map;

public class LanternaCircuitView extends View<Circuit> {

    private Screen screen;
    private Map<TileType, LanternaTileView> renderers;

    private void initRenderers() {
        renderers = new HashMap<>();
        renderers.put(TileType.NULL, new LanternaNullTileView());
        renderers.put(TileType.WIRE, new LanternaWireTileView());
        renderers.put(TileType.SOURCE, new LanternaConstantSourceTileView());
        renderers.put(TileType.LEVER, new LanternaLeverTileView());
        renderers.put(TileType.REPEATER, new LanternaRepeaterTileView());
    }

    public LanternaCircuitView(Screen screen) {
        this.screen = screen;

        this.initRenderers();
    }

    @Override
    public void render(Circuit circuit) {
        TextGraphics graphics = screen.newTextGraphics();

        // TODO: Refactor this, x is the top x-coordinate of the circuit
        for (int i = 0, x = 0; i < screen.getTerminalSize().getColumns() / 3; i++, x++) {
            // TODO: Refactor this, y is the top y-coordinate of the circuit
            for (int j = 0, y = 0; i < screen.getTerminalSize().getRows() / 3; j++, y++) {
                graphics.setBackgroundColor(TextColor.ANSI.BLACK);
                graphics.setForegroundColor(TextColor.ANSI.WHITE);
                Tile tile = circuit.getTile(x, y);
                renderers.getOrDefault(tile.getType(), new LanternaNullTileView()).render(tile, j, i, graphics);
            }
        }

        /*
        graphics = new graphics;
        for each tile:
            setbackground -> BLACK
            setforeground -> WHITE
            renderTile(tile, graphics);

        //highlighted
        setbackground -> HIGHLIGHTED;
        setForeground -> WHITE
        render highlighted
        */
    }
}
