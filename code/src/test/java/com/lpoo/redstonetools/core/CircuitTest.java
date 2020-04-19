package com.lpoo.redstonetools.core;

import com.lpoo.redstonetools.core.Circuit;
import com.lpoo.redstonetools.core.tiles.ConstantSourceTile;
import com.lpoo.redstonetools.core.tiles.RepeaterTile;
import com.lpoo.redstonetools.core.tiles.WireTile;
import com.lpoo.redstonetools.core.tiles.NullTile;
import com.lpoo.redstonetools.core.utils.Position;
import com.lpoo.redstonetools.core.utils.Side;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CircuitTest {

    private Circuit circuit;
    private final int WIDTH = 20;
    private final int HEIGHT = 20;

    private void addWires(Position position, Side direction, int howMany) {
        int p_x = position.getX();
        int p_y = position.getY();
        switch (direction) {
            case UP:
                for (int y = p_y, i = 0; i < howMany && y >= 0; i++, y--) {
                    Position new_pos = new Position(p_x, y);
                    circuit.addTile(new WireTile(new_pos));
                }
                break;
            case DOWN:
                for (int y = p_y, i = 0; i < howMany && y < circuit.getHeight(); i++, y++) {
                    Position new_pos = new Position(p_x, y);
                    circuit.addTile(new WireTile(new_pos));
                }
                break;
            case LEFT:
                for (int x = p_x, i = 0; i < howMany && x >= 0; i++, x--) {
                    Position new_pos = new Position(x, p_y);
                    circuit.addTile(new WireTile(new_pos));
                }
                break;
            case RIGHT:
                for (int x = p_x, i = 0; i < howMany && x < circuit.getWidth(); i++, x++) {
                    Position new_pos = new Position(x, p_y);
                    circuit.addTile(new WireTile(new_pos));
                }
                break;
        }
    }

    @Before
    public void create() { this.circuit = new Circuit(WIDTH, HEIGHT); }


    @Test
    public void getTileTest() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                assertEquals(NullTile.class,  circuit.getTile(j, i).getClass());
            }
        }
    }

    @Test
    public void addTileTest() {
        Position pos = new Position(4, 7);
        assertEquals(NullTile.class, circuit.getTile(pos).getClass());
        circuit.addTile(new WireTile(pos));
        assertEquals(WireTile.class, circuit.getTile(pos).getClass());
    }

    @Test
    public void removeTileTest() {
        Position pos = new Position(4, 7);
        assertEquals(NullTile.class, circuit.getTile(pos).getClass());
        circuit.addTile(new WireTile(pos));
        assertEquals(WireTile.class, circuit.getTile(pos).getClass());
        circuit.removeTile(pos);
        assertEquals(NullTile.class, circuit.getTile(pos).getClass());
    }

    @Test
    public void testBounds() {
        assertEquals(NullTile.class, circuit.getTile(-1, 5).getClass());
        assertEquals(NullTile.class, circuit.getTile(2, 200).getClass());
        assertEquals(NullTile.class, circuit.getTile(-6, 2).getClass());
    }

    @Test
    public void testConstantSourceOutput() {
        circuit.addTile(new ConstantSourceTile(new Position(2, 6)));

        circuit.advanceTick();

        assertEquals("Power : 15", circuit.getTile(2, 6).getInfo());
    }

    @Test
    public void testWirePropagation() {
        circuit.addTile(new WireTile(new Position(4, 4)));
        circuit.addTile(new WireTile(new Position(3, 4)));
        circuit.addTile(new WireTile(new Position(2, 4)));
        circuit.addTile(new WireTile(new Position(2, 5)));
        circuit.addTile(new ConstantSourceTile(new Position(2, 6)));

        circuit.advanceTick();

        assertEquals("Power : 15", circuit.getTile(2, 5).getInfo());
        assertEquals("Power : 14", circuit.getTile(2, 4).getInfo());
        assertEquals("Power : 13", circuit.getTile(3, 4).getInfo());
        assertEquals("Power : 12", circuit.getTile(4, 4).getInfo());
    }

    @Test
    public void testWirePropagationDoubleEnded() {
        circuit.addTile(new WireTile(new Position(4, 4)));
        circuit.addTile(new WireTile(new Position(3, 4)));
        circuit.addTile(new WireTile(new Position(2, 4)));
        circuit.addTile(new WireTile(new Position(2, 5)));
        circuit.addTile(new ConstantSourceTile(new Position(2, 6)));
        circuit.addTile(new ConstantSourceTile(new Position(5, 4)));

        circuit.advanceTick();

        assertEquals("Power : 15", circuit.getTile(2, 5).getInfo());
        assertEquals("Power : 14", circuit.getTile(2, 4).getInfo());
        assertEquals("Power : 14", circuit.getTile(3, 4).getInfo());
        assertEquals("Power : 15", circuit.getTile(4, 4).getInfo());
    }

    @Test
    public void testWirePropagationBlock() {
        circuit.addTile(new WireTile(new Position(0, 0)));
        circuit.addTile(new WireTile(new Position(0, 1)));
        circuit.addTile(new WireTile(new Position(0, 2)));
        circuit.addTile(new WireTile(new Position(0, 3)));
        circuit.addTile(new WireTile(new Position(1, 0)));
        circuit.addTile(new WireTile(new Position(1, 1)));
        circuit.addTile(new WireTile(new Position(1, 2)));
        circuit.addTile(new WireTile(new Position(1, 3)));
        circuit.addTile(new WireTile(new Position(0, 4)));
        circuit.addTile(new ConstantSourceTile(new Position(0, 5)));
        circuit.addTile(new WireTile(new Position(2, 3)));
        circuit.addTile(new ConstantSourceTile(new Position(3, 3)));

        circuit.advanceTick();

        assertEquals("Power : 11", circuit.getTile(0, 0).getInfo());
        assertEquals("Power : 12", circuit.getTile(0, 1).getInfo());
        assertEquals("Power : 13", circuit.getTile(0, 2).getInfo());
        assertEquals("Power : 14", circuit.getTile(0, 3).getInfo());
        assertEquals("Power : 15", circuit.getTile(0, 4).getInfo());
        assertEquals("Power : 11", circuit.getTile(1, 0).getInfo());
        assertEquals("Power : 12", circuit.getTile(1, 1).getInfo());
        assertEquals("Power : 13", circuit.getTile(1, 2).getInfo());
        assertEquals("Power : 14", circuit.getTile(1, 3).getInfo());
        assertEquals("Power : 15", circuit.getTile(2, 3).getInfo());
    }

    @Test
    public void testWirePropagationCycle() {
        circuit.addTile(new ConstantSourceTile(new Position(0, 0)));
        circuit.addTile(new WireTile(new Position(1, 0)));
        circuit.addTile(new WireTile(new Position(2, 0)));
        circuit.addTile(new WireTile(new Position(3, 0)));
        circuit.addTile(new WireTile(new Position(4, 0)));
        circuit.addTile(new WireTile(new Position(2, 1)));
        circuit.addTile(new WireTile(new Position(2, 2)));
        circuit.addTile(new WireTile(new Position(3, 2)));
        circuit.addTile(new WireTile(new Position(4, 2)));
        circuit.addTile(new WireTile(new Position(4, 1)));

        circuit.advanceTick();

        assertEquals("Power : 15", circuit.getTile(1, 0).getInfo());
        assertEquals("Power : 14", circuit.getTile(2, 0).getInfo());
        assertEquals("Power : 13", circuit.getTile(3, 0).getInfo());
        assertEquals("Power : 12", circuit.getTile(4, 0).getInfo());
        assertEquals("Power : 13", circuit.getTile(2, 1).getInfo());
        assertEquals("Power : 12", circuit.getTile(2, 2).getInfo());
        assertEquals("Power : 11", circuit.getTile(3, 2).getInfo());
        assertEquals("Power : 10", circuit.getTile(4, 2).getInfo());
        assertEquals("Power : 11", circuit.getTile(4, 1).getInfo());
    }

    @Test
    public void testWirePropagationDissipation() {
        circuit.addTile(new ConstantSourceTile(new Position(19, 0)));
        addWires(new Position(1, 0), Side.RIGHT, 18);
        /*
        circuit.addTile(new WireTile(new Position(1, 0)));
        circuit.addTile(new WireTile(new Position(2, 0)));
        circuit.addTile(new WireTile(new Position(3, 0)));
        circuit.addTile(new WireTile(new Position(4, 0)));
        circuit.addTile(new WireTile(new Position(5, 0)));
        circuit.addTile(new WireTile(new Position(6, 0)));
        circuit.addTile(new WireTile(new Position(7, 0)));
        circuit.addTile(new WireTile(new Position(8, 0)));
        circuit.addTile(new WireTile(new Position(9, 0)));
        circuit.addTile(new WireTile(new Position(10, 0)));
        circuit.addTile(new WireTile(new Position(11, 0)));
        circuit.addTile(new WireTile(new Position(12, 0)));
        circuit.addTile(new WireTile(new Position(13, 0)));
        circuit.addTile(new WireTile(new Position(14, 0)));
        circuit.addTile(new WireTile(new Position(15, 0)));
        circuit.addTile(new WireTile(new Position(16, 0)));
        circuit.addTile(new WireTile(new Position(17, 0)));
        circuit.addTile(new WireTile(new Position(18, 0)));*/

        circuit.advanceTick();

        assertEquals("Power : 0", circuit.getTile(1, 0).getInfo());
        assertEquals("Power : 0", circuit.getTile(2, 0).getInfo());
        assertEquals("Power : 0", circuit.getTile(3, 0).getInfo());
        assertEquals("Power : 1", circuit.getTile(4, 0).getInfo());
        assertEquals("Power : 2", circuit.getTile(5, 0).getInfo());
        assertEquals("Power : 3", circuit.getTile(6, 0).getInfo());
    }

    @Test
    public void testOrientedTileRotation() {
        RepeaterTile repeater = new RepeaterTile(new Position(0, 0));
        assertEquals(true, repeater.isInput(Side.LEFT));
        assertEquals(false, repeater.isInput(Side.RIGHT));
        assertEquals(false, repeater.isInput(Side.UP));
        assertEquals(false, repeater.isInput(Side.DOWN));


        assertEquals(false, repeater.isOutput(Side.LEFT));
        assertEquals(true, repeater.isOutput(Side.RIGHT));
        assertEquals(false, repeater.isOutput(Side.UP));
        assertEquals(false, repeater.isOutput(Side.DOWN));

        repeater.rotateRight();

        assertEquals(false, repeater.isInput(Side.LEFT));
        assertEquals(false, repeater.isInput(Side.RIGHT));
        assertEquals(true, repeater.isInput(Side.UP));
        assertEquals(false, repeater.isInput(Side.DOWN));


        assertEquals(false, repeater.isOutput(Side.LEFT));
        assertEquals(false, repeater.isOutput(Side.RIGHT));
        assertEquals(false, repeater.isOutput(Side.UP));
        assertEquals(true, repeater.isOutput(Side.DOWN));

        repeater.rotateLeft(); repeater.rotateLeft();

        assertEquals(false, repeater.isInput(Side.LEFT));
        assertEquals(false, repeater.isInput(Side.RIGHT));
        assertEquals(false, repeater.isInput(Side.UP));
        assertEquals(true, repeater.isInput(Side.DOWN));


        assertEquals(false, repeater.isOutput(Side.LEFT));
        assertEquals(false, repeater.isOutput(Side.RIGHT));
        assertEquals(true, repeater.isOutput(Side.UP));
        assertEquals(false, repeater.isOutput(Side.DOWN));
    }

    @Test
    public void testRepeaterCircuit() {
        circuit.addTile(new ConstantSourceTile(new Position(0, 0)));
        circuit.addTile(new WireTile(new Position(1, 0)));
        circuit.addTile(new WireTile(new Position(2, 0)));
        circuit.addTile(new RepeaterTile(new Position(3, 0))); // oriented from left to right
        circuit.addTile(new WireTile(new Position(4, 0)));
        circuit.addTile(new WireTile(new Position(5, 0)));
        circuit.addTile(new WireTile(new Position(5, 1)));
        circuit.addTile(new WireTile(new Position(5, 2)));
        circuit.addTile(new WireTile(new Position(4, 2)));
        circuit.addTile(new WireTile(new Position(3, 2)));
        RepeaterTile repeater2 = new RepeaterTile(new Position(2, 2));
        repeater2.rotateLeft(); repeater2.rotateLeft(); // oriented from right to left
        circuit.addTile(repeater2);
        circuit.addTile(new WireTile(new Position(1, 2)));
        circuit.addTile(new WireTile(new Position(0, 2)));
        RepeaterTile repeater3 = new RepeaterTile(new Position(0, 3));
        repeater3.rotateRight(); // oriented from up to down
        circuit.addTile(repeater3);
        addWires(new Position(0, 4), Side.RIGHT, 8);
        circuit.addTile(new WireTile(new Position(0, 5)));
        addWires(new Position(0, 6), Side.RIGHT, 14);
        RepeaterTile repeater4 = new RepeaterTile(new Position(14, 6)); // oriented left to right
        circuit.addTile(repeater4);
        circuit.addTile(new WireTile(new Position(15, 6)));
        circuit.addTile(new WireTile(new Position(7, 3)));
        RepeaterTile repeater5 = new RepeaterTile(new Position(7, 2));
        repeater5.rotateLeft(); // oriented from down to up
        circuit.addTile(repeater5);
        circuit.addTile(new WireTile(new Position(7, 1)));
        circuit.addTile(new WireTile(new Position(7, 0)));

        circuit.advanceTick();

        assertEquals("Power : 14", circuit.getTile(2, 0).getInfo());
        assertEquals("Active : true", circuit.getTile(3, 0).getInfo());
        assertEquals("Power : 15", circuit.getTile(4, 0).getInfo());
        assertEquals("Power : 10", circuit.getTile(3, 2).getInfo());
        assertEquals("Active : true", circuit.getTile(2, 2).getInfo());
        assertEquals("Power : 15", circuit.getTile(1, 2).getInfo());
        assertEquals("Power : 14", circuit.getTile(0, 2).getInfo());
        assertEquals("Active : true", circuit.getTile(0, 3).getInfo());
        assertEquals("Power : 15", circuit.getTile(0, 4).getInfo());
        assertEquals("Power : 7", circuit.getTile(7, 3).getInfo());
        assertEquals("Active : true", circuit.getTile(7, 2).getInfo());
        assertEquals("Power : 15", circuit.getTile(7, 1).getInfo());

        assertEquals("Power : 14", circuit.getTile(0, 5).getInfo());
        assertEquals("Power : 1", circuit.getTile(12, 6).getInfo());
        assertEquals("Power : 0", circuit.getTile(13, 6).getInfo());
        assertEquals("Active : false", circuit.getTile(14, 6).getInfo());
        assertEquals("Power : 0", circuit.getTile(15, 6).getInfo());
    }

}
