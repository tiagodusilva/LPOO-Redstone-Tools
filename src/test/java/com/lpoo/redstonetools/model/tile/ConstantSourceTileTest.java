package com.lpoo.redstonetools.model.tile;

import com.lpoo.redstonetools.model.utils.Position;
import com.lpoo.redstonetools.model.utils.Power;
import com.lpoo.redstonetools.model.utils.Side;
import com.lpoo.redstonetools.model.utils.TileType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ConstantSourceTileTest {

    private ConstantSourceTile source;

    @BeforeEach
    public void setup() {
        Position position = Mockito.mock(Position.class);
        Mockito.when(position.getX()).thenReturn(1);
        Mockito.when(position.getY()).thenReturn(2);

        this.source = new ConstantSourceTile(position);
    }

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testConstantSource() {
        Assertions.assertEquals(1, source.getPosition().getX());
        Assertions.assertEquals(2, source.getPosition().getY());
        Assertions.assertEquals(TileType.SOURCE, source.getType());
        Assertions.assertFalse(source.isWire());
        Assertions.assertFalse(source.isTickedTile());
    }

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testConstantSourcePower() {
        for (Side side : Side.values()) {
            Assertions.assertFalse(source.acceptsPower(side));
            Assertions.assertTrue(source.outputsPower(side));
            Assertions.assertEquals(Power.getMax(), source.getPower(side));
        }
    }
}
