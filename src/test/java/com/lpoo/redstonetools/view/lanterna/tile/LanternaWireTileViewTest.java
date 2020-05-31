package com.lpoo.redstonetools.view.lanterna.tile;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.lpoo.redstonetools.model.tile.Tile;
import com.lpoo.redstonetools.model.tile.WireTile;
import com.lpoo.redstonetools.model.utils.Power;
import com.lpoo.redstonetools.model.utils.Side;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class LanternaWireTileViewTest {

    @Test
    @Tag("view")
    @Tag("unit-test") @Tag("fast")
    public void testRender() {
        LanternaWireTileView view = Mockito.mock(LanternaWireTileView.class, Mockito.withSettings().useConstructor());

        Mockito.doCallRealMethod().when(view)
                .render(Mockito.any(Tile.class), Mockito.anyInt(), Mockito.anyInt(), Mockito.any(TextGraphics.class));

        TextColor color = Mockito.mock(TextColor.class);

        Mockito.when(view.getPowerColor(Mockito.anyInt())).thenReturn(color);

        WireTile tile = Mockito.mock(WireTile.class);

        Mockito.when(tile.getPower(Mockito.any(Side.class))).thenReturn(5);

        Mockito.when(tile.isConnected(Side.UP)).thenReturn(false);
        Mockito.when(tile.isConnected(Side.LEFT)).thenReturn(false);
        Mockito.when(tile.isConnected(Side.DOWN)).thenReturn(false);
        Mockito.when(tile.isConnected(Side.RIGHT)).thenReturn(false);

        TextGraphics graphics = Mockito.mock(TextGraphics.class);

        view.render(tile, -3, 5, graphics);

        Mockito.verify(tile, Mockito.times(1)).getPower(Mockito.any(Side.class));
        Mockito.verify(tile, Mockito.times(1)).isConnected(Side.UP);
        Mockito.verify(tile, Mockito.times(1)).isConnected(Side.LEFT);
        Mockito.verify(tile, Mockito.times(1)).isConnected(Side.DOWN);
        Mockito.verify(tile, Mockito.times(1)).isConnected(Side.RIGHT);

        Mockito.verify(view, Mockito.times(1)).getPowerColor(5);
        Mockito.verify(view, Mockito.times(1)).getPowerColor(Mockito.anyInt());

        Mockito.verify(graphics, Mockito.times(1)).setForegroundColor(color);
        Mockito.verify(graphics, Mockito.times(1))
                .setCharacter(Mockito.eq(6), Mockito.eq(-2), Mockito.anyChar());
        Mockito.verify(graphics, Mockito.times(1))
                .setCharacter(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyChar());

        Mockito.verify(graphics, Mockito.times(0))
                .putString(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString());
    }

    @Test
    @Tag("view")
    @Tag("unit-test") @Tag("fast")
    public void testRenderSecond() {
        LanternaWireTileView view = Mockito.mock(LanternaWireTileView.class, Mockito.withSettings().useConstructor());

        Mockito.doCallRealMethod().when(view).toggleShowPower();

        view.toggleShowPower();

        Mockito.doCallRealMethod().when(view)
                .render(Mockito.any(Tile.class), Mockito.anyInt(), Mockito.anyInt(), Mockito.any(TextGraphics.class));

        TextColor color = Mockito.mock(TextColor.class);

        Mockito.when(view.getPowerColor(Mockito.anyInt())).thenReturn(color);

        WireTile tile = Mockito.mock(WireTile.class);

        Mockito.when(tile.getPower(Mockito.any(Side.class))).thenReturn(Power.getMax());

        Mockito.when(tile.isConnected(Side.UP)).thenReturn(true);
        Mockito.when(tile.isConnected(Side.LEFT)).thenReturn(false);
        Mockito.when(tile.isConnected(Side.DOWN)).thenReturn(true);
        Mockito.when(tile.isConnected(Side.RIGHT)).thenReturn(false);

        TextGraphics graphics = Mockito.mock(TextGraphics.class);

        view.render(tile, 0, -1, graphics);

        Mockito.verify(tile, Mockito.times(2)).getPower(Mockito.any(Side.class));
        Mockito.verify(tile, Mockito.times(1)).isConnected(Side.UP);
        Mockito.verify(tile, Mockito.times(1)).isConnected(Side.LEFT);
        Mockito.verify(tile, Mockito.times(1)).isConnected(Side.DOWN);
        Mockito.verify(tile, Mockito.times(1)).isConnected(Side.RIGHT);

        Mockito.verify(view, Mockito.times(1)).getPowerColor(Power.getMax());
        Mockito.verify(view, Mockito.times(1)).getPowerColor(Mockito.anyInt());

        Mockito.verify(graphics, Mockito.times(1)).setForegroundColor(color);

        Mockito.verify(graphics, Mockito.times(1))
                .setCharacter(Mockito.eq(0), Mockito.eq(0), Mockito.anyChar());
        Mockito.verify(graphics, Mockito.times(0))
                .setCharacter(Mockito.eq(-1), Mockito.eq(1), Mockito.anyChar());
        Mockito.verify(graphics, Mockito.times(1))
                .setCharacter(Mockito.eq(0), Mockito.eq(2), Mockito.anyChar());
        Mockito.verify(graphics, Mockito.times(0))
                .setCharacter(Mockito.eq(1), Mockito.eq(1), Mockito.anyChar());

        Mockito.verify(graphics, Mockito.times(0))
                .setCharacter(Mockito.eq(0), Mockito.eq(1), Mockito.anyChar());
        Mockito.verify(graphics, Mockito.times(2))
                .setCharacter(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyChar());

        Mockito.verify(graphics, Mockito.times(1))
                .putString(0,1, "F");
        Mockito.verify(graphics, Mockito.times(1))
                .putString(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString());
    }

    @Test
    @Tag("view")
    @Tag("unit-test") @Tag("fast")
    public void testRenderThird() {
        LanternaWireTileView view = Mockito.mock(LanternaWireTileView.class, Mockito.withSettings().useConstructor());

        Mockito.doCallRealMethod().when(view)
                .render(Mockito.any(Tile.class), Mockito.anyInt(), Mockito.anyInt(), Mockito.any(TextGraphics.class));

        TextColor color = Mockito.mock(TextColor.class);

        Mockito.when(view.getPowerColor(Mockito.anyInt())).thenReturn(color);

        WireTile tile = Mockito.mock(WireTile.class);

        Mockito.when(tile.getPower(Mockito.any(Side.class))).thenReturn(Power.getMin());

        Mockito.when(tile.isConnected(Side.UP)).thenReturn(false);
        Mockito.when(tile.isConnected(Side.LEFT)).thenReturn(true);
        Mockito.when(tile.isConnected(Side.DOWN)).thenReturn(true);
        Mockito.when(tile.isConnected(Side.RIGHT)).thenReturn(true);

        TextGraphics graphics = Mockito.mock(TextGraphics.class);

        view.render(tile, 0, -1, graphics);

        Mockito.verify(tile, Mockito.times(1)).getPower(Mockito.any(Side.class));
        Mockito.verify(tile, Mockito.times(1)).isConnected(Side.UP);
        Mockito.verify(tile, Mockito.times(1)).isConnected(Side.LEFT);
        Mockito.verify(tile, Mockito.times(1)).isConnected(Side.DOWN);
        Mockito.verify(tile, Mockito.times(1)).isConnected(Side.RIGHT);

        Mockito.verify(view, Mockito.times(1)).getPowerColor(Power.getMin());
        Mockito.verify(view, Mockito.times(1)).getPowerColor(Mockito.anyInt());

        Mockito.verify(graphics, Mockito.times(1)).setForegroundColor(color);

        Mockito.verify(graphics, Mockito.times(0))
                .setCharacter(Mockito.eq(0), Mockito.eq(0), Mockito.anyChar());
        Mockito.verify(graphics, Mockito.times(1))
                .setCharacter(Mockito.eq(-1), Mockito.eq(1), Mockito.anyChar());
        Mockito.verify(graphics, Mockito.times(1))
                .setCharacter(Mockito.eq(0), Mockito.eq(2), Mockito.anyChar());
        Mockito.verify(graphics, Mockito.times(1))
                .setCharacter(Mockito.eq(1), Mockito.eq(1), Mockito.anyChar());

        Mockito.verify(graphics, Mockito.times(1))
                .setCharacter(Mockito.eq(0), Mockito.eq(1), Mockito.anyChar());
        Mockito.verify(graphics, Mockito.times(4))
                .setCharacter(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyChar());

        Mockito.verify(graphics, Mockito.times(0))
                .putString(0,1, "0");
        Mockito.verify(graphics, Mockito.times(0))
                .putString(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString());
    }
}
