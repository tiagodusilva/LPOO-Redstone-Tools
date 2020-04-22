package com.lpoo.redstonetools.core.tiles;

import com.lpoo.redstonetools.core.Circuit;
import com.lpoo.redstonetools.core.utils.Position;
import com.lpoo.redstonetools.core.utils.Side;
import com.lpoo.redstonetools.graphics.TileRenderer;

public class NullTile extends Tile {

    public NullTile(Position position, TileRenderer renderer) {
        super(position, renderer);
    }

    @Override
    public void update(Circuit circuit, int power, Side side) { }

    @Override
    protected void onChange(Circuit circuit, int power, Side side) { }

    @Override
    public String getName() {
        return "null";
    }

    @Override
    public boolean isSource(Side side) {
        return false;
    }

    @Override
    public int getPower(Side side) {
        return 0;
    }

    @Override
    public boolean acceptsPower(Side side) {
        return false;
    }

    @Override
    public boolean outputsPower(Side side) {
        return false;
    }

    @Override
    public String getInfo() {
        return "";
    }

}
