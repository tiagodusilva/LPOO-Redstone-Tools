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
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class NotTileTest {

    private NotGateTile notGate;

    @BeforeEach
    @BeforeProperty
    public void setup() {
        Position position = Mockito.mock(Position.class);
        Mockito.when(position.getX()).thenReturn(1);
        Mockito.when(position.getY()).thenReturn(2);

        this.notGate = new NotGateTile(position);
    }

    @Test
    public void testNotGate() {
        Assertions.assertEquals(1, notGate.getPosition().getX());
        Assertions.assertEquals(2, notGate.getPosition().getY());
        Assertions.assertEquals(TileType.NOT_GATE, notGate.getType());
        Assertions.assertFalse(notGate.isWire());
        Assertions.assertFalse(notGate.isTickedTile());
    }

    @Test
    public void testPower() {
        Assertions.assertTrue(notGate.acceptsPower(Side.LEFT));
        Assertions.assertFalse(notGate.acceptsPower(Side.RIGHT));
        Assertions.assertFalse(notGate.acceptsPower(Side.UP));
        Assertions.assertFalse(notGate.acceptsPower(Side.DOWN));

        Assertions.assertTrue(notGate.outputsPower(Side.RIGHT));
        Assertions.assertFalse(notGate.outputsPower(Side.LEFT));
        Assertions.assertFalse(notGate.outputsPower(Side.UP));
        Assertions.assertFalse(notGate.outputsPower(Side.DOWN));

        for (Side side : Side.values()) {
            Assertions.assertEquals(Power.getMin(), notGate.getPower(side));
        }

        // activate not gate
        notGate.setStatus(true);

        for (Side side : Side.values()) {
            if (notGate.outputsPower(side)) {
                Assertions.assertEquals(Power.getMax(), notGate.getPower(side));
            } else {
                Assertions.assertEquals(Power.getMin(), notGate.getPower(side));
            }
        }
    }

    @Test
    public void testRotation() {
        Circuit circuit = Mockito.mock(Circuit.class);

        Assertions.assertTrue(notGate.acceptsPower(Side.LEFT));
        Assertions.assertFalse(notGate.acceptsPower(Side.RIGHT));
        Assertions.assertFalse(notGate.acceptsPower(Side.UP));
        Assertions.assertFalse(notGate.acceptsPower(Side.DOWN));


        Assertions.assertFalse(notGate.outputsPower(Side.LEFT));
        Assertions.assertTrue(notGate.outputsPower(Side.RIGHT));
        Assertions.assertFalse(notGate.outputsPower(Side.UP));
        Assertions.assertFalse(notGate.outputsPower(Side.DOWN));

        notGate.rotateRight(circuit);

        Assertions.assertFalse(notGate.acceptsPower(Side.LEFT));
        Assertions.assertFalse(notGate.acceptsPower(Side.RIGHT));
        Assertions.assertTrue(notGate.acceptsPower(Side.UP));
        Assertions.assertFalse(notGate.acceptsPower(Side.DOWN));


        Assertions.assertFalse(notGate.outputsPower(Side.LEFT));
        Assertions.assertFalse(notGate.outputsPower(Side.RIGHT));
        Assertions.assertFalse(notGate.outputsPower(Side.UP));
        Assertions.assertTrue(notGate.outputsPower(Side.DOWN));

        notGate.rotateLeft(circuit); notGate.rotateLeft(circuit);

        Assertions.assertFalse(notGate.acceptsPower(Side.LEFT));
        Assertions.assertFalse(notGate.acceptsPower(Side.RIGHT));
        Assertions.assertFalse(notGate.acceptsPower(Side.UP));
        Assertions.assertTrue(notGate.acceptsPower(Side.DOWN));


        Assertions.assertFalse(notGate.outputsPower(Side.LEFT));
        Assertions.assertFalse(notGate.outputsPower(Side.RIGHT));
        Assertions.assertTrue(notGate.outputsPower(Side.UP));
        Assertions.assertFalse(notGate.outputsPower(Side.DOWN));
    }

    @Test
    public void testOnChange() {
        Circuit circuit = Mockito.mock(Circuit.class);

        Assertions.assertEquals(Power.getMin(), notGate.getPower(Side.RIGHT));

        Assertions.assertTrue(notGate.onChange(circuit, Power.getMin(), Side.LEFT));

        Assertions.assertEquals(Power.getMax(), notGate.getPower(Side.RIGHT));

        Assertions.assertFalse(notGate.onChange(circuit, Power.getMin(), Side.LEFT));

        Assertions.assertEquals(Power.getMax(), notGate.getPower(Side.RIGHT));

        Assertions.assertTrue(notGate.onChange(circuit, Power.getMax(), Side.LEFT));

        Assertions.assertEquals(Power.getMin(), notGate.getPower(Side.RIGHT));

        Assertions.assertFalse(notGate.onChange(circuit, Power.getMax(), Side.LEFT));

        Assertions.assertEquals(Power.getMin(), notGate.getPower(Side.RIGHT));
    }

    @Property
    public void testUpdateOnNonInputSide(@ForAll int power) {
        Circuit circuit = Mockito.mock(Circuit.class);

        NotGateTile notGateSpy = Mockito.spy(notGate);

        Assertions.assertFalse(notGateSpy.update(circuit, power, Side.UP));
        Assertions.assertFalse(notGateSpy.update(circuit, power, Side.RIGHT));
        Assertions.assertFalse(notGateSpy.update(circuit, power, Side.DOWN));

        Mockito.verify(notGateSpy, Mockito.times(0)).onChange(Mockito.eq(circuit), Mockito.eq(power), Mockito.any(Side.class));
    }

    @Test
    public void testUpdate() {
        Circuit circuit = Mockito.mock(Circuit.class);

        notGate.setStatus(false);

        Assertions.assertTrue(notGate.update(circuit, Power.getMin(), Side.LEFT));

        Assertions.assertEquals(Power.getMax(), notGate.getPower(Side.RIGHT));

        Assertions.assertFalse(notGate.update(circuit, Power.getMin(), Side.LEFT));

        Assertions.assertEquals(Power.getMax(), notGate.getPower(Side.RIGHT));

        Assertions.assertTrue(notGate.update(circuit, 6, Side.LEFT));

        Assertions.assertEquals(Power.getMin(), notGate.getPower(Side.RIGHT));
    }
}
