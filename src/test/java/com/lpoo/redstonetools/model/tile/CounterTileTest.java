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

@Tag("model") @net.jqwik.api.Tag("model")
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
    @Tag("unit-test") @Tag("fast")
    public void testCounter() {
        Assertions.assertEquals(1, counter.getPosition().getX());
        Assertions.assertEquals(2, counter.getPosition().getY());
        Assertions.assertEquals(TileType.COUNTER, counter.getType());
        Assertions.assertFalse(counter.isWire());
        Assertions.assertFalse(counter.isTickedTile());
    }

    @Test
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
    @Tag("unit-test") @Tag("fast")
    public void testOnChange() {
        Circuit circuit = Mockito.mock(Circuit.class);

        counter.setDelay(3);

        Assertions.assertEquals(0, counter.getCounter());

        Assertions.assertFalse(counter.onChange(circuit, Power.getMax(), Side.LEFT));

        Assertions.assertEquals(1, counter.getCounter());

        Assertions.assertFalse(counter.onChange(circuit, 5, Side.LEFT));

        Assertions.assertEquals(2, counter.getCounter());

        Assertions.assertFalse(counter.onChange(circuit, Power.getMin(), Side.LEFT));

        Assertions.assertEquals(2, counter.getCounter());

        Assertions.assertTrue(counter.onChange(circuit, 8, Side.LEFT));

        Assertions.assertEquals(0, counter.getCounter());

        Assertions.assertFalse(counter.onChange(circuit, Power.getMin(), Side.LEFT));

        Assertions.assertEquals(0, counter.getCounter());

        Assertions.assertFalse(counter.onChange(circuit, Power.getMin(), Side.LEFT));

        Assertions.assertEquals(0, counter.getCounter());
    }

    @Test
    @Tag("unit-test") @Tag("fast")
    public void testResetOnSetDelay() {
        Circuit circuit = Mockito.mock(Circuit.class);

        counter.setDelay(3);
        Assertions.assertEquals(0, counter.getCounter());
        Assertions.assertFalse(counter.onChange(circuit, Power.getMax(), Side.LEFT));
        Assertions.assertEquals(1, counter.getCounter());

        counter.setDelay(2);
        Assertions.assertEquals(0, counter.getCounter());
        Assertions.assertFalse(counter.onChange(circuit, Power.getMax(), Side.LEFT));
        Assertions.assertEquals(1, counter.getCounter());
        Assertions.assertTrue(counter.onChange(circuit, Power.getMax(), Side.LEFT));
        Assertions.assertEquals(0, counter.getCounter());
    }

    @Test
    @Tag("unit-test") @Tag("fast")
    public void testSpecialDelays() {
        Circuit circuit = Mockito.mock(Circuit.class);

        counter.setDelay(1);
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
    @Tag("unit-test") @Tag("fast")
    public void testUpdate() {
        Circuit circuit = Mockito.mock(Circuit.class);

        Assertions.assertEquals(Power.getMin(), counter.getPower(Side.RIGHT));

        for (Side side : Side.values()) {
            Assertions.assertFalse(circuit.update(circuit, Power.getMin(), side));
        }

    }
}
