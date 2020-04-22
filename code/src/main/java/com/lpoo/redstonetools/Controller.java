package com.lpoo.redstonetools;

import com.lpoo.redstonetools.core.Circuit;
import com.lpoo.redstonetools.core.tiles.ConstantSourceTile;
import com.lpoo.redstonetools.core.tiles.LeverTile;
import com.lpoo.redstonetools.core.tiles.WireTile;
import com.lpoo.redstonetools.core.utils.Position;
import com.lpoo.redstonetools.graphics.Renderer;
import com.lpoo.redstonetools.graphics.TileRenderer;
import com.lpoo.redstonetools.graphics.lanterna.LanternaRenderer;
import com.lpoo.redstonetools.graphics.lanterna.tiles.LanternaConstantSourceTileRenderer;
import com.lpoo.redstonetools.graphics.lanterna.tiles.LanternaLeverTileRenderer;
import com.lpoo.redstonetools.graphics.lanterna.tiles.LanternaWireTileRenderer;

public class Controller {

    private Renderer renderer;
    private Circuit circuit;


    public static void main(String[] args) {
        Controller app = new Controller();
        app.run();
    }

    void run() {
        LanternaRenderer renderer = new LanternaRenderer();
        TileRenderer wireRenderer = new LanternaWireTileRenderer(renderer.getScreen());
        TileRenderer constantSourceTileRenderer = new LanternaConstantSourceTileRenderer(renderer.getScreen());
        TileRenderer leverTileRenderer = new LanternaLeverTileRenderer(renderer.getScreen());


        Circuit circuit = new Circuit(20, 20, renderer.getCircuitRenderer());


        circuit.addTile(new ConstantSourceTile(new Position(0, 0), constantSourceTileRenderer));
        circuit.addTile(new WireTile(new Position(1, 0), wireRenderer));
        circuit.addTile(new WireTile(new Position(2, 0), wireRenderer));
        circuit.addTile(new WireTile(new Position(3, 0), wireRenderer));
        circuit.addTile(new WireTile(new Position(4, 0), wireRenderer));
        circuit.addTile(new WireTile(new Position(5, 0), wireRenderer));
        circuit.addTile(new WireTile(new Position(6, 0), wireRenderer));
        circuit.addTile(new WireTile(new Position(6, 1), wireRenderer));
        circuit.addTile(new WireTile(new Position(6, 2), wireRenderer));
        circuit.addTile(new WireTile(new Position(5, 2), wireRenderer));
        circuit.addTile(new WireTile(new Position(4, 2), wireRenderer));
        circuit.addTile(new WireTile(new Position(3, 2), wireRenderer));
        circuit.addTile(new WireTile(new Position(2, 2), wireRenderer));
        circuit.addTile(new WireTile(new Position(1, 2), wireRenderer));
        circuit.addTile(new WireTile(new Position(0, 2), wireRenderer));
        circuit.addTile(new WireTile(new Position(0, 3), wireRenderer));
        circuit.addTile(new WireTile(new Position(0, 4), wireRenderer));
        circuit.addTile(new WireTile(new Position(1, 4), wireRenderer));
        circuit.addTile(new WireTile(new Position(2, 4), wireRenderer));
        circuit.addTile(new WireTile(new Position(3, 4), wireRenderer));
        circuit.addTile(new WireTile(new Position(4, 4), wireRenderer));
        circuit.addTile(new WireTile(new Position(5, 4), wireRenderer));
        circuit.addTile(new WireTile(new Position(6, 4), wireRenderer));
        circuit.addTile(new LeverTile(new Position(7, 5), leverTileRenderer));
        circuit.addTile(new LeverTile(new Position(9, 5), leverTileRenderer));

        ((LeverTile)circuit.getTile(9, 5)).toggle();

        circuit.addTile(new WireTile(new Position(3, 1), wireRenderer));

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
