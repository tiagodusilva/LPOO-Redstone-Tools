package com.lpoo.redstonetools.model.tile.strategy;

import com.lpoo.redstonetools.model.utils.Power;
import com.lpoo.redstonetools.model.utils.Side;
import com.lpoo.redstonetools.model.utils.SideType;

import java.util.Map;

public class NORGateStrategy implements LogicGateStrategy {
    @Override
    public boolean logic(Map<Side, Integer> inputs, Map<Side, SideType> sideTypes) {
        boolean output = false;
        for (Side side : Side.values()) {
            if (sideTypes.getOrDefault(side, SideType.DEFAULT).isInput()) {
                output |= Power.isOn(inputs.getOrDefault(side, Power.getMin()));
            }
        }
        return !output;
    }

    @Override
    public String getName() {
        return "nor_gate";
    }

    @Override
    public LogicGateStrategyType getType() {
        return LogicGateStrategyType.NOR;
    }
}
