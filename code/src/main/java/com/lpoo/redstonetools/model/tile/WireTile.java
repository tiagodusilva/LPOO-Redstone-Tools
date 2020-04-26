package com.lpoo.redstonetools.model.tile;

import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.model.utils.Position;
import com.lpoo.redstonetools.model.utils.Power;
import com.lpoo.redstonetools.model.utils.Side;

import java.util.HashMap;
import java.util.Map;

public class WireTile extends Tile {

    private int power;
    private Map<Side, Boolean> connected;

    public WireTile(Position position) {
        super(position);
        this.power = Power.getMin();
        connected = new HashMap<>();
        for (Side side : Side.values())
            connected.put(side, false);
    }

    @Override
    public String getName() { return "wire"; }

    @Override
    public String getInfo() {
        return "Power : " + this.power;
    }

    @Override
    public boolean isSource(Side side) {
        return false;
    }

    @Override
    public void rotateLeft() { }

    @Override
    public void rotateRight() { }

    @Override
    public int getPower(Side side) { return power; }

    @Override
    public boolean acceptsPower(Side side) {
        return true;
    }

    @Override
    public boolean outputsPower(Side side) {
        return true;
    }

    @Override
    public boolean update(Circuit circuit, int power, Side side) {
        int surroundingPower = circuit.getSurroundingPower(position);
        if (this.power != surroundingPower)
            return onChange(circuit, surroundingPower, side);
        return false;
    }

    @Override
    protected boolean onChange(Circuit circuit, int power, Side side) {
        this.power = power;
        return true;
    }

    @Override
    public void updateConnections(Circuit circuit) {
        for (Side side : Side.values())
            connected.put(side, circuit.canTilesConnect(this.position, side));
    }

    @Override
    public boolean isWire() { return true; }
}
