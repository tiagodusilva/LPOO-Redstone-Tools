package com.lpoo.redstonetools.core;

import com.lpoo.redstonetools.core.Circuit;
import com.lpoo.redstonetools.core.tiles.ConstantSourceTile;
import com.lpoo.redstonetools.core.tiles.WireTile;
import com.lpoo.redstonetools.core.tiles.NullTile;
import com.lpoo.redstonetools.core.utils.Position;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CircuitTest {

    private Circuit circuit;
    private final int WIDTH = 20;
    private final int HEIGHT = 20;

    @Before
    public void create() {
        this.circuit = new Circuit(WIDTH, HEIGHT);
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

}
