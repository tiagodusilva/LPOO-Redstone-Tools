package com.lpoo.redstonetools.model.tile;

import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.model.utils.Position;
import com.lpoo.redstonetools.model.utils.Power;
import com.lpoo.redstonetools.model.utils.Side;
import com.lpoo.redstonetools.model.utils.TileType;

/**
 *  <h1>Constant Power Source Tile</h1>
 *  Constant source tile is a tile that provides maximum power level all the time
 *
 * @author g79
 */
public class ConstantSourceTile extends Tile implements SourceTile {

    /**
     * <h1>Indicates if it's the first time the block is updated</h1>
     */
    private boolean firstTick;

    /**
     * <h1>Constant Source constructor</h1>
     *
     * @param position  Position of the tile
     */
    public ConstantSourceTile(Position position) {
        super(position);
        firstTick = true;
    }

    /**
     * <h1>Get name of the tile</h1>
     *
     * @return  "source"
     */
    @Override
    public String getName() {
        return "source";
    }

    /**
     * <h1>Get tile information</h1>
     *
     * @return  "Power : " followed by the maximum power level
     */
    @Override
    public String getInfo() {
        return "Power : " + Power.getMax();
    }

    /**
     * <h1>Get tile type</h1>
     *
     * @see TileType
     *
     * @return  Source type
     */
    @Override
    public TileType getType() { return TileType.SOURCE; }

    /*
        TODO: Unnecessary Side Argument?
     */

    /**
     * <h1>Checks if tile is a source of power</h1>
     *
     * @see SourceTile
     *
     * @return  true
     */
    @Override
    public boolean isSource() {
        return true;
    }

    /**
     * <h1>Get the power level emitted on the side specified</h1>
     *
     * @param side  Side of the tile
     * @return  Maximum power
     */
    @Override
    public int getPower(Side side) {
        return Power.getMax();
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

    /**
     * <h1>Updates source and calculates next tick</h1>
     * Constant source only needs to be updated one time, as it emits constant power regardless of situation
     *
     * @return true if is the first update, false otherwise
     */
    @Override
    public boolean nextTick() {
        if (firstTick) {
            firstTick = false;
            return true;
        }
        return false;
    }
}
