package com.lpoo.redstonetools.model.tile;

import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.model.utils.Position;
import com.lpoo.redstonetools.model.utils.Power;
import com.lpoo.redstonetools.model.utils.Side;
import com.lpoo.redstonetools.model.utils.TileType;

import java.util.HashMap;
import java.util.Map;

/**
 *  <h1>Wire Tile</h1>
 *  Wire tile is the main tile responsible of transporting power
 *
 * @author g79
 */
public class CrossWireTile extends Tile {

    /**
     * <h1>Power level on the wire's sides</h1>
     */
    private int powerVert, powerHor;

    /**
     * <h1>Sides of the wire connected to other tiles</h1>
     */
    private Map<Side, Boolean> connected;

    /**
     * <h1>Wire tile constructor</h1>
     * By default wire isn't connected to any tile and the power in the wire is the minimum power
     *
     * @param position  Position of the tile
     */
    public CrossWireTile(Position position) {
        super(position);
        powerVert = Power.getMin();
        powerHor = Power.getMin();
        connected = new HashMap<>();
        for (Side side : Side.values())
            connected.put(side, false);
    }

    /**
     * <h1>Get name of the tile</h1>
     *
     * @return  "crosswire"
     */
    @Override
    public String getName() { return "crosswire"; }

    /**
     * <h1>Get tile information</h1>
     *
     * @return  "PowerV : %d\nPowerH : %d" followed by the respective power levels on the wire
     */
    @Override
    public String getInfo() {
        return "PowerV : " + powerVert + "\n" +
                "PowerH : " + powerHor;
    }

    /**
     * <h1>Checks if wire is connected to neighbour tile on the side specified</h1>
     *
     * @param side  Side to test connection
     * @return  true if wire is connected, false otherwise
     */
    @Override
    public boolean isConnected(Side side) {
        return connected.getOrDefault(side, false);
    }

    /**
     * <h1>Get tile type</h1>
     *
     * @see TileType
     *
     * @return  Wire type
     */
    @Override
    public TileType getType() { return TileType.CROSSWIRE; }

    /**
     * <h1>Get the power level emitted on the side specified</h1>
     *
     * @param side  Side of the tile
     * @return  Power level on the wire
     */
    @Override
    public int getPower(Side side) {
        return side == Side.LEFT || side == Side.RIGHT ? powerHor : powerVert;
    }

    /**
     * <h1>Checks if side specified is an output of power</h1>
     *
     * @param side  Side of the tile to be checked
     * @return  true
     */
    @Override
    public boolean acceptsPower(Side side) {
        return true;
    }

    /**
     * <h1>Checks if side specified is an input of power</h1>
     *
     * @param side  Side of the tile to be checked
     * @return  true
     */
    @Override
    public boolean outputsPower(Side side) {
        return true;
    }

    /**
     * <h1>Triggers a tile update</h1>
     * Checks if wire needs to be updated
     * A wire needs to be update if the maximum power level being transmitted into it is different than the power level on the wire
     *
     * @param circuit   Circuit where updates are taking place
     * @param power     Power received on the update
     * @param side      Side from which repeater received an update
     * @return  true if wire was updated, false otherwise
     */
    @Override
    public boolean update(Circuit circuit, int power, Side side) {

        int surroundingPower, cur_power;
        if (side == Side.LEFT || side == Side.RIGHT) {
            surroundingPower = Math.max(circuit.getSurroundingWirePower(position, Side.LEFT),
                    circuit.getSurroundingWirePower(position, Side.RIGHT));
            cur_power = powerHor;
        }
        else {
            surroundingPower = Math.max(circuit.getSurroundingWirePower(position, Side.UP),
                    circuit.getSurroundingWirePower(position, Side.DOWN));
            cur_power = powerVert;
        }

        if (cur_power != surroundingPower)
            return onChange(circuit, surroundingPower, side);
        return false;
    }

    /**
     * <h1>Updates the tile</h1>
     * Updates power level on the wire
     *
     * @param circuit   Circuit where updates are taking place
     * @param power     Power received on the update
     * @param side      Side from which repeater received an update
     * @return  true
     */
    @Override
    public boolean onChange(Circuit circuit, int power, Side side) {
        if (side == Side.LEFT || side == Side.RIGHT)
            powerHor = power;
        else
            powerVert = power;
        return true;
    }

    /**
     * <h1>Updates tile connections to other tiles</h1>
     * A tile can connect to other if the side of contact of the tiles are output and input or vice-versa
     *
     * @param circuit   Circuit where update are taking place
     */
    @Override
    public void updateConnections(Circuit circuit) {
        for (Side side : Side.values())
            connected.put(side, circuit.canTilesConnect(this.position, side));
    }

    /**
     * <h1>Checks if tile is a wire</h1>
     *
     * @see WireTile
     *
     * @return  true
     */
    @Override
    public boolean isWire() { return true; }
}
