package com.lpoo.redstonetools.model.tile;

import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.model.utils.Position;
import com.lpoo.redstonetools.model.utils.Power;
import com.lpoo.redstonetools.model.utils.Side;
import com.lpoo.redstonetools.model.utils.TileType;

/**
 *  <h1>Lever Tile</h1>
 *  Lever is source tile that can be toggled to emit power or not
 *
 * @author g79
 */
public class LeverTile extends Tile {

    /**
     * <h1>If lever is emitting power or not</h1>
     */
    private boolean activated;

    /**
     * <h1>Lever constructor</h1>
     *
     * @param position  Position of the tile
     */
    public LeverTile(Position position) {
        super(position);
        activated = false;
    }

    /**
     * <h1>Checks if lever is emitting power</h1>
     *
     * @return  true if lever is activated, false otherwise
     */
    public boolean isActivated() {
        return activated;
    }

    /**
     * <h1>Changes lever state</h1>
     *
     * @param circuit Circuit where interaction is taking place
     *
     * @return true
     */
    @Override
    public boolean interact(Circuit circuit) {
        activated = !activated;
        return true;
    }

    /**
     * <h1>Get name of the tile</h1>
     *
     * @return  "lever"
     */
    @Override
    public String getName() {
        return "lever";
    }

    /**
     * <h1>Get tile information</h1>
     *
     * @return  "Active : "
     */
    @Override
    public String getInfo() {
        return "Active : " + this.activated;
    }

    /**
     * <h1>Get tile type</h1>
     *
     * @see TileType
     *
     * @return  Lever type
     */
    @Override
    public TileType getType() { return TileType.LEVER; }

    /**
     * <h1>Get the power level emitted on the side specified</h1>
     *
     * @param side  Side of the tile
     * @return  Maximum power if lever is activated, minimum power otherwise
     */
    @Override
    public int getPower(Side side) {
        return activated ? Power.getMax() : Power.getMin();
    }

    /**
     * <h1>Checks if side specified is an input of power</h1>
     *
     * @param side  Side of the tile to be checked
     * @return  false
     */
    @Override
    public boolean acceptsPower(Side side) {
        return false;
    }

    /**
     * <h1>Checks if side specified is an output of power</h1>
     *
     * @param side  Side of the tile to be checked
     * @return  true
     */
    @Override
    public boolean outputsPower(Side side) {
        return true;
    }
}
