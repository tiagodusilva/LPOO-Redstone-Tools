package com.lpoo.redstonetools.controller.command;

import com.lpoo.redstonetools.controller.circuit.CircuitController;
import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.model.utils.Position;

public class InteractionCommand implements Command {

    private Position position;
    private CircuitController circuitController;
    private Circuit circuit;

    public InteractionCommand(CircuitController circuitController, Circuit circuit, Position position) {
        this.circuitController = circuitController;
        this.circuit = circuit;
        this.position = position;
    }

    @Override
    public void execute() {
        circuitController.interact(circuit, position);
    }

}
