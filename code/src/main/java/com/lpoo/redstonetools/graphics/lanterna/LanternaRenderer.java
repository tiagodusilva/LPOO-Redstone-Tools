package com.lpoo.redstonetools.graphics.lanterna;

import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.lpoo.redstonetools.core.Circuit;
import com.lpoo.redstonetools.graphics.Renderer;

import java.io.IOException;

public class LanternaRenderer implements Renderer {

    private Terminal terminal;
    private Screen screen;

    public LanternaRenderer() {
        try {
            this.terminal = new DefaultTerminalFactory().createTerminal();
            this.screen = new TerminalScreen(terminal);

            this.screen.setCursorPosition(null);   // we don't need a cursor
            this.screen.startScreen();             // screens must be started
            this.screen.doResizeIfNecessary();     // resize screen if necessary
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render(Circuit circuit) {

    }

}
