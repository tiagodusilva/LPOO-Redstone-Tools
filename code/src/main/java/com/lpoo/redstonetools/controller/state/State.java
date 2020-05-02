package com.lpoo.redstonetools.controller.state;

public abstract class State {

    protected boolean exit;

    public State() {
        exit = false;
    }

    public boolean exit() { return exit; }
    public abstract void processEvents();
    public abstract void render(); // Only wrapper to the actual call to View, so SRP is not violated
    public abstract void atExit();
}
