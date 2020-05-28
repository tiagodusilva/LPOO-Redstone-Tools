package com.lpoo.redstonetools.model.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("model")
public class PositionTest {

    @Test
    @Tag("unit-test") @Tag("fast")
    public void testPositionConstructor() {
        Position position = new Position(5, 10);
        Assertions.assertEquals(5, position.getX());
        Assertions.assertEquals(10, position.getY());
    }

    @Test
    @Tag("unit-test") @Tag("fast")
    public void testPositionGetNeighbour() {
        Position middle = new Position(1, 1);
        Position left = new Position(0, 1);
        Position right = new Position(2, 1);
        Position up = new Position(1, 0);
        Position down = new Position(1, 2);

        Assertions.assertEquals(left, middle.getNeighbour(Side.LEFT));
        Assertions.assertEquals(right, middle.getNeighbour(Side.RIGHT));
        Assertions.assertEquals(up, middle.getNeighbour(Side.UP));
        Assertions.assertEquals(down, middle.getNeighbour(Side.DOWN));
    }

    @Test
    @Tag("unit-test") @Tag("fast")
    public void testPositionEquals() {
        Position position1 = new Position(5, 6);
        Position position2 = new Position(5, 6);
        Position position3 = new Position(5, 4);

        Assertions.assertEquals(position1, position2);
        Assertions.assertNotEquals(position1, position3);
        Assertions.assertNotEquals(position2, position3);
    }

    @Test
    @Tag("unit-test") @Tag("fast")
    public void testPositionClone() {
        Position position = new Position(5, 4);
        Position clone = position.clone();

        Assertions.assertEquals(position, clone);
        Assertions.assertNotSame(position, clone);
    }
}
