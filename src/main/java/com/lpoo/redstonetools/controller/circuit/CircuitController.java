package com.lpoo.redstonetools.controller.circuit;

import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.model.tile.*;
import com.lpoo.redstonetools.model.utils.Position;
import com.lpoo.redstonetools.model.utils.Power;
import com.lpoo.redstonetools.model.utils.Side;

/**
 *  <h1>Circuit Controller</h1>
 *  Circuit controller handles the job of notifying the tiles of updates on a circuit
 *
 * @see Circuit
 *
 * @author g79
 */
public class CircuitController {

    // TODO: Temporary, as the name suggests (ignore the code smells ;D )
    public static Circuit loadTestCircuit() {
        CircuitController cc = new CircuitController();
        Circuit circuit = new Circuit(20, 10);

        cc.addTile(circuit, new ConstantSourceTile(new Position(0, 0)));
        cc.addTile(circuit, new WireTile(new Position(2, 0)));
        cc.addTile(circuit, new WireTile(new Position(3, 0)));
        cc.addTile(circuit, new WireTile(new Position(1, 0)));
        cc.addTile(circuit, new WireTile(new Position(4, 0)));
        cc.addTile(circuit, new WireTile(new Position(5, 0)));
        cc.addTile(circuit, new WireTile(new Position(6, 0)));
        cc.addTile(circuit, new WireTile(new Position(6, 1)));
        cc.addTile(circuit, new WireTile(new Position(6, 2)));
        cc.addTile(circuit, new WireTile(new Position(5, 2)));
        cc.addTile(circuit, new WireTile(new Position(4, 2)));
        cc.addTile(circuit, new WireTile(new Position(3, 2)));
        cc.addTile(circuit, new WireTile(new Position(2, 2)));
        cc.addTile(circuit, new WireTile(new Position(1, 2)));
        cc.addTile(circuit, new WireTile(new Position(0, 2)));
        cc.addTile(circuit, new WireTile(new Position(0, 3)));
        cc.addTile(circuit, new WireTile(new Position(0, 4)));
        cc.addTile(circuit, new WireTile(new Position(1, 4)));
        cc.addTile(circuit, new WireTile(new Position(2, 4)));
        cc.addTile(circuit, new WireTile(new Position(3, 4)));
        cc.addTile(circuit, new WireTile(new Position(4, 4)));
        cc.addTile(circuit, new WireTile(new Position(5, 4)));
        cc.addTile(circuit, new WireTile(new Position(6, 4)));
        cc.addTile(circuit, new LeverTile(new Position(7, 5)));
        cc.addTile(circuit, new LeverTile(new Position(4, 1)));

        cc.addTile(circuit, new WireTile(new Position(3, 1)));

        cc.addTile(circuit, new RepeaterTile(new Position(6, 5)));
        cc.addTile(circuit, new RepeaterTile(new Position(6, 3)));

        cc.rotateTileRight(circuit, new Position(6, 5));
        cc.rotateTileRight(circuit, new Position(6, 3));

        cc.advanceTick(circuit);

        return circuit;
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
        if (circuit.getTile(position).interact()) {
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

        for (Position position : circuit.getSources()) {
            Tile tile = circuit.getTile(position);
            if (((SourceTile)tile).nextTick()) {
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
            if (tile.update(circuit, power, side.opposite())) {
                updateAllNeighbourTiles(circuit, neighbour);
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
    private void notifyNeighbourTiles(Circuit circuit, Position position) {
        Tile tile;
        for (Side side : Side.values()) {
            tile = circuit.getTile(position.getNeighbour(side));
            tile.updateConnections(circuit);
            updateNeighbourTile(circuit, position, tile.getPower(side), side);
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
