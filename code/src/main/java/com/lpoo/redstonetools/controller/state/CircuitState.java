package com.lpoo.redstonetools.controller.state;

import com.lpoo.redstonetools.controller.circuit.CircuitController;
import com.lpoo.redstonetools.controller.command.*;
import com.lpoo.redstonetools.controller.event.Event;
import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.model.tile.*;
import com.lpoo.redstonetools.model.utils.Position;
import com.lpoo.redstonetools.view.CircuitView;
import com.lpoo.redstonetools.view.ViewFactory;

import java.util.Queue;

public class CircuitState extends State {

    private CircuitController circuitController;
    private Circuit circuit;
    private CircuitView circuitView;

    public CircuitState(Circuit circuit, ViewFactory viewFactory) {
        super();

        this.circuit = circuit;
        this.circuitController = new CircuitController();
        this.circuitView = viewFactory.getCircuitView(circuit);

        circuitController.addTile(circuit, new ConstantSourceTile(new Position(0, 0)));
        circuitController.addTile(circuit, new WireTile(new Position(2, 0)));
        circuitController.addTile(circuit, new WireTile(new Position(3, 0)));
        circuitController.addTile(circuit, new WireTile(new Position(1, 0)));
        circuitController.addTile(circuit, new WireTile(new Position(4, 0)));
        circuitController.addTile(circuit, new WireTile(new Position(5, 0)));
        circuitController.addTile(circuit, new WireTile(new Position(6, 0)));
        circuitController.addTile(circuit, new WireTile(new Position(6, 1)));
        circuitController.addTile(circuit, new WireTile(new Position(6, 2)));
        circuitController.addTile(circuit, new WireTile(new Position(5, 2)));
        circuitController.addTile(circuit, new WireTile(new Position(4, 2)));
        circuitController.addTile(circuit, new WireTile(new Position(3, 2)));
        circuitController.addTile(circuit, new WireTile(new Position(2, 2)));
        circuitController.addTile(circuit, new WireTile(new Position(1, 2)));
        circuitController.addTile(circuit, new WireTile(new Position(0, 2)));
        circuitController.addTile(circuit, new WireTile(new Position(0, 3)));
        circuitController.addTile(circuit, new WireTile(new Position(0, 4)));
        circuitController.addTile(circuit, new WireTile(new Position(1, 4)));
        circuitController.addTile(circuit, new WireTile(new Position(2, 4)));
        circuitController.addTile(circuit, new WireTile(new Position(3, 4)));
        circuitController.addTile(circuit, new WireTile(new Position(4, 4)));
        circuitController.addTile(circuit, new WireTile(new Position(5, 4)));
        circuitController.addTile(circuit, new WireTile(new Position(6, 4)));
        circuitController.addTile(circuit, new LeverTile(new Position(7, 5)));
        circuitController.addTile(circuit, new LeverTile(new Position(4, 1)));

        circuitController.addTile(circuit, new WireTile(new Position(3, 1)));

        circuitController.addTile(circuit, new RepeaterTile(new Position(6, 5)));
        circuitController.addTile(circuit, new RepeaterTile(new Position(6, 3)));

        circuitController.rotateTileRight(circuit, new Position(6, 5));
        circuitController.rotateTileRight(circuit, new Position(6, 3));

        circuitController.advanceTick(circuit);
    }

    @Override
    public void processEvents() {

        Queue<Event> events = this.circuitView.getEvents();
        while (!events.isEmpty()) {
            Event event = events.remove();
            try {
                // TODO:
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
                    case QUIT:
                        this.exit = true;
                        break;
                    default:
                        break;
                }
            }
            catch (ClassCastException e) {
                e.printStackTrace();
            }
            catch (Exception e) {
                System.err.println("Big oof");
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

}
