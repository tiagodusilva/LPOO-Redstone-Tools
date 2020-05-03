package com.lpoo.redstonetools.model.tile.strategy;

import com.lpoo.redstonetools.model.utils.Side;
import com.lpoo.redstonetools.model.utils.SideType;
import com.lpoo.redstonetools.model.utils.TileType;

import java.util.Map;

public interface LogicGateStrategy {

    boolean logic(Map<Side, Integer> inputs, Map<Side, SideType> sideTypes);

    String getName();

    LogicGateStrategyType getType();
}
