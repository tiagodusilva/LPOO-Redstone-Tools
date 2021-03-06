package com.lpoo.redstonetools.model.tile;

import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.model.utils.Position;
import com.lpoo.redstonetools.model.utils.Power;
import com.lpoo.redstonetools.model.utils.Side;
import com.lpoo.redstonetools.model.utils.TileType;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.lifecycle.BeforeProperty;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class CounterTileTest {

    private CounterTile counter;

    @BeforeEach
    @BeforeProperty
    public void setup() {
        Position position = Mockito.mock(Position.class);
        Mockito.when(position.getX()).thenReturn(1);
        Mockito.when(position.getY()).thenReturn(2);

        this.counter = new CounterTile(position);
    }

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testCounter() {
        Assertions.assertEquals(1, counter.getPosition().getX());
        Assertions.assertEquals(2, counter.getPosition().getY());
        Assertions.assertEquals(TileType.COUNTER, counter.getType());
        Assertions.assertFalse(counter.isWire());
        Assertions.assertFalse(counter.isTickedTile());
    }

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testPower() {
        Assertions.assertTrue(counter.acceptsPower(Side.LEFT));
        Assertions.assertFalse(counter.acceptsPower(Side.RIGHT));
        Assertions.assertFalse(counter.acceptsPower(Side.UP));
        Assertions.assertFalse(counter.acceptsPower(Side.DOWN));

        Assertions.assertTrue(counter.outputsPower(Side.RIGHT));
        Assertions.assertFalse(counter.outputsPower(Side.LEFT));
        Assertions.assertFalse(counter.outputsPower(Side.UP));
        Assertions.assertFalse(counter.outputsPower(Side.DOWN));

        for (Side side : Side.values()) {
            Assertions.assertEquals(Power.getMin(), counter.getPower(side));
        }

        // activate repeater
        counter.setOutput(true);

        for (Side side : Side.values()) {
            if (counter.outputsPower(side)) {
                Assertions.assertEquals(Power.getMax(), counter.getPower(side));
            } else {
                Assertions.assertEquals(Power.getMin(), counter.getPower(side));
            }
        }
    }

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testRotation() {
        Circuit circuit = Mockito.mock(Circuit.class);

        Assertions.assertTrue(counter.acceptsPower(Side.LEFT));
        Assertions.assertFalse(counter.acceptsPower(Side.RIGHT));
        Assertions.assertFalse(counter.acceptsPower(Side.UP));
        Assertions.assertFalse(counter.acceptsPower(Side.DOWN));


        Assertions.assertFalse(counter.outputsPower(Side.LEFT));
        Assertions.assertTrue(counter.outputsPower(Side.RIGHT));
        Assertions.assertFalse(counter.outputsPower(Side.UP));
        Assertions.assertFalse(counter.outputsPower(Side.DOWN));

        counter.rotateRight(circuit);

        Assertions.assertFalse(counter.acceptsPower(Side.LEFT));
        Assertions.assertFalse(counter.acceptsPower(Side.RIGHT));
        Assertions.assertTrue(counter.acceptsPower(Side.UP));
        Assertions.assertFalse(counter.acceptsPower(Side.DOWN));


        Assertions.assertFalse(counter.outputsPower(Side.LEFT));
        Assertions.assertFalse(counter.outputsPower(Side.RIGHT));
        Assertions.assertFalse(counter.outputsPower(Side.UP));
        Assertions.assertTrue(counter.outputsPower(Side.DOWN));

        counter.rotateLeft(circuit); counter.rotateLeft(circuit);

        Assertions.assertFalse(counter.acceptsPower(Side.LEFT));
        Assertions.assertFalse(counter.acceptsPower(Side.RIGHT));
        Assertions.assertFalse(counter.acceptsPower(Side.UP));
        Assertions.assertTrue(counter.acceptsPower(Side.DOWN));


        Assertions.assertFalse(counter.outputsPower(Side.LEFT));
        Assertions.assertFalse(counter.outputsPower(Side.RIGHT));
        Assertions.assertTrue(counter.outputsPower(Side.UP));
        Assertions.assertFalse(counter.outputsPower(Side.DOWN));
    }

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testOnChange() {
        Circuit circuit = Mockito.mock(Circuit.class);

        counter.setDelay(3);

        Assertions.assertEquals(3, counter.getDelay());

        Assertions.assertEquals(0, counter.getCounter());

        Assertions.assertFalse(counter.onChange(circuit, Power.getMax(), Side.LEFT));

        Assertions.assertEquals(1, counter.getCounter());
        Assertions.assertEquals(Power.getMax(), counter.getPrevIn());

        Assertions.assertFalse(counter.onChange(circuit, 5, Side.LEFT));

        Assertions.assertEquals(2, counter.getCounter());
        Assertions.assertEquals(5, counter.getPrevIn());

        Assertions.assertFalse(counter.onChange(circuit, Power.getMin(), Side.LEFT));

        Assertions.assertEquals(2, counter.getCounter());
        Assertions.assertEquals(Power.getMin(), counter.getPrevIn());

        Assertions.assertTrue(counter.onChange(circuit, 8, Side.LEFT));

        Assertions.assertEquals(0, counter.getCounter());
        Assertions.assertEquals(8, counter.getPrevIn());

        Assertions.assertFalse(counter.onChange(circuit, Power.getMin(), Side.LEFT));

        Assertions.assertEquals(0, counter.getCounter());
        Assertions.assertEquals(Power.getMin(), counter.getPrevIn());

        Assertions.assertFalse(counter.onChange(circuit, Power.getMin(), Side.LEFT));

        Assertions.assertEquals(0, counter.getCounter());
        Assertions.assertEquals(Power.getMin(), counter.getPrevIn());
    }

    @Property
    @net.jqwik.api.Tag("model")
    @net.jqwik.api.Tag("unit-test") @net.jqwik.api.Tag("slow")
    public void testOnChangeProperty(@ForAll int power, @ForAll Side side) {
        Circuit circuit = Mockito.mock(Circuit.class);
        counter.setDelay(2);
        Assertions.assertEquals(2, counter.getDelay());
        if (Power.isOn(power)) {
            Assertions.assertFalse(counter.onChange(circuit, power, side));
            Assertions.assertEquals(1, counter.getCounter());
            Assertions.assertTrue(counter.onChange(circuit, power, side));
        } else {
            Assertions.assertFalse(counter.onChange(circuit, power, side));
            Assertions.assertEquals(0, counter.getCounter());
            Assertions.assertFalse(counter.onChange(circuit, power, side));
        }

        Assertions.assertEquals(0, counter.getCounter());
        Assertions.assertFalse(counter.onChange(circuit, Power.getMin(), side));
        counter.setOutput(false);
    }

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testResetOnSetDelay() {
        Circuit circuit = Mockito.mock(Circuit.class);

        counter.setDelay(3);
        Assertions.assertEquals(3, counter.getDelay());

        Assertions.assertEquals(0, counter.getCounter());
        Assertions.assertFalse(counter.onChange(circuit, Power.getMax(), Side.LEFT));
        Assertions.assertEquals(1, counter.getCounter());
        Assertions.assertEquals(Power.getMax(), counter.getPrevIn());

        counter.setDelay(2);
        Assertions.assertEquals(2, counter.getDelay());

        Assertions.assertEquals(0, counter.getCounter());
        Assertions.assertFalse(counter.onChange(circuit, Power.getMax(), Side.LEFT));
        Assertions.assertEquals(1, counter.getCounter());
        Assertions.assertEquals(Power.getMax(), counter.getPrevIn());

        Assertions.assertTrue(counter.onChange(circuit, Power.getMax(), Side.LEFT));
        Assertions.assertEquals(0, counter.getCounter());
        Assertions.assertEquals(Power.getMax(), counter.getPrevIn());

    }

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testSpecialDelays() {
        Circuit circuit = Mockito.mock(Circuit.class);

        counter.setDelay(1);
        Assertions.assertEquals(1, counter.getDelay());

        Assertions.assertTrue(counter.onChange(circuit, Power.getMax(), Side.LEFT));
        Assertions.assertEquals(0, counter.getCounter());
        Assertions.assertFalse(counter.onChange(circuit, Power.getMax(), Side.LEFT));
        Assertions.assertEquals(0, counter.getCounter());
        Assertions.assertFalse(counter.onChange(circuit, Power.getMax(), Side.LEFT));
        Assertions.assertEquals(0, counter.getCounter());
        Assertions.assertFalse(counter.onChange(circuit, Power.getMax(), Side.LEFT));
        Assertions.assertEquals(0, counter.getCounter());

    }

    @Property
    @net.jqwik.api.Tag("model")
    @net.jqwik.api.Tag("unit-test") @net.jqwik.api.Tag("slow")
    public void testUpdateOnNonInputSide(@ForAll int power) {
        Circuit circuit = Mockito.mock(Circuit.class);

        CounterTile counterSpy = Mockito.spy(counter);

        Assertions.assertFalse(counterSpy.update(circuit, power, Side.UP));
        Assertions.assertFalse(counterSpy.update(circuit, power, Side.RIGHT));
        Assertions.assertFalse(counterSpy.update(circuit, power, Side.DOWN));

        Mockito.verify(counterSpy, Mockito.times(0)).onChange(Mockito.eq(circuit), Mockito.eq(power), Mockito.any(Side.class));
    }

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testUpdate() {
        Circuit circuit = Mockito.mock(Circuit.class);

        counter.setDelay(4);
        Assertions.assertEquals(4, counter.getDelay());

        Assertions.assertEquals(Power.getMin(), counter.getPower(Side.RIGHT));

        for (Side side : Side.values()) {
            Assertions.assertFalse(counter.update(circuit, Power.getMin(), side));
        }

        for (Side side : Side.values()) {
            Assertions.assertFalse(counter.update(circuit, Power.getMax(), side));
        }
        Assertions.assertEquals(1, counter.getCounter());
        Assertions.assertEquals(3, counter.getPulsesLeft());

        for (Side side : Side.values()) {
            Assertions.assertFalse(counter.update(circuit, Power.getMax(), side));
        }
        Assertions.assertEquals(1, counter.getCounter());
        Assertions.assertEquals(3, counter.getPulsesLeft());

        for (Side side : Side.values()) {
            Assertions.assertFalse(counter.update(circuit, Power.getMin(), side));
        }
        Assertions.assertEquals(1, counter.getCounter());
        Assertions.assertEquals(3, counter.getPulsesLeft());

        for (Side side : Side.values()) {
            Assertions.assertFalse(counter.update(circuit, Power.getMin(), side));
            Assertions.assertFalse(counter.update(circuit, Power.getMax(), side));
        }
        Assertions.assertEquals(2, counter.getCounter());
        Assertions.assertEquals(2, counter.getPulsesLeft());

        for (Side side : Side.values()) {
            Assertions.assertFalse(counter.update(circuit, Power.getMin(), side));
            Assertions.assertFalse(counter.update(circuit, Power.getMax(), side));
        }
        Assertions.assertEquals(3, counter.getCounter());
        Assertions.assertEquals(1, counter.getPulsesLeft());

        Assertions.assertEquals(Power.getMin(), counter.getPower(Side.RIGHT));

        for (Side side : Side.values()) {
            if (side == Side.LEFT) {
                Assertions.assertFalse(counter.update(circuit, Power.getMin(), side));
                Assertions.assertTrue(counter.update(circuit, Power.getMax(), side));
            }
            else {
                Assertions.assertFalse(counter.update(circuit, Power.getMin(), side));
                Assertions.assertFalse(counter.update(circuit, Power.getMax(), side));
            }
        }
        Assertions.assertEquals(0, counter.getCounter());
        Assertions.assertEquals(4, counter.getPulsesLeft());

        Assertions.assertEquals(Power.getMax(), counter.getPower(Side.RIGHT));

        Assertions.assertFalse(counter.update(circuit, Power.getMin(), Side.LEFT));
        Assertions.assertEquals(Power.getMax(), counter.getPower(Side.RIGHT));
        Assertions.assertTrue(counter.update(circuit, Power.getMax(), Side.LEFT));
        Assertions.assertEquals(Power.getMin(), counter.getPower(Side.RIGHT));
    }
}
