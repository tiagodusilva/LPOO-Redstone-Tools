package com.lpoo.redstonetools;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFontConfiguration;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFontConfiguration;
import com.lpoo.redstonetools.controller.circuit.CircuitController;
import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.model.tile.ConstantSourceTile;
import com.lpoo.redstonetools.model.tile.LeverTile;
import com.lpoo.redstonetools.model.tile.RepeaterTile;
import com.lpoo.redstonetools.model.tile.WireTile;
import com.lpoo.redstonetools.model.utils.Position;
import com.lpoo.redstonetools.view.lanterna.circuit.LanternaCircuitView;
import com.lpoo.redstonetools.view.lanterna.input.LanternaInput;
import sun.jvm.hotspot.debugger.ThreadAccess;

import java.awt.*;
import java.io.IOException;

public class Application {

    public static void main(String[] args) {

        Application app = new Application();
        app.run();

    }

    Terminal terminal;
    Screen screen;

    Circuit circuit;
    CircuitController circuitController;

    LanternaCircuitView lanternaCircuitView;

    LanternaInput lanternaInput;

    public Application() {

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

        circuit = new Circuit(7, 4);
        circuitController = new CircuitController();

        lanternaCircuitView = new LanternaCircuitView(screen, circuit);

        lanternaInput = new LanternaInput(lanternaCircuitView);
        lanternaInput.start();
    }

    public void run() {

        // Temporary
        circuitController.addTile(circuit, new ConstantSourceTile(new Position(0, 0)));
        circuitController.addTile(circuit, new WireTile(new Position(2, 0)));
        circuitController.addTile(circuit, new WireTile(new Position(3, 0)));
        circuitController.addTile(circuit, new WireTile(new Position(1, 0)));
        circuitController.addTile(circuit, new WireTile(new Position(4, 0)));
        circuitController.addTile(circuit, new WireTile(new Position(5, 0)));
        circuitController.addTile(circuit, new WireTile(new Position(6, 0)));
        circuitController.addTile(circuit, new WireTile(new Position(6, 1)));
        circuitController.addTile(circuit, new WireTile(new Position(6, 2)));
        circuitController.addTile(circuit, new WireTile(new Position(5, 2)));
        circuitController.addTile(circuit, new WireTile(new Position(4, 2)));
        circuitController.addTile(circuit, new WireTile(new Position(3, 2)));
        circuitController.addTile(circuit, new WireTile(new Position(2, 2)));
        circuitController.addTile(circuit, new WireTile(new Position(1, 2)));
        circuitController.addTile(circuit, new WireTile(new Position(0, 2)));
        circuitController.addTile(circuit, new WireTile(new Position(0, 3)));
        circuitController.addTile(circuit, new WireTile(new Position(0, 4)));
        circuitController.addTile(circuit, new WireTile(new Position(1, 4)));
        circuitController.addTile(circuit, new WireTile(new Position(2, 4)));
        circuitController.addTile(circuit, new WireTile(new Position(3, 4)));
        circuitController.addTile(circuit, new WireTile(new Position(4, 4)));
        circuitController.addTile(circuit, new WireTile(new Position(5, 4)));
        circuitController.addTile(circuit, new WireTile(new Position(6, 4)));
        circuitController.addTile(circuit, new LeverTile(new Position(7, 5)));
        circuitController.addTile(circuit, new LeverTile(new Position(4, 1)));

        //((LeverTile)circuit.getTile(4, 1)).toggle();

        circuitController.addTile(circuit, new WireTile(new Position(3, 1)));

        circuitController.addTile(circuit, new RepeaterTile(new Position(6, 5)));
        circuitController.addTile(circuit, new RepeaterTile(new Position(6, 3)));

        circuitController.rotateTileRight(circuit, new Position(6, 5));
        circuitController.rotateTileRight(circuit, new Position(6, 3));

        // Temporary


        circuitController.advanceTick(circuit);
        while (true) {
            lanternaCircuitView.render(circuit);
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
