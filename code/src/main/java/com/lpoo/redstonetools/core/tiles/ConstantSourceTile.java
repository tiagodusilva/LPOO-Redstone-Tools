package com.lpoo.redstonetools.core.tiles;

import com.lpoo.redstonetools.core.Circuit;
import com.lpoo.redstonetools.core.utils.Position;
import com.lpoo.redstonetools.core.utils.Side;

public class ConstantSourceTile extends Tile implements SourceTile {

    private boolean firstTick;

    public ConstantSourceTile(Position position) {
        super(position);
        firstTick = true;
    }

    public void nextTick(Circuit circuit) {
        if (firstTick) {
            circuit.updateAllNeighbourTiles(position, 15);
            firstTick = false;
        }
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
    public int getPower(Side side) {
        return 15;
    }

    @Override
    public String getInfo() {
        return "Power : " + 15;
    }

}
