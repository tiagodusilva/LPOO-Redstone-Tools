package com.lpoo.redstonetools.controller.command;

import com.lpoo.redstonetools.model.utils.Side;
import com.lpoo.redstonetools.view.lanterna.circuit.LanternaCircuitView;

public class MoveViewWindowCommand implements Command {

    private LanternaCircuitView lanternaCircuitView;
    private Side side;

    // TODO: Refactor and generalize
    public MoveViewWindowCommand(LanternaCircuitView lanternaCircuitView, Side side) {
        this.lanternaCircuitView = lanternaCircuitView;
        this.side = side;
    }

    @Override
    public void execute() {
        lanternaCircuitView.moveViewWindow(side);
    }

}
