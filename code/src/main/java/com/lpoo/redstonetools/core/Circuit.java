package com.lpoo.redstonetools.core;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import com.lpoo.redstonetools.core.tiles.SourceTile;
import com.lpoo.redstonetools.core.tiles.Tile;
import com.lpoo.redstonetools.core.tiles.NullTile;
import com.lpoo.redstonetools.core.utils.Position;
import com.lpoo.redstonetools.core.utils.Side;

import javax.xml.transform.Source;

public class Circuit {

    private Tile[][] tiles;

    private List<Position> sources;

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

        this.sources = new ArrayList<Position>();

        this.tick = 0;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void advanceTick() {
        this.tick++;

        // Update sources
        for (Position position : sources) {
            ((SourceTile) tiles[position.getY()][position.getX()]).nextTick(this);
        }
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
            getTile(neighbour).update(this, power, side.opposite());
        }
    }

    public void updateAllNeighbourTiles(Position position, int power) {
        for (Side side: Side.values()) {
            updateNeighbourTile(position, power, side);
        }
    }

    public void updateAllNeighbourTilesExcept(Position position, int power, Side side) {
        for (Side a_side: Side.values()) {
            if (!side.equals(a_side))
                updateNeighbourTile(position, power, a_side);
        }
    }

    private void safeRemoveTile(Position position) {
        if (getTile(position).isSource())
            sources.remove(position);
    }

    public void addTile(Tile tile) {
        if (!isInBounds(tile.getPosition()))
            return;

        safeRemoveTile(tile.getPosition());

        this.tiles[tile.getPosition().getY()][tile.getPosition().getX()] = tile;
        if (tile.isSource())
            this.sources.add(tile.getPosition());

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
