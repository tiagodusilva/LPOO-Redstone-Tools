package com.lpoo.redstonetools.view.lanterna.tile;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.lpoo.redstonetools.model.tile.ConstantSourceTile;
import com.lpoo.redstonetools.model.tile.CounterTile;
import com.lpoo.redstonetools.model.tile.Tile;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class LanternaCounterTileViewTest {
    @Test
    @Tag("view")
    @Tag("unit-test") @Tag("fast")
    public void testRender() {
        LanternaCounterTileView view = Mockito.mock(LanternaCounterTileView.class);

        Mockito.doCallRealMethod().when(view)
                .render(Mockito.any(Tile.class), Mockito.anyInt(), Mockito.anyInt(), Mockito.any(TextGraphics.class));

        TextColor color = Mockito.mock(TextColor.class);

        Mockito.when(view.getPowerColor(Mockito.anyInt())).thenReturn(color);

        CounterTile tile = Mockito.mock(CounterTile.class);

        TextGraphics graphics = Mockito.mock(TextGraphics.class);

        view.render(tile, -6, 3, graphics);

        Mockito.verify(view, Mockito.times(1))
                .renderPowerSensitiveFrame(graphics, tile, 3, -6);
        Mockito.verify(view, Mockito.times(1)).getPowerColor(Mockito.anyInt());
        Mockito.verify(graphics, Mockito.times(1)).setForegroundColor(color);
        Mockito.verify(graphics, Mockito.times(1))
                .setCharacter(Mockito.eq(4), Mockito.eq(-5), Mockito.anyChar());

        view.render(tile, 3, 0, graphics);

        Mockito.verify(view, Mockito.times(1))
                .renderPowerSensitiveFrame(graphics, tile,0, 3);
        Mockito.verify(view, Mockito.times(2)).getPowerColor(Mockito.anyInt());
        Mockito.verify(graphics, Mockito.times(2)).setForegroundColor(color);
        Mockito.verify(graphics, Mockito.times(1))
                .setCharacter(Mockito.eq(1), Mockito.eq(4), Mockito.anyChar());
    }
}
