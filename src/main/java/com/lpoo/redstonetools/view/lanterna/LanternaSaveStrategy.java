package com.lpoo.redstonetools.view.lanterna;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.lpoo.redstonetools.controller.event.Event;
import com.lpoo.redstonetools.controller.event.InputEvent;
import com.lpoo.redstonetools.view.SaveStrategy;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class LanternaSaveStrategy implements SaveStrategy {

    private LanternaCircuitView lanternaCircuitView;
    private String filename;

    public LanternaSaveStrategy(LanternaCircuitView lanternaCircuitView) {
        this.lanternaCircuitView = lanternaCircuitView;
        filename = null;
    }

    @Override
    public String getFileName() {
        Screen screen = lanternaCircuitView.getScreen();
        AtomicBoolean windowNotClosed = new AtomicBoolean(true);

        MultiWindowTextGUI textGUI = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLUE));
        Panel mainPanel = new Panel();

        mainPanel.setLayoutManager(new GridLayout(2));

        TextBox fileTextbox = new TextBox(new TerminalSize(45, 1), "circuit.ser");
        Border borderedTextBox = fileTextbox.withBorder(Borders.singleLine("Circuits will be created in the 'circuits' directory"));
        mainPanel.addComponent(borderedTextBox);
        mainPanel.addComponent(new EmptySpace(new TerminalSize(0, 4)));

        Window window = new BasicWindow();
        window.setComponent(mainPanel);

        mainPanel.addComponent(new Button("Save Circuit", () -> {
            windowNotClosed.set(false);
            System.out.println(fileTextbox.getLine(0));
            filename = new File("circuits/" + fileTextbox.getLine(0)).getAbsolutePath();
            textGUI.removeWindow(window);
        }).withBorder(Borders.doubleLine()));

        mainPanel.addComponent(new Button("Cancel", () -> {
            windowNotClosed.set(false);
            textGUI.removeWindow(window);
        }).withBorder(Borders.singleLine()));

        window.setFocusedInteractable(fileTextbox);
        textGUI.addWindowAndWait(window);

        if (windowNotClosed.get()) {
            lanternaCircuitView.pushEvent(new Event(InputEvent.QUIT, null));
            return null;
        }

        return filename;
    }

    @Override
    public void notifySuccess() {

    }

    @Override
    public void notifyFailure() {

    }
}
