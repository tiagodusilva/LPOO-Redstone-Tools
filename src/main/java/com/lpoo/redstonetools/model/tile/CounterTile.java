package com.lpoo.redstonetools.model.tile;

import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.model.utils.*;

public class CounterTile extends OrientedTile {

    /**
     * <h1>Counter current pulses counter</h1>
     */
    private long counter;

    /**
     * <h1>Counter outputting power</h1>
     */
    private boolean output;

    /**
     * <h1>Number of pulses between activations</h1>
     */
    private long delay;

    /**
     * <h1>Previous input power level</h1>
     */
    private int prevIn;

    public CounterTile(Position position) {
        super(position);
        this.sides.put(Side.LEFT, SideType.INPUT);
        this.sides.put(Side.RIGHT, SideType.OUTPUT);

        this.counter = 0;
        this.delay = 5;
        this.output = false;
        this.prevIn = Power.getMin();
    }


    @Override
    public String getName() {
        return "counter";
    }

    @Override
    public String getInfo() {
        return "Active : " + this.output + "\n" +
                "Delay : " + this.delay + "\n" +
                "Counter : " + this.counter;
    }

    @Override
    public TileType getType() {
        return TileType.COUNTER;
    }

    @Override
    public int getPower(Side side) {
        if (outputsPower(side)) return (output) ? Power.getMax() : Power.getMin();
        return Power.getMin();
    }

    /**
     * <h1>Get counter delay</h1>
     * Tick delay between timer updates
     *
     * @return  Current Counter delay
     */
    public long getDelay() {
        return delay;
    }

    /**
     * <h1>Set counter delay</h1>
     * Delay between counter updates
     *
     * @param delay New Counter delay
     */
    public void setDelay(long delay) {
        this.delay = delay;
        this.counter = 0;
    }

    /**
     * <h1>Sets counter output</h1>
     * If counter is outputting power, then output is true
     * Otherwise output is false
     *
     * @param output    New counter output
     */
    public void setOutput(boolean output) {
        this.output = output;
    }

    /**
     * <h1>Get counter value</h1>
     *
     * @return  Current counter value
     */
    public long getCounter() {
        return counter;
    }

    /**
     * <h1>Get pulses left until next update</h1>
     *
     * @return  Pulses left until next update
     */
    public long getPulsesLeft() {
        return this.delay - this.counter;
    }

    /**
     * <h1>Get previous input</h1>
     *
     * @return  Previous input power level
     */
    public int getPrevIn() {
        return prevIn;
    }

    /**
     * <h1>Triggers a tile update</h1>
     * Checks if counter needs to be updated, by comparing the previous state with the next state of the counter
     *
     * @param circuit   Circuit where updates are taking place
     * @param power     Power received on the update
     * @param side      Side from which counter received an update
     * @return  true if timer was updated, false otherwise
     */
    @Override
    public boolean update(Circuit circuit, int power, Side side) {
        boolean different_state = Power.differentStates(prevIn, power);
        if (acceptsPower(side) && different_state) {
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
     * @param side      Side from which counter received an update
     * @return  true if counter output was updated, false otherwise
     */
    @Override
    public boolean onChange(Circuit circuit, int power, Side side) {
        this.prevIn = power;
        boolean last = this.output;

        if (Power.isOn(power)) {
            counter++;
            this.output = (counter >= delay);
            this.counter = (counter >= delay) ? 0 : counter;
        }
        return last != this.output;
    }
}
