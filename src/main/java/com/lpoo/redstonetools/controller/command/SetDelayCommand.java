package com.lpoo.redstonetools.controller.command;

import com.lpoo.redstonetools.controller.circuit.CircuitController;
import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.model.utils.Position;

public class SetDelayCommand implements Command {
    private final Position position;
    private final CircuitController circuitController;
    private final Circuit circuit;
    private final long delay;

    public SetDelayCommand(CircuitController circuitController, Circuit circuit, Position position, Long delay) {
        this.circuitController = circuitController;
        this.circuit = circuit;
        this.position = position;
        this.delay = delay;
    }

    @Override
    public void execute() {
        circuitController.setDelay(circuit, position, delay);
    }

}
