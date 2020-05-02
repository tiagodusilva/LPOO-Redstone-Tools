package com.lpoo.redstonetools.view.lanterna;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.*;
import com.googlecode.lanterna.screen.Screen;
import com.lpoo.redstonetools.controller.circuit.CircuitController;
import com.lpoo.redstonetools.controller.event.Event;
import com.lpoo.redstonetools.controller.event.InputEvent;
import com.lpoo.redstonetools.controller.state.CircuitState;
import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.view.MenuView;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;

public class LanternaMenuView extends MenuView {

    private Screen screen;
    private Circuit circuit;

    private String fileName;
    private Button quitButton;
    private final WindowBasedTextGUI textGUI;
    private BasicWindow window;

    public LanternaMenuView(Screen screen) {
        // Setup terminal and screen layers
        this.screen = screen;
        circuit = null;
        fileName = "";

        // Create gui and start gui
        textGUI = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLUE));

        File input;

        Panel mainPanel = new Panel();
        Label fileNameLabel = new Label("");
        Label circuitSizeLabel = new Label("");

        Panel selectPanel = new Panel();
        mainPanel.addComponent(selectPanel.withBorder(Borders.singleLine("Select a Circuit")));
        selectPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));

        Panel leftPanel = new Panel();
        selectPanel.addComponent(leftPanel.withBorder(Borders.singleLine("Load from File")));

        Panel rightPanel = new Panel();
        selectPanel.addComponent(rightPanel.withBorder(Borders.singleLine("Create Blank")));

        leftPanel.addComponent(new Button("Load from File", new Runnable() {
            @Override
            public void run() {
                File input = new FileDialogBuilder()
                        .setTitle("Open File")
                        .setDescription("Choose a file")
                        .setActionLabel("Open")
                        .build()
                        .showDialog(textGUI);

                if (input != null) {
//                    fileName = input.getName();
                    fileName = "Test circuit";
                    createCircuit(mainPanel, fileNameLabel, circuitSizeLabel, CircuitController.loadTestCircuit());
                }

            }
        }));

        rightPanel.addComponent(new Button("Create Blank", new Runnable() {
            @Override
            public void run() {
                BigInteger w = TextInputDialog.showNumberDialog(textGUI, "Circuit Size", "Width", "20");
                BigInteger h = TextInputDialog.showNumberDialog(textGUI, "Circuit Size", "Height", "10");

                if (w != null && h != null) {
                    if (w.intValue() < 1 || h.intValue() < 1) {
                        new MessageDialogBuilder()
                                .setTitle("Failed")
                                .setText("Both width and height must be positive integers")
                                .build()
                                .showDialog(textGUI);
                    } else {
                        fileName = "";
                        createCircuit(mainPanel, fileNameLabel, circuitSizeLabel, new Circuit(w.intValue(), h.intValue()));
                    }
                }

            }
        }));

        mainPanel.addComponent(new EmptySpace(new TerminalSize(0, 1)));



        // Create window to hold the panel
        window = new BasicWindow();
        window.setComponent(mainPanel);

        quitButton = new Button("Close game", new Runnable() {
            @Override
            public void run() {
                textGUI.removeWindow(window);
                pushEvent(new Event(InputEvent.QUIT, null));
            }
        });
        mainPanel.addComponent(quitButton);

        textGUI.addWindowAndWait(window);

        // This handles the case where the Terminal was closed without any input
        if (events.isEmpty()) {
            pushEvent(new Event(InputEvent.QUIT, null));
        }
    }

    private void createCircuit(Panel panel, Label fileNameLabel, Label circuitSizeLabel, Circuit aCircuit) {
        if (circuit == null) {

            panel.removeComponent(quitButton);

            panel.addComponent(fileNameLabel);
            panel.addComponent(circuitSizeLabel);
            panel.addComponent(new Button("Open Circuit", new Runnable() {
                @Override
                public void run() {
                    if (circuit != null) {
                        textGUI.removeWindow(window);
                        pushEvent(new Event(InputEvent.ENTER_CIRCUIT_STATE, circuit));
                    }
                    else {
                        new MessageDialogBuilder()
                                .setTitle("Failed")
                                .setText("You must select a circuit")
                                .build()
                                .showDialog(textGUI);
                    }
                }
            }));

            panel.addComponent(new EmptySpace(new TerminalSize(0, 1)));
            panel.addComponent(quitButton);
        }

        if (fileName.equals(""))
            fileNameLabel.setText("Blank Circuit");
        else
            fileNameLabel.setText("Loaded from: " + fileName);
        circuitSizeLabel.setText("Circuit size: " + aCircuit.getWidth() + "x" + aCircuit.getHeight());
        circuit = aCircuit;
    }

    @Override
    public void render() {

    }

    @Override
    public void cleanup() {
        this.events.clear();
    }
}
