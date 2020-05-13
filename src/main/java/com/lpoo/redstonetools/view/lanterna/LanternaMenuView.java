package com.lpoo.redstonetools.view.lanterna;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.*;
import com.googlecode.lanterna.screen.Screen;
import com.lpoo.redstonetools.controller.circuit.CircuitController;
import com.lpoo.redstonetools.controller.event.Event;
import com.lpoo.redstonetools.controller.event.InputEvent;
import com.lpoo.redstonetools.exception.InvalidCircuitException;
import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.model.utils.Power;
import com.lpoo.redstonetools.view.MenuView;

import java.io.File;
import java.util.regex.Pattern;

public class LanternaMenuView extends MenuView {

    private Screen screen;
    private Circuit circuit;

    private String fileName;
    private Border borderedQuitButton;
    private RadioBoxList<String> radioBoxList;
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

        selectPanel.addComponent(new Button("Select File", () -> {
            File input1 = new FileDialogBuilder()
                    .setTitle("Open File")
                    .setDescription("Choose a file")
                    .setActionLabel("Open")
                    .setSelectedFile(new File("circuits/"))
                    .build()
                    .showDialog(textGUI);

            if (input1 != null) {
                fileName = input1.getName();
                try {
                    createCircuit(mainPanel, fileNameLabel, circuitSizeLabel, CircuitController.loadCircuit(input1.getAbsolutePath()));
                } catch (InvalidCircuitException e) {
                    new MessageDialogBuilder()
                            .setTitle("Failed to Load Circuit")
                            .setText("Please select a valid .ser file")
                            .build()
                            .showDialog(textGUI);
                }
            }

        }).withBorder(Borders.singleLine("Select from a file")));

        Panel rightPanel = new Panel();
        selectPanel.addComponent(rightPanel.withBorder(Borders.singleLine("New Blank Circuit")));
        rightPanel.setLayoutManager(new GridLayout(2));

        rightPanel.addComponent(new Label("Width"));
        final TextBox widthTxt = new TextBox().setText("20").setValidationPattern(Pattern.compile("[1-9][0-9]*")).addTo(rightPanel);

        rightPanel.addComponent(new Label("Height"));
        final TextBox heightTxt = new TextBox().setText("10").setValidationPattern(Pattern.compile("[1-9][0-9]*")).addTo(rightPanel);

        rightPanel.addComponent(new EmptySpace(new TerminalSize(0, 1)));
        rightPanel.addComponent(new EmptySpace(new TerminalSize(0, 1)));

        Panel radioBoxPanel = new Panel();
        rightPanel.addComponent(radioBoxPanel.withBorder(Borders.singleLine("Select Mode")));
        radioBoxList = new RadioBoxList<String>();
        radioBoxList.addItem("Redstone");
        radioBoxList.addItem("Electrical Wire");
        radioBoxList.setCheckedItemIndex(0);

        radioBoxPanel.addComponent(radioBoxList);

        rightPanel.addComponent(new EmptySpace(new TerminalSize(0, 1)));
        rightPanel.addComponent(new EmptySpace(new TerminalSize(0, 1)));
        rightPanel.addComponent(new EmptySpace(new TerminalSize(0, 1)));

        rightPanel.addComponent(new Button("Create Blank", () -> {
            fileName = "";
            createCircuit(mainPanel, fileNameLabel, circuitSizeLabel, new Circuit(Integer.parseInt(widthTxt.getText()), Integer.parseInt(heightTxt.getText())));
        }).withBorder(Borders.singleLine()));

        mainPanel.addComponent(new EmptySpace(new TerminalSize(0, 1)));

        // Create window to hold the panel
        window = new BasicWindow();
        window.setComponent(mainPanel);

        borderedQuitButton = new Button("Close game", () -> {
            textGUI.removeWindow(window);
            pushEvent(new Event(InputEvent.QUIT, null));
        }).withBorder(Borders.doubleLine());
        mainPanel.addComponent(borderedQuitButton);

        textGUI.addWindowAndWait(window);


        // This handles the case where the Terminal was closed without any input
        if (events.isEmpty()) {
            pushEvent(new Event(InputEvent.QUIT, null));
        }
    }

    private void createCircuit(Panel panel, Label fileNameLabel, Label circuitSizeLabel, Circuit aCircuit) {
        if (circuit == null) {

            panel.removeComponent(borderedQuitButton);

            panel.addComponent(fileNameLabel);
            panel.addComponent(circuitSizeLabel);
            panel.addComponent(new Button("Open Circuit", () -> {
                if (circuit != null) {
                    // TODO: Code Smell, this call should not be here
                    if (radioBoxList.isChecked(0))
                        Power.setRedstoneMode();
                    else
                        Power.setEletricMode();
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
            }).withBorder(Borders.doubleLine()));

            panel.addComponent(new EmptySpace(new TerminalSize(0, 1)));
            panel.addComponent(borderedQuitButton);
        }

        // TODO: Add Label to indicate the circuit's power mode
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
