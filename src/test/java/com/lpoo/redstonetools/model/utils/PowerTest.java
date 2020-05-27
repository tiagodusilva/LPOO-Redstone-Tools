package com.lpoo.redstonetools.model.utils;

import org.junit.Assert;
import org.junit.Test;

public class PowerTest {
    @Test
    public void testPowerClamp() {
        int power = Power.getMin() - 20;
        Assert.assertEquals(Power.getMin(), Power.clamp(power));

        power = Power.getMax() + 50;
        Assert.assertEquals(Power.getMax(), Power.clamp(power));

        power = Power.getMax() / 2;
        Assert.assertEquals(Power.getMax() / 2, Power.clamp(power));
    }

    @Test
    public void testPowerDecrease() {
        int power = Power.getMax();
        Assert.assertEquals(power - 1, Power.decrease(power));

        power = Power.getMin();
        Assert.assertEquals(Power.getMin(), Power.decrease(power));
    }

    @Test
    public void testIsOff() {
        int power = Power.getMin();
        Assert.assertTrue(Power.isOff(power));
        for (power = Power.getMax(); power > Power.getMin(); power = Power.decrease(power)) {
            Assert.assertFalse(Power.isOff(power));
        }
    }

    @Test
    public void testIsOn() {
        int power = Power.getMin();
        Assert.assertFalse(Power.isOn(power));
        for (power = Power.getMax(); power > Power.getMin(); power = Power.decrease(power)) {
            Assert.assertTrue(Power.isOn(power));
        }
    }

    @Test
    public void testDifferentStates() {
        Assert.assertTrue(Power.differentStates(Power.getMax(), Power.getMin()));
        Assert.assertFalse(Power.differentStates(Power.getMin(), Power.getMin()));
        Assert.assertFalse(Power.differentStates(Power.getMax(), Power.getMax()));

        int power = Power.getMax();
        for (int power2 = Power.decrease(Power.getMax()); power2 > Power.getMin(); power = Power.decrease(power), power2 = Power.decrease(power2)) {
            Assert.assertFalse(Power.differentStates(power, Power.getMax()));
            Assert.assertFalse(Power.differentStates(power2, Power.getMax()));
            Assert.assertFalse(Power.differentStates(power, power2));
            Assert.assertTrue(Power.differentStates(power, Power.getMin()));
            Assert.assertTrue(Power.differentStates(power2, Power.getMin()));
        }
    }
}
