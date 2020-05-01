package com.lpoo.redstonetools.model.circuit;

import com.lpoo.redstonetools.model.Model;
import com.lpoo.redstonetools.model.tile.NullTile;
import com.lpoo.redstonetools.model.tile.Tile;
import com.lpoo.redstonetools.model.utils.Position;
import com.lpoo.redstonetools.model.utils.Power;
import com.lpoo.redstonetools.model.utils.Side;

import java.util.ArrayList;
import java.util.List;

/**
 *  <h1>Circuit model class</h1>
 *  Circuit is a group of tiles, all the interaction logic of the tiles is handled by the controller
 *
 * @see com.lpoo.redstonetools.controller.circuit.CircuitController
 *
 * @author g79
 */
public class Circuit implements Model {

    /**
     * <h1>Tiles of the circuit</h1>
     * Grid of tiles of the circuit
     *
     * @see Tile
     */
    private Tile[][] tiles;

    /**
     * <h1>List of circuit source tiles</h1>
     * Source tiles of the circuits, capable of generating power on their own
     */
    private List<Position> sources;

    /**
     * <h1>Circuit width dimension</h1>
     */
    private int width;

    /**
     * <h1>Circuit height dimension</h1>
     */
    private int height;

    /**
     * <h1>Circuit tick</h1>
     * Simulates the update rate of the circuit
     */
    private long tick;

    /**
     * <h1>Circuit constructor</h1>
     * The grid of tiles is filled with empty tiles
     * It also sets the initial tick value
     * The sources are initially empty
     *
     * @param width     Width of the circuit
     * @param height    Height of the circuit
     */
    public Circuit(int width, int height) {
        this.width = width;
        this.height = height;
        this.tiles = new Tile[height][width];

        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                this.tiles[i][j] = new NullTile(new Position(j, i));
            }
        }

        this.sources = new ArrayList<>();

        this.tick = 0;
    }

    /**
     * <h1>Gets the value of the circuit width</h1>
     *
     * @return  Circuit width
     */
    public int getWidth() { return width; }

    /**
     * <h1>Gets the value of the circuit height</h1>
     *
     * @return  Circuit height
     */
    public int getHeight() { return height; }

    /**
     * <h1>Gets the current tick value of the circuit</h1>
     *
     * @return  Circuit tick value
     */
    public long getTick() { return tick; }

    /**
     * <h1>Increments the circuit tick</h1>
     *
     * All operations to be done on new ticks are handled by the circuit controller
     *
     * @see com.lpoo.redstonetools.controller.circuit.CircuitController
     */
    public void advanceTick() { tick++; }

    /**
     * <h1>Gets the list of source tiles</h1>
     *
     * @return  Circuit source tiles
     */
    public List<Position> getSources() { return sources; }

    /**
     * <h1>Checks validity of a position</h1>
     * Tests if the position given is inside the circuit
     *
     * @param position  Position to be tested
     * @return  true if the position is inside the circuit, false otherwise
     */
    public boolean isInBounds(Position position) {
        return  position.getX() >= 0 &&
                position.getX() < width &&
                position.getY() >= 0 &&
                position.getY() < height;
    }

    /**
     * <h1>Gets the tile in the specified position of the circuit</h1>
     * Gets the tile in the specified position of the circuit if the position is valid
     * If the position isn't valid, it is returned a new empty tile
     * 
     * @see Circuit#isInBounds(Position)
     * @see NullTile
     *
     * @param position  Position of the tile
     * @return  Tile in the position specified if valid, <code>NullTile</code> on invalid position
     */
    public Tile getTile(Position position) {
        if (!isInBounds(position))
            return new NullTile(position);

        return this.tiles[position.getY()][position.getX()];
    }

    /**
     * <h1>Gets the tile in the specified position of the circuit</h1>
     * Wrapper of function <code>getTile(Position)</code> to accept two integer values instead of a <code>Position</code> object
     *
     * @see Circuit#getTile(Position)
     * 
     * @param x     Position on the horizontal axis
     * @param y     Position on the vertical axis
     * @return  Tile in the position specified if valid, <code>NullTile</code> on invalid position
     */
    public Tile getTile(int x, int y) {
        return getTile(new Position(x, y));
    }

    /**
     * <h1>Removes a tile from the sources</h1>
     * Auxiliary function to guarantee the integrity of the source tiles
     *
     * @param position  Position of the tile to be removed
     */
    private void safeRemoveTile(Position position) {
        if (getTile(position).isSource())
            sources.remove(position);
    }

    /**
     * <h1>Adds tile to the circuit</h1>
     * Adds tile to the circuit if tile has a valid position
     * To guarantee integrity it removes the previous tile from the sources if needed
     * If the tile to be added is also a source, then it is added to the list of sources
     * 
     * @see Circuit#safeRemoveTile(Position)
     *
     * @param tile  Tile to be added
     * @return  true if the tile was added, false otherwise
     */
    public boolean addTile(Tile tile) {
        if (!isInBounds(tile.getPosition()))
            return false;

        safeRemoveTile(tile.getPosition());

        this.tiles[tile.getPosition().getY()][tile.getPosition().getX()] = tile;
        if (tile.isSource())
            this.sources.add(tile.getPosition());
        tile.updateConnections(this);
        return true;
    }

    /**
     * <h1>Removes tile from circuit</h1>
     * Removing a tile from the circuit is switching the tile for a empty tile, this is adding a <code>NullTile</code>
     *
     * @see NullTile
     * @see Circuit#addTile(Tile)
     *
     * @param position  Position of the tile to be removed
     */
    public void removeTile(Position position) {
        addTile(new NullTile(position));
    }

    /**
     * <h1>Get the power level received from the neighbours</h1>
     * Gets the maximum power level received from the neighbour tiles
     *
     * @param position  Position of the tile to check surroundings
     * @return  Maximum power level in the neighbourhood
     */
    public int getSurroundingPower(Position position) {
        int maxPower = Power.getMin();
        for (Side side : Side.values()) {
            Tile tile = getTile(position.getNeighbour(side));
            maxPower = Math.max(maxPower, tile.isWire() ?
                                                Power.decrease(tile.getPower(side.opposite()))
                                                : tile.getPower(side.opposite())
                                );
        }
        return maxPower;
    }

    /**
     * <h1>Get the power level received from the neighbour wires</h1>
     * Gets the maximum power level received from the neighbour wires
     *
     * @param position  Position of the tile to check surroundings
     * @return  Maximum power level in the neighbourhood
     */
    public int getSurroundingWirePower(Position position) {
        int maxPower = Power.getMin();
        for (Side side : Side.values()) {
            Tile tile = getTile(position.getNeighbour(side));
            if (tile.isWire())
                maxPower = Math.max(maxPower, Power.decrease(tile.getPower(side.opposite())));
        }
        return maxPower;
    }

    /**
     * <h1>Get the power level received from the neighbour non-wire tiles</h1>
     * Gets the maximum power level received from the neighbour non-wire tiles
     *
     * @param position  Position of the tile to check surroundings
     * @return  Maximum power level in the neighbourhood
     */
    public int getSurroundingGatePower(Position position) {
        int maxPower = Power.getMin();
        for (Side side : Side.values()) {
            Tile tile = getTile(position.getNeighbour(side));
            if (!tile.isWire())
                maxPower = Math.max(maxPower, tile.getPower(side.opposite()));
        }
        return maxPower;
    }

    /**
     * <h1>Tests if tiles can connect</h1>
     * A tile can connect to other if the side of contact of the tiles are output and input or vice-versa
     *
     * @param position  Position of the tile
     * @param side      Side of the neighbour to test with
     * @return  true if the tiles are connectable, false otherwise
     */
    public boolean canTilesConnect(Position position, Side side) {
        Tile a = getTile(position);
        Tile b = getTile(position.getNeighbour(side));
        return (a.acceptsPower(side) && b.outputsPower(side.opposite())) ||
                (a.outputsPower(side) && b.acceptsPower(side.opposite()));
    }
}
