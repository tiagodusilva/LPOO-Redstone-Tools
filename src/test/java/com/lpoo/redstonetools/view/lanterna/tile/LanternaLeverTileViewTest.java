package com.lpoo.redstonetools.view.lanterna.tile;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.lpoo.redstonetools.model.tile.LeverTile;
import com.lpoo.redstonetools.model.tile.Tile;
import com.lpoo.redstonetools.model.utils.Power;
import com.lpoo.redstonetools.model.utils.Side;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

public class LanternaLeverTileViewTest {

    @Test
    @Tag("view")
    @Tag("unit-test") @Tag("fast")
    public void testRender() {
        LanternaLeverTileView view = Mockito.mock(LanternaLeverTileView.class);

        Mockito.doCallRealMethod().when(view)
                .render(Mockito.any(Tile.class), Mockito.anyInt(), Mockito.anyInt(), Mockito.any(TextGraphics.class));

        final TextColor[] color = {Mockito.mock(TextColor.class)};

        TextColor powerColor = Mockito.mock(TextColor.class);

        Mockito.when(color[0].toString()).thenReturn("default");
        Mockito.when(powerColor.toString()).thenReturn("power");

        Mockito.when(view.getPowerColor(Mockito.anyInt())).thenReturn(powerColor);

        TextGraphics graphics = Mockito.mock(TextGraphics.class);

        Mockito.when(graphics.getBackgroundColor()).thenReturn(color[0]);
        Mockito.doAnswer((Answer<Void>) invocation -> {
            color[0] = invocation.getArgument(0);
            return null;
        }).when(graphics).setBackgroundColor(Mockito.any(TextColor.class));

        LeverTile tile = Mockito.mock(LeverTile.class);

        Mockito.when(tile.getPower(Mockito.any(Side.class))).thenReturn(12);

        view.render(tile, -5, 4, graphics);

        Assertions.assertEquals("default", color[0].toString());

        Mockito.verify(view, Mockito.times(1)).renderFrame(graphics, 4, -5);
        Mockito.verify(view, Mockito.times(1)).getPowerColor(12);
        Mockito.verify(view, Mockito.times(1)).getPowerColor(Mockito.anyInt());

        Mockito.verify(tile, Mockito.times(1)).getPower(Mockito.any(Side.class));

        Mockito.verify(graphics, Mockito.times(1)).getBackgroundColor();
        Mockito.verify(graphics, Mockito.times(2)).setBackgroundColor(Mockito.any(TextColor.class));
        Mockito.verify(graphics, Mockito.times(1)).setForegroundColor(powerColor);
        Mockito.verify(graphics, Mockito.times(1)).setBackgroundColor(color[0]);
        Mockito.verify(graphics, Mockito.times(1))
                .setCharacter(Mockito.eq(5), Mockito.eq(-4), Mockito.anyChar());


        Mockito.when(tile.getPower(Mockito.any(Side.class))).thenReturn(Power.getMin());

        view.render(tile, 0, 5, graphics);

        Assertions.assertEquals("default", color[0].toString());

        Mockito.verify(view, Mockito.times(1)).renderFrame(graphics, 5, 0);
        Mockito.verify(view, Mockito.times(1)).getPowerColor(Power.getMin());
        Mockito.verify(view, Mockito.times(2)).getPowerColor(Mockito.anyInt());

        Mockito.verify(tile, Mockito.times(2)).getPower(Mockito.any(Side.class));

        Mockito.verify(graphics, Mockito.times(2)).getBackgroundColor();
        Mockito.verify(graphics, Mockito.times(4)).setBackgroundColor(Mockito.any(TextColor.class));
        Mockito.verify(graphics, Mockito.times(2)).setForegroundColor(powerColor);
        Mockito.verify(graphics, Mockito.times(2)).setBackgroundColor(color[0]);
        Mockito.verify(graphics, Mockito.times(1))
                .setCharacter(Mockito.eq(6), Mockito.eq(1), Mockito.anyChar());

        Mockito.verify(graphics, Mockito.times(2))
                .setCharacter(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyChar());
    }
}
