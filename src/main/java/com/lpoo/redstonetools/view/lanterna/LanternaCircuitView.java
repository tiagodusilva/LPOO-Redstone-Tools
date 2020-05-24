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
import com.lpoo.redstonetools.model.tile.strategy.*;
import com.lpoo.redstonetools.model.utils.Position;
import com.lpoo.redstonetools.model.utils.Side;
import com.lpoo.redstonetools.model.utils.TileType;
import com.lpoo.redstonetools.view.CircuitView;
import com.lpoo.redstonetools.view.lanterna.input.LanternaInput;
import com.lpoo.redstonetools.view.lanterna.tile.*;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LanternaCircuitView extends CircuitView {

    private Screen screen;
    private MultiWindowTextGUI textGUI;
    private boolean inMenu;
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
        this.textGUI = new MultiWindowTextGUI(screen);
        this.inMenu = false;

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

    public void addHelpWindow() {
        Window window = new BasicWindow();
        Panel panel = new Panel();
        window.setComponent(panel);

        panel.setLayoutManager(new GridLayout(2));

        panel.addComponent(new Label("Arrow keys - Move around\n" +
                "Z - Toggle selection/view window\n" +
                "Enter - Interact\n" +
                "Esc - Quit\n" +
                "+ - Advance Time\n" +
                "Q - Rotate Left\n" +
                "E - Rotate Right\n" +
                "P - Show Power on Wires\n" +
                "G - Save Circuit"
        ).withBorder(Borders.singleLine("Basic Controls")));

        panel.addComponent(new Label("W - Wire\n" +
                "X - Crosswire\n" +
                "1 to 7 - Logic Gates\n" +
                "S - Constant Source\n" +
                "L - Lever\n" +
                "R - Repeater\n" +
                "C - Comparator\n" +
                "N - Counter\n" +
                "T - Timer\n" +
                "I - IO Tile\n" +
                "O - Import Custom Tile"
        ).withBorder(Borders.singleLine("Tile Shortcuts")));

        Button b = new Button("Hide help", () -> {
            textGUI.removeWindow(window);
            inMenu = false;
        });
        panel.addComponent(b.withBorder(Borders.doubleLine()));

        window.setFocusedInteractable(b);

        textGUI.addWindow(window);
        textGUI.setActiveWindow(window);

        inMenu = true;
    }

    public void addInsertGateMenu(Position insertAt) {
        Window window = new BasicWindow();
        Panel panel = new Panel();
        Panel mainPanel = new Panel();
        mainPanel.addComponent(panel.withBorder(Borders.singleLine("Select Specific Logic Gate to insert:")));
        window.setComponent(mainPanel);

        panel.setLayoutManager(new GridLayout(2));

        panel.addComponent(new EmptySpace(TerminalSize.ONE));
        panel.addComponent(new EmptySpace(TerminalSize.ONE));

        List<Class<? extends LogicGateStrategy>> classes = new ArrayList<>();
        classes.add(ANDGateStrategy.class);
        classes.add(ORGateStrategy.class);
        classes.add(XORGateStrategy.class);
        classes.add(NANDGateStrategy.class);
        classes.add(NORGateStrategy.class);
        classes.add(XNORGateStrategy.class);

        ComboBox<String> strategies = new ComboBox<>();
        strategies.addItem("AND");
        strategies.addItem("OR");
        strategies.addItem("XOR");
        strategies.addItem("NAND");
        strategies.addItem("NOR");
        strategies.addItem("XNOR");

        panel.addComponent(strategies);
        panel.addComponent(new EmptySpace(TerminalSize.ZERO));

        Button insert = new Button("Insert", () -> {
            int selected = strategies.getSelectedIndex();
            try {
                Class<? extends LogicGateStrategy> c = classes.get(selected);
                Constructor<? extends LogicGateStrategy> constructor = c.getConstructor();
                LogicGateStrategy strat = constructor.newInstance();
                this.pushEvent(new Event(InputEvent.ADD_TILE, new LogicGateTile(insertAt, strat)));
            } catch (InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
//                        e.printStackTrace();
            }
            textGUI.removeWindow(window);
            inMenu = false;
        });
        panel.addComponent(insert.withBorder(Borders.doubleLine()));

        Button cancel = new Button("Cancel", () -> {
            textGUI.removeWindow(window);
            inMenu = false;
        });
        panel.addComponent(cancel.withBorder(Borders.doubleLine()));

        window.setFocusedInteractable(strategies);

        textGUI.addWindow(window);
        textGUI.setActiveWindow(window);

        inMenu = true;
    }

    public void addInsertMenu(Position insertAt) {
        Window window = new BasicWindow();
        Panel panel = new Panel();
        Panel mainPanel = new Panel();
        mainPanel.addComponent(panel.withBorder(Borders.singleLine("Select Tile to insert:")));
        window.setComponent(mainPanel);

        panel.setLayoutManager(new GridLayout(2));

        panel.addComponent(new EmptySpace(TerminalSize.ONE));
        panel.addComponent(new EmptySpace(TerminalSize.ONE));

        List<Class<? extends Tile>> classes = new ArrayList<Class<? extends Tile>>();
        classes.add(WireTile.class);
        classes.add(CrossWireTile.class);
        classes.add(ConstantSourceTile.class);
        classes.add(LeverTile.class);
        classes.add(RepeaterTile.class);
        classes.add(NotGateTile.class);
        classes.add(LogicGateTile.class);
        classes.add(TimerTile.class);
        classes.add(CounterTile.class);
        classes.add(ComparatorTile.class);
        classes.add(IOTile.class);
        classes.add(Circuit.class);

        ComboBox<String> tileTypes = new ComboBox<String>();
        tileTypes.addItem("Wire");
        tileTypes.addItem("Crosswire");
        tileTypes.addItem("Constant Source");
        tileTypes.addItem("Lever");
        tileTypes.addItem("Repeater");
        tileTypes.addItem("Not Gate");
        tileTypes.addItem("Logic Gate");
        tileTypes.addItem("Timer");
        tileTypes.addItem("Counter");
        tileTypes.addItem("Comparator");
        tileTypes.addItem("IO Tile");
        tileTypes.addItem("Custom Tile");

        panel.addComponent(tileTypes);
        panel.addComponent(new EmptySpace(TerminalSize.ZERO));

        Button insert = new Button("Insert", () -> {
            int selected = tileTypes.getSelectedIndex();
            switch (selected) {
                case 6:
                    addInsertGateMenu(insertAt);
                    textGUI.removeWindow(window);
                    break;
                case 11:
                    textGUI.removeWindow(window);
                    break;
                default: {
                    try {
                        Class<? extends Tile> c = classes.get(selected);
                        Constructor<? extends Tile> constructor = c.getConstructor(Position.class);
                        Tile t = constructor.newInstance(insertAt);
                        this.pushEvent(new Event(InputEvent.ADD_TILE, t));
                    } catch (InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
//                        e.printStackTrace();
                    }
                    textGUI.removeWindow(window);
                    inMenu = false;
                    break;
                }
            }
        });
        panel.addComponent(insert.withBorder(Borders.doubleLine()));

        Button cancel = new Button("Cancel", () -> {
            textGUI.removeWindow(window);
            inMenu = false;
        });
        panel.addComponent(cancel.withBorder(Borders.doubleLine()));

        window.setFocusedInteractable(tileTypes);

        textGUI.addWindow(window);
        textGUI.setActiveWindow(window);

        inMenu = true;
    }

    public void closeAllWindows() {
        textGUI.removeWindow(textGUI.getActiveWindow());
        if (textGUI.getWindows().size() == 0)
            inMenu = false;
    }

    @Override
    public void render() {
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
            try {
                screen.refresh();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
