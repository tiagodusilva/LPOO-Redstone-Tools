package com.lpoo.redstonetools.controller.command;

import com.lpoo.redstonetools.controller.circuit.CircuitController;
import com.lpoo.redstonetools.model.circuit.Circuit;

public class AdvanceTickCommand implements Command {

    private final CircuitController circuitController;
    private final Circuit circuit;

    public AdvanceTickCommand(CircuitController circuitController, Circuit circuit) {
        this.circuitController = circuitController;
        this.circuit = circuit;
    }

    @Override
    public void execute() {
        circuitController.advanceTick(circuit);
    }

}
