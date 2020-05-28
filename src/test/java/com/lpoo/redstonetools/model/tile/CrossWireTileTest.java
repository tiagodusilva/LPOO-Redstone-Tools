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

@Tag("model")
public class CrossWireTileTest {

    private CrossWireTile crossWire;

    @BeforeEach
    public void setup() {
        Position position = Mockito.mock(Position.class);
        Mockito.when(position.getX()).thenReturn(1);
        Mockito.when(position.getY()).thenReturn(2);

        this.crossWire = new CrossWireTile(position);
    }

    @Test
    @Tag("unit-test") @Tag("fast")
    public void testCrossWire() {
        Assertions.assertEquals(1, crossWire.getPosition().getX());
        Assertions.assertEquals(2, crossWire.getPosition().getY());
        Assertions.assertEquals(TileType.CROSSWIRE, crossWire.getType());
        Assertions.assertFalse(crossWire.isTickedTile());
        Assertions.assertTrue(crossWire.isWire());

        for (Side side : Side.values()) {
            Assertions.assertTrue(crossWire.acceptsPower(side));
            Assertions.assertTrue(crossWire.outputsPower(side));
        }
    }

    @Test
    @Tag("unit-test") @Tag("fast")
    public void testOnChange() {

        Circuit circuit = Mockito.mock(Circuit.class);

        Assertions.assertTrue(crossWire.onChange(circuit, Power.getMax(), Side.UP));
        Assertions.assertEquals(Power.getMax(), crossWire.getPower(Side.UP));
        Assertions.assertEquals(Power.getMax(), crossWire.getPower(Side.DOWN));
        Assertions.assertEquals(Power.getMin(), crossWire.getPower(Side.RIGHT));
        Assertions.assertEquals(Power.getMin(), crossWire.getPower(Side.LEFT));

        Assertions.assertTrue(crossWire.onChange(circuit, Power.getMax(), Side.RIGHT));
        Assertions.assertEquals(Power.getMax(), crossWire.getPower(Side.UP));
        Assertions.assertEquals(Power.getMax(), crossWire.getPower(Side.DOWN));
        Assertions.assertEquals(Power.getMax(), crossWire.getPower(Side.RIGHT));
        Assertions.assertEquals(Power.getMax(), crossWire.getPower(Side.LEFT));

        Assertions.assertTrue(crossWire.onChange(circuit, Power.getMin(), Side.DOWN));
        Assertions.assertEquals(Power.getMin(), crossWire.getPower(Side.UP));
        Assertions.assertEquals(Power.getMin(), crossWire.getPower(Side.DOWN));
        Assertions.assertEquals(Power.getMax(), crossWire.getPower(Side.RIGHT));
        Assertions.assertEquals(Power.getMax(), crossWire.getPower(Side.LEFT));

        Assertions.assertTrue(crossWire.onChange(circuit, Power.getMax(), Side.LEFT));
        Assertions.assertEquals(Power.getMin(), crossWire.getPower(Side.UP));
        Assertions.assertEquals(Power.getMin(), crossWire.getPower(Side.DOWN));
        Assertions.assertEquals(Power.getMax(), crossWire.getPower(Side.RIGHT));
        Assertions.assertEquals(Power.getMax(), crossWire.getPower(Side.LEFT));

    }

    @Test
    @Tag("unit-test") @Tag("fast")
    public void testUpdate() {
        Circuit circuit = Mockito.mock(Circuit.class);
        Mockito.when(circuit
            .getSurroundingWirePower(crossWire.getPosition(), Side.UP))
                .thenReturn(Power.getMax());

        Mockito.when(circuit
                .getSurroundingWirePower(crossWire.getPosition(), Side.DOWN))
                .thenReturn(Power.getMin());

        Mockito.when(circuit
                .getSurroundingWirePower(crossWire.getPosition(), Side.LEFT))
                .thenReturn(Power.getMin());

        Mockito.when(circuit
                .getSurroundingWirePower(crossWire.getPosition(), Side.RIGHT))
                .thenReturn(Power.getMin());

        CrossWireTile crossWireSpy = Mockito.spy(crossWire);

        Assertions.assertFalse(crossWireSpy.update(circuit, Power.getMin(), Side.RIGHT));
        Mockito.verify(crossWireSpy, Mockito.times(0))
                .onChange(Mockito.any(Circuit.class), Mockito.anyInt(), Mockito.any(Side.class));

        Assertions.assertTrue(crossWireSpy.update(circuit, Power.getMax(), Side.UP));
        Mockito.verify(crossWireSpy, Mockito.times(1))
                .onChange(Mockito.any(Circuit.class), Mockito.anyInt(), Mockito.any(Side.class));

        Assertions.assertFalse(crossWireSpy.update(circuit, Power.getMin(), Side.UP));
        Mockito.verify(crossWireSpy, Mockito.times(1))
                .onChange(Mockito.any(Circuit.class), Mockito.anyInt(), Mockito.any(Side.class));

        Assertions.assertFalse(crossWireSpy.update(circuit, Power.getMin(), Side.LEFT));
        Mockito.verify(crossWireSpy, Mockito.times(1))
                .onChange(Mockito.any(Circuit.class), Mockito.anyInt(), Mockito.any(Side.class));

        Mockito.when(circuit
                .getSurroundingWirePower(crossWire.getPosition(), Side.LEFT))
                .thenReturn(Power.getMax());

        Assertions.assertTrue(crossWireSpy.update(circuit, Power.getMax(), Side.LEFT));
        Mockito.verify(crossWireSpy, Mockito.times(2))
                .onChange(Mockito.any(Circuit.class), Mockito.anyInt(), Mockito.any(Side.class));

        Mockito.when(circuit
                .getSurroundingWirePower(crossWire.getPosition(), Side.RIGHT))
                .thenReturn(Power.getMax());

        Assertions.assertFalse(crossWireSpy.update(circuit, Power.getMax(), Side.RIGHT));
        Mockito.verify(crossWireSpy, Mockito.times(2))
                .onChange(Mockito.any(Circuit.class), Mockito.anyInt(), Mockito.any(Side.class));
    }

    @Test
    @Tag("unit-test") @Tag("fast")
    public void testWireConnections() {
        for (Side side : Side.values()) {
            Assertions.assertFalse(crossWire.isConnected(side));
        }

        Circuit circuit = Mockito.mock(Circuit.class);

        Mockito.when(circuit.canTilesConnect(crossWire.getPosition(), Side.UP)).thenReturn(true);
        Mockito.when(circuit.canTilesConnect(crossWire.getPosition(), Side.DOWN)).thenReturn(false);
        Mockito.when(circuit.canTilesConnect(crossWire.getPosition(), Side.RIGHT)).thenReturn(true);
        Mockito.when(circuit.canTilesConnect(crossWire.getPosition(), Side.LEFT)).thenReturn(false);

        crossWire.updateConnections(circuit);

        Assertions.assertTrue(crossWire.isConnected(Side.UP));
        Assertions.assertFalse(crossWire.isConnected(Side.DOWN));
        Assertions.assertTrue(crossWire.isConnected(Side.RIGHT));
        Assertions.assertFalse(crossWire.isConnected(Side.LEFT));
    }
}
