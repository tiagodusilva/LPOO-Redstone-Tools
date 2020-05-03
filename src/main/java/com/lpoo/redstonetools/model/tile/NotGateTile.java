package com.lpoo.redstonetools.model.tile;

import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.model.utils.*;

/**
 *  <h1>Not Gate Tile</h1>
 *  Not Gate Tile outputs power opposite power to the one received
 *
 * @author g79
 */
public class NotGateTile extends OrientedTile {

    /**
     * <h1>If is currently emitting</h1>
     * This means it is currently not receiving power
     */
    private boolean active;

    /**
     * <h1>Not Gate Tile constructor</h1>
     * By default a Not Gate has the left side as input and right side as an output
     * The input and output sides can't be changed, but the Not Gate can be rotated
     *
     * @param position  Position of the tile
     */
    public NotGateTile(Position position) {
        super(position);
        sides.put(Side.LEFT, SideType.INPUT);
        sides.put(Side.RIGHT, SideType.OUTPUT);
        active = false;
    }

    /**
     * <h1>Set not gate status</h1>
     *
     * @param status    New not gate status
     */
    public void setStatus(boolean status) {
        this.active = status;
    }

    /**
     * <h1>Get name of the tile</h1>
     *
     * @return  "not_gate"
     */
    @Override
    public String getName() { return "not_gate"; }

    /**
     * <h1>Get tile information</h1>
     *
     * @return  "Outputting : " followed by the state of the Not Gate
     */
    @Override
    public String getInfo() { return "Outputting : " + this.active; }

    /**
     * <h1>Get tile type</h1>
     *
     * @see TileType
     *
     * @return  Not Gate type
     */
    @Override
    public TileType getType() { return TileType.NOT_GATE; }

    /**
     * <h1>Get the power level emitted on the side specified</h1>
     *
     * @param side  Side of the tile
     * @return  Maximum power if it's the output side and Not Gate is active, minimum power otherwise
     */
    @Override
    public int getPower(Side side) {
        if (outputsPower(side)) return (this.active) ? Power.getMax() : Power.getMin();
        return Power.getMin();
    }

    /**
     * <h1>Triggers a tile update</h1>
     * Checks if Not Gate needs to be updated, by comparing the previous state with the next state of the Not Gate
     *
     * @param circuit   Circuit where updates are taking place
     * @param power     Power received on the update
     * @param side      Side from which repeater received an update
     * @return  true if Not Gate was updated, false otherwise
     */
    @Override
    public boolean update(Circuit circuit, int power, Side side) {
        if (acceptsPower(side)) {
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
        boolean old_status = active;
        this.setStatus(!Power.isOn(power));
        return old_status != active;
    }
}
