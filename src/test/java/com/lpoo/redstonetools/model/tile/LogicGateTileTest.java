package com.lpoo.redstonetools.model.tile;

import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.model.tile.strategy.LogicGateStrategy;
import com.lpoo.redstonetools.model.tile.strategy.LogicGateStrategyType;
import com.lpoo.redstonetools.model.utils.*;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.lifecycle.BeforeProperty;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Map;

@Tag("model") @net.jqwik.api.Tag("model")
public class LogicGateTileTest {

    private class StubLogicGateStrategy implements LogicGateStrategy {

        @Override
        public boolean logic(Map<Side, Integer> inputs, Map<Side, SideType> sideTypes) {
            return !Power.isOn(inputs.getOrDefault(Side.LEFT, Power.getMin()));
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

    @BeforeEach
    @BeforeProperty
    public void setup() {
        Position position = Mockito.mock(Position.class);
        Mockito.when(position.getX()).thenReturn(1);
        Mockito.when(position.getY()).thenReturn(2);

        this.logicGate = new LogicGateTile(position, new StubLogicGateStrategy());
    }

    @Test
    @Tag("unit-test") @Tag("fast")
    public void testLogicGate() {
        Assertions.assertEquals(1, logicGate.getPosition().getX());
        Assertions.assertEquals(2, logicGate.getPosition().getY());
        Assertions.assertEquals("stub", logicGate.getName());
        Assertions.assertEquals(TileType.LOGIC_GATE, logicGate.getType());
        Assertions.assertFalse(logicGate.isWire());
        Assertions.assertFalse(logicGate.isTickedTile());
    }

    @Test
    @Tag("unit-test") @Tag("fast")
    public void testPower() {
        Assertions.assertTrue(logicGate.acceptsPower(Side.LEFT));
        Assertions.assertTrue(logicGate.acceptsPower(Side.RIGHT));
        Assertions.assertFalse(logicGate.acceptsPower(Side.UP));
        Assertions.assertFalse(logicGate.acceptsPower(Side.DOWN));

        Assertions.assertFalse(logicGate.outputsPower(Side.LEFT));
        Assertions.assertFalse(logicGate.outputsPower(Side.RIGHT));
        Assertions.assertTrue(logicGate.outputsPower(Side.UP));
        Assertions.assertFalse(logicGate.outputsPower(Side.DOWN));

        for (Side side : Side.values()) {
            if (logicGate.outputsPower(side)) {
                Assertions.assertEquals(Power.getMax(), logicGate.getPower(side));
            } else {
                Assertions.assertEquals(Power.getMin(), logicGate.getPower(side));
            }
        }

        // Force deactivation of logic gate
        logicGate.setStatus(false);

        for (Side side : Side.values()) {
            Assertions.assertEquals(Power.getMin(), logicGate.getPower(side));
        }
    }

    @Test
    @Tag("unit-test") @Tag("fast")
    public void testRotation() {
        Circuit circuit = Mockito.mock(Circuit.class);

        Assertions.assertTrue(logicGate.acceptsPower(Side.LEFT));
        Assertions.assertTrue(logicGate.acceptsPower(Side.RIGHT));
        Assertions.assertFalse(logicGate.acceptsPower(Side.UP));
        Assertions.assertFalse(logicGate.acceptsPower(Side.DOWN));


        Assertions.assertFalse(logicGate.outputsPower(Side.LEFT));
        Assertions.assertFalse(logicGate.outputsPower(Side.RIGHT));
        Assertions.assertTrue(logicGate.outputsPower(Side.UP));
        Assertions.assertFalse(logicGate.outputsPower(Side.DOWN));

        logicGate.rotateRight(circuit);

        Assertions.assertFalse(logicGate.acceptsPower(Side.LEFT));
        Assertions.assertFalse(logicGate.acceptsPower(Side.RIGHT));
        Assertions.assertTrue(logicGate.acceptsPower(Side.UP));
        Assertions.assertTrue(logicGate.acceptsPower(Side.DOWN));


        Assertions.assertFalse(logicGate.outputsPower(Side.LEFT));
        Assertions.assertTrue(logicGate.outputsPower(Side.RIGHT));
        Assertions.assertFalse(logicGate.outputsPower(Side.UP));
        Assertions.assertFalse(logicGate.outputsPower(Side.DOWN));

        logicGate.rotateLeft(circuit); logicGate.rotateLeft(circuit);

        Assertions.assertFalse(logicGate.acceptsPower(Side.LEFT));
        Assertions.assertFalse(logicGate.acceptsPower(Side.RIGHT));
        Assertions.assertTrue(logicGate.acceptsPower(Side.UP));
        Assertions.assertTrue(logicGate.acceptsPower(Side.DOWN));


        Assertions.assertTrue(logicGate.outputsPower(Side.LEFT));
        Assertions.assertFalse(logicGate.outputsPower(Side.RIGHT));
        Assertions.assertFalse(logicGate.outputsPower(Side.UP));
        Assertions.assertFalse(logicGate.outputsPower(Side.DOWN));
    }

    @Test
    @Tag("unit-test") @Tag("fast")
    public void testOnChange() {
        Circuit circuit = Mockito.mock(Circuit.class);

        Assertions.assertEquals(Power.getMax(), logicGate.getPower(Side.UP));

        Assertions.assertFalse(logicGate.onChange(circuit, Power.getMin(), Side.LEFT));

        Assertions.assertEquals(Power.getMax(), logicGate.getPower(Side.UP));

        Assertions.assertTrue(logicGate.onChange(circuit, Power.getMax(), Side.LEFT));

        Assertions.assertEquals(Power.getMin(), logicGate.getPower(Side.UP));

        Assertions.assertTrue(logicGate.onChange(circuit, Power.getMin(), Side.LEFT));

        Assertions.assertEquals(Power.getMax(), logicGate.getPower(Side.UP));

        Assertions.assertFalse(logicGate.onChange(circuit, Power.getMax(), Side.RIGHT));

        Assertions.assertEquals(Power.getMax(), logicGate.getPower(Side.UP));

        Assertions.assertFalse(logicGate.onChange(circuit, Power.getMin(), Side.RIGHT));

        Assertions.assertEquals(Power.getMax(), logicGate.getPower(Side.UP));
    }

    @Property
    @net.jqwik.api.Tag("unit-test") @net.jqwik.api.Tag("slow")
    public void testUpdateOnNonInputSides(@ForAll int power) {
        Circuit circuit = Mockito.mock(Circuit.class);

        LogicGateTile logicGateSpy = Mockito.spy(logicGate);

        Assertions.assertFalse(logicGateSpy.update(circuit, power, Side.UP));
        Assertions.assertFalse(logicGateSpy.update(circuit, power, Side.DOWN));

        Mockito.verify(logicGateSpy, Mockito.times(0)).onChange(Mockito.eq(circuit), Mockito.eq(power), Mockito.any(Side.class));
    }

    @Test
    @Tag("unit-test") @Tag("fast")
    public void testUpdate() {
        Circuit circuit = Mockito.mock(Circuit.class);

        Assertions.assertEquals(Power.getMax(), logicGate.getPower(Side.UP));

        for (Side side : Side.values()) {
            Assertions.assertFalse(logicGate.update(circuit, Power.getMin(), side));
        }

        Assertions.assertEquals(Power.getMax(), logicGate.getPower(Side.UP));

        Assertions.assertTrue(logicGate.update(circuit, Power.getMax(), Side.LEFT));

        Assertions.assertEquals(Power.getMin(), logicGate.getPower(Side.UP));

        Assertions.assertFalse(logicGate.update(circuit, 13, Side.LEFT));

        Assertions.assertEquals(Power.getMin(), logicGate.getPower(Side.UP));

        Assertions.assertTrue(logicGate.update(circuit, Power.getMin(), Side.LEFT));

        Assertions.assertEquals(Power.getMax(), logicGate.getPower(Side.UP));
    }
}
