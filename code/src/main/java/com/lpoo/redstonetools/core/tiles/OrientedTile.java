package com.lpoo.redstonetools.core.tiles;

import com.lpoo.redstonetools.core.utils.Position;
import com.lpoo.redstonetools.core.utils.Side;
import com.lpoo.redstonetools.core.utils.SideType;

import java.util.HashMap;
import java.util.Map;

public abstract class OrientedTile extends Tile {

    protected Map<Side, SideType> sides;

    public OrientedTile(Position position) {
        super(position);
        sides = new HashMap<>();
        for (Side side : Side.values())
            sides.put(side, SideType.DEFAULT);
    }

    public void rotateLeft() {
        SideType leftType = sides.getOrDefault(Side.LEFT, SideType.DEFAULT);
        SideType rightType = sides.getOrDefault(Side.RIGHT, SideType.DEFAULT);
        sides.put(Side.LEFT, sides.getOrDefault(Side.UP, SideType.DEFAULT));
        sides.put(Side.RIGHT, sides.getOrDefault(Side.DOWN, SideType.DEFAULT));
        sides.put(Side.DOWN, leftType);
        sides.put(Side.UP, rightType);

    }

    public void rotateRight() {
        SideType leftType = sides.getOrDefault(Side.LEFT, SideType.DEFAULT);
        SideType rightType = sides.getOrDefault(Side.RIGHT, SideType.DEFAULT);
        sides.put(Side.RIGHT, sides.getOrDefault(Side.UP, SideType.DEFAULT));
        sides.put(Side.LEFT, sides.getOrDefault(Side.DOWN, SideType.DEFAULT));
        sides.put(Side.UP, leftType);
        sides.put(Side.DOWN, rightType);
    }

    public boolean acceptsPower(Side side) {
        return sides.getOrDefault(side, SideType.DEFAULT).isInput();
    }

    public boolean outputsPower(Side side) {
        return sides.getOrDefault(side, SideType.DEFAULT).isOutput();
    }
}
