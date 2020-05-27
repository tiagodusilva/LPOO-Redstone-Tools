package com.lpoo.redstonetools.model.tile.strategy;

import com.lpoo.redstonetools.model.utils.Power;
import com.lpoo.redstonetools.model.utils.Side;
import com.lpoo.redstonetools.model.utils.SideType;

import java.util.HashMap;
import java.util.Map;

public class ANDGateStrategyTest {
/*
    @Test
    public void testLogic() {
        Map<Side, Integer> inputs = new HashMap<>();
        Map<Side, SideType> sideTypes = new HashMap<>();

        // Initialize default values
        for (Side side : Side.values()) {
            inputs.put(side, Power.getMin());
            sideTypes.put(side, SideType.DEFAULT);
        }
        //

        ANDGateStrategy strategy = new ANDGateStrategy();

        Assert.assertEquals(LogicGateStrategyType.AND, strategy.getType());

        Assert.assertFalse(strategy.logic(inputs, sideTypes));

        // All side outputs
        for (Side side : Side.values()) {
            sideTypes.put(side, SideType.OUTPUT);
        }
        //
        inputs.put(Side.LEFT, Power.getMax());
        inputs.put(Side.DOWN, Power.getMax());

        Assert.assertFalse(strategy.logic(inputs, sideTypes));

        // Input doesn't have power
        sideTypes.put(Side.RIGHT, SideType.INPUT);
        sideTypes.put(Side.UP, SideType.INPUT);

        Assert.assertFalse(strategy.logic(inputs, sideTypes));

        // Input sides have power
        inputs.put(Side.RIGHT, Power.getMax());

        Assert.assertFalse(strategy.logic(inputs, sideTypes));

        inputs.put(Side.UP, Power.getMax());

        Assert.assertTrue(strategy.logic(inputs, sideTypes));

        sideTypes.put(Side.LEFT, SideType.INPUT);
        inputs.put(Side.LEFT, Power.getMin());
        Assert.assertFalse(strategy.logic(inputs, sideTypes));

        inputs.put(Side.LEFT, 1);

        Assert.assertTrue(strategy.logic(inputs, sideTypes));
    }*/
}
