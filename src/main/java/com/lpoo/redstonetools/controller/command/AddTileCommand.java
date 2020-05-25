package com.lpoo.redstonetools.controller.command;

import com.lpoo.redstonetools.controller.circuit.CircuitController;
import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.model.tile.Tile;

public class AddTileCommand implements Command {

    private final CircuitController circuitController;
    private final Circuit circuit;
    private final Tile tile;

    public AddTileCommand(CircuitController circuitController, Circuit circuit, Tile tile) {
        this.circuitController = circuitController;
        this.circuit = circuit;
        this.tile = tile;
    }

    @Override
    public void execute() {
        circuitController.addTile(circuit, tile);
    }
}
