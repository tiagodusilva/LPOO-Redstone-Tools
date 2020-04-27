package com.lpoo.redstonetools.model.circuit;

import com.lpoo.redstonetools.model.tile.NullTile;
import com.lpoo.redstonetools.model.tile.Tile;
import com.lpoo.redstonetools.model.utils.Position;
import com.lpoo.redstonetools.model.utils.Power;
import com.lpoo.redstonetools.model.utils.Side;

import java.util.ArrayList;
import java.util.List;

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

        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                this.tiles[i][j] = new NullTile(new Position(j, i));
            }
        }

        this.sources = new ArrayList<>();

        this.tick = 0;
    }

    public int getWidth() { return width; }

    public int getHeight() { return height; }

    public long getTick() { return tick; }

    public void advanceTick() { tick++; }

    public List<Position> getSources() { return sources; }

    public boolean isInBounds(Position position) {
        return  position.getX() >= 0 &&
                position.getX() < width &&
                position.getY() >= 0 &&
                position.getY() < height;
    }

    public Tile getTile(Position position) {
        if (!isInBounds(position))
            return new NullTile(position);

        return this.tiles[position.getY()][position.getX()];
    }

    public Tile getTile(int x, int y) {
        return getTile(new Position(x, y));
    }

    private void safeRemoveTile(Position position) {
        if (getTile(position).isSource())
            sources.remove(position);
    }

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

    public void removeTile(Position position) {
        addTile(new NullTile(position));
    }

    public int getSurroundingPower(Position position) {
        int maxPower = Power.getMin();
        for (Side side : Side.values()) {
            Tile tile = getTile(position.getNeighbour(side));
            maxPower = Math.max(maxPower, tile.isWire() ?
                                                tile.getPower(side.opposite()) - 1
                                                : tile.getPower(side.opposite())
                                );
        }
        return maxPower;
    }

    public int getSurroundingWirePower(Position position) {
        int maxPower = Power.getMin();
        for (Side side : Side.values()) {
            Tile tile = getTile(position.getNeighbour(side));
            if (tile.isWire())
                maxPower = Math.max(maxPower, tile.getPower(side.opposite()) - 1);
        }
        return maxPower;
    }

    public int getSurroundingGatePower(Position position) {
        int maxPower = Power.getMin();
        for (Side side : Side.values()) {
            Tile tile = getTile(position.getNeighbour(side));
            if (!tile.isWire())
                maxPower = Math.max(maxPower, tile.getPower(side.opposite()));
        }
        return maxPower;
    }

    public boolean canTilesConnect(Position position, Side side) {
        Tile a = getTile(position);
        Tile b = getTile(position.getNeighbour(side));
        return (a.acceptsPower(side) && b.outputsPower(side.opposite())) ||
                (a.outputsPower(side) && b.acceptsPower(side.opposite()));
    }
}
