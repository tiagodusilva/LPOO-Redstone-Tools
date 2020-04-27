package com.lpoo.redstonetools.model.tile;

import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.model.utils.Position;
import com.lpoo.redstonetools.model.utils.Side;
import com.lpoo.redstonetools.model.utils.SideType;

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

    @Override
    public boolean rotateLeft() {
        SideType leftType = sides.getOrDefault(Side.LEFT, SideType.DEFAULT);
        SideType rightType = sides.getOrDefault(Side.RIGHT, SideType.DEFAULT);
        sides.put(Side.LEFT, sides.getOrDefault(Side.UP, SideType.DEFAULT));
        sides.put(Side.RIGHT, sides.getOrDefault(Side.DOWN, SideType.DEFAULT));
        sides.put(Side.DOWN, leftType);
        sides.put(Side.UP, rightType);
        return true;
    }

    @Override
    public boolean rotateRight() {
        SideType leftType = sides.getOrDefault(Side.LEFT, SideType.DEFAULT);
        SideType rightType = sides.getOrDefault(Side.RIGHT, SideType.DEFAULT);
        sides.put(Side.RIGHT, sides.getOrDefault(Side.UP, SideType.DEFAULT));
        sides.put(Side.LEFT, sides.getOrDefault(Side.DOWN, SideType.DEFAULT));
        sides.put(Side.UP, leftType);
        sides.put(Side.DOWN, rightType);
        return true;
    }

    @Override
    public boolean acceptsPower(Side side) { return sides.getOrDefault(side, SideType.DEFAULT).isInput(); }

    @Override
    public boolean outputsPower(Side side) { return sides.getOrDefault(side, SideType.DEFAULT).isOutput(); }
}
