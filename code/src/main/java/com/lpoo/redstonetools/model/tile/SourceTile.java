package com.lpoo.redstonetools.model.tile;

import com.lpoo.redstonetools.model.circuit.Circuit;

public interface SourceTile {
    boolean nextTick(Circuit circuit);
}
