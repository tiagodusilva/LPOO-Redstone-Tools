package com.lpoo.redstonetools.view.lanterna;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.DefaultWindowManager;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFontConfiguration;
import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.view.CircuitView;
import com.lpoo.redstonetools.view.MenuView;
import com.lpoo.redstonetools.view.ViewFactory;

import java.awt.*;
import java.io.IOException;

public class LanternaViewFactory implements ViewFactory {

    private Terminal terminal;
    private Screen screen;
    private MultiWindowTextGUI textGUI;
    private LanternaMenuBuilder lanternaMenuBuilder;

    public LanternaViewFactory() {
        try {
            Font font = new Font("Consolas", Font.PLAIN, 15);
            SwingTerminalFontConfiguration cfg = SwingTerminalFontConfiguration.getDefault();/*new SwingTerminalFontConfiguration(
                    true,
                    SwingTerminalFontConfiguration.BoldMode.NOTHING,
                    font);*/

            this.terminal = new DefaultTerminalFactory()
                    .setInitialTerminalSize(new TerminalSize(100, 40))
                    .setTerminalEmulatorFontConfiguration(cfg)
                    .setTerminalEmulatorTitle("RedstoneTools")
                    .createTerminal();
            this.screen = new TerminalScreen(terminal);

            this.screen.setCursorPosition(null);   // we don't need a cursor
            this.screen.startScreen();             // screens must be started
            this.screen.doResizeIfNecessary();     // resize screen if necessary

            textGUI = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLACK));
            lanternaMenuBuilder = new LanternaMenuBuilder(textGUI);
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public CircuitView getCircuitView(Circuit circuit) {
        return new LanternaCircuitView(screen, lanternaMenuBuilder, circuit);
    }

    @Override
    public MenuView getMenuView() {
        return new LanternaMenuView(screen, lanternaMenuBuilder);
    }

    @Override
    public void terminate() {
        try {
            terminal.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
