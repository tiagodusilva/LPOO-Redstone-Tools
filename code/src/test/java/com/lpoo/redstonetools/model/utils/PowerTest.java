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
    public void testPowerModes() {
        Power.setRedstoneMode();
        Assert.assertTrue(Power.isRedstoneMode());
        Assert.assertFalse(Power.isEletricMode());


        Power.setEletricMode();
        Assert.assertTrue(Power.isEletricMode());
        Assert.assertFalse(Power.isRedstoneMode());
    }

    @Test
    public void testPowerDecrease() {
        Power.setRedstoneMode();
        int power = Power.getMax();
        Assert.assertEquals(power - 1, Power.decrease(power));

        power = Power.getMin();
        Assert.assertEquals(Power.getMin(), Power.decrease(power));

        Power.setEletricMode();

        power = Power.getMin();
        Assert.assertEquals(Power.getMin(), Power.decrease(power));

        power = Power.getMax();
        Assert.assertEquals(Power.getMax(), Power.decrease(power));
    }
}
