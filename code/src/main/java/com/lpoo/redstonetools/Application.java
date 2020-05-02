package com.lpoo.redstonetools;

import com.lpoo.redstonetools.controller.state.CircuitState;
import com.lpoo.redstonetools.controller.state.State;
import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.view.ViewFactory;
import com.lpoo.redstonetools.view.lanterna.LanternaViewFactory;

public class Application {

    public static void main(String[] args) {
        Application app = new Application();
        app.run();
    }

    ViewFactory viewFactory;
    State state;

    public Application() {
        viewFactory = new LanternaViewFactory();
        state = new CircuitState(new Circuit(20, 10), viewFactory);
    }

    public void run() {
        while (true) {
            state.processEvents();
            state.render();

            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
