package com.lpoo.redstonetools.view.lanterna.tile;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.lpoo.redstonetools.model.tile.DigitTile;
import com.lpoo.redstonetools.model.tile.Tile;
import com.lpoo.redstonetools.model.utils.Power;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

public class LanternaDigitTileViewTest {

    @Test
    @Tag("view")
    @Tag("unit-test") @Tag("fast")
    public void testRender() {
        LanternaDigitTileView view = Mockito.mock(LanternaDigitTileView.class);

        final TextColor[] color = {Mockito.mock(TextColor.class)};

        Mockito.when(color[0].toString()).thenReturn("default");

        TextGraphics graphics = Mockito.mock(TextGraphics.class);
        Mockito.when(graphics.getBackgroundColor()).thenReturn(color[0]);
        Mockito.doAnswer((Answer<Void>) invocation -> {
            color[0] = invocation.getArgument(0);
            return null;
        }).when(graphics).setBackgroundColor(Mockito.any(TextColor.class));

        Mockito.doCallRealMethod().when(view)
                .render(Mockito.any(Tile.class), Mockito.anyInt(), Mockito.anyInt(), Mockito.any(TextGraphics.class));

        TextColor powerColor = Mockito.mock(TextColor.class);

        Mockito.when(view.getPowerColor(Mockito.anyInt())).thenReturn(powerColor);

        DigitTile tile = Mockito.mock(DigitTile.class);


        Mockito.when(tile.getPowerLevel()).thenReturn(Power.getMin());

        view.render(tile, -3, 6, graphics);

        Mockito.verify(view, Mockito.times(1))
                .renderFrame(graphics, 6, -3);
        Mockito.verify(view, Mockito.times(1)).getPowerColor(Mockito.anyInt());
        Mockito.verify(graphics, Mockito.times(1)).setForegroundColor(powerColor);
        Mockito.verify(graphics, Mockito.times(1)).getBackgroundColor();
        Mockito.verify(graphics, Mockito.times(1)).setBackgroundColor(color[0]);
        Mockito.verify(graphics, Mockito.times(2)).setBackgroundColor(Mockito.any(TextColor.class));
        Mockito.verify(graphics, Mockito.times(1))
                .putString(7, -2, "0");
        Mockito.when(tile.getPowerLevel()).thenReturn(10);

        view.render(tile, 3, 0, graphics);

        Mockito.verify(view, Mockito.times(1))
                .renderFrame(graphics, 0, 3);
        Mockito.verify(view, Mockito.times(2)).getPowerColor(Mockito.anyInt());
        Mockito.verify(graphics, Mockito.times(2)).setForegroundColor(powerColor);
        Mockito.verify(graphics, Mockito.times(2)).getBackgroundColor();
        Mockito.verify(graphics, Mockito.times(2)).setBackgroundColor(color[0]);
        Mockito.verify(graphics, Mockito.times(4)).setBackgroundColor(Mockito.any(TextColor.class));
        Mockito.verify(graphics, Mockito.times(1))
                .putString(1, 4, "A");

        Assertions.assertEquals("default", color[0].toString());
    }
}
