package com.lpoo.redstonetools.core.tiles;

import com.lpoo.redstonetools.core.Circuit;
import com.lpoo.redstonetools.core.utils.Position;
import com.lpoo.redstonetools.core.utils.Side;

public class ConstantSourceTile extends Tile implements SourceTile {

    public ConstantSourceTile(Position position) {
        super(position);
    }

    public void nextTick(Circuit circuit) {
        circuit.updateAllNeighbourTiles(position, 15);
    }

    @Override
    public void update(Circuit circuit, int power, Side side) {

    }

    @Override
    protected void onChange(Circuit circuit, int power, Side side) {

    }

    @Override
    public String getName() {
        return "Source";
    }

    @Override
    public boolean isSource(Side side) {
        return true;
    }

    @Override
    public String getInfo() {
        return "Power : " + 15;
    }

}
