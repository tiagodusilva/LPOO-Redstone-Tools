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

import static org.junit.Assert.assertEquals;

public class RepeaterTileTest {

    private RepeaterTile repeater;

    private Position expectedRepeaterPosition;

    @Before
    public void setup() {
        Position position = Mockito.mock(Position.class);
        Mockito.when(position.getX()).thenReturn(1);
        Mockito.when(position.getY()).thenReturn(2);

        expectedRepeaterPosition = Mockito.mock(Position.class);
        Mockito.when(expectedRepeaterPosition.getX()).thenReturn(1);
        Mockito.when(expectedRepeaterPosition.getY()).thenReturn(2);

        this.repeater = new RepeaterTile(position);
    }

    @Test
    public void testRepeater() {
        Assert.assertEquals(expectedRepeaterPosition.getX(), repeater.getPosition().getX());
        Assert.assertEquals(expectedRepeaterPosition.getY(), repeater.getPosition().getY());
        Assert.assertEquals("repeater", repeater.getName());
        Assert.assertEquals("Active : false", repeater.getInfo());
        Assert.assertEquals(TileType.REPEATER, repeater.getType());
        Assert.assertFalse(repeater.isSource());
    }

    @Test
    public void testRepeaterPower() {
        Assert.assertTrue(repeater.acceptsPower(Side.LEFT));
        Assert.assertFalse(repeater.acceptsPower(Side.RIGHT));
        Assert.assertFalse(repeater.acceptsPower(Side.UP));
        Assert.assertFalse(repeater.acceptsPower(Side.DOWN));

        Assert.assertTrue(repeater.outputsPower(Side.RIGHT));
        Assert.assertFalse(repeater.outputsPower(Side.LEFT));
        Assert.assertFalse(repeater.outputsPower(Side.UP));
        Assert.assertFalse(repeater.outputsPower(Side.DOWN));

        for (Side side : Side.values()) {
            Assert.assertEquals(Power.getMin(), repeater.getPower(side));
        }

        // activate repeater
        repeater.setStatus(true);

        for (Side side : Side.values()) {
            if (repeater.outputsPower(side)) {
                Assert.assertEquals(Power.getMax(), repeater.getPower(side));
            } else {
                Assert.assertEquals(Power.getMin(), repeater.getPower(side));
            }
        }
    }

    @Test
    public void testRepeaterRotation() {
        Assert.assertTrue(repeater.acceptsPower(Side.LEFT));
        Assert.assertFalse(repeater.acceptsPower(Side.RIGHT));
        Assert.assertFalse(repeater.acceptsPower(Side.UP));
        Assert.assertFalse(repeater.acceptsPower(Side.DOWN));


        Assert.assertFalse(repeater.outputsPower(Side.LEFT));
        Assert.assertTrue(repeater.outputsPower(Side.RIGHT));
        Assert.assertFalse(repeater.outputsPower(Side.UP));
        Assert.assertFalse(repeater.outputsPower(Side.DOWN));

        repeater.rotateRight();

        Assert.assertFalse(repeater.acceptsPower(Side.LEFT));
        Assert.assertFalse(repeater.acceptsPower(Side.RIGHT));
        Assert.assertTrue(repeater.acceptsPower(Side.UP));
        Assert.assertFalse(repeater.acceptsPower(Side.DOWN));


        Assert.assertFalse(repeater.outputsPower(Side.LEFT));
        Assert.assertFalse(repeater.outputsPower(Side.RIGHT));
        Assert.assertFalse(repeater.outputsPower(Side.UP));
        Assert.assertTrue(repeater.outputsPower(Side.DOWN));

        repeater.rotateLeft(); repeater.rotateLeft();

        Assert.assertFalse(repeater.acceptsPower(Side.LEFT));
        Assert.assertFalse(repeater.acceptsPower(Side.RIGHT));
        Assert.assertFalse(repeater.acceptsPower(Side.UP));
        Assert.assertTrue(repeater.acceptsPower(Side.DOWN));


        Assert.assertFalse(repeater.outputsPower(Side.LEFT));
        Assert.assertFalse(repeater.outputsPower(Side.RIGHT));
        Assert.assertTrue(repeater.outputsPower(Side.UP));
        Assert.assertFalse(repeater.outputsPower(Side.DOWN));
    }

    @Test
    public void testRepeaterOnChange() {
        Circuit circuit = Mockito.mock(Circuit.class);

        Mockito.when(circuit.getTick()).thenReturn(5L);

        Assert.assertEquals(Power.getMin(), repeater.getPower(Side.RIGHT));

        Assert.assertTrue(repeater.onChange(circuit, Power.getMin(), Side.LEFT));

        Assert.assertEquals(Power.getMin(), repeater.getPower(Side.RIGHT));

        Assert.assertFalse(repeater.onChange(circuit, Power.getMin(), Side.LEFT));

        Assert.assertEquals(Power.getMin(), repeater.getPower(Side.RIGHT));

        Assert.assertTrue(repeater.onChange(circuit, Power.getMax(), Side.LEFT));

        Assert.assertEquals(Power.getMax(), repeater.getPower(Side.RIGHT));

        Mockito.when(circuit.getTick()).thenReturn(6L);

        Assert.assertTrue(repeater.onChange(circuit, Power.getMin(), Side.LEFT));

        Assert.assertEquals(Power.getMin(), repeater.getPower(Side.RIGHT));
    }

    @Test
    public void testRepeaterUpdate() {
        Circuit circuit = Mockito.mock(Circuit.class);

        Mockito.when(circuit.getTick()).thenReturn(5L);

        Assert.assertEquals(Power.getMin(), repeater.getPower(Side.RIGHT));

        for (Side side : Side.values()) {
            Assert.assertFalse(repeater.update(circuit, Power.getMin(), side));
        }

        Assert.assertEquals(Power.getMin(), repeater.getPower(Side.RIGHT));

        Assert.assertFalse(repeater.update(circuit, Power.getMax(), Side.DOWN));
        Assert.assertFalse(repeater.update(circuit, Power.getMax(), Side.UP));
        Assert.assertFalse(repeater.update(circuit, Power.getMax(), Side.RIGHT));

        Assert.assertEquals(Power.getMin(), repeater.getPower(Side.RIGHT));

        Assert.assertTrue(repeater.update(circuit, Power.getMax(), Side.LEFT));

        Assert.assertEquals(Power.getMax(), repeater.getPower(Side.RIGHT));

        Assert.assertFalse(repeater.update(circuit, Power.getMax(), Side.LEFT));

        Assert.assertEquals(Power.getMax(), repeater.getPower(Side.RIGHT));

        Assert.assertFalse(repeater.update(circuit, Power.getMin(), Side.LEFT));

        Assert.assertEquals(Power.getMax(), repeater.getPower(Side.RIGHT));

        Mockito.when(circuit.getTick()).thenReturn(6L);

        Assert.assertFalse(repeater.update(circuit, Power.getMax(), Side.LEFT));

        Assert.assertEquals(Power.getMax(), repeater.getPower(Side.RIGHT));

        Assert.assertTrue(repeater.update(circuit, Power.getMin(), Side.LEFT));

        Assert.assertEquals(Power.getMin(), repeater.getPower(Side.RIGHT));
    }

}
