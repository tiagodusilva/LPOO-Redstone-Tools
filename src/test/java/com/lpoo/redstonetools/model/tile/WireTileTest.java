package com.lpoo.redstonetools.model.tile;

import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.model.utils.Position;
import com.lpoo.redstonetools.model.utils.Power;
import com.lpoo.redstonetools.model.utils.Side;
import com.lpoo.redstonetools.model.utils.TileType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class WireTileTest {

    private WireTile wire;

    @BeforeEach
    public void setup() {
        Position position = Mockito.mock(Position.class);
        Mockito.when(position.getX()).thenReturn(1);
        Mockito.when(position.getY()).thenReturn(2);

        this.wire = new WireTile(position);
    }

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testWire() {
        Assertions.assertEquals(1, wire.getPosition().getX());
        Assertions.assertEquals(2, wire.getPosition().getY());
        Assertions.assertEquals(TileType.WIRE, wire.getType());
        Assertions.assertTrue(wire.isWire());
        Assertions.assertFalse(wire.isTickedTile());
    }

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testPower() {
        for (Side side : Side.values()) {
            Assertions.assertTrue(wire.acceptsPower(side));
            Assertions.assertTrue(wire.outputsPower(side));
            Assertions.assertEquals(Power.getMin(), wire.getPower(side));
        }

        Circuit circuit = Mockito.mock(Circuit.class);

        Assertions.assertTrue(wire.onChange(circuit, Power.getMax(), Side.UP));

        for (Side side : Side.values()) {
            Assertions.assertEquals(Power.getMax(), wire.getPower(side));
        }

        Assertions.assertTrue(wire.onChange(circuit, Power.decrease(Power.getMax()), Side.LEFT));

        for (Side side : Side.values()) {
            Assertions.assertEquals(Power.decrease(Power.getMax()), wire.getPower(side));
        }
    }

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testUpdate() {
        Circuit circuit = Mockito.mock(Circuit.class);

        Mockito.when(circuit.getSurroundingWirePower(wire.getPosition())).thenReturn(Power.getMin());

        Assertions.assertEquals(Power.getMin(), wire.getPower(Side.UP));
        Assertions.assertFalse(wire.update(circuit, Power.getMin(), Side.UP));
        Assertions.assertEquals(Power.getMin(), wire.getPower(Side.UP));

        Mockito.when(circuit.getSurroundingWirePower(wire.getPosition())).thenReturn(Power.getMax());

        Assertions.assertEquals(Power.getMin(), wire.getPower(Side.UP));
        Assertions.assertTrue(wire.update(circuit, Power.getMin(), Side.UP));
        Assertions.assertEquals(Power.getMax(), wire.getPower(Side.UP));

        Assertions.assertFalse(wire.update(circuit, Power.getMin(), Side.UP));
        Assertions.assertEquals(Power.getMax(), wire.getPower(Side.UP));

        Mockito.when(circuit.getSurroundingWirePower(wire.getPosition())).thenReturn(Power.decrease(Power.getMax()));

        Assertions.assertTrue(wire.update(circuit, Power.getMin(), Side.UP));
        Assertions.assertEquals(Power.decrease(Power.getMax()), wire.getPower(Side.UP));
    }

    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testConnections() {
        for (Side side : Side.values()) {
            Assertions.assertFalse(wire.isConnected(side));
        }

        Circuit circuit = Mockito.mock(Circuit.class);

        Mockito.when(circuit.canTilesConnect(wire.getPosition(), Side.UP)).thenReturn(true);
        Mockito.when(circuit.canTilesConnect(wire.getPosition(), Side.DOWN)).thenReturn(false);
        Mockito.when(circuit.canTilesConnect(wire.getPosition(), Side.RIGHT)).thenReturn(true);
        Mockito.when(circuit.canTilesConnect(wire.getPosition(), Side.LEFT)).thenReturn(false);

        wire.updateConnections(circuit);

        Assertions.assertTrue(wire.isConnected(Side.UP));
        Assertions.assertFalse(wire.isConnected(Side.DOWN));
        Assertions.assertTrue(wire.isConnected(Side.RIGHT));
        Assertions.assertFalse(wire.isConnected(Side.LEFT));
    }
}
