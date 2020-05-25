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

public class CrossWireTileTest {

    private CrossWireTile crossWire;

    @Before
    public void setup() {
        Position position = Mockito.mock(Position.class);
        Mockito.when(position.getX()).thenReturn(1);
        Mockito.when(position.getY()).thenReturn(2);

        this.crossWire = new CrossWireTile(position);
    }

    @Test
    public void testTimer() {
        Assert.assertEquals(1, crossWire.getPosition().getX());
        Assert.assertEquals(2, crossWire.getPosition().getY());
        Assert.assertEquals(TileType.CROSSWIRE, crossWire.getType());
        Assert.assertFalse(crossWire.isTickedTile());
    }

    @Test
    public void testOnChange() {

        Circuit circuit = Mockito.mock(Circuit.class);

        Assert.assertTrue(crossWire.onChange(circuit, Power.getMax(), Side.UP));
        Assert.assertEquals(Power.getMax(), crossWire.getPower(Side.UP));
        Assert.assertEquals(Power.getMax(), crossWire.getPower(Side.DOWN));
        Assert.assertEquals(Power.getMin(), crossWire.getPower(Side.RIGHT));
        Assert.assertEquals(Power.getMin(), crossWire.getPower(Side.LEFT));

        Assert.assertTrue(crossWire.onChange(circuit, Power.getMax(), Side.RIGHT));
        Assert.assertEquals(Power.getMax(), crossWire.getPower(Side.UP));
        Assert.assertEquals(Power.getMax(), crossWire.getPower(Side.DOWN));
        Assert.assertEquals(Power.getMax(), crossWire.getPower(Side.RIGHT));
        Assert.assertEquals(Power.getMax(), crossWire.getPower(Side.LEFT));

        Assert.assertTrue(crossWire.onChange(circuit, Power.getMin(), Side.DOWN));
        Assert.assertEquals(Power.getMin(), crossWire.getPower(Side.UP));
        Assert.assertEquals(Power.getMin(), crossWire.getPower(Side.DOWN));
        Assert.assertEquals(Power.getMax(), crossWire.getPower(Side.RIGHT));
        Assert.assertEquals(Power.getMax(), crossWire.getPower(Side.LEFT));

        Assert.assertTrue(crossWire.onChange(circuit, Power.getMax(), Side.LEFT));
        Assert.assertEquals(Power.getMin(), crossWire.getPower(Side.UP));
        Assert.assertEquals(Power.getMin(), crossWire.getPower(Side.DOWN));
        Assert.assertEquals(Power.getMax(), crossWire.getPower(Side.RIGHT));
        Assert.assertEquals(Power.getMax(), crossWire.getPower(Side.LEFT));

    }

    @Test
    public void testUpdate() {
        Circuit circuit = Mockito.mock(Circuit.class);
        Mockito.when(circuit
            .getSurroundingPower(crossWire.getPosition(), Side.UP))
                .thenReturn(Power.getMax());

        Mockito.when(circuit
                .getSurroundingPower(crossWire.getPosition(), Side.DOWN))
                .thenReturn(Power.getMin());

        Mockito.when(circuit
                .getSurroundingPower(crossWire.getPosition(), Side.LEFT))
                .thenReturn(Power.getMin());

        Mockito.when(circuit
                .getSurroundingPower(crossWire.getPosition(), Side.RIGHT))
                .thenReturn(Power.getMin());

        CrossWireTile crossWireSpy = Mockito.spy(crossWire);

        Assert.assertFalse(crossWireSpy.update(circuit, Power.getMin(), Side.RIGHT));
        Mockito.verify(crossWireSpy, Mockito.times(0))
                .onChange(Mockito.any(Circuit.class), Mockito.anyInt(), Mockito.any(Side.class));

        Assert.assertTrue(crossWireSpy.update(circuit, Power.getMax(), Side.UP));
        Mockito.verify(crossWireSpy, Mockito.times(1))
                .onChange(Mockito.any(Circuit.class), Mockito.anyInt(), Mockito.any(Side.class));

        Assert.assertFalse(crossWireSpy.update(circuit, Power.getMin(), Side.UP));
        Mockito.verify(crossWireSpy, Mockito.times(1))
                .onChange(Mockito.any(Circuit.class), Mockito.anyInt(), Mockito.any(Side.class));

        Assert.assertFalse(crossWireSpy.update(circuit, Power.getMin(), Side.LEFT));
        Mockito.verify(crossWireSpy, Mockito.times(1))
                .onChange(Mockito.any(Circuit.class), Mockito.anyInt(), Mockito.any(Side.class));

        Mockito.when(circuit
                .getSurroundingPower(crossWire.getPosition(), Side.LEFT))
                .thenReturn(Power.getMax());

        Assert.assertTrue(crossWireSpy.update(circuit, Power.getMax(), Side.LEFT));
        Mockito.verify(crossWireSpy, Mockito.times(2))
                .onChange(Mockito.any(Circuit.class), Mockito.anyInt(), Mockito.any(Side.class));

        Mockito.when(circuit
                .getSurroundingPower(crossWire.getPosition(), Side.RIGHT))
                .thenReturn(Power.getMax());

        Assert.assertFalse(crossWireSpy.update(circuit, Power.getMax(), Side.RIGHT));
        Mockito.verify(crossWireSpy, Mockito.times(2))
                .onChange(Mockito.any(Circuit.class), Mockito.anyInt(), Mockito.any(Side.class));
    }

    @Test
    public void testWireConnections() {
        for (Side side : Side.values()) {
            Assert.assertFalse(crossWire.isConnected(side));
        }

        Circuit circuit = Mockito.mock(Circuit.class);

        Mockito.when(circuit.canTilesConnect(crossWire.getPosition(), Side.UP)).thenReturn(true);
        Mockito.when(circuit.canTilesConnect(crossWire.getPosition(), Side.DOWN)).thenReturn(false);
        Mockito.when(circuit.canTilesConnect(crossWire.getPosition(), Side.RIGHT)).thenReturn(true);
        Mockito.when(circuit.canTilesConnect(crossWire.getPosition(), Side.LEFT)).thenReturn(false);

        crossWire.updateConnections(circuit);

        Assert.assertTrue(crossWire.isConnected(Side.UP));
        Assert.assertFalse(crossWire.isConnected(Side.DOWN));
        Assert.assertTrue(crossWire.isConnected(Side.RIGHT));
        Assert.assertFalse(crossWire.isConnected(Side.LEFT));
    }
}
