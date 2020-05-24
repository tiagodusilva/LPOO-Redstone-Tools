package com.lpoo.redstonetools.view.lanterna;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.FileDialogBuilder;
import com.googlecode.lanterna.screen.Screen;
import com.lpoo.redstonetools.controller.circuit.CircuitController;
import com.lpoo.redstonetools.controller.event.Event;
import com.lpoo.redstonetools.controller.event.InputEvent;
import com.lpoo.redstonetools.exception.InvalidCircuitException;
import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.model.tile.*;
import com.lpoo.redstonetools.model.tile.strategy.*;
import com.lpoo.redstonetools.model.utils.Position;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class LanternaMenuBuilder {

    private final MultiWindowTextGUI textGUI;

    public LanternaMenuBuilder(MultiWindowTextGUI textGUI) {
        this.textGUI = textGUI;
    }

    public void addHelpWindow(Runnable onExit) {
        Window window = new BasicWindow();
        Panel panel = new Panel();
        window.setComponent(panel);
        window.setHints(Arrays.asList(Window.Hint.CENTERED));

        panel.setLayoutManager(new GridLayout(2));

        panel.addComponent(new Label("Arrow keys - Move around\n" +
                "Z - Toggle selection/view window\n" +
                "Enter - Interact\n" +
                "Insert - Add Tile Dropdown\n" +
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

        Button b = new Button("Hide Help", () -> {
            textGUI.removeWindow(window);
            onExit.run();
        });
        panel.addComponent(b.withBorder(Borders.doubleLine()));

        window.setFocusedInteractable(b);

        textGUI.addWindow(window);
        textGUI.setActiveWindow(window);
    }

    public void addInsertMenu(Position position, Consumer<Tile> consumer, Runnable onExit) {
        Window window = new BasicWindow();
        Panel panel = new Panel();
        Panel mainPanel = new Panel();
        mainPanel.addComponent(panel.withBorder(Borders.singleLine("Select Tile to insert:")));
        window.setComponent(mainPanel);
        window.setHints(Arrays.asList(Window.Hint.CENTERED));

        panel.setLayoutManager(new GridLayout(2));

        panel.addComponent(new EmptySpace(TerminalSize.ONE));
        panel.addComponent(new EmptySpace(TerminalSize.ONE));

        List<Class<? extends Tile>> classes = new ArrayList<>();
            classes.add(WireTile .class);
            classes.add(CrossWireTile .class);
            classes.add(ConstantSourceTile .class);
            classes.add(LeverTile .class);
            classes.add(RepeaterTile .class);
            classes.add(NotGateTile .class);
            classes.add(LogicGateTile .class);
            classes.add(TimerTile .class);
            classes.add(CounterTile .class);
            classes.add(ComparatorTile .class);
            classes.add(IOTile .class);
            classes.add(Circuit .class);

        ComboBox<String> tileTypes = new ComboBox<>();
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
                case 6: // Logic Gate
                    textGUI.removeWindow(window);
                    addInsertGateMenu(position, consumer, onExit);
                    break;
                case 11: // Custom
                    textGUI.removeWindow(window);
                    addInsertCustomMenu(position, consumer, onExit);
                    break;
                default: {
                    try {
                        Class<? extends Tile> c = classes.get(selected);
                        Constructor<? extends Tile> constructor = c.getConstructor(Position.class);
                        Tile t = constructor.newInstance(position);
                        consumer.accept(t);
                    } catch (InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
//                            e.printStackTrace();
                    }
                    textGUI.removeWindow(window);
                    onExit.run();
                    break;
                }
            }
        });
        panel.addComponent(insert.withBorder(Borders.doubleLine()));

        Button cancel = new Button("Cancel", () -> {
            textGUI.removeWindow(window);
            onExit.run();
        });
        panel.addComponent(cancel.withBorder(Borders.doubleLine()));

        window.setFocusedInteractable(tileTypes);

        textGUI.addWindow(window);
        textGUI.setActiveWindow(window);
    }

    public void addInsertGateMenu(Position position, Consumer<Tile> consumer, Runnable onExit) {
        Window window = new BasicWindow();
        Panel panel = new Panel();
        Panel mainPanel = new Panel();
        mainPanel.addComponent(panel.withBorder(Borders.singleLine("Select Specific Logic Gate to insert:")));
        window.setComponent(mainPanel);
        window.setHints(Arrays.asList(Window.Hint.CENTERED));

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
                consumer.accept(new LogicGateTile(position, strat));
            } catch (InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
//                        e.printStackTrace();
            }
            textGUI.removeWindow(window);
            onExit.run();
        });
        panel.addComponent(insert.withBorder(Borders.doubleLine()));

        Button cancel = new Button("Cancel", () -> {
            textGUI.removeWindow(window);
            onExit.run();
        });
        panel.addComponent(cancel.withBorder(Borders.doubleLine()));

        window.setFocusedInteractable(strategies);

        textGUI.addWindow(window);
        textGUI.setActiveWindow(window);
    }

    public void addInsertCustomMenu(Position position, Consumer<Tile> consumer, Runnable onExit) {
        Panel mainPanel = new Panel();

        Window window = new BasicWindow();
        window.setComponent(mainPanel);
        window.setHints(Arrays.asList(Window.Hint.CENTERED));

        mainPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
        mainPanel.setLayoutManager(new GridLayout(2));

        // If alt+enter did this, then it must be correct
        final String[] fileName = new String[1];

        Label fileNameLabel = new Label("");
        Button cancelButton = new Button("Cancel", () -> {
            fileName[0] = null;
            onExit.run();
            textGUI.removeWindow(window);
        });
        final Border[] borderedCancelButton = {cancelButton.withBorder(Borders.doubleLine())};

        Button openButton = new Button("Load Circuit", () -> {
            Circuit circuit = null;
            try {
                circuit = CircuitController.loadCircuit(fileName[0]);
                circuit.setPosition(position);
                consumer.accept(circuit);
                textGUI.removeWindow(window);
                onExit.run();
            } catch (InvalidCircuitException e) {
//                e.printStackTrace();
                this.addConfirmation("Failed to load circuit\nCircuit's version may mismatch the current one", onExit);
                textGUI.removeWindow(window);
            }
        });

        Button selectFile = new Button("Load Custom", () -> {
            File input1 = new FileDialogBuilder()
                    .setTitle("Load Custom")
                    .setDescription("Choose a file")
                    .setActionLabel("Open")
                    .setSelectedFile(new File("circuits/"))
                    .build()
                    .showDialog(textGUI);

            if (input1 != null) {
                if (fileName[0] == null) {
                    mainPanel.removeComponent(borderedCancelButton[0]);

                    mainPanel.addComponent(fileNameLabel);
                    mainPanel.addComponent(new EmptySpace(TerminalSize.ZERO));
                    mainPanel.addComponent(openButton.withBorder(Borders.doubleLine()));

                    borderedCancelButton[0] = cancelButton.withBorder(Borders.singleLine());
                    mainPanel.addComponent(borderedCancelButton[0]);
                }

                fileName[0] = input1.getAbsolutePath();
                fileNameLabel.setText("Loading custom:\n" + fileName[0]);
            }

        });
        mainPanel.addComponent(selectFile.withBorder(Borders.doubleLine("Select from a file")));
        mainPanel.addComponent(new EmptySpace(TerminalSize.ZERO));

        mainPanel.addComponent(new EmptySpace(TerminalSize.ONE));
        mainPanel.addComponent(new EmptySpace(TerminalSize.ZERO));

        mainPanel.addComponent(borderedCancelButton[0]);

        window.setFocusedInteractable(selectFile);
        textGUI.addWindow(window);
    }

    public void addConfirmation(String message, Runnable onExit) {
        AtomicBoolean windowNotClosed = new AtomicBoolean(true);

        Panel mainPanel = new Panel();

        Window window = new BasicWindow();
        window.setComponent(mainPanel);
        window.setHints(Arrays.asList(Window.Hint.CENTERED));

        mainPanel.addComponent(new Label(message));

        mainPanel.addComponent(new EmptySpace(TerminalSize.ZERO));

        Button acceptButton = new Button("Ok", () -> {
            windowNotClosed.set(false);
            textGUI.removeWindow(window);
            onExit.run();
        });
        mainPanel.addComponent(acceptButton.withBorder(Borders.doubleLine()));

        window.setFocusedInteractable(acceptButton);
        textGUI.addWindowAndWait(window);
    }

}
