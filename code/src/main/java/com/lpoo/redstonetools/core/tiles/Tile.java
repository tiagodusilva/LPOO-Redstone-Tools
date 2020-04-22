package com.lpoo.redstonetools.core.tiles;

import com.lpoo.redstonetools.core.Circuit;
import com.lpoo.redstonetools.core.utils.Position;
import com.lpoo.redstonetools.core.utils.Side;
import com.lpoo.redstonetools.graphics.TileRenderer;
import com.lpoo.redstonetools.graphics.lanterna.LanternaTileRenderer;

public abstract class Tile {

    protected Position position;
    protected TileRenderer renderer;

    public Tile(Position position, TileRenderer renderer) {
        this.position = position;
        this.renderer = renderer;
    }

    abstract public void update(Circuit circuit, int power, Side side);

    abstract protected void onChange(Circuit circuit, int power, Side side);

    public Position getPosition() {
        return position;
    }

    abstract public String getName();

    abstract public boolean isSource(Side side);

    public boolean isSource() {
        for (Side side : Side.values())
            if (isSource(side))
                return true;

        return false;
    }

    abstract public int getPower(Side side);

    abstract public boolean acceptsPower(Side side);

    abstract public boolean outputsPower(Side side);

    abstract public String getInfo();

    public TileRenderer getRenderer() {
        return renderer;
    }
}
