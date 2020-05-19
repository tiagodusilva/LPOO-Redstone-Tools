package com.lpoo.redstonetools.model.tile;

import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.model.utils.*;

/**
 *  <h1>Repeater Tile</h1>
 *  Repeater is a type of oriented tile that extends signal
 *  It has a input side and an output side on the opposite direction of the input
 *  Transform any signal greater than minimum power lever into a maximum power level
 *
 * @author g79
 */
public class RepeaterTile extends OrientedTile {

    /**
     * <h1>If is extending a signal</h1>
     * If repeater is receiving power from the input side then it is active and extending that power
     */
    private boolean active;

    /**
     * <h1>Repeater tile constructor</h1>
     * By default a repeater has the left side as input and right side as an output
     * The input and output sides can't be changed, but the repeater can be rotated
     *
     * @param position  Position of the tile
     */
    public RepeaterTile(Position position) {
        super(position);
        this.sides.put(Side.LEFT, SideType.INPUT);
        this.sides.put(Side.RIGHT, SideType.OUTPUT);
        this.active = false;
    }

    /**
     * <h1>Get name of the tile</h1>
     *
     * @return  "repeater"
     */
    @Override
    public String getName() { return "repeater"; }

    /**
     * <h1>Get tile information</h1>
     *
     * @return  "Active : " followed by the state of the repeater
     */
    @Override
    public String getInfo() { return "Active : " + this.active; }

    /**
     * <h1>Get repeater status</h1>
     *
     * @return  Repeater status
     */
    public boolean getStatus() {
        return this.active;
    }

    /**
     * <h1>Set repeater status</h1>
     *
     * @param status    New repeater status
     */
    public void setStatus(boolean status) {
        this.active = status;
    }

    /**
     * <h1>Get tile type</h1>
     *
     * @see TileType
     *
     * @return  Repeater type
     */
    @Override
    public TileType getType() { return TileType.REPEATER; }

    /**
     * <h1>Get the power level emitted on the side specified</h1>
     *
     * @param side  Side of the tile
     * @return  Maximum power if it's the output side and repeater is active, minimum power otherwise
     */
    @Override
    public int getPower(Side side) {
        if (outputsPower(side)) return (this.active) ? Power.getMax() : Power.getMin();
        return Power.getMin();
    }

    /**
     * <h1>Triggers a tile update</h1>
     * Checks if repeater needs to be updated, by comparing the previous state with the next state of the repeater
     *
     * @param circuit   Circuit where updates are taking place
     * @param power     Power received on the update
     * @param side      Side from which repeater received an update
     * @return  true if repeater was updated, false otherwise
     */
    @Override
    public boolean update(Circuit circuit, int power, Side side) {
        if (acceptsPower(side) && Power.isOn(power) != active) {
            return onChange(circuit, power, side);
        }
        return false;
    }

    /**
     * <h1>Updates the tile</h1>
     * Checks if the update is necessary, updating it if so
     *
     * @param circuit   Circuit where updates are taking place
     * @param power     Power received on the update
     * @param side      Side from which repeater received an update
     * @return  true if repeater was updated, false otherwise
     */
    @Override
    protected boolean onChange(Circuit circuit, int power, Side side) {
        this.setStatus(Power.isOn(power));
        return true;
    }
}
