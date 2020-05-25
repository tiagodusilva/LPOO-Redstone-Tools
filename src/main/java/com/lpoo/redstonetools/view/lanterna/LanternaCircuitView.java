package com.lpoo.redstonetools.view.lanterna;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.lpoo.redstonetools.controller.event.Event;
import com.lpoo.redstonetools.controller.event.InputEvent;
import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.model.tile.*;
import com.lpoo.redstonetools.model.utils.Position;
import com.lpoo.redstonetools.model.utils.Side;
import com.lpoo.redstonetools.model.utils.TileType;
import com.lpoo.redstonetools.view.CircuitView;
import com.lpoo.redstonetools.view.SaveCircuitListener;
import com.lpoo.redstonetools.view.lanterna.input.LanternaAutoAdvanceTime;
import com.lpoo.redstonetools.view.lanterna.input.LanternaInput;
import com.lpoo.redstonetools.view.lanterna.tile.*;
import javafx.util.Pair;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;

public class LanternaCircuitView extends CircuitView {

    private final Screen screen;
    private final MultiWindowTextGUI textGUI;
    private boolean inMenu;
    private final LanternaMenuBuilder lanternaMenuBuilder;

    private Map<TileType, LanternaTileView> renderers;

    private final Circuit circuit;

    private Position selectedTile;
    private Position viewWindow; // Top-left corner of it

    private final LanternaInput lanternaInput;
    private LanternaAutoAdvanceTime lanternaAutoAdvanceTime;

    private final TextColor circuitBackground;

    public LanternaCircuitView(Screen screen, MultiWindowTextGUI textGUI, Circuit circuit) {
        super();

        this.circuit = circuit;
        this.screen = screen;
        this.inMenu = false;
        this.textGUI = textGUI;

        this.lanternaMenuBuilder = new LanternaMenuBuilder(textGUI);

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
        lanternaInput = new LanternaInput(this);
        lanternaInput.start();

        // Init auto advance thread
        lanternaAutoAdvanceTime = new LanternaAutoAdvanceTime(this);
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

    public boolean inMenu() {
        return inMenu;
    }

    public void setInMenu(boolean inMenu) {
        this.inMenu = inMenu;
    }

    public Screen getScreen() {
        return screen;
    }

    public Position getSelectedTile() {
        return selectedTile;
    }

    public void toggleAutoAdvance() {
        if (lanternaAutoAdvanceTime.isAlive()) {
            lanternaAutoAdvanceTime.interrupt();
            try {
                lanternaAutoAdvanceTime.join();
            } catch (InterruptedException e) {
//                e.printStackTrace();
            }
        }
        else {
            lanternaAutoAdvanceTime = new LanternaAutoAdvanceTime(this);
            lanternaAutoAdvanceTime.start();
        }
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
    }

    private void renderOverlay(TextGraphics graphics) {
        graphics.setForegroundColor(TextColor.ANSI.WHITE);
        graphics.setBackgroundColor(TextColor.ANSI.BLACK);
        String message = "Press [H] for help";
        graphics.putString(screen.getTerminalSize().getColumns() - 1 - message.length(), screen.getTerminalSize().getRows() - 1, message);

        boolean alive = lanternaAutoAdvanceTime.isAlive();
        message = alive ? "Time Running" : "Time Stopped";
        graphics.setForegroundColor(alive ? TextColor.ANSI.GREEN : TextColor.ANSI.RED);
        graphics.putString(screen.getTerminalSize().getColumns() - 1 - message.length(), 0, message);
    }

    public void showHelpMenu() {
        lanternaMenuBuilder.addHelpWindow(() -> inMenu = false);
        inMenu = true;
    }

    public void showInsertMenu(Position insertAt) {
        Consumer<Tile> c = (tile) -> pushEvent(new Event(InputEvent.ADD_TILE, tile));
        lanternaMenuBuilder.addInsertMenu(insertAt, c, () -> inMenu = false);
        inMenu = true;
    }

    public void showInsertGateMenu(Position insertAt) {
        Consumer<Tile> c = (tile) -> pushEvent(new Event(InputEvent.ADD_TILE, tile));
        lanternaMenuBuilder.addInsertGateMenu(insertAt, c, () -> inMenu = false);
        inMenu = true;
    }

    public void showInsertCustomMenu(Position insertAt) {
        Consumer<Tile> c = (tile) -> pushEvent(new Event(InputEvent.ADD_TILE, tile));
        lanternaMenuBuilder.addInsertCustomMenu(insertAt, c, () -> inMenu = false);
        inMenu = true;
    }

    public void showSaveCircuitMenu() {
        boolean alive = lanternaAutoAdvanceTime.isAlive();
        if (alive) {
            lanternaAutoAdvanceTime.interrupt();
            try {
                lanternaAutoAdvanceTime.join();
            } catch (InterruptedException e) {
                // e.printStackTrace();
            }
        }
        Consumer<SaveCircuitListener> c = (listener) -> pushEvent(new Event(InputEvent.SAVE, listener));
        lanternaMenuBuilder.addSaveCircuitMenu(c, circuit.getCircuitName(),() -> inMenu = false);
        inMenu = true;
        if (alive) {
            lanternaAutoAdvanceTime = new LanternaAutoAdvanceTime(this);
            lanternaAutoAdvanceTime.start();
        }
    }

    public void showTileInfo(Position position) {
        Tile tile = circuit.getTile(position);
        if (tile.getType() != TileType.NULL) {
            lanternaMenuBuilder.addConfirmation(circuit.getTile(position).getInfo(), () -> inMenu = false);
            inMenu = true;
        }
    }

    public void showSetDelayMenu(Position position) {
        Tile tile = circuit.getTile(position);
        Consumer<Long> c;
        switch (tile.getType()) {
            case TIMER:
                c = (delay) -> pushEvent(new Event(InputEvent.SET_DELAY, new Pair<>(position, delay)));
                lanternaMenuBuilder.addNumberInput(c, "Timer delay", "[1-9][0-9]{0,4}", ((TimerTile) tile).getDelay(), () -> inMenu = false);
                inMenu = true;
                break;
            case COUNTER:
                c = (delay) -> pushEvent(new Event(InputEvent.SET_DELAY, new Pair<>(position, delay)));
                lanternaMenuBuilder.addNumberInput(c, "Counter delay", "[1-9][0-9]{0,4}", ((CounterTile) tile).getDelay(), () -> inMenu = false);
                inMenu = true;
                break;
            default:
                break;
        }
    }

    @Override
    public void render() {
        screen.doResizeIfNecessary();
        if (inMenu) {
            try {
                textGUI.processInput();
                textGUI.updateScreen();
            } catch (IOException e) {
//                e.printStackTrace();
                pushEvent(new Event(InputEvent.QUIT, null));
            }
        }
        else {
            screen.clear();
            // As per documentation, this object should be discarded regularly
            TextGraphics graphics = screen.newTextGraphics();

            renderCircuit(graphics);
            renderOverlay(graphics);
            try {
                screen.refresh();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void cleanup() {
        lanternaInput.interrupt();
        lanternaAutoAdvanceTime.interrupt();
        try {
            lanternaInput.join();
            lanternaAutoAdvanceTime.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.events.clear();
    }

}
