package com.lpoo.redstonetools.core;

import com.lpoo.redstonetools.controller.circuit.CircuitController;
import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.model.tile.*;
import com.lpoo.redstonetools.model.utils.Position;
import com.lpoo.redstonetools.model.utils.Power;
import com.lpoo.redstonetools.model.utils.Side;

public class CircuitTest {

    private Circuit circuit;
    private CircuitController controller;
    private final int WIDTH = 20;
    private final int HEIGHT = 20;

    /*
    private void addWires(Position position, Side direction, int howMany) {
        int p_x = position.getX();
        int p_y = position.getY();
        switch (direction) {
            case UP:
                for (int y = p_y, i = 0; i < howMany && y >= 0; i++, y--) {
                    Position new_pos = new Position(p_x, y);
                    controller.addTile(circuit, new WireTile(new_pos));
                }
                break;
            case DOWN:
                for (int y = p_y, i = 0; i < howMany && y < circuit.getHeight(); i++, y++) {
                    Position new_pos = new Position(p_x, y);
                    controller.addTile(circuit, new WireTile(new_pos));
                }
                break;
            case LEFT:
                for (int x = p_x, i = 0; i < howMany && x >= 0; i++, x--) {
                    Position new_pos = new Position(x, p_y);
                    controller.addTile(circuit, new WireTile(new_pos));
                }
                break;
            case RIGHT:
                for (int x = p_x, i = 0; i < howMany && x < circuit.getWidth(); i++, x++) {
                    Position new_pos = new Position(x, p_y);
                    controller.addTile(circuit, new WireTile(new_pos));
                }
                break;
        }
    }

    @Before
    public void create() {
        this.circuit = new Circuit(WIDTH, HEIGHT);
        this.controller = new CircuitController();
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
        controller.addTile(circuit, new ConstantSourceTile(new Position(2, 6)));

        controller.advanceTick(circuit);

        assertEquals("Power : 15", circuit.getTile(2, 6).getInfo());
    }

    @Test
    public void testWirePropagation() {
        controller.addTile(circuit, new WireTile(new Position(4, 4)));
        controller.addTile(circuit, new WireTile(new Position(3, 4)));
        controller.addTile(circuit, new WireTile(new Position(2, 4)));
        controller.addTile(circuit, new WireTile(new Position(2, 5)));
        controller.addTile(circuit, new ConstantSourceTile(new Position(2, 6)));

        controller.advanceTick(circuit);

        assertEquals("Power : 15", circuit.getTile(2, 5).getInfo());
        assertEquals("Power : 14", circuit.getTile(2, 4).getInfo());
        assertEquals("Power : 13", circuit.getTile(3, 4).getInfo());
        assertEquals("Power : 12", circuit.getTile(4, 4).getInfo());
    }

    @Test
    public void testWirePropagationDoubleEnded() {
        controller.addTile(circuit, new WireTile(new Position(4, 4)));
        controller.addTile(circuit, new WireTile(new Position(3, 4)));
        controller.addTile(circuit, new WireTile(new Position(2, 4)));
        controller.addTile(circuit, new WireTile(new Position(2, 5)));
        controller.addTile(circuit, new ConstantSourceTile(new Position(2, 6)));
        controller.addTile(circuit, new ConstantSourceTile(new Position(5, 4)));

        controller.advanceTick(circuit);

        assertEquals("Power : 15", circuit.getTile(2, 5).getInfo());
        assertEquals("Power : 14", circuit.getTile(2, 4).getInfo());
        assertEquals("Power : 14", circuit.getTile(3, 4).getInfo());
        assertEquals("Power : 15", circuit.getTile(4, 4).getInfo());
    }

    @Test
    public void testWirePropagationBlock() {
        controller.addTile(circuit, new WireTile(new Position(0, 0)));
        controller.addTile(circuit, new WireTile(new Position(0, 1)));
        controller.addTile(circuit, new WireTile(new Position(0, 2)));
        controller.addTile(circuit, new WireTile(new Position(0, 3)));
        controller.addTile(circuit, new WireTile(new Position(1, 0)));
        controller.addTile(circuit, new WireTile(new Position(1, 1)));
        controller.addTile(circuit, new WireTile(new Position(1, 2)));
        controller.addTile(circuit, new WireTile(new Position(1, 3)));
        controller.addTile(circuit, new WireTile(new Position(0, 4)));
        controller.addTile(circuit, new ConstantSourceTile(new Position(0, 5)));
        controller.addTile(circuit, new WireTile(new Position(2, 3)));
        controller.addTile(circuit, new ConstantSourceTile(new Position(3, 3)));

        controller.advanceTick(circuit);

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
        controller.addTile(circuit, new ConstantSourceTile(new Position(0, 0)));
        controller.addTile(circuit, new WireTile(new Position(1, 0)));
        controller.addTile(circuit, new WireTile(new Position(2, 0)));
        controller.addTile(circuit, new WireTile(new Position(3, 0)));
        controller.addTile(circuit, new WireTile(new Position(4, 0)));
        controller.addTile(circuit, new WireTile(new Position(2, 1)));
        controller.addTile(circuit, new WireTile(new Position(2, 2)));
        controller.addTile(circuit, new WireTile(new Position(3, 2)));
        controller.addTile(circuit, new WireTile(new Position(4, 2)));
        controller.addTile(circuit, new WireTile(new Position(4, 1)));

        controller.advanceTick(circuit);

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
        controller.addTile(circuit, new ConstantSourceTile(new Position(19, 0)));
        addWires(new Position(1, 0), Side.RIGHT, 18);

        controller.advanceTick(circuit);

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

        repeater.rotateRight(circuit);

        assertEquals(false, repeater.acceptsPower(Side.LEFT));
        assertEquals(false, repeater.acceptsPower(Side.RIGHT));
        assertEquals(true, repeater.acceptsPower(Side.UP));
        assertEquals(false, repeater.acceptsPower(Side.DOWN));


        assertEquals(false, repeater.outputsPower(Side.LEFT));
        assertEquals(false, repeater.outputsPower(Side.RIGHT));
        assertEquals(false, repeater.outputsPower(Side.UP));
        assertEquals(true, repeater.outputsPower(Side.DOWN));

        repeater.rotateLeft(circuit); repeater.rotateLeft(circuit);

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
        controller.addTile(circuit, new ConstantSourceTile(new Position(0, 0)));
        controller.addTile(circuit, new WireTile(new Position(1, 0)));
        controller.addTile(circuit, new WireTile(new Position(2, 0)));
        controller.addTile(circuit, new RepeaterTile(new Position(3, 0))); // oriented from left to right
        controller.addTile(circuit, new WireTile(new Position(4, 0)));
        controller.addTile(circuit, new WireTile(new Position(5, 0)));
        controller.addTile(circuit, new WireTile(new Position(5, 1)));
        controller.addTile(circuit, new WireTile(new Position(5, 2)));
        controller.addTile(circuit, new WireTile(new Position(4, 2)));
        controller.addTile(circuit, new WireTile(new Position(3, 2)));
        RepeaterTile repeater2 = new RepeaterTile(new Position(2, 2));
        repeater2.rotateLeft(circuit); repeater2.rotateLeft(circuit); // oriented from right to left
        controller.addTile(circuit, repeater2);
        controller.addTile(circuit, new WireTile(new Position(1, 2)));
        controller.addTile(circuit, new WireTile(new Position(0, 2)));
        RepeaterTile repeater3 = new RepeaterTile(new Position(0, 3));
        repeater3.rotateRight(circuit); // oriented from up to down
        controller.addTile(circuit, repeater3);
        addWires(new Position(0, 4), Side.RIGHT, 8);
        controller.addTile(circuit, new WireTile(new Position(0, 5)));
        addWires(new Position(0, 6), Side.RIGHT, 14);
        RepeaterTile repeater4 = new RepeaterTile(new Position(14, 6)); // oriented left to right
        controller.addTile(circuit, repeater4);
        controller.addTile(circuit, new WireTile(new Position(15, 6)));
        controller.addTile(circuit, new WireTile(new Position(7, 3)));
        RepeaterTile repeater5 = new RepeaterTile(new Position(7, 2));
        repeater5.rotateLeft(circuit); // oriented from down to up
        controller.addTile(circuit, repeater5);
        controller.addTile(circuit, new WireTile(new Position(7, 1)));
        controller.addTile(circuit, new WireTile(new Position(7, 0)));

        controller.advanceTick(circuit);

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

        controller.addTile(circuit, new LeverTile(new Position(0, 0)));
        controller.addTile(circuit, new WireTile(new Position(1, 0)));
        controller.addTile(circuit, new WireTile(new Position(2, 0)));

        controller.advanceTick(circuit);

        assertEquals("Power : 0", circuit.getTile(1, 0).getInfo());
        assertEquals("Power : 0", circuit.getTile(2, 0).getInfo());

        controller.interact(circuit, new Position(0, 0));

        assertEquals("Power : 15", circuit.getTile(1, 0).getInfo());
        assertEquals("Power : 14", circuit.getTile(2, 0).getInfo());

        controller.interact(circuit, new Position(0, 0));

        assertEquals("Power : 0", circuit.getTile(1, 0).getInfo());
        assertEquals("Power : 0", circuit.getTile(2, 0).getInfo());
    }

    @Test
    public void testDoubleSources() {
        controller.addTile(circuit, new LeverTile(new Position(0, 0)));
        controller.addTile(circuit, new WireTile(new Position(1, 0)));
        controller.addTile(circuit, new WireTile(new Position(2, 0)));
        controller.addTile(circuit, new WireTile(new Position(3, 0)));
        controller.addTile(circuit, new WireTile(new Position(4, 0)));
        controller.addTile(circuit, new WireTile(new Position(5, 0)));
        controller.addTile(circuit, new ConstantSourceTile(new Position(6, 0)));

        controller.advanceTick(circuit);

        assertEquals("Power : 11", circuit.getTile(1, 0).getInfo());
        assertEquals("Power : 12", circuit.getTile(2, 0).getInfo());
        assertEquals("Power : 13", circuit.getTile(3, 0).getInfo());
        assertEquals("Power : 14", circuit.getTile(4, 0).getInfo());
        assertEquals("Power : 15", circuit.getTile(5, 0).getInfo());

        controller.interact(circuit, new Position(0, 0));

        assertEquals("Power : 15", circuit.getTile(1, 0).getInfo());
        assertEquals("Power : 14", circuit.getTile(2, 0).getInfo());
        assertEquals("Power : 13", circuit.getTile(3, 0).getInfo());
        assertEquals("Power : 14", circuit.getTile(4, 0).getInfo());
        assertEquals("Power : 15", circuit.getTile(5, 0).getInfo());

        controller.interact(circuit, new Position(0, 0));

        assertEquals("Power : 11", circuit.getTile(1, 0).getInfo());
        assertEquals("Power : 12", circuit.getTile(2, 0).getInfo());
        assertEquals("Power : 13", circuit.getTile(3, 0).getInfo());
        assertEquals("Power : 14", circuit.getTile(4, 0).getInfo());
        assertEquals("Power : 15", circuit.getTile(5, 0).getInfo());

    }
*/
}
