package com.lpoo.redstonetools.model.tile;

import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.model.utils.*;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.lifecycle.BeforeProperty;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class DigitTileTest {

    private DigitTile digit;
    private Position position;

    @BeforeEach
    @BeforeProperty
    public void setup() {
        position = Mockito.mock(Position.class);
        Mockito.when(position.getX()).thenReturn(1);
        Mockito.when(position.getY()).thenReturn(2);

        this.digit = new DigitTile(position);
    }

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testDigit() {
        Assertions.assertEquals(1, digit.getPosition().getX());
        Assertions.assertEquals(2, digit.getPosition().getY());
        Assertions.assertEquals(TileType.DIGIT, digit.getType());
        Assertions.assertFalse(digit.isWire());
        Assertions.assertFalse(digit.isTickedTile());
        for (Side side : Side.values()) {
            Assertions.assertTrue(digit.acceptsPower(side));
            Assertions.assertFalse(digit.outputsPower(side));
            Assertions.assertEquals(Power.getMin(), digit.getPower(side));
        }
    }

    @Property
    @net.jqwik.api.Tag("model")
    @net.jqwik.api.Tag("unit-test") @net.jqwik.api.Tag("slow")
    public void testOnChange(@ForAll int power, @ForAll Side side) {
        Circuit circuit = Mockito.mock(Circuit.class);
        Assertions.assertFalse(digit.onChange(circuit, power, side));
        Assertions.assertEquals(power, digit.getPowerLevel());
    }

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testOnUpdate() {
        Circuit circuit = Mockito.mock(Circuit.class);

        Assertions.assertEquals(Power.getMin(), digit.getPowerLevel());
        Mockito.when(circuit.getSurroundingPower(position)).thenReturn(2);
        Assertions.assertFalse(digit.update(circuit, 2, Side.UP));
        Assertions.assertEquals(2, digit.getPowerLevel());
        Assertions.assertFalse(digit.update(circuit, Power.getMax(), Side.UP));
        Assertions.assertEquals(2, digit.getPowerLevel());
        Mockito.when(circuit.getSurroundingPower(position)).thenReturn(7);
        Assertions.assertFalse(digit.update(circuit, 7, Side.UP));
        Assertions.assertEquals(7, digit.getPowerLevel());
    }

    @Property
    @net.jqwik.api.Tag("model")
    @net.jqwik.api.Tag("unit-test") @net.jqwik.api.Tag("slow")
    public void testOnUpdateProperty(@ForAll int power, @ForAll Side side) {
        Circuit circuit = Mockito.mock(Circuit.class);
        Mockito.when(circuit.getSurroundingPower(position)).thenReturn(power);
        Assertions.assertFalse(digit.update(circuit, power, side));
        Assertions.assertEquals(power, digit.getPowerLevel());
    }

}
