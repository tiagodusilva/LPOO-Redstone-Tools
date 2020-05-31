package com.lpoo.redstonetools.view.lanterna.tile;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.model.tile.Tile;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class LanternaCircuitTileViewTest {

    @Test
    @Tag("view")
    @Tag("unit-test") @Tag("fast")
    public void testRender() {
        LanternaCircuitTileView view = Mockito.mock(LanternaCircuitTileView.class);

        Mockito.doCallRealMethod().when(view)
                .render(Mockito.any(Tile.class), Mockito.anyInt(), Mockito.anyInt(), Mockito.any(TextGraphics.class));

        TextColor color = Mockito.mock(TextColor.class);

        Mockito.when(view.getPowerColor(Mockito.anyInt())).thenReturn(color);

        Circuit tile = Mockito.mock(Circuit.class);

        TextGraphics graphics = Mockito.mock(TextGraphics.class);

        view.render(tile, 10, -4, graphics);

        Mockito.verify(view, Mockito.times(1))
                .renderPowerSensitiveFrame(graphics, tile, -4, 10);
        Mockito.verify(view, Mockito.times(1)).getPowerColor(Mockito.anyInt());
        Mockito.verify(graphics, Mockito.times(1)).setForegroundColor(color);
        Mockito.verify(graphics, Mockito.times(1))
                .setCharacter(Mockito.eq(-3), Mockito.eq(11), Mockito.anyChar());

        view.render(tile, 0, 471, graphics);

        Mockito.verify(view, Mockito.times(1))
                .renderPowerSensitiveFrame(graphics, tile, 471, 0);
        Mockito.verify(view, Mockito.times(2)).getPowerColor(Mockito.anyInt());
        Mockito.verify(graphics, Mockito.times(2)).setForegroundColor(color);
        Mockito.verify(graphics, Mockito.times(1))
                .setCharacter(Mockito.eq(472), Mockito.eq(1), Mockito.anyChar());
    }
}
