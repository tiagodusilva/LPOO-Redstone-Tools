package com.lpoo.redstonetools.model.tile;

import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.model.utils.*;

public class IOTile extends OrientedTile {

    /**
     * <h1>Power level being received/emitted</h1>
     */
    private int power;

    /**
     * <h1>IO side</h1>
     */
    private Side ioSide;

    /**
     * <h1>IO tile constructor</h1>
     * By default all the sides are neither input or output
     *
     * @param position Position of the tile
     * @see OrientedTile
     */
    public IOTile(Position position) {
        super(position);

        this.power = Power.getMin();
        this.ioSide = Side.UP;
    }

    /**
     * <h1>Get IO side</h1>
     *
     * @return IO side
     */
    public Side getIOSide() {
        return ioSide;
    }

    /**
     * <h1>Set IO side</h1>
     *
     * @param side new IO side
     */
    public void setIOSide(Side side) {
        this.ioSide = side;
    }

    @Override
    public String getName() {
        return "io";
    }

    @Override
    public String getInfo() {
        return "IO Side : " + this.ioSide + "\n" +
               "Power : " + this.power;
    }

    @Override
    public TileType getType() {
        return TileType.IO;
    }

    @Override
    public int getPower(Side side) {
        return outputsPower(side) ? power : Power.getMin();
    }

    /**
     * <h1>Get tile power</h1>
     * Temporary function for circuit to get IO power regardless of the side
     *
     * @return  IO stored power
     */
    public int getPower() {
        return power;
    }

    @Override
    public boolean rotateLeft(Circuit circuit) {
        Side oldSide = this.ioSide;
        Side newSide = this.ioSide.atLeft();
        this.setIOSide(newSide);
        super.rotateLeft(circuit);

        while (oldSide != newSide) {

            if (circuit.updateOnIORotation(position, oldSide)) return true;

            newSide = newSide.atLeft();
            this.setIOSide(newSide);
            super.rotateLeft(circuit);
        }
        return false;
    }

    @Override
    public boolean rotateRight(Circuit circuit) {
        Side oldSide = this.ioSide;
        Side newSide = this.ioSide.atRight();
        this.setIOSide(newSide);
        super.rotateRight(circuit);

        while (oldSide != newSide) {

            if (circuit.updateOnIORotation(position, oldSide)) return true;

            newSide = newSide.atRight();
            this.setIOSide(newSide);
            super.rotateRight(circuit);
        }
        return false;
    }

    @Override
    public boolean update(Circuit circuit, int power, Side side) {
        boolean needs_update = this.power != power;
        if (acceptsPower(side) && needs_update) {
            return onChange(circuit, power, side);
        }
        return false;
    }

    @Override
    public boolean onChange(Circuit circuit, int power, Side side) {
        this.power = power;
        return true;
    }

    @Override
    public boolean interact(Circuit circuit) {
        SideType oldState = sides.getOrDefault(ioSide, SideType.DEFAULT);
        SideType newState = oldState.next();
        sides.put(ioSide, newState);

        while (newState != oldState) {

            if (circuit.updateOnIOInteract(position)) return true;

            newState = newState.next();
            sides.put(ioSide, newState);
        }
        return false;
    }
}
