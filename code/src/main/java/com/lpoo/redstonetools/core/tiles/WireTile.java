package com.lpoo.redstonetools.core.tiles;

import com.lpoo.redstonetools.core.Circuit;
import com.lpoo.redstonetools.core.utils.Position;
import com.lpoo.redstonetools.core.utils.Side;
import com.lpoo.redstonetools.graphics.TileRenderer;

public class WireTile extends Tile {

    private int power;
    private long updateTick;

    public WireTile(Position position, TileRenderer renderer) {
        super(position, renderer);
        this.power = 0;
        this.updateTick = -1;
    }

    @Override
    public void update(Circuit circuit, int power, Side side) {
        onChange(circuit, power, side);
    }

    @Override
    protected void onChange(Circuit circuit, int power, Side side) {
//        System.out.println(power + " " + side);
        if (circuit.getTick() > updateTick || power > this.power) {
            if (circuit.getTile(position.getNeighbour(side)) instanceof WireTile)
                this.power = Math.max(0, power - 1);
            else
                this.power = power;
            this.updateTick = circuit.getTick();
            circuit.updateAllNeighbourTilesExcept(position, this.power, side);
        }
    }

    @Override
    public String getName() {
        return "wire";
    }

    @Override
    public boolean isSource(Side side) {
        return false;
    }

    @Override
    public int getPower(Side side) {
        return power;
    }

    @Override
    public String getInfo() {
        return "Power : " + this.power;
    }
}
