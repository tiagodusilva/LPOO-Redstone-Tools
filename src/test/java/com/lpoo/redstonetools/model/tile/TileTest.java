package com.lpoo.redstonetools.model.tile;

import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.model.utils.Position;
import com.lpoo.redstonetools.model.utils.Power;
import com.lpoo.redstonetools.model.utils.Side;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class TileTest {
    @Test
    @Tag("model")
    @Tag("unit-test") @Tag("fast")
    public void testUpdate() {

        Circuit circuit = Mockito.mock(Circuit.class);

        Position position = Mockito.mock(Position.class);

        Tile tile = Mockito.mock(Tile.class, Mockito.withSettings().useConstructor(position));

        Mockito.when(circuit.getTile(position)).thenReturn(tile);

        for (Side side : Side.values()) {
            Mockito.when(position.getNeighbour(side)).thenReturn(position);
            Mockito.when(tile.getPower(side)).thenReturn(Power.getMax());
            Mockito.when(tile.update(circuit, Power.getMax(), side)).thenReturn(false);
        }
        Mockito.when(tile.getPower(Side.DOWN)).thenReturn(Power.getMin());
        Mockito.when(tile.update(circuit, Power.getMin(), Side.UP)).thenReturn(false);

        Mockito.when(tile.update(circuit)).thenCallRealMethod();

        Assertions.assertFalse(tile.update(circuit));

        for (Side side : Side.values()) {
            Mockito.verify(position, Mockito.times(1)).getNeighbour(side);
            Mockito.verify(tile, Mockito.times(1)).getPower(side);
        }

        Mockito.verify(tile, Mockito.times(1)).update(circuit, Power.getMin(), Side.UP);
        Mockito.verify(tile, Mockito.times(1)).update(circuit, Power.getMax(), Side.LEFT);
        Mockito.verify(tile, Mockito.times(1)).update(circuit, Power.getMax(), Side.RIGHT);
        Mockito.verify(tile, Mockito.times(1)).update(circuit, Power.getMax(), Side.DOWN);

        Mockito.when(tile.update(circuit, Power.getMin(), Side.UP)).thenReturn(true);
        Assertions.assertTrue(tile.update(circuit));
    }
}
