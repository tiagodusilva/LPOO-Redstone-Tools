package com.lpoo.redstonetools.model.utils;

/**
 * <h1>Type of tile</h1>
 * Type of tile, see the tile documentation for more information about each type
 */
public enum TileType {
    /**
     * @see com.lpoo.redstonetools.model.tile.NullTile
     */
    NULL,
    /**
     * @see com.lpoo.redstonetools.model.tile.WireTile
     */
    WIRE,
    /**
     * @see com.lpoo.redstonetools.model.tile.CrossWireTile
     */
    CROSSWIRE,
    /**
     * @see com.lpoo.redstonetools.model.tile.ConstantSourceTile
     */
    SOURCE,
    /**
     * @see com.lpoo.redstonetools.model.tile.LeverTile
     */
    LEVER,
    /**
     * @see com.lpoo.redstonetools.model.tile.RepeaterTile
     */
    REPEATER,
    /**
     * @see com.lpoo.redstonetools.model.tile.LogicGateTile
     */
    LOGIC_GATE,
    /**
     * @see com.lpoo.redstonetools.model.tile.NotGateTile
     */
    NOT_GATE,
    /**
     * @see com.lpoo.redstonetools.model.tile.ComparatorTile
     */
    COMPARATOR,
    /**
     * @see com.lpoo.redstonetools.model.tile.TimerTile
     */
    TIMER,
    /**
     * @see com.lpoo.redstonetools.model.tile.CounterTile
     */
    COUNTER
}
