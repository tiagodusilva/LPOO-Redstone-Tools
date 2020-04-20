package com.lpoo.redstonetools.core.tiles;

import com.lpoo.redstonetools.core.Circuit;
import com.lpoo.redstonetools.core.utils.Position;
import com.lpoo.redstonetools.core.utils.Power;
import com.lpoo.redstonetools.core.utils.Side;

public class LeverTile extends Tile implements SourceTile {

    boolean activated;
    boolean lastTickState;

    public LeverTile(Position position) {
        super(position);
        activated = false;
        lastTickState = false;
    }

    @Override
    public void update(Circuit circuit, int power, Side side) {

    }

    @Override
    protected void onChange(Circuit circuit, int power, Side side) {

    }

    @Override
    public String getName() {
        return "lever";
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
    public String getInfo() {
        return activated ? "Power : " + Power.getMax() : "Power : " + Power.getMin();
    }

    @Override
    public void nextTick(Circuit circuit) {
        if (activated != lastTickState) {
            lastTickState = activated;
            if (activated) {
                circuit.updateAllNeighbourTiles(position, Power.getMax());
            }
            else {
                circuit.updateAllNeighbourTiles(position, Power.getMin());
            }
        }
    }

    public void toggle() {
        activated = !activated;
    }

}