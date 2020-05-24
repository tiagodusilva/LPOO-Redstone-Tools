package com.lpoo.redstonetools;

import com.lpoo.redstonetools.controller.circuit.CircuitController;
import com.lpoo.redstonetools.controller.state.CircuitState;
import com.lpoo.redstonetools.controller.state.MenuState;
import com.lpoo.redstonetools.controller.state.State;
import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.view.ViewFactory;
import com.lpoo.redstonetools.view.lanterna.LanternaViewFactory;

public class Application implements MainController {

    public static void main(String[] args) {
        Application app = new Application();
        app.run();
    }

    private ViewFactory viewFactory;
    private State state;

    private final long timeBetweenFrames;

    public Application() {
        viewFactory = new LanternaViewFactory();
        state = new MenuState(this);
        long framerate = 60;
        timeBetweenFrames = 1000 / framerate;
    }

    public void run() {
        long sleepTime, lastTime;
        while (!state.exit()) {
            lastTime = System.currentTimeMillis();
            state.processEvents();
            state.render();

            sleepTime = Math.max(0, timeBetweenFrames - (System.currentTimeMillis() - lastTime));

            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        state.atExit();
        viewFactory.terminate();
    }

    @Override
    public ViewFactory getViewFactory() {
        return viewFactory;
    }

    @Override
    public void changeState(State state) {
        this.state.atExit();
        this.state = state;
    }

    @Override
    public CircuitController getCircuitController() {
        return new CircuitController();
    }
}
