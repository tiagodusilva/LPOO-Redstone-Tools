package com.lpoo.redstonetools.model.tile;

import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.model.utils.Position;
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
    public void rotateLeft() { }

    @Override
    public void rotateRight() { }

    @Override
    public int getPower(Side side) { return 0; }

    @Override
    public boolean acceptsPower(Side side) { return false; }

    @Override
    public boolean outputsPower(Side side) { return false; }

    @Override
    public boolean update(Circuit circuit, int power, Side side) { return false; }

    @Override
    protected boolean onChange(Circuit circuit, int power, Side side) { return false; }

    @Override
    public void updateConnections(Circuit circuit) { }
}
