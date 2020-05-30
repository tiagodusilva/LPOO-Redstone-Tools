package com.lpoo.redstonetools.model.tile;

import com.lpoo.redstonetools.model.utils.Position;
import com.lpoo.redstonetools.model.utils.Power;
import com.lpoo.redstonetools.model.utils.Side;
import com.lpoo.redstonetools.model.utils.TileType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class NullTileTest {

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testNullTile() {
        Position position = Mockito.mock(Position.class);
        Mockito.when(position.getX()).thenReturn(1);
        Mockito.when(position.getY()).thenReturn(2);

        NullTile nullTile = new NullTile(position);

        Assertions.assertEquals(1, nullTile.getPosition().getX());
        Assertions.assertEquals(2, nullTile.getPosition().getY());
        Assertions.assertEquals(TileType.NULL, nullTile.getType());
        Assertions.assertFalse(nullTile.isWire());
        Assertions.assertFalse(nullTile.isTickedTile());

        for (Side side : Side.values()) {
            Assertions.assertFalse(nullTile.acceptsPower(side));
            Assertions.assertFalse(nullTile.outputsPower(side));
            Assertions.assertEquals(Power.getMin(), nullTile.getPower(side));
        }
    }

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testBrokenTile() {
        Position position = Mockito.mock(Position.class);
        Mockito.when(position.getX()).thenReturn(1);
        Mockito.when(position.getY()).thenReturn(2);

        NullTile brokenTile = new NullTile(position, true);

        Assertions.assertEquals(1, brokenTile.getPosition().getX());
        Assertions.assertEquals(2, brokenTile.getPosition().getY());
        Assertions.assertEquals(TileType.NULL, brokenTile.getType());
        Assertions.assertTrue(brokenTile.isBroken());
    }
}
