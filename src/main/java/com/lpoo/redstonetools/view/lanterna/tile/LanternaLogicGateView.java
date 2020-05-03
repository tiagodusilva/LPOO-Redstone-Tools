package com.lpoo.redstonetools.view.lanterna.tile;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.lpoo.redstonetools.model.tile.LogicGateTile;
import com.lpoo.redstonetools.model.tile.Tile;
import com.lpoo.redstonetools.model.tile.strategy.LogicGateStrategyType;
import com.lpoo.redstonetools.model.utils.Power;

import java.util.HashMap;
import java.util.Map;

public class LanternaLogicGateView extends LanternaTileView {

    private Map<LogicGateStrategyType, Character> characters;

    public LanternaLogicGateView() {
        super();

        this.characters = new HashMap<>();
        this.characters.put(LogicGateStrategyType.AND, '\u2227');
        this.characters.put(LogicGateStrategyType.OR, '\u2228');
        this.characters.put(LogicGateStrategyType.NAND, '\u22BC');
        this.characters.put(LogicGateStrategyType.NOR, '\u22BD');
        this.characters.put(LogicGateStrategyType.XOR, '\u22BB');
        this.characters.put(LogicGateStrategyType.XNOR, '\u2299');
    }

    @Override
    public void render(Tile tile, int row, int column, TextGraphics graphics) {
        renderPowerSensitiveFrame(graphics, tile, column, row);

        graphics.setForegroundColor(TextColor.Factory.fromString(getPowerColor(Power.getMax())));
        graphics.setCharacter(column + 1, row + 1, characters.getOrDefault(((LogicGateTile)tile).getStrategy().getType(), '\u262D'));
    }
}
