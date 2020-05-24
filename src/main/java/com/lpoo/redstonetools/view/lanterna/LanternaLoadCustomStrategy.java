package com.lpoo.redstonetools.view.lanterna;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.FileDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.screen.Screen;
import com.lpoo.redstonetools.controller.circuit.CircuitController;
import com.lpoo.redstonetools.controller.event.Event;
import com.lpoo.redstonetools.controller.event.InputEvent;
import com.lpoo.redstonetools.exception.InvalidCircuitException;
import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.model.utils.Position;
import com.lpoo.redstonetools.view.LoadCustomStrategy;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

public class LanternaLoadCustomStrategy implements LoadCustomStrategy {

    private final LanternaCircuitView lanternaCircuitView;
    private final Position position;
    private String fileName;

    private Button cancelButton;
    private Border borderedCancelButton;
    private Label fileNameLabel;

    public LanternaLoadCustomStrategy(LanternaCircuitView lanternaCircuitView, Position position) {
        this.lanternaCircuitView = lanternaCircuitView;
        this.position = position;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public String getFileName() {
        Screen screen = lanternaCircuitView.getScreen();
        AtomicBoolean windowNotClosed = new AtomicBoolean(true);

        MultiWindowTextGUI textGUI = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLUE));
        Panel mainPanel = new Panel();

        Window window = new BasicWindow();
        window.setComponent(mainPanel);

        mainPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
        mainPanel.setLayoutManager(new GridLayout(2));

        fileNameLabel = new Label("");
        cancelButton = new Button("Cancel", () -> {
            fileName = null;
            windowNotClosed.set(false);
            textGUI.removeWindow(window);
        });
        borderedCancelButton = cancelButton.withBorder(Borders.doubleLine());

        Button selectFile = new Button("Load Custom", () -> {
            File input1 = new FileDialogBuilder()
                    .setTitle("Load Custom")
                    .setDescription("Choose a file")
                    .setActionLabel("Open")
                    .setSelectedFile(new File("circuits/"))
                    .build()
                    .showDialog(textGUI);

            if (input1 != null) {
                if (fileName == null) {
                    mainPanel.removeComponent(borderedCancelButton);

                    mainPanel.addComponent(fileNameLabel);
                    mainPanel.addComponent(new EmptySpace(TerminalSize.ZERO));
                    mainPanel.addComponent(new Button("Open Circuit", () -> {
                        windowNotClosed.set(false);
                        textGUI.removeWindow(window);
                    }).withBorder(Borders.doubleLine()));

                    borderedCancelButton = cancelButton.withBorder(Borders.singleLine());
                    mainPanel.addComponent(borderedCancelButton);
                }

                fileName = input1.getAbsolutePath();
                fileNameLabel.setText("Loading custom:\n" + fileName);
            }

        });
        mainPanel.addComponent(selectFile.withBorder(Borders.doubleLine("Select from a file")));
        mainPanel.addComponent(new EmptySpace(TerminalSize.ZERO));

        mainPanel.addComponent(new EmptySpace(TerminalSize.ONE));
        mainPanel.addComponent(new EmptySpace(TerminalSize.ZERO));

        mainPanel.addComponent(borderedCancelButton);

        window.setFocusedInteractable(selectFile);
        textGUI.addWindowAndWait(window);

        if (windowNotClosed.get()) {
            lanternaCircuitView.pushEvent(new Event(InputEvent.QUIT, null));
            return null;
        }

        return fileName;
    }

    @Override
    public void notifyFailure() {
        Screen screen = lanternaCircuitView.getScreen();
        AtomicBoolean windowNotClosed = new AtomicBoolean(true);

        MultiWindowTextGUI textGUI = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLUE));
        Panel mainPanel = new Panel();

        Window window = new BasicWindow();
        window.setComponent(mainPanel);

        mainPanel.addComponent(new Label("Failed to load circuit\nCircuit's version may mismatch the current one"));

        mainPanel.addComponent(new EmptySpace(TerminalSize.ZERO));

        Button acceptButton = new Button("Ok", () -> {
            windowNotClosed.set(false);
            textGUI.removeWindow(window);
        });
        mainPanel.addComponent(acceptButton.withBorder(Borders.doubleLine()));

        window.setFocusedInteractable(acceptButton);
        textGUI.addWindowAndWait(window);
    }
}
