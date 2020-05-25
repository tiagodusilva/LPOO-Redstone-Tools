package com.lpoo.redstonetools.controller.state;

import com.lpoo.redstonetools.MainController;
import com.lpoo.redstonetools.view.ViewFactory;

public abstract class State {

    protected boolean exit;
    protected MainController mainController;
    protected final int processed_per_frame = 8;

    public State(MainController mainController) {
        exit = false;
        this.mainController = mainController;
    }

    public boolean exit() { return exit; }
    public abstract void processEvents();
    public abstract void render(); // Only wrapper to the actual call to View, so SRP is not violated
    public abstract void atExit();
}
