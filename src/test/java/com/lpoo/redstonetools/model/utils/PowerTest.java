package com.lpoo.redstonetools.model.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("model")
public class PowerTest {
    @Test
    @Tag("unit-test") @Tag("fast")
    public void testPowerClamp() {
        int power = Power.getMin() - 20;
        Assertions.assertEquals(Power.getMin(), Power.clamp(power));

        power = Power.getMax() + 50;
        Assertions.assertEquals(Power.getMax(), Power.clamp(power));

        power = Power.getMax() / 2;
        Assertions.assertEquals(Power.getMax() / 2, Power.clamp(power));
    }

    @Test
    @Tag("unit-test") @Tag("fast")
    public void testPowerDecrease() {
        int power = Power.getMax();
        Assertions.assertEquals(power - 1, Power.decrease(power));

        power = Power.getMin();
        Assertions.assertEquals(Power.getMin(), Power.decrease(power));
    }

    @Test
    @Tag("unit-test") @Tag("fast")
    public void testIsOff() {
        int power = Power.getMin();
        Assertions.assertTrue(Power.isOff(power));
        for (power = Power.getMax(); power > Power.getMin(); power = Power.decrease(power)) {
            Assertions.assertFalse(Power.isOff(power));
        }
    }

    @Test
    @Tag("unit-test") @Tag("fast")
    public void testIsOn() {
        int power = Power.getMin();
        Assertions.assertFalse(Power.isOn(power));
        for (power = Power.getMax(); power > Power.getMin(); power = Power.decrease(power)) {
            Assertions.assertTrue(Power.isOn(power));
        }
    }

    @Test
    @Tag("unit-test") @Tag("fast")
    public void testDifferentStates() {
        Assertions.assertTrue(Power.differentStates(Power.getMax(), Power.getMin()));
        Assertions.assertFalse(Power.differentStates(Power.getMin(), Power.getMin()));
        Assertions.assertFalse(Power.differentStates(Power.getMax(), Power.getMax()));

        int power = Power.getMax();
        for (int power2 = Power.decrease(Power.getMax()); power2 > Power.getMin(); power = Power.decrease(power), power2 = Power.decrease(power2)) {
            Assertions.assertFalse(Power.differentStates(power, Power.getMax()));
            Assertions.assertFalse(Power.differentStates(power2, Power.getMax()));
            Assertions.assertFalse(Power.differentStates(power, power2));
            Assertions.assertTrue(Power.differentStates(power, Power.getMin()));
            Assertions.assertTrue(Power.differentStates(power2, Power.getMin()));
        }
    }
}
