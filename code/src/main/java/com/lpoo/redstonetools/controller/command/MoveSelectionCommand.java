package com.lpoo.redstonetools.controller.command;

import com.lpoo.redstonetools.model.utils.Side;
import com.lpoo.redstonetools.view.lanterna.circuit.LanternaCircuitView;

public class MoveSelectionCommand implements Command {

    private LanternaCircuitView lanternaCircuitView;
    private Side side;

    // TODO: Refactor and generalize
    public MoveSelectionCommand(LanternaCircuitView lanternaCircuitView, Side side) {
        this.lanternaCircuitView = lanternaCircuitView;
        this.side = side;
    }

    @Override
    public void execute() {
        lanternaCircuitView.moveSelectedTile(side);
    }

}
