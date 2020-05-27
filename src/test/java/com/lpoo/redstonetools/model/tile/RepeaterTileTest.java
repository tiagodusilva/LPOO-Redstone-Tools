package com.lpoo.redstonetools.model.tile;

import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.model.utils.Position;
import com.lpoo.redstonetools.model.utils.Power;
import com.lpoo.redstonetools.model.utils.Side;
import com.lpoo.redstonetools.model.utils.TileType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class RepeaterTileTest {

    private RepeaterTile repeater;

    @BeforeEach
    public void setup() {
        Position position = Mockito.mock(Position.class);
        Mockito.when(position.getX()).thenReturn(1);
        Mockito.when(position.getY()).thenReturn(2);

        this.repeater = new RepeaterTile(position);
    }

    @Test
    public void testRepeater() {
        Assertions.assertEquals(1, repeater.getPosition().getX());
        Assertions.assertEquals(2, repeater.getPosition().getY());
        Assertions.assertEquals(TileType.REPEATER, repeater.getType());
    }

    @Test
    public void testRepeaterPower() {
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
    public void testRepeaterRotation() {
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
    public void testRepeaterOnChange() {
        Circuit circuit = Mockito.mock(Circuit.class);

        Assertions.assertFalse(repeater.getStatus());

        Assertions.assertTrue(repeater.onChange(circuit, Power.getMax(), Side.LEFT));

        Assertions.assertTrue(repeater.getStatus());

        Assertions.assertTrue(repeater.onChange(circuit, 5, Side.LEFT));

        Assertions.assertTrue(repeater.getStatus());

        Assertions.assertTrue(repeater.onChange(circuit, Power.getMin(), Side.LEFT));

        Assertions.assertFalse(repeater.getStatus());
    }

    @Test
    public void testRepeaterUpdate() {
        Circuit circuit = Mockito.mock(Circuit.class);

        Assertions.assertEquals(Power.getMin(), repeater.getPower(Side.RIGHT));

        for (Side side : Side.values()) {
            Assertions.assertFalse(repeater.update(circuit, Power.getMin(), side));
        }
    }
}
