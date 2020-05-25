package com.lpoo.redstonetools.controller.command;

import com.lpoo.redstonetools.controller.circuit.CircuitController;
import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.model.utils.Position;

public class RotateRightCommand implements Command {
    private final Position position;
    private final CircuitController circuitController;
    private final Circuit circuit;

    public RotateRightCommand(CircuitController circuitController, Circuit circuit, Position position) {
        this.circuitController = circuitController;
        this.circuit = circuit;
        this.position = position;
    }

    @Override
    public void execute() {
        circuitController.rotateTileRight(circuit, position);
    }

}
