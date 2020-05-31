package com.lpoo.redstonetools.core;

import com.lpoo.redstonetools.controller.circuit.CircuitController;
import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.model.tile.*;
import com.lpoo.redstonetools.model.tile.strategy.XORGateStrategy;
import com.lpoo.redstonetools.model.utils.Position;
import com.lpoo.redstonetools.model.utils.Power;
import com.lpoo.redstonetools.model.utils.Side;
import com.lpoo.redstonetools.model.utils.TileType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class CircuitTest {

    private Circuit circuit;
    private CircuitController controller;
    private final int WIDTH = 20;
    private final int HEIGHT = 20;
    
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

    @BeforeEach
    public void create() {
        this.circuit = new Circuit(WIDTH, HEIGHT);
        this.controller = new CircuitController();
    }


    @Test
    @Tag("model") @Tag("controller")
    @Tag("system-test") @Tag("fast")
    public void getTileTest() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                Assertions.assertEquals(NullTile.class,  circuit.getTile(j, i).getClass());
            }
        }
    }

    @Test
    @Tag("model") @Tag("controller")
    @Tag("system-test") @Tag("fast")
    public void addTileTest() {
        Position pos = new Position(4, 7);
        Assertions.assertEquals(NullTile.class, circuit.getTile(pos).getClass());
        circuit.addTile(new WireTile(pos));
        Assertions.assertEquals(WireTile.class, circuit.getTile(pos).getClass());
    }

    @Test
    @Tag("model") @Tag("controller")
    @Tag("system-test") @Tag("fast")
    public void removeTileTest() {
        Position pos = new Position(4, 7);
        Assertions.assertEquals(NullTile.class, circuit.getTile(pos).getClass());
        circuit.addTile(new WireTile(pos));
        Assertions.assertEquals(WireTile.class, circuit.getTile(pos).getClass());
        circuit.removeTile(pos);
        Assertions.assertEquals(NullTile.class, circuit.getTile(pos).getClass());
    }

    @Test
    @Tag("model") @Tag("controller")
    @Tag("system-test") @Tag("fast")
    public void testBounds() {
        Assertions.assertEquals(NullTile.class, circuit.getTile(-1, 5).getClass());
        Assertions.assertEquals(NullTile.class, circuit.getTile(2, 200).getClass());
        Assertions.assertEquals(NullTile.class, circuit.getTile(-6, 2).getClass());
    }

    @Test
    @Tag("model") @Tag("controller")
    @Tag("system-test") @Tag("fast")
    public void testConstantSourceOutput() {
        controller.addTile(circuit, new ConstantSourceTile(new Position(2, 6)));

        Assertions.assertEquals("Power : 15", circuit.getTile(2, 6).getInfo());
    }

    @Test
    @Tag("model") @Tag("controller")
    @Tag("system-test") @Tag("fast")
    public void testWirePropagation() {
        controller.addTile(circuit, new WireTile(new Position(4, 4)));
        controller.addTile(circuit, new WireTile(new Position(3, 4)));
        controller.addTile(circuit, new WireTile(new Position(2, 4)));
        controller.addTile(circuit, new WireTile(new Position(2, 5)));
        controller.addTile(circuit, new ConstantSourceTile(new Position(2, 6)));

        Assertions.assertEquals("Power : 15", circuit.getTile(2, 5).getInfo());
        Assertions.assertEquals("Power : 14", circuit.getTile(2, 4).getInfo());
        Assertions.assertEquals("Power : 13", circuit.getTile(3, 4).getInfo());
        Assertions.assertEquals("Power : 12", circuit.getTile(4, 4).getInfo());
    }

    @Test
    @Tag("model") @Tag("controller")
    @Tag("system-test") @Tag("fast")
    public void testWirePropagationDoubleEnded() {
        controller.addTile(circuit, new WireTile(new Position(4, 4)));
        controller.addTile(circuit, new WireTile(new Position(3, 4)));
        controller.addTile(circuit, new WireTile(new Position(2, 4)));
        controller.addTile(circuit, new WireTile(new Position(2, 5)));
        controller.addTile(circuit, new ConstantSourceTile(new Position(2, 6)));
        controller.addTile(circuit, new ConstantSourceTile(new Position(5, 4)));

        Assertions.assertEquals("Power : 15", circuit.getTile(2, 5).getInfo());
        Assertions.assertEquals("Power : 14", circuit.getTile(2, 4).getInfo());
        Assertions.assertEquals("Power : 14", circuit.getTile(3, 4).getInfo());
        Assertions.assertEquals("Power : 15", circuit.getTile(4, 4).getInfo());
    }

    @Test
    @Tag("model") @Tag("controller")
    @Tag("system-test") @Tag("fast")
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

        Assertions.assertEquals("Power : 11", circuit.getTile(0, 0).getInfo());
        Assertions.assertEquals("Power : 12", circuit.getTile(0, 1).getInfo());
        Assertions.assertEquals("Power : 13", circuit.getTile(0, 2).getInfo());
        Assertions.assertEquals("Power : 14", circuit.getTile(0, 3).getInfo());
        Assertions.assertEquals("Power : 15", circuit.getTile(0, 4).getInfo());
        Assertions.assertEquals("Power : 11", circuit.getTile(1, 0).getInfo());
        Assertions.assertEquals("Power : 12", circuit.getTile(1, 1).getInfo());
        Assertions.assertEquals("Power : 13", circuit.getTile(1, 2).getInfo());
        Assertions.assertEquals("Power : 14", circuit.getTile(1, 3).getInfo());
        Assertions.assertEquals("Power : 15", circuit.getTile(2, 3).getInfo());
    }

    @Test
    @Tag("model") @Tag("controller")
    @Tag("system-test") @Tag("fast")
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

        Assertions.assertEquals("Power : 15", circuit.getTile(1, 0).getInfo());
        Assertions.assertEquals("Power : 14", circuit.getTile(2, 0).getInfo());
        Assertions.assertEquals("Power : 13", circuit.getTile(3, 0).getInfo());
        Assertions.assertEquals("Power : 12", circuit.getTile(4, 0).getInfo());
        Assertions.assertEquals("Power : 13", circuit.getTile(2, 1).getInfo());
        Assertions.assertEquals("Power : 12", circuit.getTile(2, 2).getInfo());
        Assertions.assertEquals("Power : 11", circuit.getTile(3, 2).getInfo());
        Assertions.assertEquals("Power : 10", circuit.getTile(4, 2).getInfo());
        Assertions.assertEquals("Power : 11", circuit.getTile(4, 1).getInfo());
    }

    @Test
    @Tag("model") @Tag("controller")
    @Tag("system-test") @Tag("fast")
    public void testWirePropagationDissipation() {
        controller.addTile(circuit, new ConstantSourceTile(new Position(19, 0)));
        addWires(new Position(1, 0), Side.RIGHT, 18);

        Assertions.assertEquals("Power : 0", circuit.getTile(1, 0).getInfo());
        Assertions.assertEquals("Power : 0", circuit.getTile(2, 0).getInfo());
        Assertions.assertEquals("Power : 0", circuit.getTile(3, 0).getInfo());
        Assertions.assertEquals("Power : 1", circuit.getTile(4, 0).getInfo());
        Assertions.assertEquals("Power : 2", circuit.getTile(5, 0).getInfo());
        Assertions.assertEquals("Power : 3", circuit.getTile(6, 0).getInfo());
    }

    @Test
    @Tag("model") @Tag("controller")
    @Tag("system-test") @Tag("fast")
    public void testOrientedTileRotation() {
        RepeaterTile repeater = new RepeaterTile(new Position(0, 0));
        Assertions.assertEquals(true, repeater.acceptsPower(Side.LEFT));
        Assertions.assertEquals(false, repeater.acceptsPower(Side.RIGHT));
        Assertions.assertEquals(false, repeater.acceptsPower(Side.UP));
        Assertions.assertEquals(false, repeater.acceptsPower(Side.DOWN));


        Assertions.assertEquals(false, repeater.outputsPower(Side.LEFT));
        Assertions.assertEquals(true, repeater.outputsPower(Side.RIGHT));
        Assertions.assertEquals(false, repeater.outputsPower(Side.UP));
        Assertions.assertEquals(false, repeater.outputsPower(Side.DOWN));

        repeater.rotateRight(circuit);

        Assertions.assertEquals(false, repeater.acceptsPower(Side.LEFT));
        Assertions.assertEquals(false, repeater.acceptsPower(Side.RIGHT));
        Assertions.assertEquals(true, repeater.acceptsPower(Side.UP));
        Assertions.assertEquals(false, repeater.acceptsPower(Side.DOWN));


        Assertions.assertEquals(false, repeater.outputsPower(Side.LEFT));
        Assertions.assertEquals(false, repeater.outputsPower(Side.RIGHT));
        Assertions.assertEquals(false, repeater.outputsPower(Side.UP));
        Assertions.assertEquals(true, repeater.outputsPower(Side.DOWN));

        repeater.rotateLeft(circuit); repeater.rotateLeft(circuit);

        Assertions.assertEquals(false, repeater.acceptsPower(Side.LEFT));
        Assertions.assertEquals(false, repeater.acceptsPower(Side.RIGHT));
        Assertions.assertEquals(false, repeater.acceptsPower(Side.UP));
        Assertions.assertEquals(true, repeater.acceptsPower(Side.DOWN));


        Assertions.assertEquals(false, repeater.outputsPower(Side.LEFT));
        Assertions.assertEquals(false, repeater.outputsPower(Side.RIGHT));
        Assertions.assertEquals(true, repeater.outputsPower(Side.UP));
        Assertions.assertEquals(false, repeater.outputsPower(Side.DOWN));
    }

    @Test
    @Tag("model") @Tag("controller")
    @Tag("system-test") @Tag("fast")
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

        Assertions.assertEquals("Power : 14", circuit.getTile(2, 0).getInfo());
        Assertions.assertEquals("Active : true", circuit.getTile(3, 0).getInfo());
        Assertions.assertEquals("Power : 15", circuit.getTile(4, 0).getInfo());
        Assertions.assertEquals("Power : 10", circuit.getTile(3, 2).getInfo());
        Assertions.assertEquals("Active : true", circuit.getTile(2, 2).getInfo());
        Assertions.assertEquals("Power : 15", circuit.getTile(1, 2).getInfo());
        Assertions.assertEquals("Power : 14", circuit.getTile(0, 2).getInfo());
        Assertions.assertEquals("Active : true", circuit.getTile(0, 3).getInfo());
        Assertions.assertEquals("Power : 15", circuit.getTile(0, 4).getInfo());
        Assertions.assertEquals("Power : 7", circuit.getTile(7, 3).getInfo());
        Assertions.assertEquals("Active : true", circuit.getTile(7, 2).getInfo());
        Assertions.assertEquals("Power : 15", circuit.getTile(7, 1).getInfo());

        Assertions.assertEquals("Power : 14", circuit.getTile(0, 5).getInfo());
        Assertions.assertEquals("Power : 1", circuit.getTile(12, 6).getInfo());
        Assertions.assertEquals("Power : 0", circuit.getTile(13, 6).getInfo());
        Assertions.assertEquals("Active : false", circuit.getTile(14, 6).getInfo());
        Assertions.assertEquals("Power : 0", circuit.getTile(15, 6).getInfo());
    }

    @Test
    @Tag("model") @Tag("controller")
    @Tag("system-test") @Tag("fast")
    public void testLever() {

        controller.addTile(circuit, new LeverTile(new Position(0, 0)));
        controller.addTile(circuit, new WireTile(new Position(1, 0)));
        controller.addTile(circuit, new WireTile(new Position(2, 0)));

        Assertions.assertEquals("Power : 0", circuit.getTile(1, 0).getInfo());
        Assertions.assertEquals("Power : 0", circuit.getTile(2, 0).getInfo());

        controller.interact(circuit, new Position(0, 0));

        Assertions.assertEquals("Power : 15", circuit.getTile(1, 0).getInfo());
        Assertions.assertEquals("Power : 14", circuit.getTile(2, 0).getInfo());

        controller.interact(circuit, new Position(0, 0));

        Assertions.assertEquals("Power : 0", circuit.getTile(1, 0).getInfo());
        Assertions.assertEquals("Power : 0", circuit.getTile(2, 0).getInfo());
    }

    @Test
    @Tag("model") @Tag("controller")
    @Tag("system-test") @Tag("fast")
    public void testDoubleSources() {
        controller.addTile(circuit, new LeverTile(new Position(0, 0)));
        controller.addTile(circuit, new WireTile(new Position(1, 0)));
        controller.addTile(circuit, new WireTile(new Position(2, 0)));
        controller.addTile(circuit, new WireTile(new Position(3, 0)));
        controller.addTile(circuit, new WireTile(new Position(4, 0)));
        controller.addTile(circuit, new WireTile(new Position(5, 0)));
        controller.addTile(circuit, new ConstantSourceTile(new Position(6, 0)));

        Assertions.assertEquals("Power : 11", circuit.getTile(1, 0).getInfo());
        Assertions.assertEquals("Power : 12", circuit.getTile(2, 0).getInfo());
        Assertions.assertEquals("Power : 13", circuit.getTile(3, 0).getInfo());
        Assertions.assertEquals("Power : 14", circuit.getTile(4, 0).getInfo());
        Assertions.assertEquals("Power : 15", circuit.getTile(5, 0).getInfo());

        controller.interact(circuit, new Position(0, 0));

        Assertions.assertEquals("Power : 15", circuit.getTile(1, 0).getInfo());
        Assertions.assertEquals("Power : 14", circuit.getTile(2, 0).getInfo());
        Assertions.assertEquals("Power : 13", circuit.getTile(3, 0).getInfo());
        Assertions.assertEquals("Power : 14", circuit.getTile(4, 0).getInfo());
        Assertions.assertEquals("Power : 15", circuit.getTile(5, 0).getInfo());

        controller.interact(circuit, new Position(0, 0));

        Assertions.assertEquals("Power : 11", circuit.getTile(1, 0).getInfo());
        Assertions.assertEquals("Power : 12", circuit.getTile(2, 0).getInfo());
        Assertions.assertEquals("Power : 13", circuit.getTile(3, 0).getInfo());
        Assertions.assertEquals("Power : 14", circuit.getTile(4, 0).getInfo());
        Assertions.assertEquals("Power : 15", circuit.getTile(5, 0).getInfo());

    }

    @Test
    @Tag("model") @Tag("controller")
    @Tag("system-test") @Tag("fast")
    public void testUnstableNotGate() {
        controller.addTile(circuit, new NotGateTile(new Position(1, 0)));
        controller.addTile(circuit, new WireTile(new Position(0, 0)));
        controller.addTile(circuit, new WireTile(new Position(2, 0)));
        controller.addTile(circuit, new WireTile(new Position(0, 1)));
        controller.addTile(circuit, new WireTile(new Position(1, 1)));
        controller.addTile(circuit, new WireTile(new Position(2, 1)));

        Assertions.assertEquals(TileType.NULL, circuit.getTile(1, 0).getType());
        Assertions.assertTrue(((NullTile) circuit.getTile(1, 0)).isBroken());
    }

    @Test
    @Tag("model") @Tag("controller")
    @Tag("system-test") @Tag("fast")
    public void testUnstableXor() {
        controller.addTile(circuit, new WireTile(new Position(1, 0)));
        controller.addTile(circuit, new WireTile(new Position(2, 0)));
        controller.addTile(circuit, new WireTile(new Position(2, 1)));
        controller.addTile(circuit, new LogicGateTile(new Position(1, 1), new XORGateStrategy()));

        Assertions.assertEquals(TileType.LOGIC_GATE, circuit.getTile(1, 1).getType());

        controller.addTile(circuit, new ConstantSourceTile(new Position(0, 1)));

        Assertions.assertEquals(TileType.NULL, circuit.getTile(1, 1).getType());
        Assertions.assertTrue(((NullTile) circuit.getTile(1, 1)).isBroken());
    }

    @Test
    @Tag("model") @Tag("controller")
    @Tag("system-test") @Tag("fast")
    public void testSyncedTimerXor() {
        controller.addTile(circuit, new TimerTile(new Position(0, 0)));
        controller.setDelay(circuit, new Position(0, 0), 3);
        controller.addTile(circuit, new TimerTile(new Position(0, 2)));
        controller.setDelay(circuit, new Position(0, 2), 3);

        controller.addTile(circuit, new WireTile(new Position(1, 0)));
        controller.addTile(circuit, new LogicGateTile(new Position(1, 1), new XORGateStrategy()));
        controller.rotateTileRight(circuit, new Position(1, 1));
        controller.addTile(circuit, new WireTile(new Position(1, 2)));

        controller.addTile(circuit, new WireTile(new Position(2, 1)));
        controller.addTile(circuit, new WireTile(new Position(3, 1)));
        controller.addTile(circuit, new RepeaterTile(new Position(4, 1)));
        controller.addTile(circuit, new WireTile(new Position(5, 1)));
        controller.addTile(circuit, new WireTile(new Position(5, 0)));
        controller.addTile(circuit, new WireTile(new Position(4, 0)));
        controller.addTile(circuit, new WireTile(new Position(3, 0)));

        Assertions.assertEquals(TileType.WIRE, circuit.getTile(2, 1).getType());

        Assertions.assertFalse(Power.isOn(circuit.getTile(2, 1).getPower(Side.UP)));

        controller.advanceTick(circuit);
        Assertions.assertFalse(Power.isOn(circuit.getTile(2, 1).getPower(Side.UP)));

        controller.advanceTick(circuit);
        Assertions.assertFalse(Power.isOn(circuit.getTile(2, 1).getPower(Side.UP)));

        controller.advanceTick(circuit);
        Assertions.assertTrue(Power.isOn(circuit.getTile(2, 1).getPower(Side.UP)));
    }

    @Test
    @Tag("model") @Tag("controller")
    @Tag("system-test") @Tag("fast")
    public void testCustom () {
        Circuit interior = new Circuit(3, 1);
        controller.addTile(interior, new NotGateTile(new Position(1, 0)));
        controller.addTile(interior, new IOTile(new Position(0, 0)));
        controller.rotateTileLeft(interior, new Position(0, 0));
        controller.interact(interior, new Position(0, 0));
        controller.interact(interior, new Position(0, 0));
        controller.addTile(interior, new IOTile(new Position(2, 0)));
        controller.rotateTileRight(interior, new Position(2, 0));
        controller.interact(interior, new Position(2, 0));


        interior.setPosition(new Position(1, 0));
        controller.addTile(circuit, interior);
        Assertions.assertEquals(TileType.CIRCUIT, circuit.getTile(1, 0).getType());

        controller.addTile(circuit, new WireTile(new Position(0, 0)));
        controller.addTile(circuit, new WireTile(new Position(2, 0)));

        Assertions.assertTrue(Power.isOn(circuit.getTile(2, 0).getPower(Side.UP)));

        controller.addTile(circuit, new WireTile(new Position(2, 1)));
        controller.addTile(circuit, new WireTile(new Position(1, 1)));
        controller.addTile(circuit, new WireTile(new Position(0, 1)));

        Assertions.assertFalse(Power.isOn(circuit.getTile(2, 0).getPower(Side.UP)));
        Assertions.assertEquals(TileType.NULL, circuit.getTile(1, 0).getType());
        Assertions.assertTrue(((NullTile) circuit.getTile(1, 0)).isBroken());
    }

}
