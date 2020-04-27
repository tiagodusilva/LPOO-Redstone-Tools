package com.lpoo.redstonetools.model.tile;

import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.model.utils.Position;
import com.lpoo.redstonetools.model.utils.Power;
import com.lpoo.redstonetools.model.utils.Side;
import com.lpoo.redstonetools.model.utils.SideType;

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
     * <h1>Last tick repeater was updated</h1>
     */
    private long updateTick;

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
        this.updateTick = -1;
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
     * <h1>Checks if tile is a source of power</h1>
     *
     * @param side ????
     * @return  false
     */
    @Override
    public boolean isSource(Side side) { return false; }

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
        boolean next_status = power != 0;
        if (acceptsPower(side) && (next_status != this.active)) {
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
        if (circuit.getTick() > updateTick || power > 0) {
            this.active = power != 0;
            this.updateTick = circuit.getTick();
            return true;
        }
        return false;
    }
}
