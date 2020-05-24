package com.lpoo.redstonetools.view.lanterna;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.model.tile.Tile;
import com.lpoo.redstonetools.model.utils.Position;
import com.lpoo.redstonetools.model.utils.Side;
import com.lpoo.redstonetools.model.utils.TileType;
import com.lpoo.redstonetools.view.CircuitView;
import com.lpoo.redstonetools.view.lanterna.input.LanternaInput;
import com.lpoo.redstonetools.view.lanterna.tile.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LanternaCircuitView extends CircuitView {

    private Screen screen;
    private Map<TileType, LanternaTileView> renderers;

    private Circuit circuit;

    private Position selectedTile;
    private Position viewWindow; // Top-left corner of it

    private LanternaInput lanternaInput;

    private TextColor circuitBackground;

    public LanternaCircuitView(Screen screen, Circuit circuit) {
        super();

        this.circuit = circuit;
        this.screen = screen;

        // Init internal vars
        selectedTile = new Position(0, 0);
        viewWindow = new Position(0, 0);
        this.initRenderers();
        circuitBackground = TextColor.Factory.fromString("#181818");

        if (circuit.getWidth() < getColumns()) {
            int offset = (getColumns() - circuit.getWidth()) / 2;
            viewWindow.setX(-offset);
        }

        if (circuit.getHeight() < getRows()) {
            int offset = (getRows() - circuit.getHeight()) / 2;
            viewWindow.setY(-offset);
        }

        // Init input thread
        lanternaInput = null;
        startInputs();
    }

    private void initRenderers() {
        renderers = new HashMap<>();
        renderers.put(TileType.NULL, new LanternaNullTileView());
        renderers.put(TileType.WIRE, new LanternaWireTileView());
        renderers.put(TileType.CROSSWIRE, new LanternaCrossWireTileView());
        renderers.put(TileType.SOURCE, new LanternaConstantSourceTileView());
        renderers.put(TileType.LEVER, new LanternaLeverTileView());
        renderers.put(TileType.REPEATER, new LanternaRepeaterTileView());
        renderers.put(TileType.LOGIC_GATE, new LanternaLogicGateView());
        renderers.put(TileType.NOT_GATE, new LanternaNotGateTileView());
        renderers.put(TileType.COMPARATOR, new LanternaComparatorTileView());
        renderers.put(TileType.TIMER, new LanternaTimerView());
        renderers.put(TileType.COUNTER, new LanternaCounterTileView());
        renderers.put(TileType.IO, new LanternaIOTileView());
        renderers.put(TileType.CIRCUIT, new LanternaCircuitTileView());
    }

    public Screen getScreen() {
        return screen;
    }

    public Position getSelectedTile() {
        return selectedTile;
    }

    public void toggleShowPower() {
        ((LanternaWireTileView) renderers.get(TileType.WIRE)).toggleShowPower();
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

        graphics.setBackgroundColor(circuitBackground);
        graphics.setForegroundColor(TextColor.ANSI.WHITE);

        graphics.fillRectangle(new TerminalPosition(
                -viewWindow.getX()*3,
                -viewWindow.getY()*3),
                new TerminalSize(circuit.getWidth()*3,circuit.getHeight()*3),
                ' '
        );

        int iStart = Math.max(0, -viewWindow.getX()*3);
        int jStart = Math.max(0, -viewWindow.getY()*3);
        int xStart = Math.max(0, viewWindow.getX());
        int yStart = Math.max(0, viewWindow.getY());

        for (int i = iStart, x = xStart; i < getColumns() * 3 + 2 && x < circuit.getWidth(); i+=3, x++) {
            for (int j = jStart, y = yStart; j < getRows() * 3 + 2 && y < circuit.getHeight(); j+=3, y++) {
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
        stopInputs();
        this.events.clear();
    }

    @Override
    public void stopInputs() {
        lanternaInput.interrupt();
        try {
            lanternaInput.join();
            lanternaInput = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startInputs() {
        if (lanternaInput == null) {
            lanternaInput = new LanternaInput(this);
            lanternaInput.start();
        }
    }
}
