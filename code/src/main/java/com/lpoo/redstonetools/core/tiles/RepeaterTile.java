package com.lpoo.redstonetools.core.tiles;

import com.lpoo.redstonetools.core.Circuit;
import com.lpoo.redstonetools.core.utils.Position;
import com.lpoo.redstonetools.core.utils.Side;
import com.lpoo.redstonetools.core.utils.SideType;
import com.lpoo.redstonetools.graphics.TileRenderer;

public class RepeaterTile extends OrientedTile implements ComponentTile {

    private long updateTick;
    private boolean active;

    public RepeaterTile(Position position, TileRenderer renderer) {
        super(position, renderer);
        this.sides.put(Side.LEFT, SideType.INPUT);
        this.sides.put(Side.RIGHT, SideType.OUTPUT);
        this.active = false;
        this.updateTick = -1;
    }

    @Override
    public void update(Circuit circuit, int power, Side side) {
        boolean next_status = power != 0;
        if (isInput(side) && (next_status != this.active)) {
            onChange(circuit, power, side);
        }
    }

    @Override
    protected void onChange(Circuit circuit, int power, Side side) {
        if (circuit.getTick() > updateTick || power > 0) {
            this.active = power != 0;
            this.updateTick = circuit.getTick();
            circuit.updateNeighbourTile(position, getPower(side.opposite()), side.opposite());
        }
    }

    @Override
    public String getName() { return "repeater"; }

    @Override
    public boolean isSource(Side side) {
        return false;
    }

    @Override
    public int getPower(Side side) {
        if (isOutput(side)) return (this.active) ? 15 : 0;
        return 0;
    }

    @Override
    public String getInfo() {
        return "Active : " + this.active;
    }
}
