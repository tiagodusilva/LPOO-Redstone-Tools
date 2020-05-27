package com.lpoo.redstonetools.model.tile;

import com.lpoo.redstonetools.model.utils.Position;
import com.lpoo.redstonetools.model.utils.Power;
import com.lpoo.redstonetools.model.utils.Side;
import com.lpoo.redstonetools.model.utils.TileType;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class NullTileTest {
/*
    @Test
    public void testNullTile() {
        Position position = Mockito.mock(Position.class);
        Mockito.when(position.getX()).thenReturn(1);
        Mockito.when(position.getY()).thenReturn(2);

        Position expected = Mockito.mock(Position.class);
        Mockito.when(expected.getX()).thenReturn(1);
        Mockito.when(expected.getY()).thenReturn(2);

        NullTile nullTile = new NullTile(position);

        Assert.assertEquals(expected.getX(), nullTile.getPosition().getX());
        Assert.assertEquals(expected.getY(), nullTile.getPosition().getY());
        Assert.assertEquals("null", nullTile.getName());
        Assert.assertEquals("", nullTile.getInfo());
        Assert.assertEquals(TileType.NULL, nullTile.getType());

        for (Side side : Side.values()) {
            Assert.assertFalse(nullTile.acceptsPower(side));
            Assert.assertFalse(nullTile.outputsPower(side));
            Assert.assertEquals(Power.getMin(), nullTile.getPower(side));
        }
    }

 */
}
