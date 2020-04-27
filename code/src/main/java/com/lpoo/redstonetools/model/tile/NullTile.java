package com.lpoo.redstonetools.model.tile;

import com.lpoo.redstonetools.model.utils.Position;
import com.lpoo.redstonetools.model.utils.Power;
import com.lpoo.redstonetools.model.utils.Side;

public class NullTile extends Tile {

    public NullTile(Position position) {
        super(position);
    }

    @Override
    public String getName() { return "null"; }

    @Override
    public String getInfo() { return ""; }

    @Override
    public boolean isSource(Side side) { return false; }

    @Override
    public int getPower(Side side) { return Power.getMin(); }

    @Override
    public boolean acceptsPower(Side side) { return false; }

    @Override
    public boolean outputsPower(Side side) { return false; }
}
