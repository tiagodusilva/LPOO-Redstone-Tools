package com.lpoo.redstonetools.model.tile;

import com.lpoo.redstonetools.model.utils.Position;
import com.lpoo.redstonetools.model.utils.Power;
import com.lpoo.redstonetools.model.utils.Side;
import com.lpoo.redstonetools.model.utils.TileType;

/**
 *  <h1>Empty Tile</h1>
 *  <code>NullTile</code> is a tile of the circuit that has no function
 *
 * @author g79
 */
public class NullTile extends Tile {

    /**
     * <h1>Indicates if it's broken</h1>
     * If not broken, just representing empty space
     * otherwise it's a Tile that got short-circuited
     */
    private boolean broken;

    /**
     * <h1>Empty tile constructor</h1>
     *
     * @param position  Position of the tile
     */
    public NullTile(Position position) {
        super(position);
        broken = false;
    }

    /**
     *
     * @param position
     * @param broken
     */
    public NullTile(Position position, boolean broken) {
        super(position);
        this.broken = broken;
    }

    /**
     *
     * @return
     */
    public boolean isBroken() {
        return broken;
    }

    /**
     * <h1>Get name of the tile</h1>
     *
     * @return  "null"
     */
    @Override
    public String getName() { return "null"; }

    /**
     * <h1>Get tile information</h1>
     * Empty tile has no information
     *
     * @return ""
     */
    @Override
    public String getInfo() { return ""; }

    /**
     * <h1>Get tile type</h1>
     *
     * @see TileType
     *
     * @return  Null type
     */
    @Override
    public TileType getType() { return TileType.NULL; }

    /**
     * <h1>Get the power level emitted on the side specified</h1>
     * Empty tile doesn't emit power
     *
     * @param side  Side of the tile
     * @return  Minimum power
     */
    @Override
    public int getPower(Side side) { return Power.getMin(); }

    /**
     * <h1>Checks if side specified is an input of power</h1>
     *
     * @param side  Side of the tile to be checked
     * @return  false
     */
    @Override
    public boolean acceptsPower(Side side) { return false; }

    /**
     * <h1>Checks if side specified is an output of power</h1>
     *
     * @param side  Side of the tile to be checked
     * @return  false
     */
    @Override
    public boolean outputsPower(Side side) { return false; }
}
