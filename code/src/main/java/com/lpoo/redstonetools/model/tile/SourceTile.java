package com.lpoo.redstonetools.model.tile;

/**
 *  <h1>Source Tile</h1>
 *  Source tile is a tile that can provide power on its own
 *
 * @author g79
 */
public interface SourceTile {
    /**
     * <h1>Updates source and calculates next tick</h1>
     *
     * @return true if the source was updated, false otherwise
     */
    boolean nextTick();
}
