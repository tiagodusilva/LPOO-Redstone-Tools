package com.lpoo.redstonetools.controller.circuit;

import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.model.tile.SourceTile;
import com.lpoo.redstonetools.model.tile.Tile;
import com.lpoo.redstonetools.model.utils.Position;
import com.lpoo.redstonetools.model.utils.Side;

public class CircuitController {

    public void addTile(Circuit circuit, Tile tile) {
        if (circuit.addTile(tile)) {
            notifyNeighbourWires(circuit, tile.getPosition());
        }
    }

    public void advanceTick(Circuit circuit) {
        circuit.advanceTick();

        for (Position position : circuit.getSources()) {
            Tile tile = circuit.getTile(position);
            if (((SourceTile)tile).nextTick(circuit)) {
                updateAllNeighbourTiles(circuit, position);
            }
        }
    }

    private void updateAllNeighbourTiles(Circuit circuit, Position position) {
        Tile tile = circuit.getTile(position);
        for (Side side: Side.values()) {
            if (tile.outputsPower(side))
                updateNeighbourTile(circuit, position, tile.getPower(side), side);
        }
    }

    public void updateNeighbourTile(Circuit circuit, Position position, int power, Side side) {
        Position neighbour = position.getNeighbour(side);
        if (circuit.isInBounds(neighbour)) {
            if (circuit.getTile(neighbour).update(circuit, power, side.opposite())) {
                updateAllNeighbourTiles(circuit, neighbour);
            }
        }
    }

    private void notifyNeighbourWires(Circuit circuit, Position position) {
        for (Side side : Side.values()) {
            circuit.getTile(position.getNeighbour(side)).updateConnections(circuit);
        }
    }

    public void rotateTileLeft(Circuit circuit, Position position) {
        if (!circuit.isInBounds(position))
            return;

        Tile tile = circuit.getTile(position);

        if (tile.rotateLeft()) {
            tile.updateConnections(circuit);
            notifyNeighbourWires(circuit, position);
        }
    }

    public void rotateTileRight(Circuit circuit, Position position) {
        if (!circuit.isInBounds(position))
            return;

        Tile tile = circuit.getTile(position);

        if (tile.rotateRight()) {
            tile.updateConnections(circuit);
            notifyNeighbourWires(circuit, position);
        }
    }

}
