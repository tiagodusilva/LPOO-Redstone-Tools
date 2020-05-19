package com.lpoo.redstonetools.model.tile;

import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.model.utils.*;

import java.util.HashMap;
import java.util.Map;

public class ComparatorTile extends OrientedTile {

    /**
     * <h1>Comparator mode</h1>
     * Comparison mode - compares rear signal with side signals
     * Subtract mode - subtraction between rear signal and side signals
     */
    private boolean subtractMode;

    /**
     * <h1>Force update to take place</h1>
     * Forces an update even if the inputs haven't changed, in case the comparator changes modes
     */
    private boolean forceUpdate;

    /**
     * <h1>Power being outputted</h1>
     * Comparator outputs power if:
     *  - In comparison mode: rear signal is greater than the side signals, then outputs the rear signal
     *  - In subtract mode: subtracts rear signal with side signals (the maximum of the signals), then outputs the result value
     */
    private int power;

    /**
     * <h1>Table of power levels being received by the comparator</h1>
     */
    private Map<Side, Integer> powers;

    /**
     * <h1>Store rear side to improve updates performance</h1>
     */
    private Side rear;

    /**
     * <h1>Oriented tile constructor</h1>
     * Comparator is a type of oriented tile that compares/subtracts the signals received and outputs accordingly
     * It has one output side, and three input sides (one in the rear, two on the side)
     * The input side on the opposing side of the output is the main signal
     * The other two inputs are the comparator signals
     *
     * @param position Position of the tile
     */
    public ComparatorTile(Position position) {
        super(position);
        this.sides.put(Side.UP, SideType.INPUT);
        this.sides.put(Side.LEFT, SideType.INPUT);
        this.sides.put(Side.DOWN, SideType.INPUT);
        this.sides.put(Side.RIGHT, SideType.OUTPUT);

        updateRear();

        this.forceUpdate = false;

        this.powers = new HashMap<>();
        this.powers.put(Side.UP, Power.getMin());
        this.powers.put(Side.DOWN, Power.getMin());
        this.powers.put(Side.LEFT, Power.getMin());
        this.powers.put(Side.RIGHT, Power.getMin());

        this.subtractMode = false;
        this.power = Power.getMin();
    }

    /**
     * <h1>Rotates a tile to the left</h1>
     * In an oriented tile rotating a tile to the left is switching the types of the sides in clock-wise direction
     * In addition updates the rear side of the comparator
     *
     * @param circuit Circuit where rotation is taking place
     *
     * @return  true
     */
    @Override
    public boolean rotateLeft(Circuit circuit) {
        boolean ret = super.rotateLeft(circuit);
        updateRear();
        return ret;
    }

    /**
     * <h1>Rotates a tile to the right</h1>
     * In an oriented tile rotating a tile to the right is switching the types of the sides in clock-wise direction
     * In addition updates the rear side of the comparator
     *
     * @param circuit Circuit where rotation is taking place
     *
     * @return  true
     */
    @Override
    public boolean rotateRight(Circuit circuit) {
        boolean ret = super.rotateRight(circuit);
        updateRear();
        return ret;
    }

    /**
     * <h1>Get rear side of the comparator</h1>
     * If comparator has no inputs and rear side is up-to-date then the value is null
     *
     * @return  Rear side
     */
    public Side getRear() {
        return rear;
    }

    /**
     * <h1>Updates rear side</h1>
     * If there's no output side, rear side will be set to null
     */
    public void updateRear() {
        Side new_rear = null;
        for (Side s : Side.values()) {
            if (outputsPower(s)) {
                new_rear = s.opposite();
                break;
            }
        }
        this.rear = new_rear;
    }

    /**
     * <h1>Get name of the tile</h1>
     *
     * @return  "comparator"
     */
    @Override
    public String getName() {
        return "comparator";
    }

    /**
     * <h1>Get tile information</h1>
     *
     * @return  "Mode : " followed by "comparison" or "subtract"
     *          "Power : " followed by the power the comparator is outputting
     */
    @Override
    public String getInfo() {
        return "Mode : " + (this.subtractMode ? "subtract" : "comparison") + "\n" +
                "Power : " + this.power;
    }

    /**
     * <h1>Get tile type</h1>
     *
     * @see TileType
     *
     * @return  Comparator type
     */
    @Override
    public TileType getType() {
        return TileType.COMPARATOR;
    }

    /**
     * <h1>Get the power level emitted on the side specified</h1>
     *
     * @param side  Side of the tile
     * @return  Comparator power if it's the output side, minimum power otherwise
     */
    @Override
    public int getPower(Side side) {
        return (outputsPower(side)) ? power : Power.getMin();
    }

    /**
     * <h1>Sets comparator output power level</h1>
     * The new power level gets clamped to assure it's always a valid power level
     *
     * @param power    New power level
     */
    public void setOutput(int power) {
        this.power = Power.clamp(power);
    }

    /**
     * <h1>Gets the comparator mode</h1>
     *
     * @return  false if timer is in Comparison mode, true for Subtract mode
     */
    public boolean getSubtractMode() {
        return this.subtractMode;
    }

    /**
     * <h1>Changes the comparator mode</h1>
     * For Comparison mode set <code>mode</code> to false
     * For Subtract mode set <code>mode</code> to true
     *
     * @param mode  New timer mode
     */
    public void setSubtractMode(boolean mode) {
        this.subtractMode = mode;
    }

    /**
     * <h1>Triggers a tile update</h1>
     * Checks if comparator needs to be updated
     *
     * @param circuit   Circuit where updates are taking place
     * @param power     Power received on the update
     * @param side      Side from which comparator received an update
     * @return  true if comparator was updated, false otherwise
     */
    @Override
    public boolean update(Circuit circuit, int power, Side side) {
        boolean needs_update = forceUpdate || powers.getOrDefault(side, Power.getMin()) != power;
        if (acceptsPower(side) && needs_update) {
            return onChange(circuit, power, side);
        }
        return false;
    }

    /**
     * <h1>Updates the tile</h1>
     * Checks if the update is necessary, updating it if so
     *
     * @param circuit   Circuit where updates are taking place
     * @param power     Power received on the update
     * @param side      Side from which comparator received an update
     * @return  true if comparator was updated, false otherwise
     */
    @Override
    protected boolean onChange(Circuit circuit, int power, Side side) {
        this.powers.put(side, power);
        this.forceUpdate = false;
        if (rear == null) return false;

        int old_power = this.power;

        int rearPower = this.powers.getOrDefault(rear, Power.getMin());
        int rightPower = this.powers.getOrDefault(rear.atRight(), Power.getMin());
        int leftPower = this.powers.getOrDefault(rear.atLeft(), Power.getMin());

        if (subtractMode) {
            this.setOutput(rearPower - Math.max(rightPower, leftPower));
        } else {
            boolean valid = leftPower <= rearPower && rightPower <= rearPower;
            this.setOutput((valid ? rearPower : Power.getMin()));
        }

        return old_power != this.power;
    }

    /**
     * <h1>Interacts with a tile</h1>
     * Interacting with a comparator changes the comparator mode
     *
     * @param circuit Circuit where interaction is taking place
     *
     * @return  true
     */
    @Override
    public boolean interact(Circuit circuit) {
        this.setSubtractMode(!this.subtractMode);
        this.forceUpdate = true;
        return true;
    }
}
