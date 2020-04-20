package com.lpoo.redstonetools.core.tiles;

import com.lpoo.redstonetools.core.Circuit;
import com.lpoo.redstonetools.core.utils.Position;
import com.lpoo.redstonetools.core.utils.Power;
import com.lpoo.redstonetools.core.utils.Side;

public class WireTile extends Tile {

    private int power;
    private long updateTick;

    public WireTile(Position position) {
        super(position);
        this.power = Power.getMin();
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
                this.power = Power.decrease(power);
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
    public boolean acceptsPower(Side side) {
        return true;
    }

    @Override
    public boolean outputsPower(Side side) {
        return true;
    }

    @Override
    public String getInfo() {
        return "Power : " + this.power;
    }
}
