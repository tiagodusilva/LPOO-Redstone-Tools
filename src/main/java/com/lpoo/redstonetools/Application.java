package com.lpoo.redstonetools;

import com.lpoo.redstonetools.controller.circuit.CircuitController;
import com.lpoo.redstonetools.controller.state.MenuState;
import com.lpoo.redstonetools.controller.state.State;
import com.lpoo.redstonetools.exception.InvalidConfigException;
import com.lpoo.redstonetools.view.ViewFactory;
import com.lpoo.redstonetools.view.lanterna.LanternaViewFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Application implements MainController {

    public static void main(String[] args) {
        Application app = null;
        try {
            app = new Application();
        } catch (InvalidConfigException e) {
            e.printStackTrace();
            return;
        }
        app.run();
    }

    private ViewFactory viewFactory;
    private State state;

    private long timeBetweenFrames;

    private boolean dynamicFPS;

    public Application() throws InvalidConfigException {
        viewFactory = new LanternaViewFactory();
        state = new MenuState(this);

        try {
            this.readConfig();
        } catch (Exception e) {
            e.printStackTrace();
            throw new InvalidConfigException("Invalid configuration file for RedstoneTools");
        }
    }

    public void readConfig() throws IOException, NumberFormatException {
        HashMap<String, String> map = new HashMap<String, String>();

        String line;
        BufferedReader reader = new BufferedReader(new FileReader("redstonetools.cfg"));
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(":", 2);
            if (parts.length >= 2) {
                String key = parts[0];
                String value = parts[1];
                map.put(key, value);
            }
        }

        String frameRateCfg = map.getOrDefault("framerate", "60");
        long framerate = Long.parseLong(frameRateCfg);
        this.timeBetweenFrames = 1000 / framerate;

        String dynamicFpsCfg = map.getOrDefault("dynamic-fps", "false");
        this.dynamicFPS = Boolean.parseBoolean(dynamicFpsCfg);

        reader.close();
    }

    public void run() {
        long sleepTime, lastTime;
        while (!state.exit()) {
            lastTime = System.currentTimeMillis();
            state.processEvents();
            state.render();

            sleepTime = this.dynamicFPS ? Math.max(2, timeBetweenFrames - (System.currentTimeMillis() - lastTime)) : timeBetweenFrames;

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
