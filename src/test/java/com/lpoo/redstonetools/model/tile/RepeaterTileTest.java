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

public class RepeaterTileTest {

    private RepeaterTile repeater;

    @Before
    public void setup() {
        Position position = Mockito.mock(Position.class);
        Mockito.when(position.getX()).thenReturn(1);
        Mockito.when(position.getY()).thenReturn(2);

        this.repeater = new RepeaterTile(position);
    }

    @Test
    public void testRepeater() {
        Assert.assertEquals(1, repeater.getPosition().getX());
        Assert.assertEquals(2, repeater.getPosition().getY());
        Assert.assertEquals(TileType.REPEATER, repeater.getType());
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

        // deactivate repeater
        repeater.setStatus(false);

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
        Circuit circuit = Mockito.mock(Circuit.class);

        Assert.assertTrue(repeater.acceptsPower(Side.LEFT));
        Assert.assertFalse(repeater.acceptsPower(Side.RIGHT));
        Assert.assertFalse(repeater.acceptsPower(Side.UP));
        Assert.assertFalse(repeater.acceptsPower(Side.DOWN));


        Assert.assertFalse(repeater.outputsPower(Side.LEFT));
        Assert.assertTrue(repeater.outputsPower(Side.RIGHT));
        Assert.assertFalse(repeater.outputsPower(Side.UP));
        Assert.assertFalse(repeater.outputsPower(Side.DOWN));

        repeater.rotateRight(circuit);

        Assert.assertFalse(repeater.acceptsPower(Side.LEFT));
        Assert.assertFalse(repeater.acceptsPower(Side.RIGHT));
        Assert.assertTrue(repeater.acceptsPower(Side.UP));
        Assert.assertFalse(repeater.acceptsPower(Side.DOWN));


        Assert.assertFalse(repeater.outputsPower(Side.LEFT));
        Assert.assertFalse(repeater.outputsPower(Side.RIGHT));
        Assert.assertFalse(repeater.outputsPower(Side.UP));
        Assert.assertTrue(repeater.outputsPower(Side.DOWN));

        repeater.rotateLeft(circuit); repeater.rotateLeft(circuit);

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

        Assert.assertFalse(repeater.getStatus());

        Assert.assertTrue(repeater.onChange(circuit, Power.getMax(), Side.LEFT));

        Assert.assertTrue(repeater.getStatus());

        Assert.assertTrue(repeater.onChange(circuit, 5, Side.LEFT));

        Assert.assertTrue(repeater.getStatus());

        Assert.assertTrue(repeater.onChange(circuit, Power.getMin(), Side.LEFT));

        Assert.assertFalse(repeater.getStatus());
    }

    @Test
    public void testRepeaterUpdate() {
        Circuit circuit = Mockito.mock(Circuit.class);

        Assert.assertEquals(Power.getMin(), repeater.getPower(Side.RIGHT));

        for (Side side : Side.values()) {
            Assert.assertFalse(repeater.update(circuit, Power.getMin(), side));
        }
    }
}
