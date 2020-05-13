package com.lpoo.redstonetools.controller.circuit;

import com.lpoo.redstonetools.exception.InvalidCircuitException;
import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.model.tile.*;
import com.lpoo.redstonetools.model.utils.Position;
import com.lpoo.redstonetools.model.utils.Side;
import com.lpoo.redstonetools.model.utils.TileType;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 *  <h1>Circuit Controller</h1>
 *  Circuit controller handles the job of notifying the tiles of updates on a circuit
 *
 * @see Circuit
 *
 * @author g79
 */
public class CircuitController {

    private static final int MAX_UPDATES = 10;
    private Map<Position, Integer> updateTracker;


    public CircuitController() {
        updateTracker = new HashMap<Position, Integer>();
    }

    /**
     * <h1>Loads a circuit from a .ser file</h1>
     * @see Serializable
     * @param filename File containing the Circuit object to be loaded
     * @return Loaded circuit upon success
     * @throws InvalidCircuitException Thrown otherwise
     */
    public static Circuit loadCircuit(String filename) throws InvalidCircuitException {
        Circuit circuit;
        try {
            FileInputStream fileIn = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            circuit = (Circuit) in.readObject();
            in.close();
            fileIn.close();
        } catch (Exception i) {
            i.printStackTrace();
            throw new InvalidCircuitException("'" + filename + "' is not a valid circuit");
        }
        return circuit;
    }

    /**
     * <h1>Saves a Circuit to a .ser file</h1>
     * Changes the filename to always end in ".ser"
     * @see Serializable
     * @param filename File for the object to be saved
     * @return True upon success, false otherwise
     */
    public static boolean saveCircuit(Circuit circuit, String filename) {
        filename = filename.endsWith(".ser") ? filename : filename + ".ser";
        try {
            FileOutputStream fileOut =
                    new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(circuit);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * <h1>Adds tile to circuit</h1>
     * Wrapper of circuit <code>addTile</code> function
     * On top of adding the tile to the circuit, it handles the process of notifying all neighbour wires of the addition
     * in order to update the wire connections
     *
     * @see Circuit#addTile(Tile)
     *
     * @param circuit   Circuit where tile will be added
     * @param tile      Tile to be added
     */
    public void addTile(Circuit circuit, Tile tile) {
        if (circuit.addTile(tile)) {
            tile.update(circuit);
            notifyNeighbourTiles(circuit, tile.getPosition());
        }
    }

    /**
     * <h1>Interacts with the tile at position</h1>
     * Wrapper of <code>interact</code> function
     * On top of interacting with the tile, updates the neighbour tiles if needed
     *
     * @see Tile#interact()
     *
     * @param circuit   Circuit where tile will be added
     * @param position  Position of the Tile to be interacted with
     */
    public void interact(Circuit circuit, Position position) {
        Tile tile = circuit.getTile(position);
        if (tile.interact()) {
            tile.update(circuit);
            updateAllNeighbourTiles(circuit, position);
        }
    }

    /**
     * <h1>Advances to the next tick of the circuit</h1>
     * Wrapper of circuit <code>advanceTick</code> function
     * On top of updating the circuit tick, it handles all the updates that need to be done on each tick
     *
     * @see Circuit#advanceTick()
     *
     * @param circuit   Circuit to advance the tick
     */
    public void advanceTick(Circuit circuit) {
        circuit.advanceTick();

        for (Position position : circuit.getTickedTiles()) {
            if (circuit.getTile(position).nextTick()) {
                updateAllNeighbourTiles(circuit, position);
            }
        }
    }

    /**
     * <h1>Sends updates to all neighbours tiles</h1>
     * Sends updates to all neighbour tiles if the tile that originated the update outputs on that side
     *
     * @param circuit       Circuit where the updates are being done
     * @param position      Position of the tile that originated the update
     */
    public void updateAllNeighbourTiles(Circuit circuit, Position position) {
        Tile tile = circuit.getTile(position);
        for (Side side: Side.values()) {
            if (tile.outputsPower(side))
                updateNeighbourTile(circuit, position, tile.getPower(side), side);
        }
    }

    /**
     * <h1>Sends update to a neighbour tile</h1>
     * Sends update to the neighbour tile of the side specified, if that tile generates an update it will be started a
     * new chain of updates of the neighbour tiles.
     *
     * @param circuit       Circuit where the updates are being done
     * @param position      Position of the tile that originated the update
     * @param power         Power level to send on the update
     * @param side          Side of the tile where the update will be sent to
     */
    public void updateNeighbourTile(Circuit circuit, Position position, int power, Side side) {
        Position neighbour = position.getNeighbour(side);
        if (circuit.isInBounds(neighbour)) {
            Tile tile = circuit.getTile(neighbour);
            if (tile.getType() != TileType.NULL) {
                if (!tile.isWire()) {
                    try {
                        updateTracker.put(neighbour, updateTracker.getOrDefault(neighbour, 0) + 1);
                        if (updateTracker.get(neighbour) < MAX_UPDATES) {
                            if (tile.update(circuit, power, side.opposite())) {
                                updateAllNeighbourTiles(circuit, neighbour);
                            }
                        } else {
                            // Shortcircuit
                            addTile(circuit, new NullTile(neighbour.clone(), true));
                        }
                    } finally {
                        updateTracker.put(neighbour, updateTracker.getOrDefault(neighbour, 1) - 1);
                    }
                } else {
                    if (tile.update(circuit, power, side.opposite())) {
                        updateAllNeighbourTiles(circuit, neighbour);
                    }
                }
            }
        }
    }

    /**
     * <h1>Notifies all neighbour tiles of change on the tile specified</h1>
     * Sends a notification to all neighbours of the tile specified to handle the updates on the tile connections
     * 
     * @see Tile#updateConnections(Circuit)
     *
     * @param circuit       Circuit where updates are being done
     * @param position      Position of the tile that generated the update
     */
    public void notifyNeighbourTiles(Circuit circuit, Position position) {
        Tile self = circuit.getTile(position);
        Tile tile;
        for (Side side : Side.values()) {
            tile = circuit.getTile(position.getNeighbour(side));
            tile.updateConnections(circuit);
            updateNeighbourTile(circuit, position, self.getPower(side), side);
        }
    }

    /**
     * <h1>Rotates a tile of the circuit to the left</h1>
     * Handles the rotation of a tile of the circuit, generating all the updates and notifications needed upon rotation
     *
     * @see Tile#rotateLeft()
     *
     * @param circuit       Circuit where updates are being done
     * @param position      Position of the tile to be rotated
     */
    public void rotateTileLeft(Circuit circuit, Position position) {
        if (!circuit.isInBounds(position))
            return;

        Tile tile = circuit.getTile(position);

        if (tile.rotateLeft()) {
            tile.updateConnections(circuit);
            tile.update(circuit);
            notifyNeighbourTiles(circuit, position);
        }
    }

    /**
     * <h1>Rotates a tile of the circuit to the right</h1>
     * Handles the rotation of a tile of the circuit, generating all the updates and notifications needed upon rotation
     *
     * @see Tile#rotateRight()
     *
     * @param circuit       Circuit where updates are being done
     * @param position      Position of the tile to be rotated
     */
    public void rotateTileRight(Circuit circuit, Position position) {
        if (!circuit.isInBounds(position))
            return;

        Tile tile = circuit.getTile(position);

        if (tile.rotateRight()) {
            tile.updateConnections(circuit);
            tile.update(circuit);
            notifyNeighbourTiles(circuit, position);
        }
    }

}
