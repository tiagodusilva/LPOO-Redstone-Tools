package com.lpoo.redstonetools;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFontConfiguration;

import java.awt.*;
import java.io.IOException;

public class Application {

    public static void main(String[] args) {

        if (true /* lanterna */) {
            try {
                Font font = new Font("Consolas", Font.PLAIN, 15);
                SwingTerminalFontConfiguration cfg = SwingTerminalFontConfiguration.newInstance(font);
                Terminal terminal = new DefaultTerminalFactory()
                                        .setTerminalEmulatorFontConfiguration(cfg)
                                        .setInitialTerminalSize(new TerminalSize(100, 40))
                                        .createTerminal();

                TerminalScreen screen = new TerminalScreen(terminal);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    private static void run() {

    }

}
