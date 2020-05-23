package com.lpoo.redstonetools.controller.state;

import com.lpoo.redstonetools.MainController;
import com.lpoo.redstonetools.controller.circuit.CircuitController;
import com.lpoo.redstonetools.controller.command.*;
import com.lpoo.redstonetools.controller.event.Event;
import com.lpoo.redstonetools.exception.InvalidCircuitException;
import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.model.tile.*;
import com.lpoo.redstonetools.model.utils.Position;
import com.lpoo.redstonetools.view.CircuitView;
import com.lpoo.redstonetools.view.LoadCustomStrategy;
import com.lpoo.redstonetools.view.SaveStrategy;
import com.lpoo.redstonetools.view.ViewFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Queue;

public class CircuitState extends State {

    private CircuitController circuitController;
    private Circuit circuit;
    private CircuitView circuitView;

    public CircuitState(Circuit circuit, MainController mainController) {
        super(mainController);

        this.circuit = circuit;
        this.circuitController = mainController.getCircuitController();
        this.circuitView = mainController.getViewFactory().getCircuitView(circuit);
    }

    @Override
    public void processEvents() {

        Queue<Event> events = this.circuitView.getEvents();
        while (!events.isEmpty()) {
            Event event = events.remove();
            try {
                switch (event.getInputEvent()) {
                    case ADD_TILE:
                        new AddTileCommand(circuitController, circuit, (Tile) event.getObject()).execute();
                        break;
                    case INTERACT:
                        new InteractionCommand(circuitController, circuit, (Position) event.getObject()).execute();
                        break;
                    case ADVANCE_TICK:
                        new AdvanceTickCommand(circuitController, circuit).execute();
                        break;
                    case ROTATE_LEFT:
                        new RotateLeftCommand(circuitController, circuit, (Position) event.getObject()).execute();
                        break;
                    case ROTATE_RIGHT:
                        new RotateRightCommand(circuitController, circuit, (Position) event.getObject()).execute();
                        break;
                    case SAVE:
                        saveCircuit((SaveStrategy) event.getObject());
                        break;
                    case LOAD_CUSTOM:
                        loadCustom((LoadCustomStrategy) event.getObject());
                        break;
                    case QUIT:
                        this.exit = true;
                        events.clear();
                        return;
                    default:
                        break;
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void render() {
        circuitView.render();
    }

    @Override
    public void atExit() {
        circuitView.cleanup();
    }

    private void saveCircuit(SaveStrategy saveStrategy) {
        circuitView.stopInputs();
        String filename = saveStrategy.getFileName(circuit.getCircuitName());
        if (filename != null) {
            if (CircuitController.saveCircuit(circuit, filename))
                saveStrategy.notifySuccess(filename);
            else
                saveStrategy.notifyFailure(filename);
        }
        circuitView.startInputs();
    }

    private void loadCustom(LoadCustomStrategy loadCustomStrategy) {
        circuitView.stopInputs();
        String filename = loadCustomStrategy.getFileName();
        if (filename != null) {
            try {
                Circuit newSubcircuit = CircuitController.loadCircuit(filename);
                newSubcircuit.setPosition(loadCustomStrategy.getPosition());
                this.circuitController.addTile(circuit, newSubcircuit);
            } catch (InvalidCircuitException e) {
                e.printStackTrace();
            }
        }
        circuitView.startInputs();
    }

}
