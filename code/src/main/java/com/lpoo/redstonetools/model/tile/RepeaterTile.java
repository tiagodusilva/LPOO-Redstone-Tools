package com.lpoo.redstonetools.model.tile;

import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.model.utils.Position;
import com.lpoo.redstonetools.model.utils.Power;
import com.lpoo.redstonetools.model.utils.Side;
import com.lpoo.redstonetools.model.utils.SideType;

public class RepeaterTile extends OrientedTile {

    private long updateTick;
    private boolean active;

    public RepeaterTile(Position position) {
        super(position);
        this.sides.put(Side.LEFT, SideType.INPUT);
        this.sides.put(Side.RIGHT, SideType.OUTPUT);
        this.active = false;
        this.updateTick = -1;
    }

    @Override
    public String getName() { return "repeater"; }

    @Override
    public String getInfo() { return "Active : " + this.active; }

    @Override
    public boolean isSource(Side side) { return false; }

    @Override
    public int getPower(Side side) {
        if (outputsPower(side)) return (this.active) ? Power.getMax() : Power.getMin();
        return Power.getMin();
    }

    @Override
    public boolean update(Circuit circuit, int power, Side side) {
        boolean next_status = power != 0;
        if (acceptsPower(side) && (next_status != this.active)) {
            return onChange(circuit, power, side);
        }
        return false;
    }

    @Override
    protected boolean onChange(Circuit circuit, int power, Side side) {
        if (circuit.getTick() > updateTick || power > 0) {
            this.active = power != 0;
            this.updateTick = circuit.getTick();
            return true;
        }
        return false;
    }
}
