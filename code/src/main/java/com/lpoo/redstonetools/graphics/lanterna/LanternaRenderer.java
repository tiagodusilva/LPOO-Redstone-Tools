package com.lpoo.redstonetools.graphics.lanterna;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import com.lpoo.redstonetools.core.Circuit;
import com.lpoo.redstonetools.core.tiles.NullTile;
import com.lpoo.redstonetools.core.tiles.Tile;

import com.lpoo.redstonetools.graphics.Renderer;

import java.io.IOException;

public class LanternaRenderer implements Renderer {

    private Terminal terminal;
    private Screen screen;
    private TextGraphics graphics;
    private LanternaTileRenderer tileRenderer;

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

        tileRenderer = new LanternaTileRenderer();
    }

    public void renderCircuit(Circuit circuit) {
        graphics.setBackgroundColor(TextColor.ANSI.BLACK);
        graphics.setForegroundColor(TextColor.ANSI.WHITE);
        for (int i = 0; i < screen.getTerminalSize().getColumns() / 3; i++) {
            for (int j = 0; j < screen.getTerminalSize().getRows() / 3; j++) {
                tileRenderer.renderTile(circuit.getTile(i, j), j*3, i*3);
            }
        }
    }

    @Override
    public void render(Circuit circuit) {
        try {
            graphics = screen.newTextGraphics();
            screen.clear();
            tileRenderer.setGraphics(graphics);
            renderCircuit(circuit);
            screen.refresh();
        }
        catch (IOException e) {
            System.out.println("Whoops, ioexception :upside_down:");
        }
    }

}
