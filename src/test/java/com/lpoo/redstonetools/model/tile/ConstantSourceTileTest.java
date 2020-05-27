package com.lpoo.redstonetools.model.tile;

import com.lpoo.redstonetools.model.utils.Position;
import com.lpoo.redstonetools.model.utils.Power;
import com.lpoo.redstonetools.model.utils.Side;
import com.lpoo.redstonetools.model.utils.TileType;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

public class ConstantSourceTileTest {

    private ConstantSourceTile source;

    private Position expectedSourcePosition;

    @BeforeEach
    public void setup() {
        Position position = Mockito.mock(Position.class);
        Mockito.when(position.getX()).thenReturn(1);
        Mockito.when(position.getY()).thenReturn(2);

        expectedSourcePosition = Mockito.mock(Position.class);
        Mockito.when(expectedSourcePosition.getX()).thenReturn(1);
        Mockito.when(expectedSourcePosition.getY()).thenReturn(2);

        this.source = new ConstantSourceTile(position);
    }
/*
    @Test
    public void testConstantSource() {
        Assert.assertEquals(expectedSourcePosition.getX(), source.getPosition().getX());
        Assert.assertEquals(expectedSourcePosition.getY(), source.getPosition().getY());
        Assert.assertEquals("source", source.getName());
        Assert.assertEquals("Power : " + Power.getMax(), source.getInfo());
        Assert.assertEquals(TileType.SOURCE, source.getType());
    }

    @Test
    public void testConstantSourcePower() {
        for (Side side : Side.values()) {
            Assert.assertFalse(source.acceptsPower(side));
            Assert.assertTrue(source.outputsPower(side));
            Assert.assertEquals(Power.getMax(), source.getPower(side));
        }
    }
*/
}
