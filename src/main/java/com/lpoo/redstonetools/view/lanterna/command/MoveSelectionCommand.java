package com.lpoo.redstonetools.view.lanterna.command;

import com.lpoo.redstonetools.controller.command.Command;
import com.lpoo.redstonetools.model.utils.Side;
import com.lpoo.redstonetools.view.lanterna.LanternaCircuitView;

public class MoveSelectionCommand implements Command {

    private LanternaCircuitView lanternaCircuitView;
    private Side side;

    public MoveSelectionCommand(LanternaCircuitView lanternaCircuitView, Side side) {
        this.lanternaCircuitView = lanternaCircuitView;
        this.side = side;
    }

    @Override
    public void execute() {
        lanternaCircuitView.moveSelectedTile(side);
    }

}
