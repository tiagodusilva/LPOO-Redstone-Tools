package com.lpoo.redstonetools.view.lanterna.circuit;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFontConfiguration;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFontConfiguration;
import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.model.tile.Tile;
import com.lpoo.redstonetools.model.utils.Position;
import com.lpoo.redstonetools.model.utils.Side;
import com.lpoo.redstonetools.model.utils.TileType;
import com.lpoo.redstonetools.view.CircuitView;
import com.lpoo.redstonetools.view.lanterna.input.LanternaInput;
import com.lpoo.redstonetools.view.lanterna.tile.*;

import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LanternaCircuitView extends CircuitView {

    private Terminal terminal;
    private Screen screen;
    private Map<TileType, LanternaTileView> renderers;

    private Circuit circuit;

    private Position selectedTile;
    private Position viewWindow; // Top-left corner of it

    LanternaInput lanternaInput;

    public LanternaCircuitView(Circuit circuit) {
        super();

        this.circuit = circuit;

        try {
            Font font = new Font("Consolas", Font.PLAIN, 15);
            AWTTerminalFontConfiguration cfg = new SwingTerminalFontConfiguration(
                    true,
                    AWTTerminalFontConfiguration.BoldMode.NOTHING,
                    font);

            this.terminal = new DefaultTerminalFactory()
                    .setInitialTerminalSize(new TerminalSize(100, 40))
                    .setTerminalEmulatorFontConfiguration(cfg)
                    .createTerminal();
            this.screen = new TerminalScreen(terminal);

            this.screen.setCursorPosition(null);   // we don't need a cursor
            this.screen.startScreen();             // screens must be started
            this.screen.doResizeIfNecessary();     // resize screen if necessary
        } catch (
                IOException e) {
            e.printStackTrace();
        }

        // Init internal vars
        selectedTile = new Position(0, 0);
        viewWindow = new Position(0, 0);
        this.initRenderers();

        // Init input thread
        lanternaInput = new LanternaInput(this);
        lanternaInput.start();
    }

    private void initRenderers() {
        renderers = new HashMap<>();
        renderers.put(TileType.NULL, new LanternaNullTileView());
        renderers.put(TileType.WIRE, new LanternaWireTileView());
        renderers.put(TileType.SOURCE, new LanternaConstantSourceTileView());
        renderers.put(TileType.LEVER, new LanternaLeverTileView());
        renderers.put(TileType.REPEATER, new LanternaRepeaterTileView());
    }

    public Screen getScreen() {
        return screen;
    }

    public Position getSelectedTile() {
        return selectedTile;
    }

    public void moveSelectedTile(Side side) {
        Position newPos = selectedTile.getNeighbour(side);
        if (!validHiglightedPosition(newPos))
            return;
        selectedTile = newPos;
        if (!isInsideViewWindow(selectedTile))
            moveViewWindow(side);
    }

    public void moveViewWindow(Side side) {
        Position newPos = viewWindow.getNeighbour(side);
        if (!validViewWindow(newPos))
            return;
        viewWindow = newPos;
        if (!isInsideViewWindow(selectedTile))
            moveSelectedTile(side);
    }

    private boolean validHiglightedPosition(Position position) {
        return position.getX() >= 0 &&
                position.getX() < circuit.getWidth() &&
                position.getY() >= 0 &&
                position.getY() < circuit.getHeight();
    }

    private boolean isInsideViewWindow(Position position) {
        return position.getX() >= viewWindow.getX() &&
                position.getX() < viewWindow.getX() + getColumns() &&
                position.getY() >= viewWindow.getY() &&
                position.getY() < viewWindow.getY() + getRows();
    }

    private boolean validViewWindow(Position position) {
        return position.getX() + getColumns() > 0 &&
                position.getX() < circuit.getWidth() &&
                position.getY() + getRows() > 0 &&
                position.getY() < circuit.getHeight();
    }

    private int getColumns() {
        return screen.getTerminalSize().getColumns() / 3;
    }

    private int getRows() {
        return screen.getTerminalSize().getRows() / 3;
    }

    private void renderCircuit(TextGraphics graphics) {
        // TODO: Refactor this, x is the top x-coordinate of the circuit
        for (int i = 0, x = viewWindow.getX(); i < getColumns() * 3; i+=3, x++) {
            // TODO: Refactor this, y is the top y-coordinate of the circuit
            for (int j = 0, y = viewWindow.getY(); j < getRows() * 3; j+=3, y++) {
                graphics.setBackgroundColor(TextColor.ANSI.BLACK);
                graphics.setForegroundColor(TextColor.ANSI.WHITE);
                Tile tile = circuit.getTile(x, y);
                renderers.getOrDefault(tile.getType(), new LanternaNullTileView()).render(tile, j, i, graphics);
            }
        }

        // Render highlighted
        Tile highlighted = circuit.getTile(selectedTile);
        graphics.setBackgroundColor(TextColor.ANSI.MAGENTA);
        renderers.getOrDefault(highlighted.getType(), new LanternaNullTileView()).render(highlighted,
                (selectedTile.getY() - viewWindow.getY()) * 3,
                (selectedTile.getX() - viewWindow.getX()) * 3, graphics);

        try {
            screen.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render() {
        screen.clear();
        TextGraphics graphics = screen.newTextGraphics();

        renderCircuit(graphics);
    }

    @Override
    public void cleanup() {
        lanternaInput.interrupt();
        try {
            lanternaInput.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.events.clear();

        try {
            terminal.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
