package com.lpoo.redstonetools.model.tile;

import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.model.tile.strategy.LogicGateStrategy;
import com.lpoo.redstonetools.model.utils.*;

import java.util.HashMap;
import java.util.Map;

public class LogicGateTile extends OrientedTile {

    private LogicGateStrategy strategy;
    private boolean active;

    private Map<Side, Integer> powers;

    /**
     * <h1>Logic Gate tile constructor</h1>
     * By default a repeater has the left and right side as input and up side as an output
     * The input and output sides can't be changed, but the logic gate can be rotated
     *
     * @param position  Position of the tile
     */
    public LogicGateTile(Position position, LogicGateStrategy strategy) {
        super(position);
        this.sides.put(Side.LEFT, SideType.INPUT);
        this.sides.put(Side.RIGHT, SideType.INPUT);
        this.sides.put(Side.UP, SideType.OUTPUT);
        this.strategy = strategy;

        this.powers = new HashMap<>();
        this.powers.put(Side.UP, Power.getMin());
        this.powers.put(Side.DOWN, Power.getMin());
        this.powers.put(Side.LEFT, Power.getMin());
        this.powers.put(Side.RIGHT, Power.getMin());
        //this.updateTick = -1;
        this.active = strategy.logic(this.powers, this.sides);
    }

    public LogicGateStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(LogicGateStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public String getName() {
        return strategy.getName();
    }

    @Override
    public String getInfo() {
        return "Active : " + this.active;
    }

    @Override
    public TileType getType() {
        return TileType.LOGIC_GATE;
    }

    @Override
    public int getPower(Side side) {
        if (outputsPower(side)) return (this.active) ? Power.getMax() : Power.getMin();
        return Power.getMin();
    }

    /**
     * <h1>Set logic gate status</h1>
     *
     * @param status    New logic gate status
     */
    public void setStatus(boolean status) {
        this.active = status;
    }

    /**
     * <h1>Triggers a tile update</h1>
     * Checks if logic gate needs to be updated
     *
     * @param circuit   Circuit where updates are taking place
     * @param power     Power received on the update
     * @param side      Side from which repeater received an update
     * @return  true if repeater was updated, false otherwise
     */
    @Override
    public boolean update(Circuit circuit, int power, Side side) {
        boolean needs_update = Power.differentStates(powers.getOrDefault(side, Power.getMin()), power);
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
     * @param side      Side from which repeater received an update
     * @return  true if repeater was updated, false otherwise
     */
    @Override
    protected boolean onChange(Circuit circuit, int power, Side side) {
        this.powers.put(side, power);
        boolean old_status = this.active;
        this.setStatus(this.strategy.logic(this.powers, this.sides));
        return this.active != old_status;
    }
}
