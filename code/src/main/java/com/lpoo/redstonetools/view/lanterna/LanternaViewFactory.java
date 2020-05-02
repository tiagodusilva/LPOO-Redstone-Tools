package com.lpoo.redstonetools.view.lanterna;

import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.view.CircuitView;
import com.lpoo.redstonetools.view.MenuView;
import com.lpoo.redstonetools.view.ViewFactory;
import com.lpoo.redstonetools.view.lanterna.circuit.LanternaCircuitView;

public class LanternaViewFactory implements ViewFactory {

    @Override
    public CircuitView getCircuitView(Circuit circuit) {
        return new LanternaCircuitView(circuit);
    }

    @Override
    public MenuView getMenuView() {
        return null;
    }
}
