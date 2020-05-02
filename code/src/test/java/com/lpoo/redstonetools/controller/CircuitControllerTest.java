package com.lpoo.redstonetools.controller;

import com.lpoo.redstonetools.controller.circuit.CircuitController;
import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.model.tile.SourceTile;
import com.lpoo.redstonetools.model.tile.Tile;
import com.lpoo.redstonetools.model.utils.Position;
import com.lpoo.redstonetools.model.utils.Power;
import com.lpoo.redstonetools.model.utils.Side;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class CircuitControllerTest {

    private CircuitController controller;

    @Before
    public void setup() {
        this.controller = new CircuitController();
    }

    @Test
    public void testNotifyNeighbourTiles() {
        Circuit circuit = Mockito.mock(Circuit.class);

        Position position = Mockito.mock(Position.class);

        Tile tile = Mockito.mock(Tile.class);
        Mockito.when(tile.getPosition()).thenReturn(position);

        Mockito.when(circuit.addTile(tile)).thenReturn(false);

        this.controller.addTile(circuit, tile);

        Mockito.verify(circuit, Mockito.times(1)).addTile(tile);
        Mockito.verify(circuit, Mockito.times(0)).getTile(Mockito.any());

        Mockito.when(circuit.addTile(tile)).thenReturn(true);

        Mockito.when(circuit.getTile(position)).thenReturn(tile);

        for (Side side : Side.values()) {
            Mockito.when(position.getNeighbour(side)).thenReturn(position);
        }

        this.controller.addTile(circuit, tile);

        Mockito.verify(circuit, Mockito.times(2)).addTile(tile);
        Mockito.verify(circuit, Mockito.times(Side.values().length)).getTile(position);

        for (Side side : Side.values()) {
            Mockito.verify(position, Mockito.times(1)).getNeighbour(side);
        }

        Mockito.verify(tile, Mockito.times(Side.values().length)).updateConnections(circuit);
    }

    @Test
    public void testAdvanceTick() {
        Circuit circuit = Mockito.mock(Circuit.class);

        Position position = Mockito.mock(Position.class);
        Position position2 = Mockito.mock(Position.class);

        SourceTile source = Mockito.mock(SourceTile.class);
        SourceTile source2 = Mockito.mock(SourceTile.class);

        List<Position> sourceList = new ArrayList<>();
        sourceList.add(position); sourceList.add(position2);

        // ---- Circuit Configuration
        Mockito.when(circuit.getSources()).thenReturn(sourceList);

        Mockito.when(circuit.getTile(position)).thenReturn(source);
        Mockito.when(circuit.getTile(position2)).thenReturn(source2);
        Mockito.when(circuit.isInBounds(position)).thenReturn(true);
        // ----

        // ---- Source 1 Configuration
        Mockito.when(source.nextTick()).thenReturn(false);
        Mockito.when(source.update(circuit, Power.getMax(), Side.DOWN)).thenReturn(false);
        // ----

        // ---- Source 2 Configuration
        Mockito.when(source2.nextTick()).thenReturn(true);

        Mockito.when(source2.outputsPower(Side.UP)).thenReturn(true);
        Mockito.when(source2.outputsPower(Side.DOWN)).thenReturn(false);
        Mockito.when(source2.outputsPower(Side.LEFT)).thenReturn(false);
        Mockito.when(source2.outputsPower(Side.RIGHT)).thenReturn(false);

        Mockito.when(source2.getPower(Side.UP)).thenReturn(Power.getMax());

        Mockito.when(position2.getNeighbour(Side.UP)).thenReturn(position);

        // ----

        this.controller.advanceTick(circuit);

        // ---- Circuit verifying
        Mockito.verify(circuit, Mockito.times(1)).advanceTick();
        Mockito.verify(circuit, Mockito.times(1)).getSources();
        Mockito.verify(circuit, Mockito.times(1)).isInBounds(position);
        // Source 1
        Mockito.verify(circuit, Mockito.times(2)).getTile(position);

        // Source 2
        Mockito.verify(circuit, Mockito.times(2)).getTile(position2);

        // ----

        // ---- Source 1 verifying
        Mockito.verify(source, Mockito.times(1)).nextTick();
        Mockito.verify(source, Mockito.times(1)).update(circuit, Power.getMax(), Side.DOWN);
        // ----

        // ---- Source 2 verifying
        Mockito.verify(source2, Mockito.times(1)).nextTick();

        for (Side side : Side.values()) {
            Mockito.verify(source2, Mockito.times(1)).outputsPower(side);
        }

        Mockito.verify(source2, Mockito.times(1)).getPower(Side.UP);

        Mockito.verify(position2, Mockito.times(1)).getNeighbour(Side.UP);
        Mockito.verify(position2, Mockito.times(0)).getNeighbour(Side.DOWN);
        Mockito.verify(position2, Mockito.times(0)).getNeighbour(Side.LEFT);
        Mockito.verify(position2, Mockito.times(0)).getNeighbour(Side.RIGHT);
        // ----
    }
}
