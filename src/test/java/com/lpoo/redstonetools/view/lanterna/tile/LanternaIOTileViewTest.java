package com.lpoo.redstonetools.view.lanterna.tile;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.lpoo.redstonetools.model.tile.ComparatorTile;
import com.lpoo.redstonetools.model.tile.IOTile;
import com.lpoo.redstonetools.model.tile.Tile;
import com.lpoo.redstonetools.model.utils.Side;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class LanternaIOTileViewTest {
    @Test
    @Tag("view")
    @Tag("unit-test") @Tag("fast")
    public void testRender() {
        LanternaIOTileView view = Mockito.mock(LanternaIOTileView.class, Mockito.withSettings().useConstructor());

        Mockito.doCallRealMethod().when(view)
                .render(Mockito.any(Tile.class), Mockito.anyInt(), Mockito.anyInt(), Mockito.any(TextGraphics.class));

        TextColor input = Mockito.mock(TextColor.class);
        TextColor output = Mockito.mock(TextColor.class);
        TextColor color = Mockito.mock(TextColor.class);

        Mockito.when(view.getInputColor()).thenReturn(input);
        Mockito.when(view.getOutputColor()).thenReturn(output);
        Mockito.when(view.getForegroundColor()).thenReturn(color);

        IOTile tile = Mockito.mock(IOTile.class);
        Mockito.when(tile.getIOSide()).thenReturn(Side.LEFT);
        Mockito.when(tile.acceptsPower(Mockito.any(Side.class))).thenReturn(false);
        Mockito.when(tile.outputsPower(Mockito.any(Side.class))).thenReturn(false);

        TextGraphics graphics = Mockito.mock(TextGraphics.class);

        view.render(tile, -3, -5, graphics);

        Mockito.verify(tile, Mockito.times(1)).acceptsPower(Mockito.any(Side.class));
        Mockito.verify(tile, Mockito.times(1)).outputsPower(Mockito.any(Side.class));
        Mockito.verify(tile, Mockito.times(1)).getIOSide();

        Mockito.verify(view, Mockito.times(0)).getInputColor();
        Mockito.verify(view, Mockito.times(0)).getOutputColor();
        Mockito.verify(view, Mockito.times(1)).getForegroundColor();
        Mockito.verify(view, Mockito.times(1))
                .renderPowerSensitiveFrame(graphics, tile, -5, -3, color);
        Mockito.verify(view, Mockito.times(1))
                .renderPowerSensitiveFrame(Mockito.any(TextGraphics.class), Mockito.any(IOTile.class), Mockito.anyInt(), Mockito.anyInt(), Mockito.any(TextColor.class));

        Mockito.verify(graphics, Mockito.times(0)).setForegroundColor(input);
        Mockito.verify(graphics, Mockito.times(0)).setForegroundColor(output);
        Mockito.verify(graphics, Mockito.times(1)).setForegroundColor(color);
        Mockito.verify(graphics, Mockito.times(1)).setCharacter(Mockito.eq(-4), Mockito.eq(-2), Mockito.anyChar());
        Mockito.verify(graphics, Mockito.times(1)).setCharacter(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyChar());
    }

    @Test
    @Tag("view")
    @Tag("unit-test") @Tag("fast")
    public void testRenderSecond() {
        LanternaIOTileView view = Mockito.mock(LanternaIOTileView.class, Mockito.withSettings().useConstructor());

        Mockito.doCallRealMethod().when(view)
                .render(Mockito.any(Tile.class), Mockito.anyInt(), Mockito.anyInt(), Mockito.any(TextGraphics.class));

        TextColor input = Mockito.mock(TextColor.class);
        TextColor output = Mockito.mock(TextColor.class);
        TextColor color = Mockito.mock(TextColor.class);

        Mockito.when(view.getInputColor()).thenReturn(input);
        Mockito.when(view.getOutputColor()).thenReturn(output);
        Mockito.when(view.getForegroundColor()).thenReturn(color);

        IOTile tile = Mockito.mock(IOTile.class);
        Mockito.when(tile.getIOSide()).thenReturn(Side.DOWN);
        Mockito.when(tile.acceptsPower(Mockito.any(Side.class))).thenReturn(true);
        Mockito.when(tile.outputsPower(Mockito.any(Side.class))).thenReturn(false);

        TextGraphics graphics = Mockito.mock(TextGraphics.class);

        view.render(tile, 5, 0, graphics);

        Mockito.verify(tile, Mockito.times(1)).acceptsPower(Mockito.any(Side.class));
        Mockito.verify(tile, Mockito.times(0)).outputsPower(Mockito.any(Side.class));
        Mockito.verify(tile, Mockito.times(1)).getIOSide();

        Mockito.verify(view, Mockito.times(0)).getInputColor();
        Mockito.verify(view, Mockito.times(1)).getOutputColor();
        Mockito.verify(view, Mockito.times(0)).getForegroundColor();
        Mockito.verify(view, Mockito.times(1))
                .renderPowerSensitiveFrame(graphics, tile, 0, 5, output);
        Mockito.verify(view, Mockito.times(1))
                .renderPowerSensitiveFrame(Mockito.any(TextGraphics.class), Mockito.any(IOTile.class), Mockito.anyInt(), Mockito.anyInt(), Mockito.any(TextColor.class));

        Mockito.verify(graphics, Mockito.times(0)).setForegroundColor(input);
        Mockito.verify(graphics, Mockito.times(1)).setForegroundColor(output);
        Mockito.verify(graphics, Mockito.times(0)).setForegroundColor(color);
        Mockito.verify(graphics, Mockito.times(1)).setCharacter(Mockito.eq(1), Mockito.eq(6), Mockito.anyChar());
        Mockito.verify(graphics, Mockito.times(1)).setCharacter(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyChar());
    }

    @Test
    @Tag("view")
    @Tag("unit-test") @Tag("fast")
    public void testRenderThird() {
        LanternaIOTileView view = Mockito.mock(LanternaIOTileView.class, Mockito.withSettings().useConstructor());

        Mockito.doCallRealMethod().when(view)
                .render(Mockito.any(Tile.class), Mockito.anyInt(), Mockito.anyInt(), Mockito.any(TextGraphics.class));

        TextColor input = Mockito.mock(TextColor.class);
        TextColor output = Mockito.mock(TextColor.class);
        TextColor color = Mockito.mock(TextColor.class);

        Mockito.when(view.getInputColor()).thenReturn(input);
        Mockito.when(view.getOutputColor()).thenReturn(output);
        Mockito.when(view.getForegroundColor()).thenReturn(color);

        IOTile tile = Mockito.mock(IOTile.class);
        Mockito.when(tile.getIOSide()).thenReturn(Side.UP);
        Mockito.when(tile.acceptsPower(Mockito.any(Side.class))).thenReturn(false);
        Mockito.when(tile.outputsPower(Mockito.any(Side.class))).thenReturn(true);

        TextGraphics graphics = Mockito.mock(TextGraphics.class);

        view.render(tile, -3, 10, graphics);

        Mockito.verify(tile, Mockito.times(1)).acceptsPower(Mockito.any(Side.class));
        Mockito.verify(tile, Mockito.times(1)).outputsPower(Mockito.any(Side.class));
        Mockito.verify(tile, Mockito.times(1)).getIOSide();

        Mockito.verify(view, Mockito.times(1)).getInputColor();
        Mockito.verify(view, Mockito.times(0)).getOutputColor();
        Mockito.verify(view, Mockito.times(0)).getForegroundColor();
        Mockito.verify(view, Mockito.times(1))
                .renderPowerSensitiveFrame(graphics, tile, 10, -3, input);
        Mockito.verify(view, Mockito.times(1))
                .renderPowerSensitiveFrame(Mockito.any(TextGraphics.class), Mockito.any(IOTile.class), Mockito.anyInt(), Mockito.anyInt(), Mockito.any(TextColor.class));

        Mockito.verify(graphics, Mockito.times(1)).setForegroundColor(input);
        Mockito.verify(graphics, Mockito.times(0)).setForegroundColor(output);
        Mockito.verify(graphics, Mockito.times(0)).setForegroundColor(color);
        Mockito.verify(graphics, Mockito.times(1)).setCharacter(Mockito.eq(11), Mockito.eq(-2), Mockito.anyChar());
        Mockito.verify(graphics, Mockito.times(1)).setCharacter(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyChar());
    }
}
