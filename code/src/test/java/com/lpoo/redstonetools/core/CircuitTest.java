package com.lpoo.redstonetools.core;

import com.lpoo.redstonetools.core.tiles.ConstantSourceTile;
import com.lpoo.redstonetools.core.tiles.RepeaterTile;
import com.lpoo.redstonetools.core.tiles.LeverTile;
import com.lpoo.redstonetools.core.tiles.WireTile;
import com.lpoo.redstonetools.core.tiles.NullTile;
import com.lpoo.redstonetools.core.utils.Position;
import com.lpoo.redstonetools.core.utils.Power;
import com.lpoo.redstonetools.core.utils.Side;
import com.lpoo.redstonetools.graphics.CircuitRenderer;
import com.lpoo.redstonetools.graphics.TileRenderer;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CircuitTest {

    private Circuit circuit;
    private final int WIDTH = 20;
    private final int HEIGHT = 20;

    private TileRenderer tileRenderer;
    private CircuitRenderer circuitRenderer;

    private class StubTileRenderer extends TileRenderer {
        @Override
        public void render(Object object, int row, int column) { }
    }

    private class StubCircuitRenderer implements CircuitRenderer {

        @Override
        public TileRenderer getNullTileRenderer() {
            return null;
        }

        @Override
        public void render(Circuit object, int row, int column) { }
    }

    public CircuitTest() {
        this.tileRenderer = new StubTileRenderer();
        this.circuitRenderer = new StubCircuitRenderer();
    }

    private void addWires(Position position, Side direction, int howMany) {
        int p_x = position.getX();
        int p_y = position.getY();
        switch (direction) {
            case UP:
                for (int y = p_y, i = 0; i < howMany && y >= 0; i++, y--) {
                    Position new_pos = new Position(p_x, y);
                    circuit.addTile(new WireTile(new_pos, tileRenderer));
                }
                break;
            case DOWN:
                for (int y = p_y, i = 0; i < howMany && y < circuit.getHeight(); i++, y++) {
                    Position new_pos = new Position(p_x, y);
                    circuit.addTile(new WireTile(new_pos, tileRenderer));
                }
                break;
            case LEFT:
                for (int x = p_x, i = 0; i < howMany && x >= 0; i++, x--) {
                    Position new_pos = new Position(x, p_y);
                    circuit.addTile(new WireTile(new_pos, tileRenderer));
                }
                break;
            case RIGHT:
                for (int x = p_x, i = 0; i < howMany && x < circuit.getWidth(); i++, x++) {
                    Position new_pos = new Position(x, p_y);
                    circuit.addTile(new WireTile(new_pos, tileRenderer));
                }
                break;
        }
    }

    @Before
    public void create() {
        this.circuit = new Circuit(WIDTH, HEIGHT);
        Power.setRedstoneMode();
    }


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
        circuit.addTile(new WireTile(pos, tileRenderer));
        assertEquals(WireTile.class, circuit.getTile(pos).getClass());
    }

    @Test
    public void removeTileTest() {
        Position pos = new Position(4, 7);
        assertEquals(NullTile.class, circuit.getTile(pos).getClass());
        circuit.addTile(new WireTile(pos, tileRenderer));
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
        circuit.addTile(new ConstantSourceTile(new Position(2, 6), tileRenderer));

        circuit.advanceTick();

        assertEquals("Power : 15", circuit.getTile(2, 6).getInfo());
    }

    @Test
    public void testWirePropagation() {
        circuit.addTile(new WireTile(new Position(4, 4), tileRenderer));
        circuit.addTile(new WireTile(new Position(3, 4), tileRenderer));
        circuit.addTile(new WireTile(new Position(2, 4), tileRenderer));
        circuit.addTile(new WireTile(new Position(2, 5), tileRenderer));
        circuit.addTile(new ConstantSourceTile(new Position(2, 6), tileRenderer));

        circuit.advanceTick();

        assertEquals("Power : 15", circuit.getTile(2, 5).getInfo());
        assertEquals("Power : 14", circuit.getTile(2, 4).getInfo());
        assertEquals("Power : 13", circuit.getTile(3, 4).getInfo());
        assertEquals("Power : 12", circuit.getTile(4, 4).getInfo());
    }

    @Test
    public void testWirePropagationDoubleEnded() {
        circuit.addTile(new WireTile(new Position(4, 4), tileRenderer));
        circuit.addTile(new WireTile(new Position(3, 4), tileRenderer));
        circuit.addTile(new WireTile(new Position(2, 4), tileRenderer));
        circuit.addTile(new WireTile(new Position(2, 5), tileRenderer));
        circuit.addTile(new ConstantSourceTile(new Position(2, 6), tileRenderer));
        circuit.addTile(new ConstantSourceTile(new Position(5, 4), tileRenderer));

        circuit.advanceTick();

        assertEquals("Power : 15", circuit.getTile(2, 5).getInfo());
        assertEquals("Power : 14", circuit.getTile(2, 4).getInfo());
        assertEquals("Power : 14", circuit.getTile(3, 4).getInfo());
        assertEquals("Power : 15", circuit.getTile(4, 4).getInfo());
    }

    @Test
    public void testWirePropagationBlock() {
        circuit.addTile(new WireTile(new Position(0, 0), tileRenderer));
        circuit.addTile(new WireTile(new Position(0, 1), tileRenderer));
        circuit.addTile(new WireTile(new Position(0, 2), tileRenderer));
        circuit.addTile(new WireTile(new Position(0, 3), tileRenderer));
        circuit.addTile(new WireTile(new Position(1, 0), tileRenderer));
        circuit.addTile(new WireTile(new Position(1, 1), tileRenderer));
        circuit.addTile(new WireTile(new Position(1, 2), tileRenderer));
        circuit.addTile(new WireTile(new Position(1, 3), tileRenderer));
        circuit.addTile(new WireTile(new Position(0, 4), tileRenderer));
        circuit.addTile(new ConstantSourceTile(new Position(0, 5), tileRenderer));
        circuit.addTile(new WireTile(new Position(2, 3), tileRenderer));
        circuit.addTile(new ConstantSourceTile(new Position(3, 3), tileRenderer));

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
        circuit.addTile(new ConstantSourceTile(new Position(0, 0), tileRenderer));
        circuit.addTile(new WireTile(new Position(1, 0), tileRenderer));
        circuit.addTile(new WireTile(new Position(2, 0), tileRenderer));
        circuit.addTile(new WireTile(new Position(3, 0), tileRenderer));
        circuit.addTile(new WireTile(new Position(4, 0), tileRenderer));
        circuit.addTile(new WireTile(new Position(2, 1), tileRenderer));
        circuit.addTile(new WireTile(new Position(2, 2), tileRenderer));
        circuit.addTile(new WireTile(new Position(3, 2), tileRenderer));
        circuit.addTile(new WireTile(new Position(4, 2), tileRenderer));
        circuit.addTile(new WireTile(new Position(4, 1), tileRenderer));

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
        circuit.addTile(new ConstantSourceTile(new Position(19, 0), tileRenderer));
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
        assertEquals(true, repeater.acceptsPower(Side.LEFT));
        assertEquals(false, repeater.acceptsPower(Side.RIGHT));
        assertEquals(false, repeater.acceptsPower(Side.UP));
        assertEquals(false, repeater.acceptsPower(Side.DOWN));


        assertEquals(false, repeater.outputsPower(Side.LEFT));
        assertEquals(true, repeater.outputsPower(Side.RIGHT));
        assertEquals(false, repeater.outputsPower(Side.UP));
        assertEquals(false, repeater.outputsPower(Side.DOWN));

        repeater.rotateRight();

        assertEquals(false, repeater.acceptsPower(Side.LEFT));
        assertEquals(false, repeater.acceptsPower(Side.RIGHT));
        assertEquals(true, repeater.acceptsPower(Side.UP));
        assertEquals(false, repeater.acceptsPower(Side.DOWN));


        assertEquals(false, repeater.outputsPower(Side.LEFT));
        assertEquals(false, repeater.outputsPower(Side.RIGHT));
        assertEquals(false, repeater.outputsPower(Side.UP));
        assertEquals(true, repeater.outputsPower(Side.DOWN));

        repeater.rotateLeft(); repeater.rotateLeft();

        assertEquals(false, repeater.acceptsPower(Side.LEFT));
        assertEquals(false, repeater.acceptsPower(Side.RIGHT));
        assertEquals(false, repeater.acceptsPower(Side.UP));
        assertEquals(true, repeater.acceptsPower(Side.DOWN));


        assertEquals(false, repeater.outputsPower(Side.LEFT));
        assertEquals(false, repeater.outputsPower(Side.RIGHT));
        assertEquals(true, repeater.outputsPower(Side.UP));
        assertEquals(false, repeater.outputsPower(Side.DOWN));
    }

    @Test
    public void testRepeaterCircuit() {
        circuit.addTile(new ConstantSourceTile(new Position(0, 0), tileRenderer));
        circuit.addTile(new WireTile(new Position(1, 0), tileRenderer));
        circuit.addTile(new WireTile(new Position(2, 0), tileRenderer));
        circuit.addTile(new RepeaterTile(new Position(3, 0), tileRenderer)); // oriented from left to right
        circuit.addTile(new WireTile(new Position(4, 0), tileRenderer));
        circuit.addTile(new WireTile(new Position(5, 0), tileRenderer));
        circuit.addTile(new WireTile(new Position(5, 1), tileRenderer));
        circuit.addTile(new WireTile(new Position(5, 2), tileRenderer));
        circuit.addTile(new WireTile(new Position(4, 2), tileRenderer));
        circuit.addTile(new WireTile(new Position(3, 2), tileRenderer));
        RepeaterTile repeater2 = new RepeaterTile(new Position(2, 2), tileRenderer);
        repeater2.rotateLeft(); repeater2.rotateLeft(); // oriented from right to left
        circuit.addTile(repeater2);
        circuit.addTile(new WireTile(new Position(1, 2), tileRenderer));
        circuit.addTile(new WireTile(new Position(0, 2), tileRenderer));
        RepeaterTile repeater3 = new RepeaterTile(new Position(0, 3), tileRenderer);
        repeater3.rotateRight(); // oriented from up to down
        circuit.addTile(repeater3);
        addWires(new Position(0, 4), Side.RIGHT, 8);
        circuit.addTile(new WireTile(new Position(0, 5), tileRenderer));
        addWires(new Position(0, 6), Side.RIGHT, 14);
        RepeaterTile repeater4 = new RepeaterTile(new Position(14, 6), tileRenderer); // oriented left to right
        circuit.addTile(repeater4);
        circuit.addTile(new WireTile(new Position(15, 6), tileRenderer));
        circuit.addTile(new WireTile(new Position(7, 3), tileRenderer));
        RepeaterTile repeater5 = new RepeaterTile(new Position(7, 2), tileRenderer);
        repeater5.rotateLeft(); // oriented from down to up
        circuit.addTile(repeater5);
        circuit.addTile(new WireTile(new Position(7, 1), tileRenderer));
        circuit.addTile(new WireTile(new Position(7, 0), tileRenderer));

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

    @Test
    public void testLever() {

        circuit.addTile(new LeverTile(new Position(0, 0), tileRenderer));
        circuit.addTile(new WireTile(new Position(1, 0), tileRenderer));
        circuit.addTile(new WireTile(new Position(2, 0), tileRenderer));

        circuit.advanceTick();

        assertEquals("Power : 0", circuit.getTile(1, 0).getInfo());
        assertEquals("Power : 0", circuit.getTile(2, 0).getInfo());

        ((LeverTile) circuit.getTile(0, 0)).toggle();
        circuit.advanceTick();

        assertEquals("Power : 15", circuit.getTile(1, 0).getInfo());
        assertEquals("Power : 14", circuit.getTile(2, 0).getInfo());

        ((LeverTile) circuit.getTile(0, 0)).toggle();
        circuit.advanceTick();

        assertEquals("Power : 0", circuit.getTile(1, 0).getInfo());
        assertEquals("Power : 0", circuit.getTile(2, 0).getInfo());
    }

    @Test
    public void testDoubleSources() {
        circuit.addTile(new LeverTile(new Position(0, 0), tileRenderer));
        circuit.addTile(new WireTile(new Position(1, 0), tileRenderer));
        circuit.addTile(new WireTile(new Position(2, 0), tileRenderer));
        circuit.addTile(new WireTile(new Position(3, 0), tileRenderer));
        circuit.addTile(new WireTile(new Position(4, 0), tileRenderer));
        circuit.addTile(new WireTile(new Position(5, 0), tileRenderer));
        circuit.addTile(new ConstantSourceTile(new Position(6, 0), tileRenderer));

        circuit.advanceTick();

        assertEquals("Power : 11", circuit.getTile(1, 0).getInfo());
        assertEquals("Power : 12", circuit.getTile(2, 0).getInfo());
        assertEquals("Power : 13", circuit.getTile(3, 0).getInfo());
        assertEquals("Power : 14", circuit.getTile(4, 0).getInfo());
        assertEquals("Power : 15", circuit.getTile(5, 0).getInfo());

        ((LeverTile) circuit.getTile(0, 0)).toggle();
        circuit.advanceTick();

        assertEquals("Power : 15", circuit.getTile(1, 0).getInfo());
        assertEquals("Power : 14", circuit.getTile(2, 0).getInfo());
        assertEquals("Power : 13", circuit.getTile(3, 0).getInfo());
        assertEquals("Power : 14", circuit.getTile(4, 0).getInfo());
        assertEquals("Power : 15", circuit.getTile(5, 0).getInfo());

        ((LeverTile) circuit.getTile(0, 0)).toggle();
        circuit.advanceTick();

        assertEquals("Power : 11", circuit.getTile(1, 0).getInfo());
        assertEquals("Power : 12", circuit.getTile(2, 0).getInfo());
        assertEquals("Power : 13", circuit.getTile(3, 0).getInfo());
        assertEquals("Power : 14", circuit.getTile(4, 0).getInfo());
        assertEquals("Power : 15", circuit.getTile(5, 0).getInfo());

    }

}
