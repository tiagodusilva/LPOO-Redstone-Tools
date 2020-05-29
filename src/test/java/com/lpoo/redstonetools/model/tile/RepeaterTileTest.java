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

public class RepeaterTileTest {

    private RepeaterTile repeater;

    @BeforeEach
    @BeforeProperty
    public void setup() {
        Position position = Mockito.mock(Position.class);
        Mockito.when(position.getX()).thenReturn(1);
        Mockito.when(position.getY()).thenReturn(2);

        this.repeater = new RepeaterTile(position);
    }

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testRepeater() {
        Assertions.assertEquals(1, repeater.getPosition().getX());
        Assertions.assertEquals(2, repeater.getPosition().getY());
        Assertions.assertEquals(TileType.REPEATER, repeater.getType());
        Assertions.assertFalse(repeater.isWire());
        Assertions.assertFalse(repeater.isTickedTile());
    }

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testPower() {
        Assertions.assertTrue(repeater.acceptsPower(Side.LEFT));
        Assertions.assertFalse(repeater.acceptsPower(Side.RIGHT));
        Assertions.assertFalse(repeater.acceptsPower(Side.UP));
        Assertions.assertFalse(repeater.acceptsPower(Side.DOWN));

        Assertions.assertTrue(repeater.outputsPower(Side.RIGHT));
        Assertions.assertFalse(repeater.outputsPower(Side.LEFT));
        Assertions.assertFalse(repeater.outputsPower(Side.UP));
        Assertions.assertFalse(repeater.outputsPower(Side.DOWN));

        // deactivate repeater
        repeater.setStatus(false);

        for (Side side : Side.values()) {
            Assertions.assertEquals(Power.getMin(), repeater.getPower(side));
        }

        // activate repeater
        repeater.setStatus(true);

        for (Side side : Side.values()) {
            if (repeater.outputsPower(side)) {
                Assertions.assertEquals(Power.getMax(), repeater.getPower(side));
            } else {
                Assertions.assertEquals(Power.getMin(), repeater.getPower(side));
            }
        }
    }

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testRotation() {
        Circuit circuit = Mockito.mock(Circuit.class);

        Assertions.assertTrue(repeater.acceptsPower(Side.LEFT));
        Assertions.assertFalse(repeater.acceptsPower(Side.RIGHT));
        Assertions.assertFalse(repeater.acceptsPower(Side.UP));
        Assertions.assertFalse(repeater.acceptsPower(Side.DOWN));


        Assertions.assertFalse(repeater.outputsPower(Side.LEFT));
        Assertions.assertTrue(repeater.outputsPower(Side.RIGHT));
        Assertions.assertFalse(repeater.outputsPower(Side.UP));
        Assertions.assertFalse(repeater.outputsPower(Side.DOWN));

        repeater.rotateRight(circuit);

        Assertions.assertFalse(repeater.acceptsPower(Side.LEFT));
        Assertions.assertFalse(repeater.acceptsPower(Side.RIGHT));
        Assertions.assertTrue(repeater.acceptsPower(Side.UP));
        Assertions.assertFalse(repeater.acceptsPower(Side.DOWN));


        Assertions.assertFalse(repeater.outputsPower(Side.LEFT));
        Assertions.assertFalse(repeater.outputsPower(Side.RIGHT));
        Assertions.assertFalse(repeater.outputsPower(Side.UP));
        Assertions.assertTrue(repeater.outputsPower(Side.DOWN));

        repeater.rotateLeft(circuit); repeater.rotateLeft(circuit);

        Assertions.assertFalse(repeater.acceptsPower(Side.LEFT));
        Assertions.assertFalse(repeater.acceptsPower(Side.RIGHT));
        Assertions.assertFalse(repeater.acceptsPower(Side.UP));
        Assertions.assertTrue(repeater.acceptsPower(Side.DOWN));


        Assertions.assertFalse(repeater.outputsPower(Side.LEFT));
        Assertions.assertFalse(repeater.outputsPower(Side.RIGHT));
        Assertions.assertTrue(repeater.outputsPower(Side.UP));
        Assertions.assertFalse(repeater.outputsPower(Side.DOWN));
    }

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testOnChange() {
        Circuit circuit = Mockito.mock(Circuit.class);

        Assertions.assertFalse(repeater.getStatus());

        Assertions.assertTrue(repeater.onChange(circuit, Power.getMax(), Side.LEFT));

        Assertions.assertTrue(repeater.getStatus());

        Assertions.assertTrue(repeater.onChange(circuit, 5, Side.LEFT));

        Assertions.assertTrue(repeater.getStatus());

        Assertions.assertTrue(repeater.onChange(circuit, Power.getMin(), Side.LEFT));

        Assertions.assertFalse(repeater.getStatus());
    }

    @Property
    @net.jqwik.api.Tag("model")
    @net.jqwik.api.Tag("unit-test") @net.jqwik.api.Tag("slow")
    public void testUpdateOnNonInputSide(@ForAll int power) {
        Circuit circuit = Mockito.mock(Circuit.class);

        RepeaterTile repeaterSpy = Mockito.spy(repeater);

        Assertions.assertFalse(repeaterSpy.update(circuit, power, Side.UP));
        Assertions.assertFalse(repeaterSpy.update(circuit, power, Side.RIGHT));
        Assertions.assertFalse(repeaterSpy.update(circuit, power, Side.DOWN));

        Mockito.verify(repeaterSpy, Mockito.times(0)).onChange(Mockito.eq(circuit), Mockito.eq(power), Mockito.any(Side.class));
    }

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testUpdate() {
        Circuit circuit = Mockito.mock(Circuit.class);

        Assertions.assertEquals(Power.getMin(), repeater.getPower(Side.RIGHT));

        for (Side side : Side.values()) {
            Assertions.assertFalse(repeater.update(circuit, Power.getMin(), side));
        }
    }
}
