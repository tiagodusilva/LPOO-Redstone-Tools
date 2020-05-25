package com.lpoo.redstonetools.model.tile;

import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.model.utils.Position;
import com.lpoo.redstonetools.model.utils.Power;
import com.lpoo.redstonetools.model.utils.Side;
import com.lpoo.redstonetools.model.utils.TileType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class LeverTileTest {

    private LeverTile lever;

    private Position expectedLeverPosition;

    @Before
    public void setup() {
        Position position = Mockito.mock(Position.class);
        Mockito.when(position.getX()).thenReturn(1);
        Mockito.when(position.getY()).thenReturn(2);

        expectedLeverPosition = Mockito.mock(Position.class);
        Mockito.when(expectedLeverPosition.getX()).thenReturn(1);
        Mockito.when(expectedLeverPosition.getY()).thenReturn(2);

        this.lever = new LeverTile(position);
    }

    @Test
    public void testLever() {
        Assert.assertEquals(expectedLeverPosition.getX(), lever.getPosition().getX());
        Assert.assertEquals(expectedLeverPosition.getY(), lever.getPosition().getY());
        Assert.assertEquals("lever", lever.getName());
        Assert.assertEquals("Power : " + Power.getMin(), lever.getInfo());
        Assert.assertEquals(TileType.LEVER, lever.getType());
    }

    @Test
    public void testLeverInteract() {
        Circuit circuit = Mockito.mock(Circuit.class);

        Assert.assertFalse(lever.isActivated());
        lever.interact(circuit);
        Assert.assertTrue(lever.isActivated());
        lever.interact(circuit);
        Assert.assertFalse(lever.isActivated());

        for (int i = 0; i < 6; i++) {
            lever.interact(circuit);
        }
        Assert.assertFalse(lever.isActivated());

        for (int i = 0; i < 9; i++) {
            lever.interact(circuit);
        }
        Assert.assertTrue(lever.isActivated());
    }

    @Test
    public void testLeverPower() {
        Circuit circuit = Mockito.mock(Circuit.class);

        for (Side side : Side.values()) {
            Assert.assertFalse(lever.acceptsPower(side));
            Assert.assertTrue(lever.outputsPower(side));
            Assert.assertEquals(Power.getMin(), lever.getPower(side));
        }

        lever.interact(circuit);

        for (Side side : Side.values()) {
            Assert.assertFalse(lever.acceptsPower(side));
            Assert.assertTrue(lever.outputsPower(side));
            Assert.assertEquals(Power.getMax(), lever.getPower(side));
        }
    }

}
