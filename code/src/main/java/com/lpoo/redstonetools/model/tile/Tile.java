package com.lpoo.redstonetools.model.tile;

import com.lpoo.redstonetools.model.Model;
import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.model.utils.Position;
import com.lpoo.redstonetools.model.utils.Side;

import java.util.Objects;

public abstract class Tile implements Model {

    protected Position position;

    public Tile(Position position) {
        this.position = position;
    }

    public Position getPosition() { return position; }

    public void setPosition(Position position) { this.position = position; }

    public abstract String getName();

    public abstract String getInfo();

    public abstract boolean isSource(Side side);

    public boolean isSource() {
        for (Side side : Side.values())
            if (isSource(side))
                return true;

        return false;
    }

    public boolean isWire() { return false; }

    public boolean rotateLeft() { return false; }

    public boolean rotateRight() { return false; }

    public abstract int getPower(Side side);

    public abstract boolean acceptsPower(Side side);

    public abstract boolean outputsPower(Side side);

    public boolean update(Circuit circuit, int power, Side side) {
        return false;
    }

    protected boolean onChange(Circuit circuit, int power, Side side) {
        return false;
    }

    public void updateConnections(Circuit circuit) { }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tile)) return false;
        Tile tile = (Tile) o;
        return Objects.equals(position, tile.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position);
    }
}
