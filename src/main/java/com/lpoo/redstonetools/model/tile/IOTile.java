package com.lpoo.redstonetools.model.tile;

import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.model.utils.*;

public class IOTile extends Tile {

    /**
     * <h1>Power level being received/emitted</h1>
     */
    private int power;

    /**
     * <h1>IO Type</h1>
     */
    private SideType ioType;

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
        this.ioType = SideType.DEFAULT;
        this.ioSide = Side.UP;
    }

    /**
     * <h1>Get IO type</h1>
     *
     * @return IO type
     */
    public SideType getIOType() {
        return ioType;
    }

    /**
     * <h1>Set IO type</h1>
     *
     * @param type new IO type
     */
    public void setIOType(SideType type) {
        this.ioType = type;
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
     * @param ioSide new IO side
     */
    public void setIOSide(Side ioSide) {
        this.ioSide = ioSide;
    }

    @Override
    public String getName() {
        return "io";
    }

    @Override
    public String getInfo() {
        return "IO Type : " + this.ioType + "\n" +
               "Power : " + this.power;
    }

    @Override
    public TileType getType() {
        return TileType.IO;
    }

    @Override
    public boolean acceptsPower(Side side) {
        return ioType.isInput();
    }

    @Override
    public boolean outputsPower(Side side) {
        return ioType.isOutput();
    }

    @Override
    public int getPower(Side side) {
        return outputsPower(side) ? power : Power.getMin();
    }

    /**
     * <h1>Get tile power</h1>
     * Get power IO is emitting to outside the circuit
     *
     * @param side  Side of the tile
     *
     * @return  IO stored power
     */
    public int getExteriorPower(Side side) {
        return acceptsPower(side) ? power : Power.getMin();
    }

    @Override
    public boolean update(Circuit circuit, int power, Side side) {
        int surroundingPower = circuit.getSurroundingPower(position);
        boolean needs_update = this.power != surroundingPower;
        if (acceptsPower(side) && needs_update) {
            return onChange(circuit, surroundingPower, side);
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
        SideType oldState = ioType;
        setIOType(ioType.next());

        while (ioType != oldState) {

            if (circuit.updateOnIOInteract(position)) return true;

            setIOType(ioType.next());
        }
        return false;
    }

    @Override
    public boolean rotateLeft(Circuit circuit) {
        Side oldSide = ioSide;
        this.setIOSide(ioSide.atLeft());

        while (oldSide != ioSide) {

            if (circuit.updateOnIORotation(position, oldSide)) return true;

            this.setIOSide(ioSide.atLeft());
        }
        return false;
    }

    @Override
    public boolean rotateRight(Circuit circuit) {
        Side oldSide = ioSide;
        this.setIOSide(ioSide.atRight());

        while (oldSide != ioSide) {

            if (circuit.updateOnIORotation(position, oldSide)) return true;

            this.setIOSide(ioSide.atRight());
        }
        return false;
    }
}
