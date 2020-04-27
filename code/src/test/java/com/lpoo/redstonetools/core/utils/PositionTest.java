package com.lpoo.redstonetools.core.utils;

import com.lpoo.redstonetools.model.utils.Position;
import com.lpoo.redstonetools.model.utils.Side;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class PositionTest {
    @Test
    public void testNeighbour() {
        Position middle = new Position(1, 1);
        Position left = new Position(0, 1);
        Position right = new Position(2, 1);
        Position up = new Position(1, 0);
        Position down = new Position(1, 2);

        assertEquals(left, middle.getNeighbour(Side.LEFT));
        assertEquals(right, middle.getNeighbour(Side.RIGHT));
        assertEquals(up, middle.getNeighbour(Side.UP));
        assertEquals(down, middle.getNeighbour(Side.DOWN));
    }
}
