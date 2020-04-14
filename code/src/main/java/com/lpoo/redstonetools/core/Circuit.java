package com.lpoo.redstonetools.core;

import java.util.ArrayList;
import com.lpoo.redstonetools.core.tiles.Tile;
import com.lpoo.redstonetools.core.tiles.NullTile;
import com.lpoo.redstonetools.core.utils.Position;
import com.lpoo.redstonetools.core.utils.Side;
import javafx.geometry.Pos;

public class Circuit {

    Tile[][] tiles;

    private int width;
    private int height;

    private long tick;

    public Circuit(int width, int height) {
        this.width = width;
        this.height = height;

        this.tiles = new Tile[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                this.tiles[i][j] = new NullTile(new Position(j, i));
            }
        }

        this.tick = 0;
    }

    public void advanceTick() {
        // Update stuffs
        this.tick++;
    }

    public long getTick() {
        return tick;
    }

    private boolean isInBounds(Position position) {
        return  position.getX() >= 0 &&
                position.getX() < width &&
                position.getY() >= 0 &&
                position.getY() < height;
    }

    // Side is the side the update came from
    public void updateNeighbourTile(Position position, int power, Side side) {
        Position neighbour = position.getNeighbour(side);
        if (isInBounds(neighbour)) {
            tiles[position.getY()][position.getX()].update(this, power, side.opposite());
        }
    }

    public void updateAllNeighbourTiles(Position position, int power) {
        for (Side side: Side.values()) {
            updateNeighbourTile(position, power, side);
        }
    }

    public void addTile(Tile tile) {
        this.tiles[tile.getPosition().getY()][tile.getPosition().getX()] = tile;
    }

    public void removeTile(Position position) {
        addTile(new NullTile(position));
    }

    public Tile getTile(Position position) {
        if (!isInBounds(position))
            return new NullTile(position);

        return this.tiles[position.getY()][position.getX()];
    }

    public Tile getTile(int x, int y) {
        return getTile(new Position(x, y));
    }

}
