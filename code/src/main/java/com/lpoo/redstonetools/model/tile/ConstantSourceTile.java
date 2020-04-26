package com.lpoo.redstonetools.model.tile;

import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.model.utils.Position;
import com.lpoo.redstonetools.model.utils.Power;
import com.lpoo.redstonetools.model.utils.Side;

public class ConstantSourceTile extends Tile implements SourceTile {

    private boolean firstTick;

    public ConstantSourceTile(Position position) {
        super(position);
        firstTick = true;
    }

    @Override
    public String getName() {
        return "Source";
    }

    @Override
    public String getInfo() {
        return "Power : " + Power.getMax();
    }

    @Override
    public boolean isSource(Side side) {
        return true;
    }

    @Override
    public void rotateLeft() { }

    @Override
    public void rotateRight() { }

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
    public boolean update(Circuit circuit, int power, Side side) { return false; }

    @Override
    protected boolean onChange(Circuit circuit, int power, Side side) { return false; }

    @Override
    public void updateConnections(Circuit circuit) { }

    @Override
    public boolean nextTick(Circuit circuit) {
        if (firstTick) {
            firstTick = false;
            return true;
        }
        return false;
    }
}
