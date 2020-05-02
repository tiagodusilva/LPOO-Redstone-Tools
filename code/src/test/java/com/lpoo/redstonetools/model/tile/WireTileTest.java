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

public class WireTileTest {

    private WireTile wire;

    private Position expectedWirePosition;

    @Before
    public void setup() {
        Position position = Mockito.mock(Position.class);
        Mockito.when(position.getX()).thenReturn(1);
        Mockito.when(position.getY()).thenReturn(2);

        expectedWirePosition = Mockito.mock(Position.class);
        Mockito.when(expectedWirePosition.getX()).thenReturn(1);
        Mockito.when(expectedWirePosition.getY()).thenReturn(2);

        this.wire = new WireTile(position);
    }

    @Test
    public void testWire() {
        Assert.assertEquals(expectedWirePosition.getX(), wire.getPosition().getX());
        Assert.assertEquals(expectedWirePosition.getY(), wire.getPosition().getY());
        Assert.assertEquals("wire", wire.getName());
        Assert.assertEquals("Power : " + Power.getMin(), wire.getInfo());
        Assert.assertEquals(TileType.WIRE, wire.getType());
        Assert.assertFalse(wire.isSource());
        Assert.assertTrue(wire.isWire());
    }

    @Test
    public void testWirePower() {
        for (Side side : Side.values()) {
            Assert.assertTrue(wire.acceptsPower(side));
            Assert.assertTrue(wire.outputsPower(side));
            Assert.assertEquals(Power.getMin(), wire.getPower(side));
        }

        Circuit circuit = Mockito.mock(Circuit.class);

        Assert.assertTrue(wire.onChange(circuit, Power.getMax(), Side.UP));

        for (Side side : Side.values()) {
            Assert.assertEquals(Power.getMax(), wire.getPower(side));
        }

        Assert.assertTrue(wire.onChange(circuit, Power.decrease(Power.getMax()), Side.LEFT));

        for (Side side : Side.values()) {
            Assert.assertEquals(Power.decrease(Power.getMax()), wire.getPower(side));
        }
    }

    @Test
    public void testWireUpdate() {
        Circuit circuit = Mockito.mock(Circuit.class);

        Mockito.when(circuit.getSurroundingPower(wire.getPosition())).thenReturn(Power.getMin());

        Assert.assertEquals(Power.getMin(), wire.getPower(Side.UP));
        Assert.assertFalse(wire.update(circuit, Power.getMin(), Side.UP));
        Assert.assertEquals(Power.getMin(), wire.getPower(Side.UP));

        Mockito.when(circuit.getSurroundingPower(wire.getPosition())).thenReturn(Power.getMax());

        Assert.assertEquals(Power.getMin(), wire.getPower(Side.UP));
        Assert.assertTrue(wire.update(circuit, Power.getMin(), Side.UP));
        Assert.assertEquals(Power.getMax(), wire.getPower(Side.UP));

        Assert.assertFalse(wire.update(circuit, Power.getMin(), Side.UP));
        Assert.assertEquals(Power.getMax(), wire.getPower(Side.UP));

        Mockito.when(circuit.getSurroundingPower(wire.getPosition())).thenReturn(Power.decrease(Power.getMax()));

        Assert.assertTrue(wire.update(circuit, Power.getMin(), Side.UP));
        Assert.assertEquals(Power.decrease(Power.getMax()), wire.getPower(Side.UP));
    }

    @Test
    public void testWireConnections() {
        for (Side side : Side.values()) {
            Assert.assertFalse(wire.isConnected(side));
        }

        Circuit circuit = Mockito.mock(Circuit.class);

        Mockito.when(circuit.canTilesConnect(wire.getPosition(), Side.UP)).thenReturn(true);
        Mockito.when(circuit.canTilesConnect(wire.getPosition(), Side.DOWN)).thenReturn(false);
        Mockito.when(circuit.canTilesConnect(wire.getPosition(), Side.RIGHT)).thenReturn(true);
        Mockito.when(circuit.canTilesConnect(wire.getPosition(), Side.LEFT)).thenReturn(false);

        wire.updateConnections(circuit);

        Assert.assertTrue(wire.isConnected(Side.UP));
        Assert.assertFalse(wire.isConnected(Side.DOWN));
        Assert.assertTrue(wire.isConnected(Side.RIGHT));
        Assert.assertFalse(wire.isConnected(Side.LEFT));
    }

}
