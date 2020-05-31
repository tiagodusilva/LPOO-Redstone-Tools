package com.lpoo.redstonetools.model.circuit;

import com.lpoo.redstonetools.model.tile.IOTile;
import com.lpoo.redstonetools.model.tile.Tile;
import com.lpoo.redstonetools.model.utils.Position;
import com.lpoo.redstonetools.model.utils.Power;
import com.lpoo.redstonetools.model.utils.Side;
import com.lpoo.redstonetools.model.utils.TileType;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.LongRange;
import net.jqwik.api.lifecycle.BeforeProperty;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;

public class CircuitTest {

    private Circuit circuit;
    private final int WIDTH = 20;
    private final int HEIGHT = 20;

    @BeforeEach
    @BeforeProperty
    public void setup() {
        Position position = Mockito.mock(Position.class);
        Mockito.when(position.getX()).thenReturn(1);
        Mockito.when(position.getY()).thenReturn(2);

        this.circuit = new Circuit(WIDTH, HEIGHT, position);
    }

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testCircuitInit() {
        Assertions.assertEquals(WIDTH, circuit.getWidth());
        Assertions.assertEquals(HEIGHT, circuit.getHeight());
        Assertions.assertEquals(0, circuit.getTick());
        Assertions.assertNotNull(circuit.getTickedTiles());
        Assertions.assertEquals(1, circuit.getPosition().getX());
        Assertions.assertEquals(2, circuit.getPosition().getY());

        for (int i = 0; i < circuit.getHeight(); i++) {
            for (int j = 0; j < circuit.getWidth(); j++) {
                Assertions.assertNotNull(circuit.getTile(j, i));
                Assertions.assertEquals(TileType.NULL, circuit.getTile(j, i).getType());
            }
        }

        for (Side side : Side.values()) {
            Assertions.assertEquals(TileType.NULL, circuit.getIO(side).getType());
        }

        Assertions.assertEquals(TileType.CIRCUIT, circuit.getType());
        Assertions.assertTrue(circuit.isTickedTile());
        Assertions.assertFalse(circuit.isWire());
    }

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testCircuitProperties() {
        circuit.setCircuitName("a random name");

        LocalDateTime now = LocalDateTime.now();

        circuit.setTimestamp(now);

        Assertions.assertEquals("a random name", circuit.getCircuitName());
        Assertions.assertEquals(now, circuit.getTimestamp());
    }

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testCircuitBounds() {
        Position position = Mockito.mock(Position.class);

        Mockito.when(position.getX()).thenReturn(0);
        Mockito.when(position.getY()).thenReturn(0);
        Assertions.assertTrue(circuit.isInBounds(position));

        Mockito.when(position.getX()).thenReturn(WIDTH - 1);
        Mockito.when(position.getY()).thenReturn(HEIGHT - 1);
        Assertions.assertTrue(circuit.isInBounds(position));

        Mockito.when(position.getX()).thenReturn(-1);
        Mockito.when(position.getY()).thenReturn(0);
        Assertions.assertFalse(circuit.isInBounds(position));

        Mockito.when(position.getX()).thenReturn(WIDTH - 1);
        Mockito.when(position.getY()).thenReturn(HEIGHT);
        Assertions.assertFalse(circuit.isInBounds(position));

        Mockito.when(position.getX()).thenReturn(WIDTH + 10);
        Mockito.when(position.getY()).thenReturn(HEIGHT);
        Assertions.assertFalse(circuit.isInBounds(position));

        Mockito.when(position.getX()).thenReturn(WIDTH);
        Mockito.when(position.getY()).thenReturn(HEIGHT-1);
        Assertions.assertFalse(circuit.isInBounds(position));

        Mockito.when(position.getX()).thenReturn(WIDTH);
        Mockito.when(position.getY()).thenReturn(HEIGHT);
        Assertions.assertFalse(circuit.isInBounds(position));

        Mockito.when(position.getX()).thenReturn(WIDTH);
        Mockito.when(position.getY()).thenReturn(HEIGHT + 10);
        Assertions.assertFalse(circuit.isInBounds(position));

        Mockito.when(position.getX()).thenReturn(WIDTH + 10);
        Mockito.when(position.getY()).thenReturn(HEIGHT + 10);
        Assertions.assertFalse(circuit.isInBounds(position));
    }

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testCircuitBasicAddTile() {
        Position position = Mockito.mock(Position.class);

        Mockito.when(position.getX()).thenReturn(0);
        Mockito.when(position.getY()).thenReturn(0);

        Tile tile = Mockito.mock(Tile.class);

        Mockito.when(tile.getPosition()).thenReturn(position);
        Mockito.when(tile.isTickedTile()).thenReturn(false);
        Mockito.when(tile.getType()).thenReturn(TileType.WIRE);

        Assertions.assertTrue(circuit.addTile(tile));

        Position position2 = Mockito.mock(Position.class);

        Mockito.when(position2.getX()).thenReturn(WIDTH - 1);
        Mockito.when(position2.getY()).thenReturn(HEIGHT + 1);

        Tile tile2 = Mockito.mock(Tile.class);

        Mockito.when(tile2.getPosition()).thenReturn(position2);

        Assertions.assertFalse(circuit.addTile(tile2));

        Mockito.verify(tile, Mockito.times(1)).isTickedTile();
        Mockito.verify(tile, Mockito.times(1)).updateConnections(circuit);
        Mockito.verify(tile2, Mockito.times(0)).isTickedTile();
        Mockito.verify(tile2, Mockito.times(0)).updateConnections(circuit);
    }

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testCircuitGetTile() {
        Position position = Mockito.mock(Position.class);

        Mockito.when(position.getX()).thenReturn(0);
        Mockito.when(position.getY()).thenReturn(0);

        Tile tile = Mockito.mock(Tile.class);

        Mockito.when(tile.getPosition()).thenReturn(position);
        Mockito.when(tile.isTickedTile()).thenReturn(false);
        Mockito.when(tile.getType()).thenReturn(TileType.WIRE);

        Assertions.assertEquals(TileType.NULL, circuit.getTile(position).getType());

        circuit.addTile(tile);

        Assertions.assertEquals(TileType.WIRE, circuit.getTile(position).getType());

        Position position2 = Mockito.mock(Position.class);

        Mockito.when(position2.getX()).thenReturn(WIDTH - 1);
        Mockito.when(position2.getY()).thenReturn(-5);

        Assertions.assertEquals(TileType.NULL, circuit.getTile(position2).getType());
    }

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testCircuitAdvancedAddTile() {
        Position position = Mockito.mock(Position.class);

        Mockito.when(position.getX()).thenReturn(0);
        Mockito.when(position.getY()).thenReturn(0);

        Tile tile = Mockito.mock(Tile.class);

        Mockito.when(tile.getPosition()).thenReturn(position);
        Mockito.when(tile.isTickedTile()).thenReturn(true);
        Mockito.when(tile.getType()).thenReturn(TileType.TIMER);

        Assertions.assertEquals(0, circuit.getTickedTiles().size());

        Assertions.assertTrue(circuit.addTile(tile));

        Assertions.assertEquals(TileType.TIMER, circuit.getTile(position).getType());

        Assertions.assertEquals(1, circuit.getTickedTiles().size());

        Mockito.verify(tile, Mockito.times(1)).isTickedTile();
        Mockito.verify(tile, Mockito.times(1)).updateConnections(circuit);
    }

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testGetIO() {
        Position pos = Mockito.mock(Position.class);
        Mockito.when(pos.getX()).thenReturn(1);
        Mockito.when(pos.getY()).thenReturn(1);

        Tile io = Mockito.mock(Tile.class);
        Mockito.when(io.getPosition()).thenReturn(pos);
        Mockito.when(io.getType()).thenReturn(TileType.IO);

        Assertions.assertTrue(circuit.addTile(io));

        for (Side side : Side.values()) {
            Assertions.assertEquals(TileType.NULL, circuit.getIO(side).getType());
        }

        circuit.setIO(Side.LEFT, io.getPosition());

        Assertions.assertEquals(TileType.NULL, circuit.getIO(Side.UP).getType());
        Assertions.assertEquals(TileType.NULL, circuit.getIO(Side.RIGHT).getType());
        Assertions.assertEquals(TileType.NULL, circuit.getIO(Side.DOWN).getType());
        Assertions.assertEquals(TileType.IO, circuit.getIO(Side.LEFT).getType());
    }

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testCircuitSafeRemoveTile() {
        Position position = Mockito.mock(Position.class);

        Mockito.when(position.getX()).thenReturn(0);
        Mockito.when(position.getY()).thenReturn(0);

        Tile tile = Mockito.mock(Tile.class);

        // Safe remove ticked tile
        Mockito.when(tile.getPosition()).thenReturn(position);
        Mockito.when(tile.isTickedTile()).thenReturn(true);
        Mockito.when(tile.getType()).thenReturn(TileType.TIMER);

        Assertions.assertEquals(0, circuit.getTickedTiles().size());

        Assertions.assertTrue(circuit.addTile(tile));

        Assertions.assertEquals(1, circuit.getTickedTiles().size());

        Tile tile2 = Mockito.mock(Tile.class);

        Mockito.when(tile2.getPosition()).thenReturn(position);
        Mockito.when(tile2.isTickedTile()).thenReturn(true);
        Mockito.when(tile2.getType()).thenReturn(TileType.LEVER);

        Assertions.assertTrue(circuit.addTile(tile2));

        Assertions.assertEquals(1, circuit.getTickedTiles().size());

        Tile tile3 = Mockito.mock(Tile.class);

        Mockito.when(tile3.getPosition()).thenReturn(position);
        Mockito.when(tile3.isTickedTile()).thenReturn(false);
        Mockito.when(tile3.getType()).thenReturn(TileType.WIRE);

        Assertions.assertTrue(circuit.addTile(tile3));

        Assertions.assertEquals(0, circuit.getTickedTiles().size());

        // Safe remove IO
        IOTile io = Mockito.mock(IOTile.class);

        Mockito.when(io.getPosition()).thenReturn(position);
        Mockito.when(io.isTickedTile()).thenReturn(false);
        Mockito.when(io.getIOSide()).thenReturn(Side.RIGHT);
        Mockito.when(io.getType()).thenReturn(TileType.IO);

        Assertions.assertTrue(circuit.addTile(io));
        circuit.setIO(Side.RIGHT, position);

        Assertions.assertEquals(io, circuit.getIO(Side.RIGHT));
        Assertions.assertNotEquals(io, circuit.getIO(Side.UP));
        Assertions.assertNotEquals(io, circuit.getIO(Side.DOWN));
        Assertions.assertNotEquals(io, circuit.getIO(Side.LEFT));

        Assertions.assertTrue(circuit.addTile(tile3));

        Assertions.assertNotEquals(io, circuit.getIO(Side.RIGHT));
        Assertions.assertNotEquals(io, circuit.getIO(Side.UP));
        Assertions.assertNotEquals(io, circuit.getIO(Side.DOWN));
        Assertions.assertNotEquals(io, circuit.getIO(Side.LEFT));
    }

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testRemoveTile() {
        Position position = Mockito.mock(Position.class);

        Mockito.when(position.getX()).thenReturn(0);
        Mockito.when(position.getY()).thenReturn(0);

        circuit.removeTile(position);

        Assertions.assertEquals(TileType.NULL, circuit.getTile(position).getType());
    }

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testSurroundingPowerSide() {
        Position mid = Mockito.mock(Position.class);
        Mockito.when(mid.getX()).thenReturn(1);
        Mockito.when(mid.getY()).thenReturn(1);

        Position up = Mockito.mock(Position.class);
        Mockito.when(up.getX()).thenReturn(1);
        Mockito.when(up.getY()).thenReturn(0);

        Mockito.when(mid.getNeighbour(Side.UP)).thenReturn(up);

        Tile wire = Mockito.mock(Tile.class);
        Mockito.when(wire.getPosition()).thenReturn(up);
        Mockito.when(wire.isWire()).thenReturn(true);
        Mockito.when(wire.isTickedTile()).thenReturn(false);
        Mockito.when(wire.getType()).thenReturn(TileType.WIRE);

        circuit.addTile(wire);

        Mockito.when(wire.getPower(Side.DOWN)).thenReturn(Power.getMin());
        Assertions.assertEquals(Power.getMin(), circuit.getSurroundingPower(mid, Side.UP));

        Mockito.when(wire.getPower(Side.DOWN)).thenReturn(12);
        Assertions.assertEquals(12, circuit.getSurroundingPower(mid, Side.UP));

        Mockito.verify(wire, Mockito.times(2)).getPower(Side.DOWN);
    }

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testSurroundingPower() {
        Position mid = Mockito.mock(Position.class);
        Mockito.when(mid.getX()).thenReturn(1);
        Mockito.when(mid.getY()).thenReturn(1);

        Position up = Mockito.mock(Position.class);
        Mockito.when(up.getX()).thenReturn(1);
        Mockito.when(up.getY()).thenReturn(0);

        Position down = Mockito.mock(Position.class);
        Mockito.when(down.getX()).thenReturn(1);
        Mockito.when(down.getY()).thenReturn(2);

        Position left = Mockito.mock(Position.class);
        Mockito.when(left.getX()).thenReturn(0);
        Mockito.when(left.getY()).thenReturn(1);

        Position right = Mockito.mock(Position.class);
        Mockito.when(right.getX()).thenReturn(2);
        Mockito.when(right.getY()).thenReturn(1);

        Mockito.when(mid.getNeighbour(Side.UP)).thenReturn(up);
        Mockito.when(mid.getNeighbour(Side.DOWN)).thenReturn(down);
        Mockito.when(mid.getNeighbour(Side.RIGHT)).thenReturn(right);
        Mockito.when(mid.getNeighbour(Side.LEFT)).thenReturn(left);

        Assertions.assertEquals(Power.getMin(), circuit.getSurroundingPower(mid));

        Tile wire = Mockito.mock(Tile.class);
        Mockito.when(wire.getPosition()).thenReturn(up);
        Mockito.when(wire.isWire()).thenReturn(true);
        Mockito.when(wire.isTickedTile()).thenReturn(false);
        Mockito.when(wire.getType()).thenReturn(TileType.WIRE);

        circuit.addTile(wire);

        Mockito.when(wire.getPower(Side.DOWN)).thenReturn(Power.getMin());
        Assertions.assertEquals(Power.getMin(), circuit.getSurroundingPower(mid));

        Mockito.when(wire.getPower(Side.DOWN)).thenReturn(12);
        Assertions.assertEquals(12, circuit.getSurroundingPower(mid));

        Tile source = Mockito.mock(Tile.class);
        Mockito.when(source.getPosition()).thenReturn(right);
        Mockito.when(source.isWire()).thenReturn(false);
        Mockito.when(source.isTickedTile()).thenReturn(true);
        Mockito.when(source.getType()).thenReturn(TileType.SOURCE);

        circuit.addTile(source);

        Mockito.when(source.getPower(Side.LEFT)).thenReturn(Power.getMax());
        Assertions.assertEquals(Power.getMax(), circuit.getSurroundingPower(mid));
    }

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testSurroundWirePowerSide() {
        Position mid = Mockito.mock(Position.class);
        Mockito.when(mid.getX()).thenReturn(1);
        Mockito.when(mid.getY()).thenReturn(1);

        Position up = Mockito.mock(Position.class);
        Mockito.when(up.getX()).thenReturn(1);
        Mockito.when(up.getY()).thenReturn(0);

        Position down = Mockito.mock(Position.class);
        Mockito.when(down.getX()).thenReturn(1);
        Mockito.when(down.getY()).thenReturn(2);

        Mockito.when(mid.getNeighbour(Side.UP)).thenReturn(up);
        Mockito.when(mid.getNeighbour(Side.DOWN)).thenReturn(down);

        Tile wire = Mockito.mock(Tile.class);
        Mockito.when(wire.getPosition()).thenReturn(up);
        Mockito.when(wire.isWire()).thenReturn(true);
        Mockito.when(wire.isTickedTile()).thenReturn(false);
        Mockito.when(wire.getType()).thenReturn(TileType.WIRE);

        circuit.addTile(wire);

        Mockito.when(wire.getPower(Side.DOWN)).thenReturn(Power.getMin());
        Assertions.assertEquals(Power.getMin(), circuit.getSurroundingWirePower(mid, Side.UP));

        Mockito.when(wire.getPower(Side.DOWN)).thenReturn(12);
        Assertions.assertEquals(11, circuit.getSurroundingWirePower(mid, Side.UP));

        Mockito.verify(wire, Mockito.times(2)).getPower(Side.DOWN);

        Tile source = Mockito.mock(Tile.class);
        Mockito.when(source.getPosition()).thenReturn(down);
        Mockito.when(source.isWire()).thenReturn(false);
        Mockito.when(source.isTickedTile()).thenReturn(true);
        Mockito.when(source.getType()).thenReturn(TileType.SOURCE);

        circuit.addTile(source);

        Mockito.when(source.getPower(Side.UP)).thenReturn(Power.getMin());
        Assertions.assertEquals(Power.getMin(), circuit.getSurroundingWirePower(mid, Side.DOWN));

        Mockito.when(source.getPower(Side.UP)).thenReturn(12);
        Assertions.assertEquals(12, circuit.getSurroundingWirePower(mid, Side.DOWN));

        Mockito.verify(source, Mockito.times(2)).getPower(Side.UP);
    }

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testSurroundingWirePower() {
        Position mid = Mockito.mock(Position.class);
        Mockito.when(mid.getX()).thenReturn(1);
        Mockito.when(mid.getY()).thenReturn(1);

        Position up = Mockito.mock(Position.class);
        Mockito.when(up.getX()).thenReturn(1);
        Mockito.when(up.getY()).thenReturn(0);

        Position down = Mockito.mock(Position.class);
        Mockito.when(down.getX()).thenReturn(1);
        Mockito.when(down.getY()).thenReturn(2);

        Position left = Mockito.mock(Position.class);
        Mockito.when(left.getX()).thenReturn(0);
        Mockito.when(left.getY()).thenReturn(1);

        Position right = Mockito.mock(Position.class);
        Mockito.when(right.getX()).thenReturn(2);
        Mockito.when(right.getY()).thenReturn(1);

        Mockito.when(mid.getNeighbour(Side.UP)).thenReturn(up);
        Mockito.when(mid.getNeighbour(Side.DOWN)).thenReturn(down);
        Mockito.when(mid.getNeighbour(Side.RIGHT)).thenReturn(right);
        Mockito.when(mid.getNeighbour(Side.LEFT)).thenReturn(left);

        Assertions.assertEquals(Power.getMin(), circuit.getSurroundingWirePower(mid));

        Tile wire = Mockito.mock(Tile.class);
        Mockito.when(wire.getPosition()).thenReturn(up);
        Mockito.when(wire.isWire()).thenReturn(true);
        Mockito.when(wire.isTickedTile()).thenReturn(false);
        Mockito.when(wire.getType()).thenReturn(TileType.WIRE);

        circuit.addTile(wire);

        Mockito.when(wire.getPower(Side.DOWN)).thenReturn(Power.getMin());
        Assertions.assertEquals(Power.getMin(), circuit.getSurroundingWirePower(mid));

        Mockito.when(wire.getPower(Side.DOWN)).thenReturn(12);
        Assertions.assertEquals(11, circuit.getSurroundingWirePower(mid));

        Tile source = Mockito.mock(Tile.class);
        Mockito.when(source.getPosition()).thenReturn(right);
        Mockito.when(source.isWire()).thenReturn(false);
        Mockito.when(source.isTickedTile()).thenReturn(true);
        Mockito.when(source.getType()).thenReturn(TileType.SOURCE);

        circuit.addTile(source);

        Mockito.when(source.getPower(Side.LEFT)).thenReturn(Power.getMax() - 1);
        Assertions.assertEquals(Power.getMax() - 1, circuit.getSurroundingWirePower(mid));

        Tile wire2 = Mockito.mock(Tile.class);
        Mockito.when(wire2.getPosition()).thenReturn(left);
        Mockito.when(wire2.isWire()).thenReturn(true);
        Mockito.when(wire2.isTickedTile()).thenReturn(false);
        Mockito.when(wire2.getType()).thenReturn(TileType.WIRE);

        circuit.addTile(wire2);

        Mockito.when(wire2.getPower(Side.RIGHT)).thenReturn(Power.getMax());
        Assertions.assertEquals(Power.getMax() - 1, circuit.getSurroundingWirePower(mid));
    }

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testCircuitCanTilesConnect() {
        Position mid = Mockito.mock(Position.class);
        Mockito.when(mid.getX()).thenReturn(1);
        Mockito.when(mid.getY()).thenReturn(1);

        Position up = Mockito.mock(Position.class);
        Mockito.when(up.getX()).thenReturn(1);
        Mockito.when(up.getY()).thenReturn(0);

        Position down = Mockito.mock(Position.class);
        Mockito.when(down.getX()).thenReturn(1);
        Mockito.when(down.getY()).thenReturn(2);

        Position left = Mockito.mock(Position.class);
        Mockito.when(left.getX()).thenReturn(0);
        Mockito.when(left.getY()).thenReturn(1);

        Position right = Mockito.mock(Position.class);
        Mockito.when(right.getX()).thenReturn(2);
        Mockito.when(right.getY()).thenReturn(1);

        Mockito.when(mid.getNeighbour(Side.UP)).thenReturn(up);
        Mockito.when(mid.getNeighbour(Side.DOWN)).thenReturn(down);
        Mockito.when(mid.getNeighbour(Side.RIGHT)).thenReturn(right);
        Mockito.when(mid.getNeighbour(Side.LEFT)).thenReturn(left);

        Tile tile1 = Mockito.mock(Tile.class);
        Mockito.when(tile1.getPosition()).thenReturn(mid);
        Mockito.when(tile1.acceptsPower(Side.UP)).thenReturn(false);
        Mockito.when(tile1.acceptsPower(Side.DOWN)).thenReturn(false);
        Mockito.when(tile1.acceptsPower(Side.RIGHT)).thenReturn(true);
        Mockito.when(tile1.acceptsPower(Side.LEFT)).thenReturn(false);

        Mockito.when(tile1.outputsPower(Side.UP)).thenReturn(true);
        Mockito.when(tile1.outputsPower(Side.DOWN)).thenReturn(false);
        Mockito.when(tile1.outputsPower(Side.RIGHT)).thenReturn(false);
        Mockito.when(tile1.outputsPower(Side.LEFT)).thenReturn(true);
        Mockito.when(tile1.isTickedTile()).thenReturn(false);

        Tile tile2 = Mockito.mock(Tile.class);
        Mockito.when(tile2.getPosition()).thenReturn(up);
        Mockito.when(tile2.acceptsPower(Side.DOWN)).thenReturn(true);

        Mockito.when(tile2.outputsPower(Side.DOWN)).thenReturn(false);
        Mockito.when(tile2.isTickedTile()).thenReturn(false);

        Tile tile3 = Mockito.mock(Tile.class);
        Mockito.when(tile3.getPosition()).thenReturn(down);
        Mockito.when(tile3.acceptsPower(Side.UP)).thenReturn(true);

        Mockito.when(tile3.outputsPower(Side.UP)).thenReturn(true);
        Mockito.when(tile3.isTickedTile()).thenReturn(false);

        Tile tile4 = Mockito.mock(Tile.class);
        Mockito.when(tile4.getPosition()).thenReturn(right);
        Mockito.when(tile4.acceptsPower(Side.LEFT)).thenReturn(true);

        Mockito.when(tile4.outputsPower(Side.LEFT)).thenReturn(false);
        Mockito.when(tile4.isTickedTile()).thenReturn(false);

        Tile tile5 = Mockito.mock(Tile.class);
        Mockito.when(tile5.getPosition()).thenReturn(left);
        Mockito.when(tile5.acceptsPower(Side.RIGHT)).thenReturn(false);

        Mockito.when(tile5.outputsPower(Side.RIGHT)).thenReturn(true);
        Mockito.when(tile5.isTickedTile()).thenReturn(false);
        /*
            Test Illustration https://imgur.com/UwG6BYY
         */
        circuit.addTile(tile1);
        circuit.addTile(tile2);
        circuit.addTile(tile3);
        circuit.addTile(tile4);
        circuit.addTile(tile5);

        Assertions.assertTrue(circuit.canTilesConnect(mid, Side.UP));
        Assertions.assertFalse(circuit.canTilesConnect(mid, Side.DOWN));
        Assertions.assertFalse(circuit.canTilesConnect(mid, Side.LEFT));
        Assertions.assertFalse(circuit.canTilesConnect(mid, Side.RIGHT));
    }

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testUpdateOnIOInteract() {
        Circuit circuitSpy = Mockito.spy(circuit);

        Position pos = Mockito.mock(Position.class);
        Mockito.when(pos.getX()).thenReturn(1);
        Mockito.when(pos.getY()).thenReturn(1);

        IOTile io = Mockito.mock(IOTile.class);
        Mockito.when(io.getPosition()).thenReturn(pos);
        Mockito.when(io.getType()).thenReturn(TileType.IO);
        Mockito.when(io.getIOSide()).thenReturn(Side.LEFT);

        circuitSpy.addTile(io);

        circuitSpy.setIO(Side.LEFT, io.getPosition());

        // Test - Update IO while tile being updated isn't IO
        // Expected behaviour -> does nothing
        Position noIOPos = Mockito.mock(Position.class);
        Mockito.when(noIOPos.getX()).thenReturn(2);
        Mockito.when(noIOPos.getY()).thenReturn(2);
        Tile notIO = Mockito.mock(Tile.class);
        Mockito.when(notIO.getPosition()).thenReturn(noIOPos);
        Mockito.when(notIO.getType()).thenReturn(TileType.TIMER);

        circuitSpy.addTile(notIO);

        Assertions.assertFalse(circuitSpy.updateOnIOInteract(noIOPos));

        Mockito.verify(circuitSpy, Mockito.times(0)).getIO(Side.LEFT);

        // Test - Removing IO tile while the one stored in that place isn't the one being removed
        // Expected behaviour -> Doesn't remove previous IO
        Position replaceIOPos = Mockito.mock(Position.class);
        Mockito.when(replaceIOPos.getX()).thenReturn(3);
        Mockito.when(replaceIOPos.getY()).thenReturn(3);
        IOTile replaceIO = Mockito.mock(IOTile.class);
        Mockito.when(replaceIO.getPosition()).thenReturn(replaceIOPos);
        Mockito.when(replaceIO.getType()).thenReturn(TileType.IO);
        Mockito.when(replaceIO.getIOSide()).thenReturn(Side.LEFT);

        circuitSpy.addTile(replaceIO);

        Mockito.when(replaceIO.acceptsPower(Side.LEFT)).thenReturn(false);
        Mockito.when(replaceIO.outputsPower(Side.LEFT)).thenReturn(true);
        Assertions.assertFalse(circuitSpy.updateOnIOInteract(replaceIOPos));

        Mockito.when(replaceIO.acceptsPower(Side.LEFT)).thenReturn(true);
        Mockito.when(replaceIO.outputsPower(Side.LEFT)).thenReturn(false);
        Assertions.assertFalse(circuitSpy.updateOnIOInteract(replaceIOPos));

        Mockito.when(replaceIO.acceptsPower(Side.LEFT)).thenReturn(true);
        Mockito.when(replaceIO.outputsPower(Side.LEFT)).thenReturn(true);
        Assertions.assertFalse(circuitSpy.updateOnIOInteract(replaceIOPos));

        Mockito.verify(io, Mockito.times(3)).getType();
        Mockito.verify(circuitSpy, Mockito.times(0)).setIO(Side.LEFT, replaceIOPos);

        // Test - Updating IO tile
        Mockito.when(io.acceptsPower(Side.LEFT)).thenReturn(true);
        Mockito.when(io.outputsPower(Side.LEFT)).thenReturn(false);
        Assertions.assertTrue(circuitSpy.updateOnIOInteract(pos));

        Mockito.when(io.acceptsPower(Side.LEFT)).thenReturn(false);
        Mockito.when(io.outputsPower(Side.LEFT)).thenReturn(true);
        Assertions.assertTrue(circuitSpy.updateOnIOInteract(pos));

        Mockito.verify(circuitSpy, Mockito.times(3)).setIO(Side.LEFT, pos);

        // Test - Removing IO Tile while the one specified isn't the one stored
        Position toRemovePos = Mockito.mock(Position.class);
        Mockito.when(toRemovePos.getX()).thenReturn(0);
        Mockito.when(toRemovePos.getY()).thenReturn(0);

        IOTile toRemoveIO = Mockito.mock(IOTile.class);
        Mockito.when(toRemoveIO.getPosition()).thenReturn(toRemovePos);
        Mockito.when(toRemoveIO.getType()).thenReturn(TileType.IO);
        Mockito.when(toRemoveIO.getIOSide()).thenReturn(Side.LEFT);
        Mockito.when(toRemoveIO.acceptsPower(Side.LEFT)).thenReturn(false);
        Mockito.when(toRemoveIO.outputsPower(Side.LEFT)).thenReturn(false);

        circuitSpy.addTile(toRemoveIO);

        Assertions.assertFalse(circuitSpy.updateOnIOInteract(toRemovePos));

        Assertions.assertEquals(io, circuitSpy.getIO(Side.LEFT));
        Assertions.assertEquals(io, circuitSpy.getTile(pos));

        // Test - Remove IO tile
        Mockito.when(io.acceptsPower(Side.LEFT)).thenReturn(false);
        Mockito.when(io.outputsPower(Side.LEFT)).thenReturn(false);

        Assertions.assertTrue(circuitSpy.updateOnIOInteract(pos));

        Assertions.assertNotEquals(io, circuitSpy.getIO(Side.LEFT));
        Assertions.assertEquals(io, circuitSpy.getTile(pos));

        // Test - Add IO tile
        Mockito.when(io.acceptsPower(Side.LEFT)).thenReturn(true);
        Mockito.when(io.outputsPower(Side.LEFT)).thenReturn(false);

        Assertions.assertTrue(circuitSpy.updateOnIOInteract(pos));

        Assertions.assertEquals(io, circuitSpy.getIO(Side.LEFT));
        Assertions.assertEquals(io, circuitSpy.getTile(pos));
    }

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testUpdateOnIORotation() {
        Circuit circuitSpy = Mockito.spy(circuit);

        Position pos = Mockito.mock(Position.class);
        Mockito.when(pos.getX()).thenReturn(1);
        Mockito.when(pos.getY()).thenReturn(1);

        IOTile io = Mockito.mock(IOTile.class);
        Mockito.when(io.getPosition()).thenReturn(pos);
        Mockito.when(io.getType()).thenReturn(TileType.IO);
        Mockito.when(io.getIOSide()).thenReturn(Side.LEFT);

        circuitSpy.addTile(io);

        circuitSpy.setIO(Side.LEFT, io.getPosition());

        // Test - Update IO while tile being updated isn't IO
        // Expected behaviour -> does nothing
        Position noIOPos = Mockito.mock(Position.class);
        Mockito.when(noIOPos.getX()).thenReturn(2);
        Mockito.when(noIOPos.getY()).thenReturn(2);
        Tile notIO = Mockito.mock(Tile.class);
        Mockito.when(notIO.getPosition()).thenReturn(noIOPos);
        Mockito.when(notIO.getType()).thenReturn(TileType.TIMER);

        circuitSpy.addTile(notIO);

        for (Side side : Side.values()) {
            Assertions.assertFalse(circuitSpy.updateOnIORotation(noIOPos, side));
        }

        Mockito.verify(circuitSpy, Mockito.times(0)).getIO(Mockito.any(Side.class));

        // Test - New side is equal to the previous
        Position sameIOPos = Mockito.mock(Position.class);
        Mockito.when(sameIOPos.getX()).thenReturn(3);
        Mockito.when(sameIOPos.getY()).thenReturn(3);
        IOTile sameIORotation = Mockito.mock(IOTile.class);
        Mockito.when(sameIORotation.getPosition()).thenReturn(sameIOPos);
        Mockito.when(sameIORotation.getType()).thenReturn(TileType.IO);
        Mockito.when(sameIORotation.getIOSide()).thenReturn(Side.LEFT);

        circuitSpy.addTile(sameIORotation);

        Assertions.assertFalse(circuitSpy.updateOnIORotation(sameIOPos, Side.LEFT));

        Mockito.verify(sameIORotation, Mockito.times(0)).acceptsPower(Mockito.any(Side.class));
        Mockito.verify(sameIORotation, Mockito.times(0)).outputsPower(Mockito.any(Side.class));

        // Test - IO is neutral (not input nor output)
        // Expected behaviour -> Can freely rotate
        Position neutralIOPos = Mockito.mock(Position.class);
        Mockito.when(neutralIOPos.getX()).thenReturn(0);
        Mockito.when(neutralIOPos.getY()).thenReturn(0);
        IOTile neutralIO = Mockito.mock(IOTile.class);
        Mockito.when(neutralIO.getPosition()).thenReturn(neutralIOPos);
        Mockito.when(neutralIO.getType()).thenReturn(TileType.IO);
        Mockito.when(neutralIO.getIOSide()).thenReturn(Side.UP);

        circuitSpy.addTile(neutralIO);

        Mockito.when(neutralIO.acceptsPower(Side.UP)).thenReturn(false);
        Mockito.when(neutralIO.outputsPower(Side.UP)).thenReturn(false);
        Assertions.assertTrue(circuitSpy.updateOnIORotation(neutralIOPos, Side.LEFT));
        Assertions.assertEquals(io, circuitSpy.getIO(Side.LEFT));
        Assertions.assertNotEquals(neutralIO, circuitSpy.getIO(Side.UP));

        Mockito.verify(circuitSpy, Mockito.times(0)).setIO(Side.UP, neutralIOPos);


        // Test - Block rotation that overrides another IO
        Position replaceIOPos = Mockito.mock(Position.class);
        Mockito.when(replaceIOPos.getX()).thenReturn(4);
        Mockito.when(replaceIOPos.getY()).thenReturn(4);
        IOTile replaceIO = Mockito.mock(IOTile.class);
        Mockito.when(replaceIO.getPosition()).thenReturn(replaceIOPos);
        Mockito.when(replaceIO.getType()).thenReturn(TileType.IO);
        Mockito.when(replaceIO.getIOSide()).thenReturn(Side.LEFT);

        circuitSpy.addTile(replaceIO);

        Mockito.when(replaceIO.acceptsPower(Side.LEFT)).thenReturn(true);
        Mockito.when(replaceIO.outputsPower(Side.LEFT)).thenReturn(false);
        Assertions.assertFalse(circuitSpy.updateOnIORotation(replaceIOPos, Side.UP));
        Assertions.assertEquals(io, circuitSpy.getIO(Side.LEFT));
        Assertions.assertNotEquals(replaceIO, circuitSpy.getIO(Side.LEFT));

        // Test - Rotate updates map correctly
        Mockito.when(io.getIOSide()).thenReturn(Side.UP);
        Mockito.when(io.acceptsPower(Side.UP)).thenReturn(true);
        Mockito.when(io.outputsPower(Side.UP)).thenReturn(false);
        Assertions.assertTrue(circuitSpy.updateOnIORotation(pos, Side.LEFT));
        Assertions.assertNotEquals(io, circuitSpy.getIO(Side.LEFT));
        Assertions.assertEquals(io, circuitSpy.getIO(Side.UP));
    }

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testPower() {
        // No IO tiles
        for (Side side : Side.values()) {
            Assertions.assertEquals(Power.getMin(), circuit.getPower(side));
        }

        Position left = Mockito.mock(Position.class);
        Mockito.when(left.getX()).thenReturn(0);
        Mockito.when(left.getY()).thenReturn(1);
        IOTile leftIO = Mockito.mock(IOTile.class);
        Mockito.when(leftIO.getPosition()).thenReturn(left);
        Mockito.when(leftIO.getType()).thenReturn(TileType.IO);
        Mockito.when(leftIO.getIOSide()).thenReturn(Side.LEFT);
        Mockito.when(leftIO.getExteriorPower(Side.LEFT)).thenReturn(12);
        Mockito.when(leftIO.getExteriorPower(Side.DOWN)).thenReturn(Power.getMin());
        Mockito.when(leftIO.getExteriorPower(Side.RIGHT)).thenReturn(1);
        Mockito.when(leftIO.getExteriorPower(Side.UP)).thenReturn(2);

        circuit.addTile(leftIO);

        circuit.setIO(Side.LEFT, left);

        Assertions.assertEquals(12, circuit.getPower(Side.LEFT));
        Assertions.assertEquals(Power.getMin(), circuit.getPower(Side.DOWN));
        Assertions.assertEquals(Power.getMin(), circuit.getPower(Side.RIGHT));
        Assertions.assertEquals(Power.getMin(), circuit.getPower(Side.UP));

        Position right = Mockito.mock(Position.class);
        Mockito.when(right.getX()).thenReturn(2);
        Mockito.when(right.getY()).thenReturn(1);
        IOTile rightIO = Mockito.mock(IOTile.class);
        Mockito.when(rightIO.getPosition()).thenReturn(right);
        Mockito.when(rightIO.getType()).thenReturn(TileType.IO);
        Mockito.when(rightIO.getIOSide()).thenReturn(Side.RIGHT);
        Mockito.when(rightIO.getExteriorPower(Side.LEFT)).thenReturn(5);
        Mockito.when(rightIO.getExteriorPower(Side.DOWN)).thenReturn(8);
        Mockito.when(rightIO.getExteriorPower(Side.RIGHT)).thenReturn(3);
        Mockito.when(rightIO.getExteriorPower(Side.UP)).thenReturn(4);

        circuit.addTile(rightIO);

        circuit.setIO(Side.RIGHT, right);

        Assertions.assertEquals(3, circuit.getPower(Side.RIGHT));
        Assertions.assertEquals(Power.getMin(), circuit.getPower(Side.DOWN));
        Assertions.assertEquals(12, circuit.getPower(Side.LEFT));
        Assertions.assertEquals(Power.getMin(), circuit.getPower(Side.UP));

        for (Side side : Side.values()) {
            Mockito.when(leftIO.acceptsPower(side)).thenReturn(false);
            Mockito.when(leftIO.outputsPower(side)).thenReturn(false);
            Mockito.when(rightIO.acceptsPower(side)).thenReturn(false);
            Mockito.when(rightIO.outputsPower(side)).thenReturn(false);
        }

        Mockito.when(leftIO.acceptsPower(Side.LEFT)).thenReturn(true);
        Mockito.when(rightIO.outputsPower(Side.RIGHT)).thenReturn(true);

        Assertions.assertTrue(circuit.acceptsPower(Side.RIGHT));
        Assertions.assertFalse(circuit.acceptsPower(Side.LEFT));
        Assertions.assertFalse(circuit.acceptsPower(Side.UP));
        Assertions.assertFalse(circuit.acceptsPower(Side.DOWN));

        Assertions.assertTrue(circuit.outputsPower(Side.LEFT));
        Assertions.assertFalse(circuit.outputsPower(Side.RIGHT));
        Assertions.assertFalse(circuit.outputsPower(Side.UP));
        Assertions.assertFalse(circuit.outputsPower(Side.DOWN));
    }

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testRotate() {
        Circuit superCircuit = Mockito.mock(Circuit.class);

        Position left = Mockito.mock(Position.class);
        Mockito.when(left.getX()).thenReturn(0);
        Mockito.when(left.getY()).thenReturn(1);
        IOTile leftIO = Mockito.mock(IOTile.class);
        Mockito.when(leftIO.getPosition()).thenReturn(left);

        Position right = Mockito.mock(Position.class);
        Mockito.when(right.getX()).thenReturn(2);
        Mockito.when(right.getY()).thenReturn(1);
        IOTile rightIO = Mockito.mock(IOTile.class);
        Mockito.when(rightIO.getPosition()).thenReturn(right);

        Position up = Mockito.mock(Position.class);
        Mockito.when(up.getX()).thenReturn(1);
        Mockito.when(up.getY()).thenReturn(0);
        IOTile upIO = Mockito.mock(IOTile.class);
        Mockito.when(upIO.getPosition()).thenReturn(up);

        circuit.addTile(leftIO);
        circuit.addTile(rightIO);
        circuit.addTile(upIO);

        circuit.setIO(Side.LEFT, left);
        circuit.setIO(Side.RIGHT, right);
        circuit.setIO(Side.UP, up);

        Assertions.assertEquals(leftIO, circuit.getIO(Side.LEFT));
        Assertions.assertEquals(rightIO, circuit.getIO(Side.RIGHT));
        Assertions.assertEquals(upIO, circuit.getIO(Side.UP));

        Assertions.assertNotEquals(leftIO, circuit.getIO(Side.DOWN));
        Assertions.assertNotEquals(rightIO, circuit.getIO(Side.DOWN));
        Assertions.assertNotEquals(upIO, circuit.getIO(Side.DOWN));

        Assertions.assertTrue(circuit.rotateLeft(superCircuit));

        Assertions.assertEquals(leftIO, circuit.getIO(Side.DOWN));
        Assertions.assertEquals(rightIO, circuit.getIO(Side.UP));
        Assertions.assertEquals(upIO, circuit.getIO(Side.LEFT));

        Assertions.assertNotEquals(leftIO, circuit.getIO(Side.RIGHT));
        Assertions.assertNotEquals(rightIO, circuit.getIO(Side.RIGHT));
        Assertions.assertNotEquals(upIO, circuit.getIO(Side.RIGHT));

        Assertions.assertTrue(circuit.rotateLeft(superCircuit));
        Assertions.assertTrue(circuit.rotateLeft(superCircuit));

        Assertions.assertEquals(leftIO, circuit.getIO(Side.UP));
        Assertions.assertEquals(rightIO, circuit.getIO(Side.DOWN));
        Assertions.assertEquals(upIO, circuit.getIO(Side.RIGHT));

        Assertions.assertNotEquals(leftIO, circuit.getIO(Side.LEFT));
        Assertions.assertNotEquals(rightIO, circuit.getIO(Side.LEFT));
        Assertions.assertNotEquals(upIO, circuit.getIO(Side.LEFT));

        Assertions.assertTrue(circuit.rotateRight(superCircuit));

        Assertions.assertEquals(leftIO, circuit.getIO(Side.RIGHT));
        Assertions.assertEquals(rightIO, circuit.getIO(Side.LEFT));
        Assertions.assertEquals(upIO, circuit.getIO(Side.DOWN));

        Assertions.assertNotEquals(leftIO, circuit.getIO(Side.UP));
        Assertions.assertNotEquals(rightIO, circuit.getIO(Side.UP));
        Assertions.assertNotEquals(upIO, circuit.getIO(Side.UP));
    }

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testNextTick() {
        Assertions.assertEquals(0, circuit.getTick());

        Assertions.assertTrue(circuit.nextTick());

        Assertions.assertEquals(1, circuit.getTick());

        for (int i = 0; i < 6; i++) Assertions.assertTrue(circuit.nextTick());

        Assertions.assertEquals(7, circuit.getTick());
    }

    @Property
    @net.jqwik.api.Tag("model")
    @net.jqwik.api.Tag("unit-test") @net.jqwik.api.Tag("slow")
    public void testNextTick(@ForAll @LongRange(min = 0, max = 1000000) long ticksAdvances) {
        long previous_tick = circuit.getTick();

        for (long i = 0; i < ticksAdvances; i++) {
            Assertions.assertTrue(circuit.nextTick());
        }
        Assertions.assertEquals(previous_tick + ticksAdvances, circuit.getTick());
    }

    @Property
    @net.jqwik.api.Tag("model")
    @net.jqwik.api.Tag("unit-test") @net.jqwik.api.Tag("slow")
    public void testUpdateOnNonInputSide(@ForAll int power, @ForAll Side side) {
        Circuit superCircuit = Mockito.mock(Circuit.class);

        Position left = Mockito.mock(Position.class);
        Mockito.when(left.getX()).thenReturn(0);
        Mockito.when(left.getY()).thenReturn(1);
        IOTile leftIO = Mockito.mock(IOTile.class);
        Mockito.when(leftIO.getPosition()).thenReturn(left);
        Mockito.when(leftIO.getIOSide()).thenReturn(Side.RIGHT);
        Mockito.when(leftIO.acceptsPower(Mockito.any(Side.class))).thenReturn(true);
        Mockito.when(leftIO.outputsPower(Mockito.any(Side.class))).thenReturn(false);
        Mockito.when(leftIO.getPower(Side.LEFT)).thenReturn(3);

        circuit.addTile(leftIO);
        circuit.setIO(Side.LEFT, left);

        Assertions.assertFalse(circuit.update(superCircuit, power, side));
    }

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testUpdate() {
        Circuit superCircuit = Mockito.mock(Circuit.class);

        Position right = Mockito.mock(Position.class);
        Mockito.when(right.getX()).thenReturn(2);
        Mockito.when(right.getY()).thenReturn(1);
        IOTile rightIO = Mockito.mock(IOTile.class);
        Mockito.when(rightIO.getPosition()).thenReturn(right);
        Mockito.when(rightIO.getIOSide()).thenReturn(Side.RIGHT);
        Mockito.when(rightIO.outputsPower(Mockito.any(Side.class))).thenReturn(true);
        Mockito.when(rightIO.acceptsPower(Mockito.any(Side.class))).thenReturn(false);
        Mockito.when(rightIO.getPower(Side.RIGHT)).thenReturn(12);

        circuit.addTile(rightIO);
        circuit.setIO(Side.RIGHT, right);

        for (Side side : Side.values()) {
            Assertions.assertFalse(circuit.update(superCircuit, 12, side));
        }

        Assertions.assertFalse(circuit.update(superCircuit, Power.getMin(), Side.LEFT));
        Assertions.assertTrue(circuit.update(superCircuit, Power.getMin(), Side.RIGHT));
        Assertions.assertFalse(circuit.update(superCircuit, Power.getMin(), Side.UP));
        Assertions.assertFalse(circuit.update(superCircuit, Power.getMin(), Side.DOWN));

        Assertions.assertFalse(circuit.update(superCircuit, Power.getMax(), Side.LEFT));
        Assertions.assertTrue(circuit.update(superCircuit, Power.getMax(), Side.RIGHT));
        Assertions.assertFalse(circuit.update(superCircuit, Power.getMax(), Side.UP));
        Assertions.assertFalse(circuit.update(superCircuit, Power.getMax(), Side.DOWN));

        Assertions.assertFalse(circuit.update(superCircuit, 13, Side.LEFT));
        Assertions.assertTrue(circuit.update(superCircuit, 13, Side.RIGHT));
        Assertions.assertFalse(circuit.update(superCircuit, 13, Side.UP));
        Assertions.assertFalse(circuit.update(superCircuit, 13, Side.DOWN));
    }
}