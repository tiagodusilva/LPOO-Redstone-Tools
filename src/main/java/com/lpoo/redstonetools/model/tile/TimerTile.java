package com.lpoo.redstonetools.model.tile;

import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.model.utils.*;

public class TimerTile extends OrientedTile {

    /**
     * <h1>Timer current tick</h1>
     */
    private long timer;

    /**
     * <h1>If timer is active</h1>
     * Timer is active if not deactivated by a signal in the input side
     * While active the timer is counting down
     */
    private boolean active;

    /**
     * <h1>Timer delay</h1>
     * Delay, in ticks, the timer outputs power
     */
    private long delay;

    /**
     * <h1>Timer mode</h1>
     * Delayed mode - sends a signal at specific intervals
     * Switch mode - switches signal at specific intervals
     */
    private boolean switchMode;

    /**
     * <h1>Timer is outputting power</h1>
     */
    private boolean output;

    /**
     * <h1>Tile constructor</h1>
     *
     * @param position Position of the tile
     */
    public TimerTile(Position position) {
        super(position);
        this.sides.put(Side.LEFT, SideType.INPUT);
        this.sides.put(Side.RIGHT, SideType.OUTPUT);

        this.active = true;
        this.output = false;
        this.switchMode = false;
        this.delay = 5;
        this.timer = 0;
    }

    /**
     * <h1>Get timer delay</h1>
     * Tick delay between timer updates
     *
     * @return  Current Timer delay
     */
    public long getDelay() {
        return delay;
    }

    /**
     * <h1>Set timer delay</h1>
     * Tick delay between timer updates
     *
     * @param delay New Timer delay
     */
    public void setDelay(long delay) {
        this.delay = delay;
        this.timer = 0;
    }

    /**
     * <h1>Get timer tick</h1>
     *
     * @return  Current timer tick
     */
    public long getTimer() {
        return timer;
    }

    /**
     * <h1>Get ticks left until next update</h1>
     *
     * @return  Ticks left until next update
     */
    public long getTicksLeft() {
        return this.delay - this.timer;
    }

    /**
     * <h1>Get timer output</h1>
     *
     * @return  true if timer is outputting, false otherwise
     */
    public boolean getOutput() {
        return output;
    }

    /**
     * <h1>Sets timer output</h1>
     * If timer is outputting power, then output is true
     * Otherwise output is false
     *
     * @param output    New timer output
     */
    public void setOutput(boolean output) {
        this.output = output;
    }

    /**
     * <h1>Gets the timer mode</h1>
     *
     * @return  false if timer is in Delayed mode, true for Switch mode
     */
    public boolean getSwitchMode() {
        return this.switchMode;
    }

    /**
     * <h1>Changes the timer mode</h1>
     * For Delayed mode set <code>mode</code> to false
     * For Switch mode set <code>mode</code> to true
     *
     * @param mode  New timer mode
     */
    public void setSwitchMode(boolean mode) {
        this.switchMode = mode;
        this.timer = 0;
    }

    /**
     * <h1>Get repeater status</h1>
     *
     * @return  Repeater status
     */
    public boolean getStatus() {
        return this.active;
    }

    /**
     * <h1>Set timer status</h1>
     *
     * @param status    New timer status
     */
    public void setStatus(boolean status) {
        this.active = status;
    }

    @Override
    public String getName() {
        return "timer";
    }

    @Override
    public String getInfo() {
        return "Active : " + this.active + "\n" +
                "Delay : " + this.delay + "\n" +
                "Tick : " + this.timer; }

    @Override
    public TileType getType() {
        return TileType.TIMER;
    }

    @Override
    public int getPower(Side side) {
        if (outputsPower(side)) return (output) ? Power.getMax() : Power.getMin();
        return Power.getMin();
    }

    /**
     * <h1>Triggers a tile update</h1>
     * Checks if timer needs to be updated, by comparing the previous state with the next state of the timer
     *
     * @param circuit   Circuit where updates are taking place
     * @param power     Power received on the update
     * @param side      Side from which timer received an update
     * @return  true if timer was updated, false otherwise
     */
    @Override
    public boolean update(Circuit circuit, int power, Side side) {
        boolean next_status = Power.isOff(power);
        if (acceptsPower(side) && (next_status != this.active)) {
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
     * @param side      Side from which timer received an update
     * @return  true if timer output was updated, false otherwise
     */
    @Override
    public boolean onChange(Circuit circuit, int power, Side side) {
        this.active = Power.isOff(power);
        boolean last = this.output;
        this.output = this.active && this.output;
        this.timer = 0;
        return last != this.output;
    }

    /**
     * <h1>Checks if tile is a ticked of power</h1>
     * Timer is a tick dependent tile
     *
     * @return  true
     */
    @Override
    public boolean isTickedTile() {
        return true;
    }

    /**
     * <h1>Updates tile and calculates next tick</h1>
     * Calculates next tick based on the timer status, the timer mode
     * Sets output if necessary
     *
     * @see Tile#isTickedTile()
     *
     * @return true if the tile output was updated, false otherwise
     */
    @Override
    public boolean nextTick() {
        if (!active) return false;

        boolean lastOutput = output;

        if (timer == delay) {
            setOutput(!switchMode || !output);
            timer = 0;
        } else {
            timer++;
            output = switchMode && output;
        }

        return lastOutput != output;
    }

    /**
     * <h1>Interacts with a tile</h1>
     * Interacting with a timer changes the timer mode
     *
     * @param circuit Circuit where interaction is taking place
     *
     * @return  false
     */
    @Override
    public boolean interact(Circuit circuit) {
        this.setSwitchMode(!this.switchMode);
        return false;
    }
}
