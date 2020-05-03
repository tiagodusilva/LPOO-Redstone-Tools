package com.lpoo.redstonetools.view.lanterna;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFontConfiguration;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFontConfiguration;
import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.view.CircuitView;
import com.lpoo.redstonetools.view.MenuView;
import com.lpoo.redstonetools.view.ViewFactory;

import java.awt.*;
import java.io.IOException;

public class LanternaViewFactory implements ViewFactory {

    Terminal terminal;
    Screen screen;

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
                    .createTerminal();
            this.screen = new TerminalScreen(terminal);

            this.screen.setCursorPosition(null);   // we don't need a cursor
            this.screen.startScreen();             // screens must be started
            this.screen.doResizeIfNecessary();     // resize screen if necessary
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public CircuitView getCircuitView(Circuit circuit) {
        return new LanternaCircuitView(screen, circuit);
    }

    @Override
    public MenuView getMenuView() {
        return new LanternaMenuView(screen);
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
