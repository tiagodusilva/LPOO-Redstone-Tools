package com.lpoo.redstonetools.view.lanterna.tile;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.lpoo.redstonetools.model.tile.CrossWireTile;
import com.lpoo.redstonetools.model.tile.Tile;
import com.lpoo.redstonetools.model.utils.Power;
import com.lpoo.redstonetools.model.utils.Side;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class LanternaCrossWireTileViewTest {

    @Test
    @Tag("view")
    @Tag("unit-test") @Tag("fast")
    public void testRender() {
        LanternaCrossWireTileView view = Mockito.mock(LanternaCrossWireTileView.class);

        Mockito.doCallRealMethod().when(view)
                .render(Mockito.any(Tile.class), Mockito.anyInt(), Mockito.anyInt(), Mockito.any(TextGraphics.class));

        TextColor left = Mockito.mock(TextColor.class);
        TextColor up = Mockito.mock(TextColor.class);
        TextColor right = Mockito.mock(TextColor.class);
        TextColor down = Mockito.mock(TextColor.class);

        Mockito.when(view.getPowerColor(5)).thenReturn(up);
        Mockito.when(view.getPowerColor(10)).thenReturn(left);
        Mockito.when(view.getPowerColor(Power.getMin())).thenReturn(down);
        Mockito.when(view.getPowerColor(Power.getMax())).thenReturn(right);

        CrossWireTile tile = Mockito.mock(CrossWireTile.class);

        Mockito.when(tile.isConnected(Side.UP)).thenReturn(false);
        Mockito.when(tile.getPower(Side.UP)).thenReturn(5);

        Mockito.when(tile.isConnected(Side.LEFT)).thenReturn(false);
        Mockito.when(tile.getPower(Side.LEFT)).thenReturn(10);

        Mockito.when(tile.isConnected(Side.DOWN)).thenReturn(false);
        Mockito.when(tile.getPower(Side.DOWN)).thenReturn(Power.getMin());

        Mockito.when(tile.isConnected(Side.RIGHT)).thenReturn(false);
        Mockito.when(tile.getPower(Side.RIGHT)).thenReturn(Power.getMax());

        TextGraphics graphics = Mockito.mock(TextGraphics.class);

        view.render(tile, 0, -2, graphics);

        Mockito.verify(tile, Mockito.times(1)).isConnected(Side.UP);
        Mockito.verify(tile, Mockito.times(0)).getPower(Side.UP);

        Mockito.verify(tile, Mockito.times(1)).isConnected(Side.LEFT);
        Mockito.verify(tile, Mockito.times(0)).getPower(Side.LEFT);

        Mockito.verify(tile, Mockito.times(1)).isConnected(Side.DOWN);
        Mockito.verify(tile, Mockito.times(0)).getPower(Side.DOWN);

        Mockito.verify(tile, Mockito.times(1)).isConnected(Side.RIGHT);
        Mockito.verify(tile, Mockito.times(0)).getPower(Side.RIGHT);

        Mockito.verify(view, Mockito.times(0)).getPowerColor(Mockito.anyInt());

        Mockito.verify(graphics, Mockito.times(1))
                .setForegroundColor(Mockito.any(TextColor.class));
        Mockito.verify(graphics, Mockito.times(1))
                .setCharacter(Mockito.eq(-1), Mockito.eq(1), Mockito.anyChar());
        Mockito.verify(graphics, Mockito.times(1))
                .setCharacter(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyChar());
    }

    @Test
    @Tag("view")
    @Tag("unit-test") @Tag("fast")
    public void testRenderSecond() {
        LanternaCrossWireTileView view = Mockito.mock(LanternaCrossWireTileView.class);

        Mockito.doCallRealMethod().when(view)
                .render(Mockito.any(Tile.class), Mockito.anyInt(), Mockito.anyInt(), Mockito.any(TextGraphics.class));

        TextColor left = Mockito.mock(TextColor.class);
        TextColor up = Mockito.mock(TextColor.class);
        TextColor right = Mockito.mock(TextColor.class);
        TextColor down = Mockito.mock(TextColor.class);

        Mockito.when(view.getPowerColor(5)).thenReturn(up);
        Mockito.when(view.getPowerColor(10)).thenReturn(left);
        Mockito.when(view.getPowerColor(Power.getMin())).thenReturn(down);
        Mockito.when(view.getPowerColor(Power.getMax())).thenReturn(right);

        CrossWireTile tile = Mockito.mock(CrossWireTile.class);

        Mockito.when(tile.isConnected(Side.UP)).thenReturn(false);
        Mockito.when(tile.getPower(Side.UP)).thenReturn(5);

        Mockito.when(tile.isConnected(Side.LEFT)).thenReturn(true);
        Mockito.when(tile.getPower(Side.LEFT)).thenReturn(10);

        Mockito.when(tile.isConnected(Side.DOWN)).thenReturn(false);
        Mockito.when(tile.getPower(Side.DOWN)).thenReturn(Power.getMin());

        Mockito.when(tile.isConnected(Side.RIGHT)).thenReturn(true);
        Mockito.when(tile.getPower(Side.RIGHT)).thenReturn(Power.getMax());

        TextGraphics graphics = Mockito.mock(TextGraphics.class);

        view.render(tile, -1, 3, graphics);

        Mockito.verify(tile, Mockito.times(1)).isConnected(Side.UP);
        Mockito.verify(tile, Mockito.times(0)).getPower(Side.UP);

        Mockito.verify(tile, Mockito.times(1)).isConnected(Side.LEFT);
        Mockito.verify(tile, Mockito.times(1)).getPower(Side.LEFT);

        Mockito.verify(tile, Mockito.times(1)).isConnected(Side.DOWN);
        Mockito.verify(tile, Mockito.times(0)).getPower(Side.DOWN);

        Mockito.verify(tile, Mockito.times(1)).isConnected(Side.RIGHT);
        Mockito.verify(tile, Mockito.times(1)).getPower(Side.RIGHT);

        Mockito.verify(view, Mockito.times(0)).getPowerColor(5);
        Mockito.verify(view, Mockito.times(1)).getPowerColor(10);
        Mockito.verify(view, Mockito.times(0)).getPowerColor(Power.getMin());
        Mockito.verify(view, Mockito.times(1)).getPowerColor(Power.getMax());

        Mockito.verify(view, Mockito.times(2)).getPowerColor(Mockito.anyInt());

        Mockito.verify(graphics, Mockito.times(3))
                .setForegroundColor(Mockito.any(TextColor.class));
        Mockito.verify(graphics, Mockito.times(0))
                .setForegroundColor(up);
        Mockito.verify(graphics, Mockito.times(1))
                .setForegroundColor(left);
        Mockito.verify(graphics, Mockito.times(0))
                .setForegroundColor(down);
        Mockito.verify(graphics, Mockito.times(1))
                .setForegroundColor(right);

        Mockito.verify(graphics, Mockito.times(0))
                .setCharacter(Mockito.eq(4), Mockito.eq(-1), Mockito.anyChar());

        Mockito.verify(graphics, Mockito.times(1))
                .setCharacter(Mockito.eq(3), Mockito.eq(0), Mockito.anyChar());

        Mockito.verify(graphics, Mockito.times(0))
                .setCharacter(Mockito.eq(4), Mockito.eq(1), Mockito.anyChar());

        Mockito.verify(graphics, Mockito.times(1))
                .setCharacter(Mockito.eq(5), Mockito.eq(0), Mockito.anyChar());

        Mockito.verify(graphics, Mockito.times(1))
                .setCharacter(Mockito.eq(4), Mockito.eq(0), Mockito.anyChar());
        Mockito.verify(graphics, Mockito.times(3))
                .setCharacter(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyChar());
    }

    @Test
    @Tag("view")
    @Tag("unit-test") @Tag("fast")
    public void testRenderThird() {
        LanternaCrossWireTileView view = Mockito.mock(LanternaCrossWireTileView.class);

        Mockito.doCallRealMethod().when(view)
                .render(Mockito.any(Tile.class), Mockito.anyInt(), Mockito.anyInt(), Mockito.any(TextGraphics.class));

        TextColor left = Mockito.mock(TextColor.class);
        TextColor up = Mockito.mock(TextColor.class);
        TextColor right = Mockito.mock(TextColor.class);
        TextColor down = Mockito.mock(TextColor.class);

        Mockito.when(view.getPowerColor(5)).thenReturn(up);
        Mockito.when(view.getPowerColor(10)).thenReturn(left);
        Mockito.when(view.getPowerColor(Power.getMin())).thenReturn(down);
        Mockito.when(view.getPowerColor(Power.getMax())).thenReturn(right);

        CrossWireTile tile = Mockito.mock(CrossWireTile.class);

        Mockito.when(tile.isConnected(Side.UP)).thenReturn(true);
        Mockito.when(tile.getPower(Side.UP)).thenReturn(5);

        Mockito.when(tile.isConnected(Side.LEFT)).thenReturn(true);
        Mockito.when(tile.getPower(Side.LEFT)).thenReturn(10);

        Mockito.when(tile.isConnected(Side.DOWN)).thenReturn(true);
        Mockito.when(tile.getPower(Side.DOWN)).thenReturn(Power.getMin());

        Mockito.when(tile.isConnected(Side.RIGHT)).thenReturn(false);
        Mockito.when(tile.getPower(Side.RIGHT)).thenReturn(Power.getMax());

        TextGraphics graphics = Mockito.mock(TextGraphics.class);

        view.render(tile, -1, 3, graphics);

        Mockito.verify(tile, Mockito.times(1)).isConnected(Side.UP);
        Mockito.verify(tile, Mockito.times(1)).getPower(Side.UP);

        Mockito.verify(tile, Mockito.times(1)).isConnected(Side.LEFT);
        Mockito.verify(tile, Mockito.times(1)).getPower(Side.LEFT);

        Mockito.verify(tile, Mockito.times(1)).isConnected(Side.DOWN);
        Mockito.verify(tile, Mockito.times(1)).getPower(Side.DOWN);

        Mockito.verify(tile, Mockito.times(1)).isConnected(Side.RIGHT);
        Mockito.verify(tile, Mockito.times(0)).getPower(Side.RIGHT);

        Mockito.verify(view, Mockito.times(1)).getPowerColor(5);
        Mockito.verify(view, Mockito.times(1)).getPowerColor(10);
        Mockito.verify(view, Mockito.times(1)).getPowerColor(Power.getMin());
        Mockito.verify(view, Mockito.times(0)).getPowerColor(Power.getMax());

        Mockito.verify(view, Mockito.times(3)).getPowerColor(Mockito.anyInt());

        Mockito.verify(graphics, Mockito.times(4))
                .setForegroundColor(Mockito.any(TextColor.class));
        Mockito.verify(graphics, Mockito.times(1))
                .setForegroundColor(up);
        Mockito.verify(graphics, Mockito.times(1))
                .setForegroundColor(left);
        Mockito.verify(graphics, Mockito.times(1))
                .setForegroundColor(down);
        Mockito.verify(graphics, Mockito.times(0))
                .setForegroundColor(right);

        Mockito.verify(graphics, Mockito.times(1))
                .setCharacter(Mockito.eq(4), Mockito.eq(-1), Mockito.anyChar());

        Mockito.verify(graphics, Mockito.times(1))
                .setCharacter(Mockito.eq(3), Mockito.eq(0), Mockito.anyChar());

        Mockito.verify(graphics, Mockito.times(1))
                .setCharacter(Mockito.eq(4), Mockito.eq(1), Mockito.anyChar());

        Mockito.verify(graphics, Mockito.times(0))
                .setCharacter(Mockito.eq(5), Mockito.eq(0), Mockito.anyChar());

        Mockito.verify(graphics, Mockito.times(1))
                .setCharacter(Mockito.eq(4), Mockito.eq(0), Mockito.anyChar());
        Mockito.verify(graphics, Mockito.times(4))
                .setCharacter(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyChar());
    }
}
