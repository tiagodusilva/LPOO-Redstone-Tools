package com.lpoo.redstonetools;

import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.lpoo.redstonetools.core.Circuit;
import com.lpoo.redstonetools.core.tiles.ConstantSourceTile;
import com.lpoo.redstonetools.core.tiles.WireTile;
import com.lpoo.redstonetools.core.utils.Position;
import com.lpoo.redstonetools.graphics.Renderer;
import com.lpoo.redstonetools.graphics.lanterna.LanternaRenderer;

import java.io.IOException;

public class Controller {

    private Renderer renderer;
    private Circuit circuit;


    public static void main(String[] args) {
        Controller app = new Controller();
        app.run();
    }

    void run() {
        Circuit circuit = new Circuit(20, 20);
        Renderer renderer = new LanternaRenderer();

        circuit.addTile(new ConstantSourceTile(new Position(0, 0)));
        circuit.addTile(new WireTile(new Position(1, 0)));
        circuit.addTile(new WireTile(new Position(2, 0)));
        circuit.addTile(new WireTile(new Position(3, 0)));
        circuit.addTile(new WireTile(new Position(4, 0)));
        circuit.addTile(new WireTile(new Position(5, 0)));
        circuit.addTile(new WireTile(new Position(6, 0)));
        circuit.addTile(new WireTile(new Position(6, 1)));
        circuit.addTile(new WireTile(new Position(6, 2)));
        circuit.addTile(new WireTile(new Position(5, 2)));
        circuit.addTile(new WireTile(new Position(4, 2)));
        circuit.addTile(new WireTile(new Position(3, 2)));
        circuit.addTile(new WireTile(new Position(2, 2)));
        circuit.addTile(new WireTile(new Position(1, 2)));
        circuit.addTile(new WireTile(new Position(0, 2)));
        circuit.addTile(new WireTile(new Position(0, 3)));
        circuit.addTile(new WireTile(new Position(0, 4)));
        circuit.addTile(new WireTile(new Position(1, 4)));
        circuit.addTile(new WireTile(new Position(2, 4)));
        circuit.addTile(new WireTile(new Position(3, 4)));
        circuit.addTile(new WireTile(new Position(4, 4)));
        circuit.addTile(new WireTile(new Position(5, 4)));
        circuit.addTile(new WireTile(new Position(6, 4)));

        circuit.addTile(new WireTile(new Position(3, 1)));

        while (true) {

            circuit.advanceTick();
            renderer.render(circuit);

            try {
                Thread.sleep(500, 0);
            }
            catch (InterruptedException e) {
                System.out.println("Interrupt da amizade");
            }
        }

    }

}
