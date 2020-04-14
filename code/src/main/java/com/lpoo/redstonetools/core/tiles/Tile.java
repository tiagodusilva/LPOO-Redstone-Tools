package com.lpoo.redstonetools.core.tiles;

import com.lpoo.redstonetools.core.Circuit;
import com.lpoo.redstonetools.core.utils.Position;
import com.lpoo.redstonetools.core.utils.Side;
import com.lpoo.redstonetools.graphics.TileRenderer;

public abstract class Tile {

    protected TileRenderer tr;

    protected Position position;

    public Tile(Position position) {
        this.position = position;
    }

    abstract public void update(Circuit circuit, int power, Side side);

    abstract protected void onChange(Circuit circuit, int power, Side side);

    public Position getPosition() {
        return position;
    }

    abstract public String getName();

}
