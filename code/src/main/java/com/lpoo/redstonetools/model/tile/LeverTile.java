package com.lpoo.redstonetools.model.tile;

import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.model.utils.Position;
import com.lpoo.redstonetools.model.utils.Power;
import com.lpoo.redstonetools.model.utils.Side;

public class LeverTile extends Tile implements SourceTile {

    private boolean activated;
    private boolean lastTickState;

    public LeverTile(Position position) {
        super(position);
        activated = false;
        lastTickState = false;
    }

    public boolean isActivated() {
        return activated;
    }

    public void toggle() {
        activated = !activated;
    }

    @Override
    public boolean nextTick(Circuit circuit) {
        if (activated != lastTickState) {
            lastTickState = activated;
            return true;
        }
        return false;
    }

    @Override
    public String getName() {
        return "lever";
    }

    @Override
    public String getInfo() {
        return activated ? "Power : " + Power.getMax() : "Power : " + Power.getMin();
    }

    @Override
    public boolean isSource(Side side) {
        return true;
    }

    @Override
    public int getPower(Side side) {
        return activated ? Power.getMax() : Power.getMin();
    }

    @Override
    public boolean acceptsPower(Side side) {
        return false;
    }

    @Override
    public boolean outputsPower(Side side) {
        return true;
    }
}
