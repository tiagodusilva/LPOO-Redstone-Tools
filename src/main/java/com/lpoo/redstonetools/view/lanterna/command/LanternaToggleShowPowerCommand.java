package com.lpoo.redstonetools.view.lanterna.command;

import com.lpoo.redstonetools.controller.command.Command;
import com.lpoo.redstonetools.view.lanterna.LanternaCircuitView;

public class LanternaToggleShowPowerCommand implements Command {

    LanternaCircuitView lanternaCircuitView;

    public LanternaToggleShowPowerCommand(LanternaCircuitView lanternaCircuitView) {
        this.lanternaCircuitView = lanternaCircuitView;
    }

    @Override
    public void execute() {
        lanternaCircuitView.toggleShowPower();
    }
}
