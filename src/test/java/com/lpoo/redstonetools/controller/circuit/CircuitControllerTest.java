package com.lpoo.redstonetools.controller.circuit;

import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.model.tile.Tile;
import com.lpoo.redstonetools.model.utils.Position;
import com.lpoo.redstonetools.model.utils.Power;
import com.lpoo.redstonetools.model.utils.Side;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.Set;

public class CircuitControllerTest {

    private CircuitController controller;

    @Before
    public void setup() {
        this.controller = Mockito.mock(CircuitController.class, Mockito.withSettings().useConstructor());
    }
    /*
    @Test
    public void testAddTileUnsuccessful() {
        Circuit circuit = Mockito.mock(Circuit.class);
        Tile tile = Mockito.mock(Tile.class);
        Position position = Mockito.mock(Position.class);

        Mockito.when(tile.getPosition()).thenReturn(position);

        // Call real method that is being tested
        Mockito.doCallRealMethod().when(controller).addTile(circuit, tile);

        // When tile isn't added
        Mockito.when(circuit.addTile(tile)).thenReturn(false);

        controller.addTile(circuit, tile);
        Mockito.verify(circuit, Mockito.times(1)).addTile(tile);
        Mockito.verify(tile, Mockito.times(0)).update(circuit);
        Mockito.verify(controller, Mockito.times(0)).notifyNeighbourTiles(circuit, position);
    }

    @Test
    public void testAddTileSuccessful() {
        Circuit circuit = Mockito.mock(Circuit.class);
        Tile tile = Mockito.mock(Tile.class);
        Position position = Mockito.mock(Position.class);

        Mockito.when(tile.getPosition()).thenReturn(position);

        // Call real method that is being tested
        Mockito.doCallRealMethod().when(controller).addTile(circuit, tile);

        // When tile isn't added
        Mockito.when(circuit.addTile(tile)).thenReturn(true);

        controller.addTile(circuit, tile);
        Mockito.verify(circuit, Mockito.times(1)).addTile(tile);
        Mockito.verify(tile, Mockito.times(1)).update(circuit);
        Mockito.verify(controller, Mockito.times(1)).notifyNeighbourTiles(circuit, position);
    }

    @Test
    public void testInteractNoTriggerUpdate() {
        Circuit circuit = Mockito.mock(Circuit.class);
        Tile tile = Mockito.mock(Tile.class);
        Position position = Mockito.mock(Position.class);

        Mockito.when(circuit.getTile(position)).thenReturn(tile);
        // Interaction doesn't trigger update
        Mockito.when(tile.interact(circuit)).thenReturn(false);

        // Call real method that is being tested
        Mockito.doCallRealMethod().when(controller).interact(circuit, position);

        controller.interact(circuit, position);

        Mockito.verify(circuit, Mockito.times(1)).getTile(position);
        Mockito.verify(tile, Mockito.times(1)).interact(circuit);
        Mockito.verify(controller, Mockito.times(0)).updateAllNeighbourTiles(circuit, position);
    }

    @Test
    public void testInteractTriggerUpdate() {
        Circuit circuit = Mockito.mock(Circuit.class);
        Tile tile = Mockito.mock(Tile.class);
        Position position = Mockito.mock(Position.class);

        Mockito.when(circuit.getTile(position)).thenReturn(tile);
        // Interaction triggers update
        Mockito.when(tile.interact(circuit)).thenReturn(true);

        // Call real method that is being tested
        Mockito.doCallRealMethod().when(controller).interact(circuit, position);

        controller.interact(circuit, position);

        Mockito.verify(circuit, Mockito.times(1)).getTile(position);
        Mockito.verify(tile, Mockito.times(1)).interact(circuit);
        Mockito.verify(controller, Mockito.times(1)).updateAllNeighbourTiles(circuit, position);
    }

    @Test
    public void testAdvanceTick() {
        Circuit circuit = Mockito.mock(Circuit.class);
        Tile tile1 = Mockito.mock(Tile.class);
        Tile tile2 = Mockito.mock(Tile.class);
        Position position1 = Mockito.mock(Position.class);
        Position position2 = Mockito.mock(Position.class);

        Set<Position> tickedTiles = new HashSet<>();
        tickedTiles.add(position1); tickedTiles.add(position2);

        Mockito.when(circuit.getTickedTiles()).thenReturn(tickedTiles);
        Mockito.when(circuit.getTile(position1)).thenReturn(tile1);
        Mockito.when(circuit.getTile(position2)).thenReturn(tile2);

        // tile 1 triggers update on next tick
        Mockito.when(tile1.nextTick()).thenReturn(true);
        // tile 2 doesn't trigger update on next tick
        Mockito.when(tile2.nextTick()).thenReturn(false);

        // Call real method that is being tested
        Mockito.doCallRealMethod().when(controller).advanceTick(circuit);

        controller.advanceTick(circuit);

        Mockito.verify(circuit, Mockito.times(1)).nextTick();
        Mockito.verify(circuit, Mockito.times(1)).getTickedTiles();
        Mockito.verify(circuit, Mockito.times(1)).getTile(position1);
        Mockito.verify(circuit, Mockito.times(1)).getTile(position2);

        Mockito.verify(tile1, Mockito.times(1)).nextTick();
        Mockito.verify(controller, Mockito.times(1)).updateAllNeighbourTiles(circuit, position1);

        Mockito.verify(tile2, Mockito.times(1)).nextTick();
        Mockito.verify(controller, Mockito.times(0)).updateAllNeighbourTiles(circuit, position2);
    }

    @Test
    public void testUpdateAllNeighbourTiles() {
        Circuit circuit = Mockito.mock(Circuit.class);
        Tile tile = Mockito.mock(Tile.class);
        Position position = Mockito.mock(Position.class);

        Mockito.when(circuit.getTile(position)).thenReturn(tile);

        Mockito.when(tile.outputsPower(Side.UP)).thenReturn(true);
        Mockito.when(tile.outputsPower(Side.LEFT)).thenReturn(false);
        Mockito.when(tile.outputsPower(Side.DOWN)).thenReturn(false);
        Mockito.when(tile.outputsPower(Side.RIGHT)).thenReturn(true);

        for (Side side : Side.values()) {
            Mockito.when(tile.getPower(side)).thenReturn(Power.getMin());
        }

        // Call real method that is being tested
        Mockito.doCallRealMethod().when(controller).updateAllNeighbourTiles(circuit, position);

        controller.updateAllNeighbourTiles(circuit, position);

        Mockito.verify(tile, Mockito.times(1)).outputsPower(Side.UP);
        Mockito.verify(controller, Mockito.times(1)).updateNeighbourTile(circuit, position, Power.getMin(), Side.UP);

        Mockito.verify(tile, Mockito.times(1)).outputsPower(Side.LEFT);
        Mockito.verify(controller, Mockito.times(0)).updateNeighbourTile(circuit, position, Power.getMin(), Side.LEFT);

        Mockito.verify(tile, Mockito.times(1)).outputsPower(Side.DOWN);
        Mockito.verify(controller, Mockito.times(0)).updateNeighbourTile(circuit, position, Power.getMin(), Side.DOWN);

        Mockito.verify(tile, Mockito.times(1)).outputsPower(Side.RIGHT);
        Mockito.verify(controller, Mockito.times(1)).updateNeighbourTile(circuit, position, Power.getMin(), Side.RIGHT);
    }

    // TODO: Revise test
    @Test
    public void testUpdateNeighbourTile() {
        Circuit circuit = Mockito.mock(Circuit.class);
        Position position = Mockito.mock(Position.class);
        Tile neighbourTile1 = Mockito.mock(Tile.class);
        Position neighbour1 = Mockito.mock(Position.class);
        Tile neighbourTile2 = Mockito.mock(Tile.class);
        Position neighbour2 = Mockito.mock(Position.class);
        Tile neighbourTile3 = Mockito.mock(Tile.class);
        Position neighbour3 = Mockito.mock(Position.class);

        Mockito.when(position.getNeighbour(Side.UP)).thenReturn(neighbour1);
        Mockito.when(position.getNeighbour(Side.LEFT)).thenReturn(neighbour2);
        Mockito.when(position.getNeighbour(Side.DOWN)).thenReturn(neighbour3);

        Mockito.when(circuit.isInBounds(neighbour1)).thenReturn(false);
        Mockito.when(circuit.isInBounds(neighbour2)).thenReturn(true);
        Mockito.when(circuit.isInBounds(neighbour3)).thenReturn(true);

        Mockito.when(circuit.getTile(neighbour1)).thenReturn(neighbourTile1);
        Mockito.when(circuit.getTile(neighbour2)).thenReturn(neighbourTile2);
        Mockito.when(circuit.getTile(neighbour3)).thenReturn(neighbourTile3);

        Mockito.when(neighbourTile1.update(circuit, Power.getMin(), Side.DOWN)).thenReturn(false);
        Mockito.when(neighbourTile2.update(circuit, Power.getMin(), Side.RIGHT)).thenReturn(false);
        Mockito.when(neighbourTile3.update(circuit, Power.getMin(), Side.UP)).thenReturn(true);

        // Call real method that is being tested
        Mockito.doCallRealMethod().when(controller).updateNeighbourTile(circuit, position, Power.getMin(), Side.UP);

        controller.updateNeighbourTile(circuit, position, Power.getMin(), Side.UP);

        Mockito.verify(position, Mockito.times(1)).getNeighbour(Side.UP);
        Mockito.verify(circuit, Mockito.times(1)).isInBounds(neighbour1);
        Mockito.verify(circuit, Mockito.times(0)).getTile(neighbour1);
        Mockito.verify(neighbourTile1, Mockito.times(0)).update(circuit, Power.getMin(), Side.DOWN);
        Mockito.verify(neighbourTile1, Mockito.times(0)).update(circuit, Power.getMin(), Side.UP);
        Mockito.verify(neighbourTile1, Mockito.times(0)).update(circuit, Power.getMin(), Side.LEFT);
        Mockito.verify(neighbourTile1, Mockito.times(0)).update(circuit, Power.getMin(), Side.RIGHT);
        Mockito.verify(controller, Mockito.times(0)).updateAllNeighbourTiles(circuit, neighbour1);

        // Call real method that is being tested
        Mockito.doCallRealMethod().when(controller).updateNeighbourTile(circuit, position, Power.getMin(), Side.LEFT);

        controller.updateNeighbourTile(circuit, position, Power.getMin(), Side.LEFT);

        Mockito.verify(position, Mockito.times(1)).getNeighbour(Side.LEFT);
        Mockito.verify(circuit, Mockito.times(1)).isInBounds(neighbour2);
        Mockito.verify(circuit, Mockito.times(1)).getTile(neighbour2);
        Mockito.verify(neighbourTile2, Mockito.times(1)).update(circuit, Power.getMin(), Side.RIGHT);
        Mockito.verify(neighbourTile2, Mockito.times(0)).update(circuit, Power.getMin(), Side.LEFT);
        Mockito.verify(neighbourTile2, Mockito.times(0)).update(circuit, Power.getMin(), Side.UP);
        Mockito.verify(neighbourTile2, Mockito.times(0)).update(circuit, Power.getMin(), Side.DOWN);
        Mockito.verify(controller, Mockito.times(0)).updateAllNeighbourTiles(circuit, neighbour2);

        // Call real method that is being tested
        Mockito.doCallRealMethod().when(controller).updateNeighbourTile(circuit, position, Power.getMin(), Side.DOWN);

        controller.updateNeighbourTile(circuit, position, Power.getMin(), Side.DOWN);

        Mockito.verify(position, Mockito.times(1)).getNeighbour(Side.DOWN);
        Mockito.verify(circuit, Mockito.times(1)).isInBounds(neighbour3);
        Mockito.verify(circuit, Mockito.times(1)).getTile(neighbour3);
        Mockito.verify(neighbourTile3, Mockito.times(1)).update(circuit, Power.getMin(), Side.UP);
        Mockito.verify(neighbourTile3, Mockito.times(0)).update(circuit, Power.getMin(), Side.DOWN);
        Mockito.verify(neighbourTile3, Mockito.times(0)).update(circuit, Power.getMin(), Side.LEFT);
        Mockito.verify(neighbourTile3, Mockito.times(0)).update(circuit, Power.getMin(), Side.RIGHT);
        Mockito.verify(controller, Mockito.times(1)).updateAllNeighbourTiles(circuit, neighbour3);
    }

    @Test
    public void testNotifyNeighbourTiles() {
        Circuit circuit = Mockito.mock(Circuit.class);
        Tile tile = Mockito.mock(Tile.class);
        Position position = Mockito.mock(Position.class);

        Position neighbour = Mockito.mock(Position.class);
        Tile neighbourTile = Mockito.mock(Tile.class);

        Mockito.when(circuit.getTile(position)).thenReturn(tile);
        for (Side side : Side.values()) {
            Mockito.when(position.getNeighbour(side)).thenReturn(neighbour);

            Mockito.when(tile.getPower(side)).thenReturn(Power.getMin());
            Mockito.when(neighbourTile.getPower(side)).thenReturn(Power.getMax());
        }

        Mockito.when(circuit.getTile(neighbour)).thenReturn(neighbourTile);

        // Call real method that is being tested
        Mockito.doCallRealMethod().when(controller).notifyNeighbourTiles(circuit, position);

        controller.notifyNeighbourTiles(circuit, position);

        Mockito.verify(circuit, Mockito.times(1)).getTile(position);

        for (Side side : Side.values()) {
            Mockito.verify(position, Mockito.times(1)).getNeighbour(side);
            Mockito.verify(controller, Mockito.times(1)).updateNeighbourTile(circuit, position, Power.getMin(), side);
            Mockito.verify(controller, Mockito.times(0)).updateNeighbourTile(circuit, position, Power.getMax(), side);
        }

        Mockito.verify(circuit, Mockito.times(4)).getTile(neighbour);
        Mockito.verify(neighbourTile, Mockito.times(4)).updateConnections(circuit);
    }

    @Test
    public void rotateTileLeftOutOfBounds() {
        Circuit circuit = Mockito.mock(Circuit.class);
        Tile tile = Mockito.mock(Tile.class);
        Position position = Mockito.mock(Position.class);

        Mockito.when(circuit.isInBounds(position)).thenReturn(false);

        Mockito.when(circuit.getTile(position)).thenReturn(tile);

        Mockito.when(tile.rotateLeft(circuit)).thenReturn(true);

        Mockito.doCallRealMethod().when(controller).rotateTileLeft(circuit, position);

        controller.rotateTileLeft(circuit, position);

        Mockito.verify(circuit, Mockito.times(1)).isInBounds(position);
        Mockito.verify(circuit, Mockito.times(0)).getTile(position);
        Mockito.verify(tile, Mockito.times(0)).rotateLeft(circuit);
        Mockito.verify(tile, Mockito.times(0)).updateConnections(circuit);
        Mockito.verify(tile, Mockito.times(0)).update(circuit);

        Mockito.verify(controller, Mockito.times(0)).notifyNeighbourTiles(circuit, position);
    }

    @Test
    public void rotateTileLeftNoTriggerUpdate() {
        Circuit circuit = Mockito.mock(Circuit.class);
        Tile tile = Mockito.mock(Tile.class);
        Position position = Mockito.mock(Position.class);

        Mockito.when(circuit.isInBounds(position)).thenReturn(true);

        Mockito.when(circuit.getTile(position)).thenReturn(tile);

        // rotation doesn't trigger updates
        Mockito.when(tile.rotateLeft(circuit)).thenReturn(false);

        Mockito.doCallRealMethod().when(controller).rotateTileLeft(circuit, position);

        controller.rotateTileLeft(circuit, position);

        Mockito.verify(circuit, Mockito.times(1)).isInBounds(position);
        Mockito.verify(circuit, Mockito.times(1)).getTile(position);
        Mockito.verify(tile, Mockito.times(1)).rotateLeft(circuit);
        Mockito.verify(tile, Mockito.times(0)).updateConnections(circuit);
        Mockito.verify(tile, Mockito.times(0)).update(circuit);

        Mockito.verify(controller, Mockito.times(0)).notifyNeighbourTiles(circuit, position);
    }

    @Test
    public void rotateTileLeftTriggersUpdate() {
        Circuit circuit = Mockito.mock(Circuit.class);
        Tile tile = Mockito.mock(Tile.class);
        Position position = Mockito.mock(Position.class);

        Mockito.when(circuit.isInBounds(position)).thenReturn(true);

        Mockito.when(circuit.getTile(position)).thenReturn(tile);

        // rotation triggers updates
        Mockito.when(tile.rotateLeft(circuit)).thenReturn(true);

        Mockito.doCallRealMethod().when(controller).rotateTileLeft(circuit, position);

        controller.rotateTileLeft(circuit, position);

        Mockito.verify(circuit, Mockito.times(1)).isInBounds(position);
        Mockito.verify(circuit, Mockito.times(1)).getTile(position);
        Mockito.verify(tile, Mockito.times(1)).rotateLeft(circuit);
        Mockito.verify(tile, Mockito.times(1)).updateConnections(circuit);
        Mockito.verify(tile, Mockito.times(1)).update(circuit);

        Mockito.verify(controller, Mockito.times(1)).notifyNeighbourTiles(circuit, position);
    }

    @Test
    public void rotateTileRightOutOfBounds() {
        Circuit circuit = Mockito.mock(Circuit.class);
        Tile tile = Mockito.mock(Tile.class);
        Position position = Mockito.mock(Position.class);

        Mockito.when(circuit.isInBounds(position)).thenReturn(false);

        Mockito.when(circuit.getTile(position)).thenReturn(tile);

        Mockito.when(tile.rotateRight(circuit)).thenReturn(true);

        Mockito.doCallRealMethod().when(controller).rotateTileRight(circuit, position);

        controller.rotateTileRight(circuit, position);

        Mockito.verify(circuit, Mockito.times(1)).isInBounds(position);
        Mockito.verify(circuit, Mockito.times(0)).getTile(position);
        Mockito.verify(tile, Mockito.times(0)).rotateRight(circuit);
        Mockito.verify(tile, Mockito.times(0)).updateConnections(circuit);
        Mockito.verify(tile, Mockito.times(0)).update(circuit);

        Mockito.verify(controller, Mockito.times(0)).notifyNeighbourTiles(circuit, position);
    }

    @Test
    public void rotateTileRightNoTriggerUpdate() {
        Circuit circuit = Mockito.mock(Circuit.class);
        Tile tile = Mockito.mock(Tile.class);
        Position position = Mockito.mock(Position.class);

        Mockito.when(circuit.isInBounds(position)).thenReturn(true);

        Mockito.when(circuit.getTile(position)).thenReturn(tile);

        // rotation doesn't trigger updates
        Mockito.when(tile.rotateRight(circuit)).thenReturn(false);

        Mockito.doCallRealMethod().when(controller).rotateTileRight(circuit, position);

        controller.rotateTileRight(circuit, position);

        Mockito.verify(circuit, Mockito.times(1)).isInBounds(position);
        Mockito.verify(circuit, Mockito.times(1)).getTile(position);
        Mockito.verify(tile, Mockito.times(1)).rotateRight(circuit);
        Mockito.verify(tile, Mockito.times(0)).updateConnections(circuit);
        Mockito.verify(tile, Mockito.times(0)).update(circuit);

        Mockito.verify(controller, Mockito.times(0)).notifyNeighbourTiles(circuit, position);
    }

    @Test
    public void rotateTileRightTriggersUpdate() {
        Circuit circuit = Mockito.mock(Circuit.class);
        Tile tile = Mockito.mock(Tile.class);
        Position position = Mockito.mock(Position.class);

        Mockito.when(circuit.isInBounds(position)).thenReturn(true);

        Mockito.when(circuit.getTile(position)).thenReturn(tile);

        // rotation triggers updates
        Mockito.when(tile.rotateRight(circuit)).thenReturn(true);

        Mockito.doCallRealMethod().when(controller).rotateTileRight(circuit, position);

        controller.rotateTileRight(circuit, position);

        Mockito.verify(circuit, Mockito.times(1)).isInBounds(position);
        Mockito.verify(circuit, Mockito.times(1)).getTile(position);
        Mockito.verify(tile, Mockito.times(1)).rotateRight(circuit);
        Mockito.verify(tile, Mockito.times(1)).updateConnections(circuit);
        Mockito.verify(tile, Mockito.times(1)).update(circuit);

        Mockito.verify(controller, Mockito.times(1)).notifyNeighbourTiles(circuit, position);
    }*/
}
