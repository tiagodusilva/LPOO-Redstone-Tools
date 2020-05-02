package com.lpoo.redstonetools.model.tile;

import com.lpoo.redstonetools.model.utils.Position;

/**
 *  <h1>Source Tile</h1>
 *  Source tile is a tile that can provide power on its own
 *
 * @author g79
 */
public abstract class SourceTile extends Tile {

    /**
     * <h1>Tile constructor</h1>
     *
     * @param position Position of the tile
     */
    public SourceTile(Position position) {
        super(position);
    }

    /**
     * <h1>Updates source and calculates next tick</h1>
     *
     * @return true if the source was updated, false otherwise
     */
    public abstract boolean nextTick();

    /**
     * <h1>Checks if tile is a source of power</h1>
     *
     * @see SourceTile
     *
     * @return  true
     */
    @Override
    public boolean isSource() {
        return true;
    }
}
