package com.lpoo.redstonetools.view.lanterna.input;

import com.lpoo.redstonetools.controller.event.Event;
import com.lpoo.redstonetools.controller.event.InputEvent;
import com.lpoo.redstonetools.view.lanterna.LanternaCircuitView;

public class LanternaAutoAdvanceTime extends Thread {

    LanternaCircuitView lanternaCircuitView;
    private final long updateFrequency = 1000 / 8;

    public LanternaAutoAdvanceTime(LanternaCircuitView lanternaCircuitView) {
        this.lanternaCircuitView = lanternaCircuitView;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            lanternaCircuitView.pushEvent(new Event(InputEvent.ADVANCE_TICK, null));
            try {
                Thread.sleep(updateFrequency);
            } catch (InterruptedException e) {
                // e.printStackTrace();
                return;
            }
        }
    }

}
