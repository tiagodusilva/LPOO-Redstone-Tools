package com.lpoo.redstonetools.model.tile;

import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.model.tile.strategy.LogicGateStrategy;
import com.lpoo.redstonetools.model.tile.strategy.LogicGateStrategyType;
import com.lpoo.redstonetools.model.utils.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Map;

public class LogicGateTileTest {

    private class StubLogicGateStrategy implements LogicGateStrategy {

        @Override
        public boolean logic(Map<Side, Integer> inputs, Map<Side, SideType> sideTypes) {
            int last_power = -1;
            // Tests if it is receiving power and if the powers that it receives are all exactly equal
            for (Side side : Side.values()) {
                if (sideTypes.getOrDefault(side, SideType.DEFAULT).isInput()) {
                    if (last_power != -1) {
                        int current = inputs.getOrDefault(side, Power.getMin());
                        if (current != last_power) return false;
                        last_power = current;
                    } else {
                        last_power = inputs.getOrDefault(side, Power.getMin());
                    }
                }
            }
            return last_power != -1;
        }

        @Override
        public String getName() {
            return "stub";
        }

        @Override
        public LogicGateStrategyType getType() {
            return LogicGateStrategyType.AND;
        }
    }

    private LogicGateTile logicGate;

    private Position expectedLogicGatePosition;

    @Before
    public void setup() {
        Position position = Mockito.mock(Position.class);
        Mockito.when(position.getX()).thenReturn(1);
        Mockito.when(position.getY()).thenReturn(2);

        expectedLogicGatePosition = Mockito.mock(Position.class);
        Mockito.when(expectedLogicGatePosition.getX()).thenReturn(1);
        Mockito.when(expectedLogicGatePosition.getY()).thenReturn(2);

        this.logicGate = new LogicGateTile(position, new StubLogicGateStrategy());
    }

    @Test
    public void testLogicGate() {
        Assert.assertEquals(expectedLogicGatePosition.getX(), logicGate.getPosition().getX());
        Assert.assertEquals(expectedLogicGatePosition.getY(), logicGate.getPosition().getY());
        Assert.assertEquals("stub", logicGate.getName());
        Assert.assertEquals("Active : true", logicGate.getInfo());
        Assert.assertEquals(TileType.LOGIC_GATE, logicGate.getType());
    }

    @Test
    public void testPower() {
        Assert.assertTrue(logicGate.acceptsPower(Side.LEFT));
        Assert.assertTrue(logicGate.acceptsPower(Side.RIGHT));
        Assert.assertFalse(logicGate.acceptsPower(Side.UP));
        Assert.assertFalse(logicGate.acceptsPower(Side.DOWN));

        Assert.assertFalse(logicGate.outputsPower(Side.LEFT));
        Assert.assertFalse(logicGate.outputsPower(Side.RIGHT));
        Assert.assertTrue(logicGate.outputsPower(Side.UP));
        Assert.assertFalse(logicGate.outputsPower(Side.DOWN));

        for (Side side : Side.values()) {
            if (logicGate.outputsPower(side)) {
                Assert.assertEquals(Power.getMax(), logicGate.getPower(side));
            } else {
                Assert.assertEquals(Power.getMin(), logicGate.getPower(side));
            }
        }

        // Force activation of logic gate
        logicGate.setStatus(false);

        for (Side side : Side.values()) {
            Assert.assertEquals(Power.getMin(), logicGate.getPower(side));
        }
    }

    @Test
    public void testRotation() {
        Assert.assertTrue(logicGate.acceptsPower(Side.LEFT));
        Assert.assertTrue(logicGate.acceptsPower(Side.RIGHT));
        Assert.assertFalse(logicGate.acceptsPower(Side.UP));
        Assert.assertFalse(logicGate.acceptsPower(Side.DOWN));


        Assert.assertFalse(logicGate.outputsPower(Side.LEFT));
        Assert.assertFalse(logicGate.outputsPower(Side.RIGHT));
        Assert.assertTrue(logicGate.outputsPower(Side.UP));
        Assert.assertFalse(logicGate.outputsPower(Side.DOWN));

        logicGate.rotateRight();

        Assert.assertFalse(logicGate.acceptsPower(Side.LEFT));
        Assert.assertFalse(logicGate.acceptsPower(Side.RIGHT));
        Assert.assertTrue(logicGate.acceptsPower(Side.UP));
        Assert.assertTrue(logicGate.acceptsPower(Side.DOWN));


        Assert.assertFalse(logicGate.outputsPower(Side.LEFT));
        Assert.assertTrue(logicGate.outputsPower(Side.RIGHT));
        Assert.assertFalse(logicGate.outputsPower(Side.UP));
        Assert.assertFalse(logicGate.outputsPower(Side.DOWN));

        logicGate.rotateLeft(); logicGate.rotateLeft();

        Assert.assertFalse(logicGate.acceptsPower(Side.LEFT));
        Assert.assertFalse(logicGate.acceptsPower(Side.RIGHT));
        Assert.assertTrue(logicGate.acceptsPower(Side.UP));
        Assert.assertTrue(logicGate.acceptsPower(Side.DOWN));


        Assert.assertTrue(logicGate.outputsPower(Side.LEFT));
        Assert.assertFalse(logicGate.outputsPower(Side.RIGHT));
        Assert.assertFalse(logicGate.outputsPower(Side.UP));
        Assert.assertFalse(logicGate.outputsPower(Side.DOWN));
    }

    @Test
    public void testOnChange() {
        Circuit circuit = Mockito.mock(Circuit.class);

        Assert.assertEquals(Power.getMax(), logicGate.getPower(Side.UP));

        Assert.assertFalse(logicGate.onChange(circuit, Power.getMin(), Side.LEFT));

        Assert.assertEquals(Power.getMax(), logicGate.getPower(Side.UP));

        Assert.assertTrue(logicGate.onChange(circuit, Power.getMax(), Side.LEFT));

        Assert.assertEquals(Power.getMin(), logicGate.getPower(Side.UP));

        Assert.assertTrue(logicGate.onChange(circuit, Power.getMax(), Side.RIGHT));

        Assert.assertEquals(Power.getMax(), logicGate.getPower(Side.UP));

        Assert.assertTrue(logicGate.onChange(circuit, 13, Side.RIGHT));

        Assert.assertEquals(Power.getMin(), logicGate.getPower(Side.UP));
    }

    @Test
    public void testUpdate() {
        Circuit circuit = Mockito.mock(Circuit.class);

        Assert.assertEquals(Power.getMax(), logicGate.getPower(Side.UP));

        for (Side side : Side.values()) {
            Assert.assertFalse(logicGate.update(circuit, Power.getMin(), side));
        }

        Assert.assertEquals(Power.getMax(), logicGate.getPower(Side.UP));

        Assert.assertFalse(logicGate.update(circuit, Power.getMax(), Side.DOWN));
        Assert.assertFalse(logicGate.update(circuit, Power.getMax(), Side.UP));

        Assert.assertEquals(Power.getMax(), logicGate.getPower(Side.UP));

        Assert.assertTrue(logicGate.update(circuit, Power.getMax(), Side.LEFT));

        Assert.assertEquals(Power.getMin(), logicGate.getPower(Side.UP));

        Assert.assertTrue(logicGate.update(circuit, Power.getMax(), Side.RIGHT));

        Assert.assertEquals(Power.getMax(), logicGate.getPower(Side.UP));
    }
}
