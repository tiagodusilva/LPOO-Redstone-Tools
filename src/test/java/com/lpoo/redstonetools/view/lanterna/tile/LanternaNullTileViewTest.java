package com.lpoo.redstonetools.view.lanterna.tile;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.lpoo.redstonetools.model.tile.NullTile;
import com.lpoo.redstonetools.model.tile.Tile;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class LanternaNullTileViewTest {

    @Test
    @Tag("view")
    @Tag("unit-test") @Tag("fast")
    public void testRender() {
        LanternaNullTileView view = Mockito.mock(LanternaNullTileView.class);

        Mockito.doCallRealMethod().when(view)
                .render(Mockito.any(Tile.class), Mockito.anyInt(), Mockito.anyInt(), Mockito.any(TextGraphics.class));

        TextGraphics graphics = Mockito.mock(TextGraphics.class);

        NullTile tile = Mockito.mock(NullTile.class);

        // test empty tile
        Mockito.when(tile.isBroken()).thenReturn(false);

        view.render(tile, -5, 2, graphics);

        Mockito.verify(tile, Mockito.times(1)).isBroken();

        Mockito.verify(view, Mockito.times(0))
                .renderBrokenFrame(Mockito.any(TextGraphics.class), Mockito.anyInt(), Mockito.anyInt());

        Mockito.verify(graphics, Mockito.times(1))
                .setCharacter(Mockito.eq(3), Mockito.eq(-4), Mockito.anyChar());

        // test broken tile
        Mockito.when(tile.isBroken()).thenReturn(true);

        view.render(tile, 3, 0, graphics);

        Mockito.verify(tile, Mockito.times(2)).isBroken();

        Mockito.verify(view, Mockito.times(1))
                .renderBrokenFrame(graphics, 0, 3);
        Mockito.verify(view, Mockito.times(1))
                .renderBrokenFrame(Mockito.any(TextGraphics.class), Mockito.anyInt(), Mockito.anyInt());

        Mockito.verify(graphics, Mockito.times(1))
                .setCharacter(Mockito.eq(1), Mockito.eq(4), Mockito.anyChar());

        Mockito.verify(graphics, Mockito.times(2))
                .setCharacter(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyChar());
    }
}
