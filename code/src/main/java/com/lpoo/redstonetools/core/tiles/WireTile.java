package com.lpoo.redstonetools.core.tiles;

import com.lpoo.redstonetools.core.Circuit;
import com.lpoo.redstonetools.core.utils.Position;
import com.lpoo.redstonetools.core.utils.Side;

public class WireTile extends Tile {

    private int power;
    private long updateTick;

    public WireTile(Position position) {
        super(position);
        this.power = 0;
        this.updateTick = -1;
    }

    @Override
    public void update(Circuit circuit, int power, Side side) {
        if (power != this.power) {
            onChange(circuit, power, side);
            circuit.updateAllNeighbourTiles(position, this.power);
        }
    }

    @Override
    protected void onChange(Circuit circuit, int power, Side side) {
        if (circuit.getTick() > updateTick || power > this.power) {
            this.power = Math.max(0, power - 1);
            this.updateTick = circuit.getTick();
        }
    }

    @Override
    public String getName() {
        return "wire";
    }
}
