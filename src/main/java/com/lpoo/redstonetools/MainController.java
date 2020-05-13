package com.lpoo.redstonetools;

import com.lpoo.redstonetools.controller.circuit.CircuitController;
import com.lpoo.redstonetools.controller.state.State;
import com.lpoo.redstonetools.view.ViewFactory;

public interface MainController {
    ViewFactory getViewFactory();
    void changeState(State state);
    CircuitController getCircuitController();
}
