package com.lpoo.redstonetools.model.circuit;

import com.lpoo.redstonetools.model.tile.Tile;
import com.lpoo.redstonetools.model.utils.Position;
import com.lpoo.redstonetools.model.utils.Power;
import com.lpoo.redstonetools.model.utils.Side;
import com.lpoo.redstonetools.model.utils.TileType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class CircuitTest {

    private Circuit circuit;
    private final int WIDTH = 20;
    private final int HEIGHT = 20;

    @Before
    public void setup() {
        this.circuit = new Circuit(WIDTH, HEIGHT);
        Power.setRedstoneMode();
    }

    @Test
    public void testCircuitInit() {
        Assert.assertEquals(WIDTH, circuit.getWidth());
        Assert.assertEquals(HEIGHT, circuit.getHeight());
        Assert.assertEquals(0, circuit.getTick());
        Assert.assertNotNull(circuit.getTickedTiles());

        for (int i = 0; i < circuit.getHeight(); i++) {
            for (int j = 0; j < circuit.getWidth(); j++) {
                Assert.assertNotNull(circuit.getTile(j, i));
                Assert.assertEquals(TileType.NULL, circuit.getTile(j, i).getType());
            }
        }
    }

    @Test
    public void testCircuitBounds() {
        Position position = Mockito.mock(Position.class);

        Mockito.when(position.getX()).thenReturn(0);
        Mockito.when(position.getY()).thenReturn(0);
        Assert.assertTrue(circuit.isInBounds(position));

        Mockito.when(position.getX()).thenReturn(WIDTH - 1);
        Mockito.when(position.getY()).thenReturn(HEIGHT - 1);
        Assert.assertTrue(circuit.isInBounds(position));

        Mockito.when(position.getX()).thenReturn(-1);
        Mockito.when(position.getY()).thenReturn(0);
        Assert.assertFalse(circuit.isInBounds(position));

        Mockito.when(position.getX()).thenReturn(WIDTH - 1);
        Mockito.when(position.getY()).thenReturn(HEIGHT);
        Assert.assertFalse(circuit.isInBounds(position));
    }

    @Test
    public void testCircuitBasicAddTile() {
        Position position = Mockito.mock(Position.class);

        Mockito.when(position.getX()).thenReturn(0);
        Mockito.when(position.getY()).thenReturn(0);

        Tile tile = Mockito.mock(Tile.class);

        Mockito.when(tile.getPosition()).thenReturn(position);
        Mockito.when(tile.isTickedTile()).thenReturn(false);
        Mockito.when(tile.getType()).thenReturn(TileType.WIRE);

        Assert.assertTrue(circuit.addTile(tile));

        Position position2 = Mockito.mock(Position.class);

        Mockito.when(position2.getX()).thenReturn(WIDTH - 1);
        Mockito.when(position2.getY()).thenReturn(HEIGHT + 1);

        Tile tile2 = Mockito.mock(Tile.class);

        Mockito.when(tile2.getPosition()).thenReturn(position2);

        Assert.assertFalse(circuit.addTile(tile2));
    }

    @Test
    public void testCircuitGetTile() {
        Position position = Mockito.mock(Position.class);

        Mockito.when(position.getX()).thenReturn(0);
        Mockito.when(position.getY()).thenReturn(0);

        Tile tile = Mockito.mock(Tile.class);

        Mockito.when(tile.getPosition()).thenReturn(position);
        Mockito.when(tile.isTickedTile()).thenReturn(false);
        Mockito.when(tile.getType()).thenReturn(TileType.WIRE);

        Assert.assertEquals(TileType.NULL, circuit.getTile(position).getType());

        circuit.addTile(tile);

        Assert.assertEquals(TileType.WIRE, circuit.getTile(position).getType());

        Position position2 = Mockito.mock(Position.class);

        Mockito.when(position2.getX()).thenReturn(WIDTH - 1);
        Mockito.when(position2.getY()).thenReturn(-5);

        Assert.assertEquals(TileType.NULL, circuit.getTile(position2).getType());
    }

    @Test
    public void testCircuitAdvancedAddTile() {
        Position position = Mockito.mock(Position.class);

        Mockito.when(position.getX()).thenReturn(0);
        Mockito.when(position.getY()).thenReturn(0);

        Tile tile = Mockito.mock(Tile.class);

        Mockito.when(tile.getPosition()).thenReturn(position);
        Mockito.when(tile.isTickedTile()).thenReturn(true);
        Mockito.when(tile.getType()).thenReturn(TileType.SOURCE);

        Assert.assertEquals(0, circuit.getTickedTiles().size());

        Assert.assertTrue(circuit.addTile(tile));

        Assert.assertEquals(TileType.SOURCE, circuit.getTile(position).getType());

        Assert.assertEquals(1, circuit.getTickedTiles().size());
    }

    @Test
    public void testCircuitSafeRemoveTile() {
        Position position = Mockito.mock(Position.class);

        Mockito.when(position.getX()).thenReturn(0);
        Mockito.when(position.getY()).thenReturn(0);

        Tile tile = Mockito.mock(Tile.class);

        Mockito.when(tile.getPosition()).thenReturn(position);
        Mockito.when(tile.isTickedTile()).thenReturn(true);
        Mockito.when(tile.getType()).thenReturn(TileType.SOURCE);

        Assert.assertEquals(0, circuit.getTickedTiles().size());

        Assert.assertTrue(circuit.addTile(tile));

        Assert.assertEquals(1, circuit.getTickedTiles().size());

        Tile tile2 = Mockito.mock(Tile.class);

        Mockito.when(tile2.getPosition()).thenReturn(position);
        Mockito.when(tile2.isTickedTile()).thenReturn(true);
        Mockito.when(tile2.getType()).thenReturn(TileType.LEVER);

        Assert.assertTrue(circuit.addTile(tile2));

        Assert.assertEquals(1, circuit.getTickedTiles().size());

        Tile tile3 = Mockito.mock(Tile.class);

        Mockito.when(tile3.getPosition()).thenReturn(position);
        Mockito.when(tile3.isTickedTile()).thenReturn(false);
        Mockito.when(tile3.getType()).thenReturn(TileType.WIRE);

        Assert.assertTrue(circuit.addTile(tile3));

        Assert.assertEquals(0, circuit.getTickedTiles().size());
    }

    @Test
    public void testCircuitSurroundingPower() {
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

        Assert.assertEquals(Power.getMin(), circuit.getSurroundingPower(mid));

        Tile wire = Mockito.mock(Tile.class);
        Mockito.when(wire.getPosition()).thenReturn(up);
        Mockito.when(wire.isWire()).thenReturn(true);
        Mockito.when(wire.isTickedTile()).thenReturn(false);
        Mockito.when(wire.getType()).thenReturn(TileType.WIRE);

        circuit.addTile(wire);

        Mockito.when(wire.getPower(Side.DOWN)).thenReturn(Power.getMin());
        Assert.assertEquals(Power.getMin(), circuit.getSurroundingPower(mid));

        Mockito.when(wire.getPower(Side.DOWN)).thenReturn(Power.getMax());
        Assert.assertEquals(Power.decrease(Power.getMax()), circuit.getSurroundingPower(mid));

        Tile source = Mockito.mock(Tile.class);
        Mockito.when(source.getPosition()).thenReturn(right);
        Mockito.when(source.isWire()).thenReturn(false);
        Mockito.when(source.isTickedTile()).thenReturn(true);
        Mockito.when(source.getType()).thenReturn(TileType.SOURCE);

        circuit.addTile(source);

        Mockito.when(source.getPower(Side.LEFT)).thenReturn(Power.getMax());
        Assert.assertEquals(Power.getMax(), circuit.getSurroundingPower(mid));
    }

    @Test
    public void testCircuitSurroundingWirePower() {
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

        Assert.assertEquals(Power.getMin(), circuit.getSurroundingWirePower(mid));

        Tile wire = Mockito.mock(Tile.class);
        Mockito.when(wire.getPosition()).thenReturn(up);
        Mockito.when(wire.isWire()).thenReturn(true);
        Mockito.when(wire.isTickedTile()).thenReturn(false);
        Mockito.when(wire.getType()).thenReturn(TileType.WIRE);

        circuit.addTile(wire);

        Mockito.when(wire.getPower(Side.DOWN)).thenReturn(Power.getMin());
        Assert.assertEquals(Power.getMin(), circuit.getSurroundingWirePower(mid));

        Mockito.when(wire.getPower(Side.DOWN)).thenReturn(Power.decrease(Power.getMax()));
        Assert.assertEquals(Power.decrease(Power.decrease(Power.getMax())), circuit.getSurroundingWirePower(mid));

        Tile source = Mockito.mock(Tile.class);
        Mockito.when(source.getPosition()).thenReturn(right);
        Mockito.when(source.isWire()).thenReturn(false);
        Mockito.when(source.isTickedTile()).thenReturn(true);
        Mockito.when(source.getType()).thenReturn(TileType.SOURCE);

        circuit.addTile(source);

        Mockito.when(source.getPower(Side.LEFT)).thenReturn(Power.getMax());
        Assert.assertEquals(Power.decrease(Power.decrease(Power.getMax())), circuit.getSurroundingWirePower(mid));

        Tile wire2 = Mockito.mock(Tile.class);
        Mockito.when(wire2.getPosition()).thenReturn(left);
        Mockito.when(wire2.isWire()).thenReturn(true);
        Mockito.when(wire2.isTickedTile()).thenReturn(false);
        Mockito.when(wire2.getType()).thenReturn(TileType.WIRE);

        circuit.addTile(wire2);

        Mockito.when(wire2.getPower(Side.RIGHT)).thenReturn(Power.getMax());
        Assert.assertEquals(Power.decrease(Power.getMax()), circuit.getSurroundingWirePower(mid));
    }

    @Test
    public void testCircuitSurroundingGatePower() {
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

        Assert.assertEquals(Power.getMin(), circuit.getSurroundingGatePower(mid));

        Tile wire = Mockito.mock(Tile.class);
        Mockito.when(wire.getPosition()).thenReturn(up);
        Mockito.when(wire.isWire()).thenReturn(true);
        Mockito.when(wire.isTickedTile()).thenReturn(false);
        Mockito.when(wire.getType()).thenReturn(TileType.WIRE);

        circuit.addTile(wire);

        Mockito.when(wire.getPower(Side.DOWN)).thenReturn(Power.getMin());
        Assert.assertEquals(Power.getMin(), circuit.getSurroundingGatePower(mid));

        Mockito.when(wire.getPower(Side.DOWN)).thenReturn(Power.getMax());
        Assert.assertEquals(Power.getMin(), circuit.getSurroundingGatePower(mid));

        Tile source = Mockito.mock(Tile.class);
        Mockito.when(source.getPosition()).thenReturn(right);
        Mockito.when(source.isWire()).thenReturn(false);
        Mockito.when(source.isTickedTile()).thenReturn(true);
        Mockito.when(source.getType()).thenReturn(TileType.SOURCE);

        circuit.addTile(source);

        Mockito.when(source.getPower(Side.LEFT)).thenReturn(Power.getMax());
        Assert.assertEquals(Power.getMax(), circuit.getSurroundingGatePower(mid));

        Tile tile = Mockito.mock(Tile.class);
        Mockito.when(tile.getPosition()).thenReturn(left);
        Mockito.when(tile.isWire()).thenReturn(false);
        Mockito.when(tile.isTickedTile()).thenReturn(true);
        Mockito.when(tile.getType()).thenReturn(TileType.LEVER);

        circuit.addTile(tile);

        Mockito.when(tile.getPower(Side.RIGHT)).thenReturn(Power.getMin());
        Assert.assertEquals(Power.getMax(), circuit.getSurroundingGatePower(mid));
    }

    @Test
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

        Assert.assertTrue(circuit.canTilesConnect(mid, Side.UP));
        Assert.assertFalse(circuit.canTilesConnect(mid, Side.DOWN));
        Assert.assertFalse(circuit.canTilesConnect(mid, Side.LEFT));
        Assert.assertFalse(circuit.canTilesConnect(mid, Side.RIGHT));
    }

}
