package com.lpoo.redstonetools.graphics.lanterna;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import com.googlecode.lanterna.terminal.swing.AWTTerminalFontConfiguration;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFontConfiguration;
import com.lpoo.redstonetools.core.Circuit;
import com.lpoo.redstonetools.graphics.Renderer;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class LanternaRenderer {

    private Terminal terminal;
    private Screen screen;
    private TextGraphics graphics;
    private LanternaCircuitRenderer circuitRenderer;

    public LanternaRenderer() {
        try {
            Font font = new Font("Consolas", Font.PLAIN, 15);
            AWTTerminalFontConfiguration cfg = new SwingTerminalFontConfiguration(
                    true,
                    AWTTerminalFontConfiguration.BoldMode.NOTHING,
                    font);

            this.terminal = new DefaultTerminalFactory()
                    .setInitialTerminalSize(new TerminalSize(100, 40))
                    .setTerminalEmulatorFontConfiguration(cfg)
                    .createTerminal();
            this.screen = new TerminalScreen(terminal);

            this.screen.setCursorPosition(null);   // we don't need a cursor
            this.screen.startScreen();             // screens must be started
            this.screen.doResizeIfNecessary();     // resize screen if necessary
        } catch (IOException e) {
            e.printStackTrace();
        }

        circuitRenderer = new LanternaCircuitRenderer(screen, screen.newTextGraphics());
    }

    private void loadCustomFont(){
        try {
            GraphicsEnvironment ge =
                    GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src\\main\\resources\\unifont-13.0.01.ttf")));
        } catch (IOException|FontFormatException e) {
            //Handle exception
            e.printStackTrace();
        }
    }

    public Screen getScreen() {
        return screen;
    }

    public LanternaCircuitRenderer getCircuitRenderer() {
        return circuitRenderer;
    }

    public void render(Circuit circuit) {
        try {
            graphics = screen.newTextGraphics();
            screen.clear();
            circuitRenderer.render(circuit, 0, 0);
            screen.refresh();
        }
        catch (IOException e) {
            System.out.println("Whoops, ioexception :upside_down:");
        }
    }

}
