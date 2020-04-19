package com.lpoo.redstonetools.core.tiles;

import com.lpoo.redstonetools.core.Circuit;
import com.lpoo.redstonetools.core.utils.Position;
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
        return activated ? 15 : 0;
    }

    @Override
    public String getInfo() {
        return activated ? "Power : 15" : "Power : 0";
    }

    @Override
    public void nextTick(Circuit circuit) {
        if (activated != lastTickState) {
            lastTickState = activated;
            if (activated) {
                circuit.updateAllNeighbourTiles(position, 15);
            }
            else {
                circuit.updateAllNeighbourTiles(position, 0);
            }
        }
    }

    public void toggle() {
        activated = !activated;
    }

}
