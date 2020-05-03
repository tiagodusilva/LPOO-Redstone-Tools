package com.lpoo.redstonetools.model.utils;

import org.junit.Assert;
import org.junit.Test;

public class PositionTest {

    @Test
    public void testPositionConstructor() {
        Position position = new Position(5, 10);
        Assert.assertEquals("Position(5, 10)", position.toString());
    }

    @Test
    public void testPositionGetters() {
        Position position = new Position(5, 10);
        Assert.assertEquals(5, position.getX());
        Assert.assertEquals(10, position.getY());
    }

    @Test
    public void testPositionSetters() {
        Position position = new Position(5, 10);
        Assert.assertEquals(5, position.getX());
        Assert.assertEquals(10, position.getY());
        position.setX(-5);
        position.setY(32);
        Assert.assertEquals(-5, position.getX());
        Assert.assertEquals(32, position.getY());
    }

    @Test
    public void testPositionGetNeighbour() {
        Position middle = new Position(1, 1);
        Position left = new Position(0, 1);
        Position right = new Position(2, 1);
        Position up = new Position(1, 0);
        Position down = new Position(1, 2);

        Assert.assertEquals(left, middle.getNeighbour(Side.LEFT));
        Assert.assertEquals(right, middle.getNeighbour(Side.RIGHT));
        Assert.assertEquals(up, middle.getNeighbour(Side.UP));
        Assert.assertEquals(down, middle.getNeighbour(Side.DOWN));
    }

    @Test
    public void testPositionEquals() {
        Position position1 = new Position(5, 5);
        Position position2 = new Position(5, 5);
        Position position3 = new Position(5, 4);

        Assert.assertEquals(position1, position2);
        Assert.assertNotEquals(position1, position3);
        Assert.assertNotEquals(position2, position3);
    }

    @Test
    public void testPositionClone() {
        Position position = new Position(5, 4);
        Position clone = position.clone();

        Assert.assertEquals(position, clone);
        Assert.assertNotSame(position, clone);
    }
}
