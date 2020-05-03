package com.lpoo.redstonetools.view;

import com.lpoo.redstonetools.model.circuit.Circuit;

public interface ViewFactory {
    CircuitView getCircuitView(Circuit circuit);

    MenuView getMenuView();

    void terminate();
}
