package com.lpoo.redstonetools.model.tile;

import com.lpoo.redstonetools.model.utils.Position;
import com.lpoo.redstonetools.model.utils.Side;
import com.lpoo.redstonetools.model.utils.SideType;

import java.util.HashMap;
import java.util.Map;

/**
 *  <h1>Oriented Tile</h1>
 *  Oriented tile is a tile that orientation of the tile influences on the functionality
 *  Oriented tile can have input sides, output sides and sides that don't interact
 *
 * @author g79
 */
public abstract class OrientedTile extends Tile {

    /**
     * <h1>Side and its type</h1>
     * Stores the type of the side
     */
    protected Map<Side, SideType> sides;

    /**
     * <h1>Oriented tile constructor</h1>
     * By default all the sides are neither input or output
     *
     * @see SideType#DEFAULT
     *
     * @param position  Position of the tile
     */
    public OrientedTile(Position position) {
        super(position);
        sides = new HashMap<>();
        for (Side side : Side.values())
            sides.put(side, SideType.DEFAULT);
    }

    /**
     * <h1>Rotates a tile to the left</h1>
     * In an oriented tile rotating a tile to the left is switching the types of the sides in counter clock-wise direction
     *
     * @return  true
     */
    @Override
    public boolean rotateLeft() {
        SideType leftType = sides.getOrDefault(Side.LEFT, SideType.DEFAULT);
        SideType rightType = sides.getOrDefault(Side.RIGHT, SideType.DEFAULT);
        sides.put(Side.LEFT, sides.getOrDefault(Side.UP, SideType.DEFAULT));
        sides.put(Side.RIGHT, sides.getOrDefault(Side.DOWN, SideType.DEFAULT));
        sides.put(Side.DOWN, leftType);
        sides.put(Side.UP, rightType);
        return true;
    }

    /**
     * <h1>Rotates a tile to the left</h1>
     * In an oriented tile rotating a tile to the left is switching the types of the sides in clock-wise direction
     *
     * @return  true
     */
    @Override
    public boolean rotateRight() {
        SideType leftType = sides.getOrDefault(Side.LEFT, SideType.DEFAULT);
        SideType rightType = sides.getOrDefault(Side.RIGHT, SideType.DEFAULT);
        sides.put(Side.RIGHT, sides.getOrDefault(Side.UP, SideType.DEFAULT));
        sides.put(Side.LEFT, sides.getOrDefault(Side.DOWN, SideType.DEFAULT));
        sides.put(Side.UP, leftType);
        sides.put(Side.DOWN, rightType);
        return true;
    }

    /**
     * <h1>Checks if side specified is an input of power</h1>
     *
     * @param side  Side of the tile to be checked
     * @return  true if the side is an input, false otherwise
     */
    @Override
    public boolean acceptsPower(Side side) { return sides.getOrDefault(side, SideType.DEFAULT).isInput(); }

    /**
     * <h1>Checks if side specified is an output of power</h1>
     *
     * @param side  Side of the tile to be checked
     * @return  true if the side is an output, false otherwise
     */
    @Override
    public boolean outputsPower(Side side) { return sides.getOrDefault(side, SideType.DEFAULT).isOutput(); }
}
