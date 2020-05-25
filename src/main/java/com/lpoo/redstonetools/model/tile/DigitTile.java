package com.lpoo.redstonetools.model.tile;

import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.model.utils.Position;
import com.lpoo.redstonetools.model.utils.Power;
import com.lpoo.redstonetools.model.utils.Side;
import com.lpoo.redstonetools.model.utils.TileType;

public class DigitTile extends Tile {

    private int power;

    /**
     * <h1>Tile constructor</h1>
     *
     * @param position Position of the tile
     */
    public DigitTile(Position position) {
        super(position);
        power = Power.getMin();
    }

    public int getPowerLevel() {
        return power;
    }

    @Override
    public String getName() {
        return "digit";
    }

    @Override
    public String getInfo() {
        return "Power : " + power;
    }

    @Override
    public TileType getType() {
        return TileType.DIGIT;
    }

    @Override
    public int getPower(Side side) {
        return Power.getMin();
    }

    @Override
    public boolean acceptsPower(Side side) {
        return true;
    }

    @Override
    public boolean outputsPower(Side side) {
        return false;
    }

    @Override
    public boolean update(Circuit circuit, int power, Side side) {
        int surroundingPower = circuit.getSurroundingPower(position);
        if (acceptsPower(side) && this.power != surroundingPower) {
            return onChange(circuit, surroundingPower, side);
        }
        return false;
    }

    @Override
    public boolean onChange(Circuit circuit, int power, Side side) {
        this.power = power;
        return false;
    }
}
