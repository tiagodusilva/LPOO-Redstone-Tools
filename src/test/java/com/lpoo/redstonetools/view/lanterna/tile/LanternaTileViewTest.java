package com.lpoo.redstonetools.view.lanterna.tile;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.lpoo.redstonetools.model.tile.Tile;
import com.lpoo.redstonetools.model.utils.Power;
import com.lpoo.redstonetools.model.utils.Side;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class LanternaTileViewTest {

    private LanternaTileView tileView;

    @BeforeEach
    public void setup() {
        this.tileView = Mockito.mock(LanternaTileView.class, Mockito.withSettings().defaultAnswer(Mockito.CALLS_REAL_METHODS));
    }

    @Test
    @Tag("view")
    @Tag("unit-test") @Tag("fast")
    public void testPowerColor() {
        LanternaTileView view = new LanternaTileView() {
            @Override
            public void render(Tile tile, int row, int column, TextGraphics graphics) { }
        };

        List<String> powerColors = new ArrayList<>();

        for (int i = Power.getMin(); i <= Power.getMax(); i++) {
            powerColors.add(view.getPowerColor(i).toString());
        }

        List<String> uniqueColors = powerColors.stream().distinct().collect(Collectors.toList());

        Assertions.assertEquals(powerColors.size(), uniqueColors.size());
    }

    @Test
    @Tag("view")
    @Tag("unit-test") @Tag("fast")
    public void testRenderBrokenFrame() {
        final TextColor[] color = {Mockito.mock(TextColor.class)};

        TextColor broken = Mockito.mock(TextColor.class);

        Mockito.when(color[0].toString()).thenReturn("default");
        Mockito.when(broken.toString()).thenReturn("broken");

        TextGraphics graphics = Mockito.mock(TextGraphics.class);

        Mockito.when(tileView.getBrokenColor()).thenReturn(broken);

        Mockito.when(graphics.getForegroundColor()).thenReturn(color[0]);
        Mockito.doAnswer((Answer<Void>) invocation -> {
            color[0] = invocation.getArgument(0);
            return null;
        }).when(graphics).setForegroundColor(Mockito.any(TextColor.class));

        tileView.renderBrokenFrame(graphics, 4, -5);

        Assertions.assertEquals("default", color[0].toString());

        Mockito.verify(graphics, Mockito.times(1)).getForegroundColor();
        Mockito.verify(graphics, Mockito.times(2)).setForegroundColor(Mockito.any(TextColor.class));
        Mockito.verify(graphics, Mockito.times(1))
                .putString(Mockito.eq(4), Mockito.eq(-5), Mockito.anyString());
        Mockito.verify(graphics, Mockito.times(1))
                .putString(Mockito.eq(4), Mockito.eq(-4), Mockito.anyString());
        Mockito.verify(graphics, Mockito.times(1))
                .putString(Mockito.eq(4), Mockito.eq(-3), Mockito.anyString());

        Mockito.when(color[0].toString()).thenReturn("non");

        tileView.renderBrokenFrame(graphics, 0, 4);

        Assertions.assertEquals("non", color[0].toString());

        Mockito.verify(graphics, Mockito.times(2)).getForegroundColor();
        Mockito.verify(graphics, Mockito.times(4)).setForegroundColor(Mockito.any(TextColor.class));
        Mockito.verify(graphics, Mockito.times(1))
                .putString(Mockito.eq(0), Mockito.eq(4), Mockito.anyString());
        Mockito.verify(graphics, Mockito.times(1))
                .putString(Mockito.eq(0), Mockito.eq(5), Mockito.anyString());
        Mockito.verify(graphics, Mockito.times(1))
                .putString(Mockito.eq(0), Mockito.eq(6), Mockito.anyString());
    }

    @Test
    @Tag("view")
    @Tag("unit-test") @Tag("fast")
    public void testRenderFrame() {
        TextGraphics graphics = Mockito.mock(TextGraphics.class);

        tileView.renderFrame(graphics, 0, 5);

        Mockito.verify(graphics, Mockito.times(1))
                .putString(Mockito.eq(0), Mockito.eq(5), Mockito.anyString());
        Mockito.verify(graphics, Mockito.times(1))
                .putString(Mockito.eq(0), Mockito.eq(6), Mockito.anyString());
        Mockito.verify(graphics, Mockito.times(1))
                .putString(Mockito.eq(0), Mockito.eq(7), Mockito.anyString());


        tileView.renderFrame(graphics, 19, -1);

        Mockito.verify(graphics, Mockito.times(1))
                .putString(Mockito.eq(19), Mockito.eq(-1), Mockito.anyString());
        Mockito.verify(graphics, Mockito.times(1))
                .putString(Mockito.eq(19), Mockito.eq(0), Mockito.anyString());
        Mockito.verify(graphics, Mockito.times(1))
                .putString(Mockito.eq(19), Mockito.eq(1), Mockito.anyString());
    }

    @Test
    @Tag("view")
    @Tag("unit-test") @Tag("fast")
    public void testRenderPowerSensitiveFrameWithColor() {
        Mockito.doNothing().when(tileView)
                .renderPowerSensitiveFrame(Mockito.any(TextGraphics.class), Mockito.any(Tile.class), Mockito.anyInt(), Mockito.anyInt());

        final TextColor[] color = {Mockito.mock(TextColor.class)};

        TextColor custom = Mockito.mock(TextColor.class);

        AtomicBoolean applied_custom = new AtomicBoolean(false);

        Mockito.when(color[0].toString()).thenReturn("default");
        Mockito.when(custom.toString()).thenReturn("custom");

        TextGraphics graphics = Mockito.mock(TextGraphics.class);

        Mockito.when(graphics.getForegroundColor()).thenReturn(color[0]);
        Mockito.doAnswer((Answer<Void>) invocation -> {
            color[0] = invocation.getArgument(0);
            return null;
        }).when(graphics).setForegroundColor(Mockito.any(TextColor.class));

        Mockito.doAnswer((Answer<Void>) invocation -> {
            color[0] = invocation.getArgument(0);
            applied_custom.set(true);
            return null;
        }).when(graphics).setForegroundColor(custom);

        Tile tile = Mockito.mock(Tile.class);

        tileView.renderPowerSensitiveFrame(graphics, tile, -1, 6, custom);

        Assertions.assertTrue(applied_custom.get());
        Assertions.assertEquals("default", color[0].toString());

        Mockito.verify(graphics, Mockito.times(1)).getForegroundColor();
        Mockito.verify(graphics, Mockito.times(1)).setForegroundColor(custom);
        Mockito.verify(graphics, Mockito.times(2)).setForegroundColor(Mockito.any(TextColor.class));
        Mockito.verify(tileView, Mockito.times(1)).renderPowerSensitiveFrame(graphics, tile, -1, 6);

        Mockito.when(color[0].toString()).thenReturn("non");

        tileView.renderPowerSensitiveFrame(graphics, tile, 7, 0, custom);

        Assertions.assertTrue(applied_custom.get());
        Assertions.assertEquals("non", color[0].toString());

        Mockito.verify(graphics, Mockito.times(2)).getForegroundColor();
        Mockito.verify(graphics, Mockito.times(2)).setForegroundColor(custom);
        Mockito.verify(graphics, Mockito.times(4)).setForegroundColor(Mockito.any(TextColor.class));
        Mockito.verify(tileView, Mockito.times(1)).renderPowerSensitiveFrame(graphics, tile, 7, 0);
    }

    @Test
    @Tag("view")
    @Tag("unit-test") @Tag("fast")
    public void testRenderPowerSensitiveFrame() {
        final TextColor[] color = {Mockito.mock(TextColor.class)};

        TextColor input = Mockito.mock(TextColor.class);
        TextColor output = Mockito.mock(TextColor.class);

        AtomicInteger applied_input = new AtomicInteger(0);

        AtomicInteger applied_output = new AtomicInteger(0);

        Mockito.when(color[0].toString()).thenReturn("default");
        Mockito.when(input.toString()).thenReturn("input");
        Mockito.when(output.toString()).thenReturn("output");

        TextGraphics graphics = Mockito.mock(TextGraphics.class);

        Mockito.when(graphics.getForegroundColor()).thenReturn(color[0]);

        Mockito.when(tileView.getInputColor()).thenReturn(input);
        Mockito.when(tileView.getOutputColor()).thenReturn(output);


        Mockito.doAnswer((Answer<Void>) invocation -> {
            color[0] = invocation.getArgument(0);
            return null;
        }).when(graphics).setForegroundColor(Mockito.any(TextColor.class));

        Mockito.doAnswer((Answer<Void>) invocation -> {
            color[0] = invocation.getArgument(0);
            applied_input.incrementAndGet();
            return null;
        }).when(graphics).setForegroundColor(input);

        Mockito.doAnswer((Answer<Void>) invocation -> {
            color[0] = invocation.getArgument(0);
            applied_output.incrementAndGet();
            return null;
        }).when(graphics).setForegroundColor(output);

        Tile tile = Mockito.mock(Tile.class);

        Mockito.when(tile.acceptsPower(Side.UP)).thenReturn(true);
        Mockito.when(tile.outputsPower(Side.UP)).thenReturn(false);

        Mockito.when(tile.acceptsPower(Side.LEFT)).thenReturn(false);
        Mockito.when(tile.outputsPower(Side.LEFT)).thenReturn(true);

        Mockito.when(tile.acceptsPower(Side.DOWN)).thenReturn(true);
        Mockito.when(tile.outputsPower(Side.DOWN)).thenReturn(false);

        Mockito.when(tile.acceptsPower(Side.RIGHT)).thenReturn(false);
        Mockito.when(tile.outputsPower(Side.RIGHT)).thenReturn(false);

        tileView.renderPowerSensitiveFrame(graphics, tile, 5, -3);

        Assertions.assertEquals(2, applied_input.get());
        Assertions.assertEquals(1, applied_output.get());
        Assertions.assertEquals("default", color[0].toString());

        Mockito.verify(graphics, Mockito.times(1)).getForegroundColor();
        Mockito.verify(graphics, Mockito.times(1))
                .putString(Mockito.eq(5), Mockito.eq(-3), Mockito.anyString());
        Mockito.verify(graphics, Mockito.times(1))
                .putString(Mockito.eq(5), Mockito.eq(-2), Mockito.anyString());
        Mockito.verify(graphics, Mockito.times(1))
                .putString(Mockito.eq(5), Mockito.eq(-1), Mockito.anyString());

        Mockito.verify(tile, Mockito.times(1)).acceptsPower(Side.UP);
        Mockito.verify(tile, Mockito.times(1)).acceptsPower(Side.LEFT);
        Mockito.verify(tile, Mockito.times(1)).acceptsPower(Side.DOWN);
        Mockito.verify(tile, Mockito.times(1)).acceptsPower(Side.RIGHT);

        Mockito.verify(tile, Mockito.times(0)).outputsPower(Side.UP);
        Mockito.verify(tile, Mockito.times(1)).outputsPower(Side.LEFT);
        Mockito.verify(tile, Mockito.times(0)).outputsPower(Side.DOWN);
        Mockito.verify(tile, Mockito.times(1)).outputsPower(Side.RIGHT);

        Mockito.verify(graphics, Mockito.times(2)).setForegroundColor(input);
        Mockito.verify(graphics, Mockito.times(1)).setForegroundColor(output);

        Mockito.verify(graphics, Mockito.times(1))
                .setCharacter(Mockito.eq(6), Mockito.eq(-3), Mockito.anyChar());
        Mockito.verify(graphics, Mockito.times(1))
                .setCharacter(Mockito.eq(5), Mockito.eq(-2), Mockito.anyChar());
        Mockito.verify(graphics, Mockito.times(1))
                .setCharacter(Mockito.eq(6), Mockito.eq(-1), Mockito.anyChar());
        Mockito.verify(graphics, Mockito.times(0))
                .setCharacter(Mockito.eq(7), Mockito.eq(-2), Mockito.anyChar());
    }

    @Test
    @Tag("view")
    @Tag("unit-test") @Tag("fast")
    public void testRenderPowerSensitiveFrameSecond() {
        final TextColor[] color = {Mockito.mock(TextColor.class)};

        TextColor input = Mockito.mock(TextColor.class);
        TextColor output = Mockito.mock(TextColor.class);

        AtomicInteger applied_input = new AtomicInteger(0);

        AtomicInteger applied_output = new AtomicInteger(0);

        Mockito.when(color[0].toString()).thenReturn("default");
        Mockito.when(input.toString()).thenReturn("input");
        Mockito.when(output.toString()).thenReturn("output");

        TextGraphics graphics = Mockito.mock(TextGraphics.class);

        Mockito.when(graphics.getForegroundColor()).thenReturn(color[0]);

        Mockito.when(tileView.getInputColor()).thenReturn(input);
        Mockito.when(tileView.getOutputColor()).thenReturn(output);


        Mockito.doAnswer((Answer<Void>) invocation -> {
            color[0] = invocation.getArgument(0);
            return null;
        }).when(graphics).setForegroundColor(Mockito.any(TextColor.class));

        Mockito.doAnswer((Answer<Void>) invocation -> {
            color[0] = invocation.getArgument(0);
            applied_input.incrementAndGet();
            return null;
        }).when(graphics).setForegroundColor(input);

        Mockito.doAnswer((Answer<Void>) invocation -> {
            color[0] = invocation.getArgument(0);
            applied_output.incrementAndGet();
            return null;
        }).when(graphics).setForegroundColor(output);

        Tile tile = Mockito.mock(Tile.class);

        Mockito.when(tile.acceptsPower(Side.UP)).thenReturn(false);
        Mockito.when(tile.outputsPower(Side.UP)).thenReturn(true);

        Mockito.when(tile.acceptsPower(Side.LEFT)).thenReturn(true);
        Mockito.when(tile.outputsPower(Side.LEFT)).thenReturn(false);

        Mockito.when(tile.acceptsPower(Side.DOWN)).thenReturn(false);
        Mockito.when(tile.outputsPower(Side.DOWN)).thenReturn(false);

        Mockito.when(tile.acceptsPower(Side.RIGHT)).thenReturn(true);
        Mockito.when(tile.outputsPower(Side.RIGHT)).thenReturn(false);

        tileView.renderPowerSensitiveFrame(graphics, tile, -1, 0);

        Assertions.assertEquals(2, applied_input.get());
        Assertions.assertEquals(1, applied_output.get());
        Assertions.assertEquals("default", color[0].toString());

        Mockito.verify(graphics, Mockito.times(1)).getForegroundColor();
        Mockito.verify(graphics, Mockito.times(1))
                .putString(Mockito.eq(-1), Mockito.eq(0), Mockito.anyString());
        Mockito.verify(graphics, Mockito.times(1))
                .putString(Mockito.eq(-1), Mockito.eq(1), Mockito.anyString());
        Mockito.verify(graphics, Mockito.times(1))
                .putString(Mockito.eq(-1), Mockito.eq(2), Mockito.anyString());

        Mockito.verify(tile, Mockito.times(1)).acceptsPower(Side.UP);
        Mockito.verify(tile, Mockito.times(1)).acceptsPower(Side.LEFT);
        Mockito.verify(tile, Mockito.times(1)).acceptsPower(Side.DOWN);
        Mockito.verify(tile, Mockito.times(1)).acceptsPower(Side.RIGHT);

        Mockito.verify(tile, Mockito.times(1)).outputsPower(Side.UP);
        Mockito.verify(tile, Mockito.times(0)).outputsPower(Side.LEFT);
        Mockito.verify(tile, Mockito.times(1)).outputsPower(Side.DOWN);
        Mockito.verify(tile, Mockito.times(0)).outputsPower(Side.RIGHT);

        Mockito.verify(graphics, Mockito.times(2)).setForegroundColor(input);
        Mockito.verify(graphics, Mockito.times(1)).setForegroundColor(output);

        Mockito.verify(graphics, Mockito.times(1))
                .setCharacter(Mockito.eq(0), Mockito.eq(0), Mockito.anyChar());
        Mockito.verify(graphics, Mockito.times(1))
                .setCharacter(Mockito.eq(-1), Mockito.eq(1), Mockito.anyChar());
        Mockito.verify(graphics, Mockito.times(0))
                .setCharacter(Mockito.eq(0), Mockito.eq(2), Mockito.anyChar());
        Mockito.verify(graphics, Mockito.times(1))
                .setCharacter(Mockito.eq(1), Mockito.eq(1), Mockito.anyChar());
    }

    @Test
    @Tag("view")
    @Tag("unit-test") @Tag("fast")
    public void testRenderPowerSensitiveFrameThird() {
        final TextColor[] color = {Mockito.mock(TextColor.class)};

        TextColor input = Mockito.mock(TextColor.class);
        TextColor output = Mockito.mock(TextColor.class);

        AtomicInteger applied_input = new AtomicInteger(0);

        AtomicInteger applied_output = new AtomicInteger(0);

        Mockito.when(color[0].toString()).thenReturn("default");
        Mockito.when(input.toString()).thenReturn("input");
        Mockito.when(output.toString()).thenReturn("output");

        TextGraphics graphics = Mockito.mock(TextGraphics.class);

        Mockito.when(graphics.getForegroundColor()).thenReturn(color[0]);

        Mockito.when(tileView.getInputColor()).thenReturn(input);
        Mockito.when(tileView.getOutputColor()).thenReturn(output);


        Mockito.doAnswer((Answer<Void>) invocation -> {
            color[0] = invocation.getArgument(0);
            return null;
        }).when(graphics).setForegroundColor(Mockito.any(TextColor.class));

        Mockito.doAnswer((Answer<Void>) invocation -> {
            color[0] = invocation.getArgument(0);
            applied_input.incrementAndGet();
            return null;
        }).when(graphics).setForegroundColor(input);

        Mockito.doAnswer((Answer<Void>) invocation -> {
            color[0] = invocation.getArgument(0);
            applied_output.incrementAndGet();
            return null;
        }).when(graphics).setForegroundColor(output);

        Tile tile = Mockito.mock(Tile.class);

        Mockito.when(tile.acceptsPower(Side.UP)).thenReturn(false);
        Mockito.when(tile.outputsPower(Side.UP)).thenReturn(false);

        Mockito.when(tile.acceptsPower(Side.LEFT)).thenReturn(false);
        Mockito.when(tile.outputsPower(Side.LEFT)).thenReturn(false);

        Mockito.when(tile.acceptsPower(Side.DOWN)).thenReturn(false);
        Mockito.when(tile.outputsPower(Side.DOWN)).thenReturn(true);

        Mockito.when(tile.acceptsPower(Side.RIGHT)).thenReturn(false);
        Mockito.when(tile.outputsPower(Side.RIGHT)).thenReturn(true);

        tileView.renderPowerSensitiveFrame(graphics, tile, 0, -1);

        Assertions.assertEquals(0, applied_input.get());
        Assertions.assertEquals(2, applied_output.get());
        Assertions.assertEquals("default", color[0].toString());

        Mockito.verify(graphics, Mockito.times(1)).getForegroundColor();
        Mockito.verify(graphics, Mockito.times(1))
                .putString(Mockito.eq(0), Mockito.eq(-1), Mockito.anyString());
        Mockito.verify(graphics, Mockito.times(1))
                .putString(Mockito.eq(0), Mockito.eq(0), Mockito.anyString());
        Mockito.verify(graphics, Mockito.times(1))
                .putString(Mockito.eq(0), Mockito.eq(1), Mockito.anyString());

        Mockito.verify(tile, Mockito.times(1)).acceptsPower(Side.UP);
        Mockito.verify(tile, Mockito.times(1)).acceptsPower(Side.LEFT);
        Mockito.verify(tile, Mockito.times(1)).acceptsPower(Side.DOWN);
        Mockito.verify(tile, Mockito.times(1)).acceptsPower(Side.RIGHT);

        Mockito.verify(tile, Mockito.times(1)).outputsPower(Side.UP);
        Mockito.verify(tile, Mockito.times(1)).outputsPower(Side.LEFT);
        Mockito.verify(tile, Mockito.times(1)).outputsPower(Side.DOWN);
        Mockito.verify(tile, Mockito.times(1)).outputsPower(Side.RIGHT);

        Mockito.verify(graphics, Mockito.times(0)).setForegroundColor(input);
        Mockito.verify(graphics, Mockito.times(2)).setForegroundColor(output);

        Mockito.verify(graphics, Mockito.times(0))
                .setCharacter(Mockito.eq(1), Mockito.eq(-1), Mockito.anyChar());
        Mockito.verify(graphics, Mockito.times(0))
                .setCharacter(Mockito.eq(0), Mockito.eq(0), Mockito.anyChar());
        Mockito.verify(graphics, Mockito.times(1))
                .setCharacter(Mockito.eq(1), Mockito.eq(1), Mockito.anyChar());
        Mockito.verify(graphics, Mockito.times(1))
                .setCharacter(Mockito.eq(2), Mockito.eq(0), Mockito.anyChar());
    }
}
