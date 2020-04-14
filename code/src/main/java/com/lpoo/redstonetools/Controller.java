package com.lpoo.redstonetools;

import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.lpoo.redstonetools.core.Circuit;
import com.lpoo.redstonetools.graphics.Renderer;

import java.io.IOException;

public class Controller {

    private Renderer renderer;
    private Circuit circuit;


    public static void main(String[] args) {
        Controller app = new Controller();
        app.run();
    }

    void run() {

    }

}
