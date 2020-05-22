package com.lpoo.redstonetools.model.circuit;

import com.lpoo.redstonetools.model.Model;
import com.lpoo.redstonetools.model.tile.IOTile;
import com.lpoo.redstonetools.model.tile.NullTile;
import com.lpoo.redstonetools.model.tile.Tile;
import com.lpoo.redstonetools.model.utils.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.io.Serializable;

/**
 *  <h1>Circuit model class</h1>
 *  Circuit is a group of tiles, all the interaction logic of the tiles is handled by the controller
 *
 * @see com.lpoo.redstonetools.controller.circuit.CircuitController
 *
 * @author g79
 */
public class Circuit extends Tile implements Model, Serializable {

    /**
     * <h1>Tiles of the circuit</h1>
     * Grid of tiles of the circuit
     *
     * @see Tile
     */
    private Tile[][] tiles;

    /**
     * <h1>Set of circuit ticked tiles</h1>
     *
     * @see Tile#isTickedTile()
     */
    private Set<Position> tickedTiles;

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
     * <h1>Table of IO tiles</h1>
     */
    private Map<Side, Position> ioTiles;

    /**
     * <h1>Error position of the circuit</h1>
     */
    private static final Position errorPosition = new Position(-1, -1);

    public Circuit(int width, int height, Position position) {
        super(position);
        this.width = width;
        this.height = height;
        this.tiles = new Tile[height][width];

        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                this.tiles[i][j] = new NullTile(new Position(j, i));
            }
        }

        this.tickedTiles = new HashSet<>();
        this.ioTiles = new HashMap<>();

        this.tick = 0;
    }

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
        this(width, height, errorPosition);
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
     * <h1>Gets the set of ticked tiles</h1>
     *
     * @return  Circuit ticked tiles
     */
    public Set<Position> getTickedTiles() { return tickedTiles; }

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
        Tile tile = getTile(position);
        if (tile.isTickedTile())
            tickedTiles.remove(position);

        if (tile.getType() == TileType.IO) {
            ioTiles.remove(((IOTile)tile).getIOSide().opposite(), position);
        }
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
        if (tile.isTickedTile())
            this.tickedTiles.add(tile.getPosition());
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
     * <h1>Get the power level received from a specific neighbour</h1>
     * Self explanatory
     *
     * @param position Position of the tile to check surroundings
     * @param side Side of the tile
     * @return Power received from the specific side
     */
    public int getSurroundingPower(Position position, Side side) {
        Tile tile = getTile(position.getNeighbour(side));
        return tile.isWire() ?
                Power.decrease(tile.getPower(side.opposite()))
                : tile.getPower(side.opposite());
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
            maxPower = Math.max(maxPower, getSurroundingPower(position, side));
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

    /**
     * <h1>Get IO tile in side specified</h1>
     * If circuit doesn't have IO tile on the side the behaviour is defined by the methods used
     *
     * @see Circuit#getTile(Position) 
     *
     * @param side Side where IO wanted is
     *
     * @return IO tile at the side specified
     */
    public Tile getIO(Side side) {
        Position ioPos = ioTiles.getOrDefault(side, errorPosition);
        return getTile(ioPos);
    }

    /**
     * <h1>Set IO tile in side specified</h1>
     *
     * @see Circuit#getTile(Position)
     *
     * @param side      Side where IO wanted is
     * @param position  Position of the IO tile
     *
     */
    public void setIO(Side side, Position position) {
        ioTiles.put(side, position);
    }

    /**
     * <h1>Updates IO tile to circuit mapping on interaction</h1>
     * Verifies if the IO tile can be updated, and updates it if possible
     *
     * @param position  Position of the IO tile to update
     * @return  true if IO tile was updated, false otherwise
     */
    public boolean updateOnIOInteract(Position position) {
        Tile toUpdate = getTile(position);
        if (toUpdate.getType() != TileType.IO) return false;
        Side side = ((IOTile)toUpdate).getIOSide();
        Tile toReplace = getIO(side.opposite());
        boolean differentTile = !toUpdate.equals(toReplace);

        if (toUpdate.acceptsPower(side) || toUpdate.outputsPower(side)) {
            if (toReplace.getType() == TileType.IO && differentTile) return false;
            ioTiles.put(side.opposite(), position);
        } else {
            if (differentTile) return false;
            ioTiles.remove(side.opposite(), position);
        }
        return true;
    }

    /**
     * <h1>Updates IO tile to circuit mapping on rotation</h1>
     * Verifies if the IO tile can be updated, and updates it if possible
     *
     * @param position  Position of the IO tile to update
     * @param previous  IO side before rotation
     *
     * @return  true if IO tile was updated, false otherwise
     */
    public boolean updateOnIORotation(Position position, Side previous) {
        Tile toUpdate = getTile(position);
        if (toUpdate.getType() != TileType.IO) return false;
        Side side = ((IOTile)toUpdate).getIOSide();
        if (side.equals(previous)) return false;

        boolean hasIOPort = toUpdate.acceptsPower(side) || toUpdate.outputsPower(side);

        if (hasIOPort) {
            Tile toReplace = getIO(side.opposite());

            if (toReplace.getType() == TileType.IO) return false;

            ioTiles.remove(previous.opposite(), position);
            ioTiles.put(side.opposite(), position);
        }
        return true;
    }

    /**
     * <h1>Get name of the tile</h1>
     *
     * @return  "circuit"
     */
    @Override
    public String getName() {
        return "circuit";
    }

    /**
     * <h1>Get tile information</h1>
     *
     * @return "Custom circuit"
     */
    @Override
    public String getInfo() {
        return "Custom circuit";
    }

    /**
     * <h1>Get tile type</h1>
     *
     * @see TileType
     *
     * @return  Circuit type
     */
    @Override
    public TileType getType() {
        return TileType.CIRCUIT;
    }

    /**
     * <h1>Get the power level emitted on the side specified</h1>
     * Circuit power level emitted depends on the IO ports it has
     *
     * @param side  Side of the tile (circuit)
     * @return  IO port power level if it is an output port, minimum power otherwise
     */
    @Override
    public int getPower(Side side) {
        if (outputsPower(side)) {
            Tile tile = getIO(side);
            return ((IOTile)tile).getPower();
        }
        return Power.getMin();
    }

    /**
     * <h1>Rotates a tile to the left</h1>
     * In a circuit tile it rotates the sides pointing to IO tiles
     *
     * @param circuit Circuit where rotation is taking place
     *
     * @return  true
     */
    @Override
    public boolean rotateLeft(Circuit circuit) {
        // TODO
        /*Position leftPos = ioTiles.getOrDefault(Side.LEFT, errorPosition);
        Position rightPos = ioTiles.getOrDefault(Side.RIGHT, errorPosition);
        ioTiles.put(Side.LEFT, ioTiles.getOrDefault(Side.UP, errorPosition));
        ioTiles.put(Side.RIGHT, ioTiles.getOrDefault(Side.DOWN, errorPosition));
        ioTiles.put(Side.DOWN, leftPos);
        ioTiles.put(Side.UP, rightPos);*/
        return false;
    }

    /**
     * <h1>Rotates a tile to the right</h1>
     * In a circuit tile it rotates the sides pointing to IO tiles
     *
     * @param circuit Circuit where rotation is taking place
     *
     * @return  true
     */
    @Override
    public boolean rotateRight(Circuit circuit) {
        // TODO
        /*Position leftPos = ioTiles.getOrDefault(Side.LEFT, errorPosition);
        Position rightPos = ioTiles.getOrDefault(Side.RIGHT, errorPosition);
        ioTiles.put(Side.RIGHT, ioTiles.getOrDefault(Side.UP, errorPosition));
        ioTiles.put(Side.LEFT, ioTiles.getOrDefault(Side.DOWN, errorPosition));
        ioTiles.put(Side.UP, leftPos);
        ioTiles.put(Side.DOWN, rightPos);*/
        return false;
    }

    /**
     * <h1>Checks if tile is a ticked of power</h1>
     * A circuit has its update tick as well as it can have ticked tiles inside it, so it is a ticked tile
     *
     * @return  true
     */
    @Override
    public boolean isTickedTile() {
        return true;
    }

    /**
     * <h1>Increments the circuit tick</h1>
     *
     * All operations to be done on new ticks are handled by the circuit controller
     *
     * @see com.lpoo.redstonetools.controller.circuit.CircuitController
     * @see Tile#isTickedTile()
     *
     * @return true
     */
    public boolean nextTick() {
        tick++;
        return true;
    }

    /**
     * <h1>Checks if side specified is an input of power</h1>
     * In a circuit it functions reversed, this is:
     *  - Having an output IO port means it accepts power from the outside via that IO port
     *  - Having an input IO port means it outputs power to the outside via that IO port
     *
     * @param side  Side of the tile to be checked
     * @return  true if the side is an input, false otherwise
     */
    @Override
    public boolean acceptsPower(Side side) { return getIO(side).outputsPower(side.opposite()); }

    /**
     * <h1>Checks if side specified is an output of power</h1>
     *
     * @param side  Side of the tile to be checked
     * @return  true if the side is an output, false otherwise
     */
    @Override
    public boolean outputsPower(Side side) { return getIO(side).acceptsPower(side.opposite()); }

    /**
     * <h1>Triggers a tile update</h1>
     * Checks if inner circuit needs to be updated
     * This update depends on whether the circuit has IO ports or not
     *
     * @param circuit   Outer circuit where updates are taking place
     * @param power     Power received on the update
     * @param side      Side from which inner circuit received an update
     * @return  true if inner circuit was updated, false otherwise
     */
    @Override
    public boolean update(Circuit circuit, int power, Side side) {
        Tile tile = getIO(side);
        boolean needs_update = tile.getPower(side.opposite()) != power;
        return acceptsPower(side) && needs_update;
    }

    /**
     * <h1>Updates the tile</h1>
     *
     * @param circuit   Outer circuit where updates are taking place
     * @param power     Power received on the update
     * @param side      Side from which inner circuit received an update
     * @return  false
     */
    @Override
    public boolean onChange(Circuit circuit, int power, Side side) {
        return false;
    }
}
