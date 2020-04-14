package com.lpoo.redstonetools;

import com.lpoo.redstonetools.core.Circuit;
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

}
