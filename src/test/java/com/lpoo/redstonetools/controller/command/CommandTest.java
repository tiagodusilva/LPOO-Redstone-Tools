package com.lpoo.redstonetools.controller.command;

import com.lpoo.redstonetools.MainController;
import com.lpoo.redstonetools.controller.circuit.CircuitController;
import com.lpoo.redstonetools.controller.state.State;
import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.model.tile.Tile;
import com.lpoo.redstonetools.model.utils.Position;
import org.mockito.Mockito;

public class CommandTest {
    /*
    @Test
    public void testAddTileCommand() {
        CircuitController controller = Mockito.mock(CircuitController.class);
        Circuit circuit = Mockito.mock(Circuit.class);
        Tile tile = Mockito.mock(Tile.class);

        AddTileCommand command = new AddTileCommand(controller, circuit, tile);

        command.execute();

        Mockito.verify(controller, Mockito.times(1)).addTile(circuit, tile);
    }

    @Test
    public void testRotateRightCommand() {
        CircuitController controller = Mockito.mock(CircuitController.class);
        Circuit circuit = Mockito.mock(Circuit.class);
        Position position = Mockito.mock(Position.class);

        RotateRightCommand command = new RotateRightCommand(controller, circuit, position);

        command.execute();

        Mockito.verify(controller, Mockito.times(1)).rotateTileRight(circuit, position);
    }

    @Test
    public void testRotateLeftCommand() {
        CircuitController controller = Mockito.mock(CircuitController.class);
        Circuit circuit = Mockito.mock(Circuit.class);
        Position position = Mockito.mock(Position.class);

        RotateLeftCommand command = new RotateLeftCommand(controller, circuit, position);

        command.execute();

        Mockito.verify(controller, Mockito.times(1)).rotateTileLeft(circuit, position);
    }

    @Test
    public void testInteractionCommand() {
        CircuitController controller = Mockito.mock(CircuitController.class);
        Circuit circuit = Mockito.mock(Circuit.class);
        Position position = Mockito.mock(Position.class);

        InteractionCommand command = new InteractionCommand(controller, circuit, position);

        command.execute();

        Mockito.verify(controller, Mockito.times(1)).interact(circuit, position);
    }

    @Test
    public void testEnterStateCommand() {
        State state = Mockito.mock(State.class);
        MainController controller = Mockito.mock(MainController.class);

        EnterStateCommand command = new EnterStateCommand(state, controller);

        command.execute();

        Mockito.verify(controller, Mockito.times(1)).changeState(state);
    }

    @Test
    public void testAdvanceTickCommand() {
        CircuitController controller = Mockito.mock(CircuitController.class);
        Circuit circuit = Mockito.mock(Circuit.class);

        AdvanceTickCommand command = new AdvanceTickCommand(controller, circuit);

        command.execute();

        Mockito.verify(controller, Mockito.times(1)).advanceTick(circuit);
    }*/
}
