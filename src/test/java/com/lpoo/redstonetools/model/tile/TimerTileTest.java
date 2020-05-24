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

public class TimerTileTest {

    private TimerTile timer;

    private Position expectedTimerPosition;

    @Before
    public void setup() {
        Position position = Mockito.mock(Position.class);
        Mockito.when(position.getX()).thenReturn(1);
        Mockito.when(position.getY()).thenReturn(2);

        expectedTimerPosition = Mockito.mock(Position.class);
        Mockito.when(expectedTimerPosition.getX()).thenReturn(1);
        Mockito.when(expectedTimerPosition.getY()).thenReturn(2);

        this.timer = Mockito.mock(TimerTile.class, Mockito.withSettings().useConstructor(position).defaultAnswer(Mockito.CALLS_REAL_METHODS));
    }

    @Test
    public void testTimer() {
        Assert.assertEquals(expectedTimerPosition.getX(), timer.getPosition().getX());
        Assert.assertEquals(expectedTimerPosition.getY(), timer.getPosition().getY());
        Assert.assertEquals(TileType.TIMER, timer.getType());
        Assert.assertTrue(timer.isTickedTile());
    }

    @Test
    public void testDelay() {
        timer.setDelay(15);

        Assert.assertEquals(15, timer.getDelay());
        Assert.assertEquals(0, timer.getTimer());
        Assert.assertEquals(15, timer.getTicksLeft());
    }

    @Test
    public void testInteract() {
        Circuit circuit = Mockito.mock(Circuit.class);
        timer.setSwitchMode(false);

        Assert.assertFalse(timer.getSwitchMode());

        Assert.assertFalse(timer.interact(circuit));

        Assert.assertTrue(timer.getSwitchMode());

        Assert.assertFalse(timer.interact(circuit));

        Assert.assertFalse(timer.getSwitchMode());
    }

    @Test
    public void testTimerPower() {
        Assert.assertTrue(timer.acceptsPower(Side.LEFT));
        Assert.assertFalse(timer.acceptsPower(Side.RIGHT));
        Assert.assertFalse(timer.acceptsPower(Side.UP));
        Assert.assertFalse(timer.acceptsPower(Side.DOWN));

        Assert.assertTrue(timer.outputsPower(Side.RIGHT));
        Assert.assertFalse(timer.outputsPower(Side.LEFT));
        Assert.assertFalse(timer.outputsPower(Side.UP));
        Assert.assertFalse(timer.outputsPower(Side.DOWN));

        timer.setOutput(false);

        for (Side side : Side.values()) {
            Assert.assertEquals(Power.getMin(), timer.getPower(side));
        }

        // activate repeater
        timer.setOutput(true);

        for (Side side : Side.values()) {
            if (timer.outputsPower(side)) {
                Assert.assertEquals(Power.getMax(), timer.getPower(side));
            } else {
                Assert.assertEquals(Power.getMin(), timer.getPower(side));
            }
        }
    }

    @Test
    public void testTimerRotation() {
        Circuit circuit = Mockito.mock(Circuit.class);

        Assert.assertTrue(timer.acceptsPower(Side.LEFT));
        Assert.assertFalse(timer.acceptsPower(Side.RIGHT));
        Assert.assertFalse(timer.acceptsPower(Side.UP));
        Assert.assertFalse(timer.acceptsPower(Side.DOWN));


        Assert.assertFalse(timer.outputsPower(Side.LEFT));
        Assert.assertTrue(timer.outputsPower(Side.RIGHT));
        Assert.assertFalse(timer.outputsPower(Side.UP));
        Assert.assertFalse(timer.outputsPower(Side.DOWN));

        timer.rotateRight(circuit);

        Assert.assertFalse(timer.acceptsPower(Side.LEFT));
        Assert.assertFalse(timer.acceptsPower(Side.RIGHT));
        Assert.assertTrue(timer.acceptsPower(Side.UP));
        Assert.assertFalse(timer.acceptsPower(Side.DOWN));


        Assert.assertFalse(timer.outputsPower(Side.LEFT));
        Assert.assertFalse(timer.outputsPower(Side.RIGHT));
        Assert.assertFalse(timer.outputsPower(Side.UP));
        Assert.assertTrue(timer.outputsPower(Side.DOWN));

        timer.rotateLeft(circuit); timer.rotateLeft(circuit);

        Assert.assertFalse(timer.acceptsPower(Side.LEFT));
        Assert.assertFalse(timer.acceptsPower(Side.RIGHT));
        Assert.assertFalse(timer.acceptsPower(Side.UP));
        Assert.assertTrue(timer.acceptsPower(Side.DOWN));


        Assert.assertFalse(timer.outputsPower(Side.LEFT));
        Assert.assertFalse(timer.outputsPower(Side.RIGHT));
        Assert.assertTrue(timer.outputsPower(Side.UP));
        Assert.assertFalse(timer.outputsPower(Side.DOWN));
    }

    @Test
    public void testOnChange() {
        Circuit circuit = Mockito.mock(Circuit.class);

        for (Side side : Side.values()) {
            timer.setOutput(false);
            timer.setStatus(false);
            Assert.assertFalse(timer.onChange(circuit, Power.getMin(), side));
            Assert.assertEquals(0, timer.getTimer());
            Assert.assertFalse(timer.getOutput());
            Assert.assertTrue(timer.getStatus());
        }

        for (Side side : Side.values()) {
            timer.setOutput(true);
            timer.setStatus(false);
            Assert.assertFalse(timer.onChange(circuit, Power.getMin(), side));
            Assert.assertEquals(0, timer.getTimer());
            Assert.assertTrue(timer.getOutput());
            Assert.assertTrue(timer.getStatus());
        }

        for (Side side : Side.values()) {
            timer.setOutput(false);
            timer.setStatus(false);
            Assert.assertFalse(timer.onChange(circuit, Power.getMax(), side));
            Assert.assertEquals(0, timer.getTimer());
            Assert.assertFalse(timer.getOutput());
            Assert.assertFalse(timer.getStatus());
        }

        for (Side side : Side.values()) {
            timer.setOutput(true);
            timer.setStatus(false);
            Assert.assertTrue(timer.onChange(circuit, Power.getMax(), side));
            Assert.assertEquals(0, timer.getTimer());
            Assert.assertFalse(timer.getOutput());
            Assert.assertFalse(timer.getStatus());
        }
    }

    @Test
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
            Assert.assertFalse(timer.update(circuit, Power.getMax(), side));
        }

        for (Side side : Side.values()) {
            timer.setStatus(true);
            Assert.assertFalse(timer.update(circuit, Power.getMin(), side));
        }

        for (Side side : Side.values()) {
            Mockito.verify(timer, Mockito.times(2)).acceptsPower(side);
        }

        Mockito.verify(timer, Mockito.times(0)).onChange(Mockito.eq(circuit), Mockito.eq(Power.getMin()), Mockito.any(Side.class));
        Mockito.verify(timer, Mockito.times(0)).onChange(Mockito.eq(circuit), Mockito.eq(Power.getMax()), Mockito.any(Side.class));
    }

    @Test
    public void testUpdate() {
        Circuit circuit = Mockito.mock(Circuit.class);

        Mockito.when(timer.onChange(Mockito.eq(circuit), Mockito.eq(Power.getMin()), Mockito.any(Side.class))).thenReturn(true);
        Mockito.when(timer.onChange(Mockito.eq(circuit), Mockito.eq(Power.getMax()), Mockito.any(Side.class))).thenReturn(false);

        Mockito.when(timer.acceptsPower(Side.LEFT)).thenReturn(true);
        Mockito.when(timer.acceptsPower(Side.UP)).thenReturn(true);
        Mockito.when(timer.acceptsPower(Side.RIGHT)).thenReturn(false);
        Mockito.when(timer.acceptsPower(Side.DOWN)).thenReturn(false);

        timer.setStatus(false);
        Assert.assertFalse(timer.update(circuit, Power.getMin(), Side.RIGHT));
        timer.setStatus(true);
        Assert.assertFalse(timer.update(circuit, Power.getMax(), Side.RIGHT));
        timer.setStatus(false);
        Assert.assertFalse(timer.update(circuit, Power.getMin(), Side.DOWN));
        timer.setStatus(true);
        Assert.assertFalse(timer.update(circuit, Power.getMax(), Side.DOWN));

        Mockito.verify(timer, Mockito.times(0)).onChange(Mockito.eq(circuit), Mockito.anyInt(), Mockito.any(Side.class));

        timer.setStatus(false);
        Assert.assertTrue(timer.update(circuit, Power.getMin(), Side.LEFT));

        timer.setStatus(true);
        Assert.assertFalse(timer.update(circuit, Power.getMax(), Side.UP));

        Mockito.verify(timer, Mockito.times(1)).onChange(Mockito.eq(circuit), Mockito.eq(Power.getMin()), Mockito.eq(Side.LEFT));
        Mockito.verify(timer, Mockito.times(1)).onChange(Mockito.eq(circuit), Mockito.eq(Power.getMax()), Mockito.eq(Side.UP));
    }

    @Test
    public void testNextTickDelayedMode() {
        timer.setSwitchMode(false);

        timer.setDelay(2); // update every 2 ticks

        Assert.assertEquals(0, timer.getTimer());

        timer.setStatus(false);
        timer.setOutput(false);

        Assert.assertFalse(timer.nextTick());
        Assert.assertEquals(0, timer.getTimer());
        Assert.assertEquals(2, timer.getTicksLeft());
        Assert.assertFalse(timer.getOutput());

        timer.setStatus(true);

        Assert.assertFalse(timer.nextTick());
        Assert.assertEquals(1, timer.getTimer());
        Assert.assertEquals(1, timer.getTicksLeft());
        Assert.assertFalse(timer.getOutput());

        Assert.assertFalse(timer.nextTick());
        Assert.assertEquals(2, timer.getTimer());
        Assert.assertEquals(0, timer.getTicksLeft());
        Assert.assertFalse(timer.getOutput());

        Assert.assertTrue(timer.nextTick());
        Assert.assertEquals(0, timer.getTimer());
        Assert.assertEquals(2, timer.getTicksLeft());
        Assert.assertTrue(timer.getOutput());

        Assert.assertTrue(timer.nextTick());
        Assert.assertEquals(1, timer.getTimer());
        Assert.assertEquals(1, timer.getTicksLeft());
        Assert.assertFalse(timer.getOutput());

        Assert.assertFalse(timer.nextTick());
        Assert.assertEquals(2, timer.getTimer());
        Assert.assertEquals(0, timer.getTicksLeft());
        Assert.assertFalse(timer.getOutput());
    }

    @Test
    public void testNextTickSwitchMode() {
        timer.setSwitchMode(true);

        timer.setDelay(2); // update every 2 ticks

        Assert.assertEquals(0, timer.getTimer());

        timer.setStatus(false);
        timer.setOutput(false);

        Assert.assertFalse(timer.nextTick());
        Assert.assertEquals(0, timer.getTimer());
        Assert.assertEquals(2, timer.getTicksLeft());
        Assert.assertFalse(timer.getOutput());

        timer.setStatus(true);

        Assert.assertFalse(timer.nextTick());
        Assert.assertEquals(1, timer.getTimer());
        Assert.assertEquals(1, timer.getTicksLeft());
        Assert.assertFalse(timer.getOutput());

        Assert.assertFalse(timer.nextTick());
        Assert.assertEquals(2, timer.getTimer());
        Assert.assertEquals(0, timer.getTicksLeft());
        Assert.assertFalse(timer.getOutput());

        Assert.assertTrue(timer.nextTick());
        Assert.assertEquals(0, timer.getTimer());
        Assert.assertEquals(2, timer.getTicksLeft());
        Assert.assertTrue(timer.getOutput());

        Assert.assertFalse(timer.nextTick());
        Assert.assertEquals(1, timer.getTimer());
        Assert.assertEquals(1, timer.getTicksLeft());
        Assert.assertTrue(timer.getOutput());

        Assert.assertFalse(timer.nextTick());
        Assert.assertEquals(2, timer.getTimer());
        Assert.assertEquals(0, timer.getTicksLeft());
        Assert.assertTrue(timer.getOutput());

        Assert.assertTrue(timer.nextTick());
        Assert.assertEquals(0, timer.getTimer());
        Assert.assertEquals(2, timer.getTicksLeft());
        Assert.assertFalse(timer.getOutput());
    }
}
