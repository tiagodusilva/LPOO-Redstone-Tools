package com.lpoo.redstonetools.model.tile;

import com.lpoo.redstonetools.model.Model;
import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.model.utils.Position;
import com.lpoo.redstonetools.model.utils.Side;
import com.lpoo.redstonetools.model.utils.TileType;

/**
 *  <h1>Tile</h1>
 *  Tile is a part of the circuit, containing its own functionality
 *
 * @author g79
 */
public abstract class Tile implements Model {

    /**
     * <h1>Position of the tile</h1>
     */
    protected Position position;

    /**
     * <h1>Tile constructor</h1>
     *
     * @param position  Position of the tile
     */
    public Tile(Position position) {
        this.position = position;
    }

    /**
     * <h1>Gets the tile position</h1>
     *
     * @return  Position of the tile
     */
    public Position getPosition() { return position; }

    /**
     * <h1>Get name of the tile</h1>
     *
     * @return  Name given to the tile
     */
    public abstract String getName();

    /**
     * <h1>Get tile information</h1>
     *
     * @return  Information of the tile
     */
    public abstract String getInfo();

    /**
     * <h1>Get tile type</h1>
     *
     * @see TileType
     *
     * @return  Tile type
     */
    public abstract TileType getType();

    /**
     * <h1>Checks if tile is connected to neighbour tile on the side specified</h1>
     * By default, a tile can't connect to any neighbour tiles
     *
     * @param side  Side to test connection
     * @return  true if tile is connected, false otherwise
     */
    public boolean isConnected(Side side) {
        return false;
    }

    /**
     * <h1>Checks if tile is a source of power</h1>
     * By default a tile isn't a source tile
     * Must be overridden if tile is a source
     *
     * @see SourceTile
     *
     * @return  true if tile is a source tile, false otherwise
     */
    public boolean isSource() { return false; }

    /**
     * <h1>Checks if tile is a wire</h1>
     * By default a tile isn't a wire
     * Must be overridden if tile is a wire
     *
     * @see WireTile
     *
     * @return  true if tile is a wire, false otherwise
     */
    public boolean isWire() { return false; }

    /**
     * <h1>Rotates a tile to the left</h1>
     * By default rotating a tile doesn't affect anything, so by default it returns false as the rotation is meaningless
     * Needs to be overridden if tile can be rotated
     *
     * @return  true if tile was rotated, false otherwise
     */
    public boolean rotateLeft() { return false; }

    /**
     * <h1>Rotates a tile to the right</h1>
     * By default rotating a tile doesn't affect anything, so by default it returns false as the rotation is meaningless
     * Needs to be overridden if tile can be rotated
     *
     * @return  true if tile was rotated, false otherwise
     */
    public boolean rotateRight() { return false; }

    /**
     * <h1>Get the power level emitted on the side specified</h1>
     *
     * @param side  Side of the tile
     * @return  Power level emitted on the side specified of the tile
     */
    public abstract int getPower(Side side);

    /**
     * <h1>Checks if side specified is an input of power</h1>
     *
     * @param side  Side of the tile to be checked
     * @return  true if the tile accepts power from the side specified, false otherwise
     */
    public abstract boolean acceptsPower(Side side);

    /**
     * <h1>Checks if side specified is an output of power</h1>
     *
     * @param side  Side of the tile to be checked
     * @return  true if the tile outputs power from the side specified, false otherwise
     */
    public abstract boolean outputsPower(Side side);

    /**
     * <h1>Triggers a tile update</h1>
     * By default tile doesn't need any update, not needing to trigger updates, returning false
     * Must be overridden if tile needs to be updated
     *
     * @param circuit   Circuit where updates are taking place
     * @param power     Power received on the update
     * @param side      Side from which repeater received an update
     * @return  true if tile was updated, false otherwise
     */
    public boolean update(Circuit circuit, int power, Side side) {
        return false;
    }

    /**
     * <h1>Updates the tile</h1>
     * By default tile doesn't need any update, returning false
     * Must be overridden if tile needs to be updated
     *
     * @param circuit   Circuit where updates are taking place
     * @param power     Power received on the update
     * @param side      Side from which repeater received an update
     * @return  true if tile was updated, false otherwise
     */
    protected boolean onChange(Circuit circuit, int power, Side side) {
        return false;
    }

    /**
     * <h1>Updates tile connections to other tiles</h1>
     * A tile can connect to other if the side of contact of the tiles are output and input or vice-versa
     *
     * @param circuit   Circuit where update are taking place
     */
    public void updateConnections(Circuit circuit) { }

    /**
     * <h1>Interacts with a tile</h1>
     * By default tile doesn't need to change, not needing to trigger updates, returning false
     * Must be overridden if tile has interactions
     *
     * @return  true if tile was updated, false otherwise
     */
    public boolean interact() { return false; }
}
