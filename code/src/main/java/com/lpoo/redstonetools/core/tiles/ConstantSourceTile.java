package com.lpoo.redstonetools.core.tiles;

import com.lpoo.redstonetools.core.Circuit;
import com.lpoo.redstonetools.core.utils.Position;
import com.lpoo.redstonetools.core.utils.Power;
import com.lpoo.redstonetools.core.utils.Side;
import com.lpoo.redstonetools.graphics.TileRenderer;

public class ConstantSourceTile extends Tile implements SourceTile {

    private boolean firstTick;

    public ConstantSourceTile(Position position, TileRenderer<? extends Tile> renderer) {
        super(position, renderer);
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
        return Power.getMax();
    }

    @Override
    public boolean acceptsPower(Side side) {
        return false;
    }

    @Override
    public boolean outputsPower(Side side) {
        return true;
    }

    @Override
    public String getInfo() {
        return "Power : " + Power.getMax();
    }

}
