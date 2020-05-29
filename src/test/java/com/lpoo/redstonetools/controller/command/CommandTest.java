package com.lpoo.redstonetools.controller.command;

import com.lpoo.redstonetools.MainController;
import com.lpoo.redstonetools.controller.circuit.CircuitController;
import com.lpoo.redstonetools.controller.state.State;
import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.model.tile.Tile;
import com.lpoo.redstonetools.model.utils.Position;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class CommandTest {
    @Test
    @Tag("controller")
    @Tag("unit-test") @Tag("fast")
    public void testAddTileCommand() {
        CircuitController controller = Mockito.mock(CircuitController.class);
        Circuit circuit = Mockito.mock(Circuit.class);
        Tile tile = Mockito.mock(Tile.class);

        AddTileCommand command = new AddTileCommand(controller, circuit, tile);

        command.execute();

        Mockito.verify(controller, Mockito.times(1)).addTile(circuit, tile);
    }

    @Test
    @Tag("controller")
    @Tag("unit-test") @Tag("fast")
    public void testRotateRightCommand() {
        CircuitController controller = Mockito.mock(CircuitController.class);
        Circuit circuit = Mockito.mock(Circuit.class);
        Position position = Mockito.mock(Position.class);

        RotateRightCommand command = new RotateRightCommand(controller, circuit, position);

        command.execute();

        Mockito.verify(controller, Mockito.times(1)).rotateTileRight(circuit, position);
    }

    @Test
    @Tag("controller")
    @Tag("unit-test") @Tag("fast")
    public void testRotateLeftCommand() {
        CircuitController controller = Mockito.mock(CircuitController.class);
        Circuit circuit = Mockito.mock(Circuit.class);
        Position position = Mockito.mock(Position.class);

        RotateLeftCommand command = new RotateLeftCommand(controller, circuit, position);

        command.execute();

        Mockito.verify(controller, Mockito.times(1)).rotateTileLeft(circuit, position);
    }

    @Test
    @Tag("controller")
    @Tag("unit-test") @Tag("fast")
    public void testInteractionCommand() {
        CircuitController controller = Mockito.mock(CircuitController.class);
        Circuit circuit = Mockito.mock(Circuit.class);
        Position position = Mockito.mock(Position.class);

        InteractionCommand command = new InteractionCommand(controller, circuit, position);

        command.execute();

        Mockito.verify(controller, Mockito.times(1)).interact(circuit, position);
    }

    @Test
    @Tag("controller")
    @Tag("unit-test") @Tag("fast")
    public void testEnterStateCommand() {
        State state = Mockito.mock(State.class);
        MainController controller = Mockito.mock(MainController.class);

        EnterStateCommand command = new EnterStateCommand(state, controller);

        command.execute();

        Mockito.verify(controller, Mockito.times(1)).changeState(state);
    }

    @Test
    @Tag("controller")
    @Tag("unit-test") @Tag("fast")
    public void testAdvanceTickCommand() {
        CircuitController controller = Mockito.mock(CircuitController.class);
        Circuit circuit = Mockito.mock(Circuit.class);

        AdvanceTickCommand command = new AdvanceTickCommand(controller, circuit);

        command.execute();

        Mockito.verify(controller, Mockito.times(1)).advanceTick(circuit);
    }

    @Test
    @Tag("controller")
    @Tag("unit-test") @Tag("fast")
    public void testSetDelayCommand() {
        CircuitController controller = Mockito.mock(CircuitController.class);
        Circuit circuit = Mockito.mock(Circuit.class);
        Position position = Mockito.mock(Position.class);

        SetDelayCommand command = new SetDelayCommand(controller, circuit, position, 6L);

        command.execute();

        Mockito.verify(controller, Mockito.times(1)).setDelay(circuit, position, 6L);
    }
}
