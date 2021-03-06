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

public class TimerTileTest {

    private TimerTile timer;

    @BeforeEach
    @BeforeProperty
    public void setup() {
        Position position = Mockito.mock(Position.class);
        Mockito.when(position.getX()).thenReturn(1);
        Mockito.when(position.getY()).thenReturn(2);

        this.timer = Mockito.mock(TimerTile.class, Mockito.withSettings().useConstructor(position).defaultAnswer(Mockito.CALLS_REAL_METHODS));
    }

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testTimer() {
        Assertions.assertEquals(1, timer.getPosition().getX());
        Assertions.assertEquals(2, timer.getPosition().getY());
        Assertions.assertEquals(TileType.TIMER, timer.getType());
        Assertions.assertFalse(timer.isWire());
        Assertions.assertTrue(timer.isTickedTile());
    }

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testDelay() {
        timer.setDelay(15);

        Assertions.assertEquals(15, timer.getDelay());
        Assertions.assertEquals(0, timer.getTimer());
        Assertions.assertEquals(15, timer.getTicksLeft());
    }

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testInteract() {
        Circuit circuit = Mockito.mock(Circuit.class);
        timer.setSwitchMode(false);

        Assertions.assertFalse(timer.getSwitchMode());

        Assertions.assertFalse(timer.interact(circuit));

        Assertions.assertTrue(timer.getSwitchMode());

        Assertions.assertFalse(timer.interact(circuit));

        Assertions.assertFalse(timer.getSwitchMode());
    }

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testPower() {
        Assertions.assertTrue(timer.acceptsPower(Side.LEFT));
        Assertions.assertFalse(timer.acceptsPower(Side.RIGHT));
        Assertions.assertFalse(timer.acceptsPower(Side.UP));
        Assertions.assertFalse(timer.acceptsPower(Side.DOWN));

        Assertions.assertTrue(timer.outputsPower(Side.RIGHT));
        Assertions.assertFalse(timer.outputsPower(Side.LEFT));
        Assertions.assertFalse(timer.outputsPower(Side.UP));
        Assertions.assertFalse(timer.outputsPower(Side.DOWN));

        timer.setOutput(false);

        for (Side side : Side.values()) {
            Assertions.assertEquals(Power.getMin(), timer.getPower(side));
        }

        // activate repeater
        timer.setOutput(true);

        for (Side side : Side.values()) {
            if (timer.outputsPower(side)) {
                Assertions.assertEquals(Power.getMax(), timer.getPower(side));
            } else {
                Assertions.assertEquals(Power.getMin(), timer.getPower(side));
            }
        }
    }

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testRotation() {
        Circuit circuit = Mockito.mock(Circuit.class);

        Assertions.assertTrue(timer.acceptsPower(Side.LEFT));
        Assertions.assertFalse(timer.acceptsPower(Side.RIGHT));
        Assertions.assertFalse(timer.acceptsPower(Side.UP));
        Assertions.assertFalse(timer.acceptsPower(Side.DOWN));


        Assertions.assertFalse(timer.outputsPower(Side.LEFT));
        Assertions.assertTrue(timer.outputsPower(Side.RIGHT));
        Assertions.assertFalse(timer.outputsPower(Side.UP));
        Assertions.assertFalse(timer.outputsPower(Side.DOWN));

        timer.rotateRight(circuit);

        Assertions.assertFalse(timer.acceptsPower(Side.LEFT));
        Assertions.assertFalse(timer.acceptsPower(Side.RIGHT));
        Assertions.assertTrue(timer.acceptsPower(Side.UP));
        Assertions.assertFalse(timer.acceptsPower(Side.DOWN));


        Assertions.assertFalse(timer.outputsPower(Side.LEFT));
        Assertions.assertFalse(timer.outputsPower(Side.RIGHT));
        Assertions.assertFalse(timer.outputsPower(Side.UP));
        Assertions.assertTrue(timer.outputsPower(Side.DOWN));

        timer.rotateLeft(circuit); timer.rotateLeft(circuit);

        Assertions.assertFalse(timer.acceptsPower(Side.LEFT));
        Assertions.assertFalse(timer.acceptsPower(Side.RIGHT));
        Assertions.assertFalse(timer.acceptsPower(Side.UP));
        Assertions.assertTrue(timer.acceptsPower(Side.DOWN));


        Assertions.assertFalse(timer.outputsPower(Side.LEFT));
        Assertions.assertFalse(timer.outputsPower(Side.RIGHT));
        Assertions.assertTrue(timer.outputsPower(Side.UP));
        Assertions.assertFalse(timer.outputsPower(Side.DOWN));
    }

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testOnChange() {
        Circuit circuit = Mockito.mock(Circuit.class);

        for (Side side : Side.values()) {
            timer.setOutput(false);
            timer.setStatus(false);
            Assertions.assertFalse(timer.onChange(circuit, Power.getMin(), side));
            Assertions.assertEquals(0, timer.getTimer());
            Assertions.assertFalse(timer.getOutput());
            Assertions.assertTrue(timer.getStatus());
        }

        for (Side side : Side.values()) {
            timer.setOutput(true);
            timer.setStatus(false);
            Assertions.assertFalse(timer.onChange(circuit, Power.getMin(), side));
            Assertions.assertEquals(0, timer.getTimer());
            Assertions.assertTrue(timer.getOutput());
            Assertions.assertTrue(timer.getStatus());
        }

        for (Side side : Side.values()) {
            timer.setOutput(false);
            timer.setStatus(false);
            Assertions.assertFalse(timer.onChange(circuit, Power.getMax(), side));
            Assertions.assertEquals(0, timer.getTimer());
            Assertions.assertFalse(timer.getOutput());
            Assertions.assertFalse(timer.getStatus());
        }

        for (Side side : Side.values()) {
            timer.setOutput(true);
            timer.setStatus(false);
            Assertions.assertTrue(timer.onChange(circuit, Power.getMax(), side));
            Assertions.assertEquals(0, timer.getTimer());
            Assertions.assertFalse(timer.getOutput());
            Assertions.assertFalse(timer.getStatus());
        }
    }

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testUpdateNoStatusChange() {
        Circuit circuit = Mockito.mock(Circuit.class);

        Mockito.when(timer.onChange(Mockito.eq(circuit), Mockito.eq(Power.getMin()), Mockito.any(Side.class))).thenReturn(true);
        Mockito.when(timer.onChange(Mockito.eq(circuit), Mockito.eq(Power.getMax()), Mockito.any(Side.class))).thenReturn(true);

        Mockito.when(timer.acceptsPower(Side.LEFT)).thenReturn(true);
        Mockito.when(timer.acceptsPower(Side.UP)).thenReturn(true);
        Mockito.when(timer.acceptsPower(Side.RIGHT)).thenReturn(true);
        Mockito.when(timer.acceptsPower(Side.DOWN)).thenReturn(true);

        for (Side side : Side.values()) {
            timer.setStatus(false);
            Assertions.assertFalse(timer.update(circuit, Power.getMax(), side));
        }

        for (Side side : Side.values()) {
            timer.setStatus(true);
            Assertions.assertFalse(timer.update(circuit, Power.getMin(), side));
        }

        for (Side side : Side.values()) {
            Mockito.verify(timer, Mockito.times(2)).acceptsPower(side);
        }

        Mockito.verify(timer, Mockito.times(0)).onChange(Mockito.eq(circuit), Mockito.eq(Power.getMin()), Mockito.any(Side.class));
        Mockito.verify(timer, Mockito.times(0)).onChange(Mockito.eq(circuit), Mockito.eq(Power.getMax()), Mockito.any(Side.class));
    }

    @Property
    @net.jqwik.api.Tag("model")
    @net.jqwik.api.Tag("unit-test") @net.jqwik.api.Tag("slow")
    public void testUpdateOnNonInputSide(@ForAll int power) {
        Circuit circuit = Mockito.mock(Circuit.class);

        Assertions.assertFalse(timer.update(circuit, power, Side.UP));
        Assertions.assertFalse(timer.update(circuit, power, Side.RIGHT));
        Assertions.assertFalse(timer.update(circuit, power, Side.DOWN));

        Mockito.verify(timer, Mockito.times(0)).onChange(Mockito.eq(circuit), Mockito.eq(power), Mockito.any(Side.class));
    }

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testUpdate() {
        Circuit circuit = Mockito.mock(Circuit.class);

        Mockito.when(timer.onChange(Mockito.eq(circuit), Mockito.eq(Power.getMin()), Mockito.any(Side.class))).thenReturn(true);
        Mockito.when(timer.onChange(Mockito.eq(circuit), Mockito.eq(Power.getMax()), Mockito.any(Side.class))).thenReturn(false);

        Mockito.when(timer.acceptsPower(Side.LEFT)).thenReturn(true);
        Mockito.when(timer.acceptsPower(Side.UP)).thenReturn(true);
        Mockito.when(timer.acceptsPower(Side.RIGHT)).thenReturn(false);
        Mockito.when(timer.acceptsPower(Side.DOWN)).thenReturn(false);

        timer.setStatus(false);
        Assertions.assertTrue(timer.update(circuit, Power.getMin(), Side.LEFT));

        timer.setStatus(true);
        Assertions.assertFalse(timer.update(circuit, Power.getMax(), Side.UP));

        Mockito.verify(timer, Mockito.times(1)).onChange(Mockito.eq(circuit), Mockito.eq(Power.getMin()), Mockito.eq(Side.LEFT));
        Mockito.verify(timer, Mockito.times(1)).onChange(Mockito.eq(circuit), Mockito.eq(Power.getMax()), Mockito.eq(Side.UP));
    }

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testNextTickDelayedMode() {
        timer.setSwitchMode(false);

        timer.setDelay(2); // update every 2 ticks

        Assertions.assertEquals(0, timer.getTimer());

        timer.setStatus(false);
        timer.setOutput(false);

        Assertions.assertFalse(timer.nextTick());
        Assertions.assertEquals(0, timer.getTimer());
        Assertions.assertEquals(2, timer.getTicksLeft());
        Assertions.assertFalse(timer.getOutput());

        timer.setStatus(true);

        Assertions.assertFalse(timer.nextTick());
        Assertions.assertEquals(1, timer.getTimer());
        Assertions.assertEquals(1, timer.getTicksLeft());
        Assertions.assertFalse(timer.getOutput());

        Assertions.assertTrue(timer.nextTick());
        Assertions.assertEquals(0, timer.getTimer());
        Assertions.assertEquals(2, timer.getTicksLeft());
        Assertions.assertTrue(timer.getOutput());

        Assertions.assertTrue(timer.nextTick());
        Assertions.assertEquals(1, timer.getTimer());
        Assertions.assertEquals(1, timer.getTicksLeft());
        Assertions.assertFalse(timer.getOutput());

        Assertions.assertTrue(timer.nextTick());
        Assertions.assertEquals(0, timer.getTimer());
        Assertions.assertEquals(2, timer.getTicksLeft());
        Assertions.assertTrue(timer.getOutput());

        Assertions.assertTrue(timer.nextTick());
        Assertions.assertEquals(1, timer.getTimer());
        Assertions.assertEquals(1, timer.getTicksLeft());
        Assertions.assertFalse(timer.getOutput());
    }

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testNextTickSwitchMode() {
        timer.setSwitchMode(true);

        timer.setDelay(2); // update every 2 ticks

        Assertions.assertEquals(0, timer.getTimer());

        timer.setStatus(false);
        timer.setOutput(false);

        Assertions.assertFalse(timer.nextTick());
        Assertions.assertEquals(0, timer.getTimer());
        Assertions.assertEquals(2, timer.getTicksLeft());
        Assertions.assertFalse(timer.getOutput());

        timer.setStatus(true);

        Assertions.assertFalse(timer.nextTick());
        Assertions.assertEquals(1, timer.getTimer());
        Assertions.assertEquals(1, timer.getTicksLeft());
        Assertions.assertFalse(timer.getOutput());

        Assertions.assertTrue(timer.nextTick());
        Assertions.assertEquals(0, timer.getTimer());
        Assertions.assertEquals(2, timer.getTicksLeft());
        Assertions.assertTrue(timer.getOutput());

        Assertions.assertFalse(timer.nextTick());
        Assertions.assertEquals(1, timer.getTimer());
        Assertions.assertEquals(1, timer.getTicksLeft());
        Assertions.assertTrue(timer.getOutput());

        Assertions.assertTrue(timer.nextTick());
        Assertions.assertEquals(0, timer.getTimer());
        Assertions.assertEquals(2, timer.getTicksLeft());
        Assertions.assertFalse(timer.getOutput());

        Assertions.assertFalse(timer.nextTick());
        Assertions.assertEquals(1, timer.getTimer());
        Assertions.assertEquals(1, timer.getTicksLeft());
        Assertions.assertFalse(timer.getOutput());

        Assertions.assertTrue(timer.nextTick());
        Assertions.assertEquals(0, timer.getTimer());
        Assertions.assertEquals(2, timer.getTicksLeft());
        Assertions.assertTrue(timer.getOutput());
    }
}
