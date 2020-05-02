package com.lpoo.redstonetools.controller.state;

public interface State {
    void processEvents();
    void render(); // Only wrapper to the actual call to View, so SRP is not violated
}
