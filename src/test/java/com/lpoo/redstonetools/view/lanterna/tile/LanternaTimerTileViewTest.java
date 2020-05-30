package com.lpoo.redstonetools.view.lanterna.tile;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.lpoo.redstonetools.model.tile.Tile;
import com.lpoo.redstonetools.model.tile.TimerTile;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class LanternaTimerTileViewTest {

    @Test
    @Tag("view")
    @Tag("unit-test") @Tag("fast")
    public void testRender() {
        LanternaTimerView view = Mockito.mock(LanternaTimerView.class);

        Mockito.doCallRealMethod().when(view)
                .render(Mockito.any(Tile.class), Mockito.anyInt(), Mockito.anyInt(), Mockito.any(TextGraphics.class));

        TextColor powerColor = Mockito.mock(TextColor.class);

        Mockito.when(view.getPowerColor(Mockito.anyInt())).thenReturn(powerColor);

        TextGraphics graphics = Mockito.mock(TextGraphics.class);

        TimerTile tile = Mockito.mock(TimerTile.class);

        Mockito.when(tile.getSwitchMode()).thenReturn(false);

        view.render(tile, -3, 1, graphics);

        Mockito.verify(tile, Mockito.times(1)).getSwitchMode();

        Mockito.verify(view, Mockito.times(1))
                .renderPowerSensitiveFrame(graphics, tile, 1, -3);

        Mockito.verify(view, Mockito.times(1)).getPowerColor(Mockito.anyInt());

        Mockito.verify(graphics, Mockito.times(1)).setForegroundColor(powerColor);
        Mockito.verify(graphics, Mockito.times(1))
                .setCharacter(Mockito.eq(2), Mockito.eq(-2), Mockito.anyChar());

        Mockito.when(tile.getSwitchMode()).thenReturn(true);

        view.render(tile, 2, -1, graphics);

        Mockito.verify(tile, Mockito.times(2)).getSwitchMode();

        Mockito.verify(view, Mockito.times(1))
                .renderPowerSensitiveFrame(graphics, tile, -1, 2);

        Mockito.verify(view, Mockito.times(2)).getPowerColor(Mockito.anyInt());

        Mockito.verify(graphics, Mockito.times(2)).setForegroundColor(powerColor);
        Mockito.verify(graphics, Mockito.times(1))
                .setCharacter(Mockito.eq(0), Mockito.eq(3), Mockito.anyChar());

        Mockito.verify(view, Mockito.times(2))
                .renderPowerSensitiveFrame(Mockito.any(TextGraphics.class), Mockito.any(Tile.class), Mockito.anyInt(), Mockito.anyInt());

        Mockito.verify(graphics, Mockito.times(2))
                .setCharacter(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyChar());
    }
}
