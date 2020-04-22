package com.lpoo.redstonetools.core.tiles;

import com.lpoo.redstonetools.core.Circuit;
import com.lpoo.redstonetools.core.utils.Position;
import com.lpoo.redstonetools.core.utils.Power;
import com.lpoo.redstonetools.core.utils.Side;
import com.lpoo.redstonetools.core.utils.SideType;
import com.lpoo.redstonetools.graphics.TileRenderer;

import java.util.HashMap;
import java.util.Map;

public class WireTile extends Tile {

    private int power;
    private long updateTick;
    private Map<Side, Boolean> connected;

    public WireTile(Position position, TileRenderer renderer) {
        super(position, renderer);
        this.power = Power.getMin();
        this.updateTick = -1;
        connected = new HashMap<>();
        for (Side side : Side.values())
            connected.put(side, false);
    }

    @Override
    public void update(Circuit circuit, int power, Side side) {
        onChange(circuit, power, side);
    }

    @Override
    protected void onChange(Circuit circuit, int power, Side side) {
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
    public void updateConnections(Circuit circuit) {
        for (Side side : Side.values())
            connected.put(side, circuit.canTilesConnect(this.position, side));
    }

    public boolean isConnected(Side side) {
        return connected.getOrDefault(side, false);
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
