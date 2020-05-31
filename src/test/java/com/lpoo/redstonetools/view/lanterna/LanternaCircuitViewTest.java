package com.lpoo.redstonetools.view.lanterna;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.screen.Screen;
import com.lpoo.redstonetools.controller.event.InputEvent;
import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.model.tile.CounterTile;
import com.lpoo.redstonetools.model.tile.Tile;
import com.lpoo.redstonetools.model.tile.TimerTile;
import com.lpoo.redstonetools.model.utils.Position;
import com.lpoo.redstonetools.model.utils.Side;
import com.lpoo.redstonetools.model.utils.TileType;
import com.lpoo.redstonetools.view.SaveCircuitListener;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class LanternaCircuitViewTest {

    private Screen screen;
    private MultiWindowTextGUI textGUI;
    private LanternaMenuBuilder builder;
    private Circuit circuit;

    private LanternaCircuitView view;

    @BeforeEach
    public void setup() {
        this.screen = Mockito.mock(Screen.class);

        TerminalSize terminalSize = Mockito.mock(TerminalSize.class);

        Mockito.when(screen.getTerminalSize()).thenReturn(terminalSize);
        Mockito.when(terminalSize.getRows()).thenReturn(20);
        Mockito.when(terminalSize.getColumns()).thenReturn(30);

        this.textGUI = Mockito.mock(MultiWindowTextGUI.class);
        this.builder = Mockito.mock(LanternaMenuBuilder.class);
        Mockito.when(builder.getTextGUI()).thenReturn(this.textGUI);

        this.circuit = Mockito.mock(Circuit.class);
        Mockito.when(circuit.getWidth()).thenReturn(25);
        Mockito.when(circuit.getHeight()).thenReturn(10);

        this.view = new LanternaCircuitView(screen, builder, circuit);
        // stop threads
        this.view.cleanup();
        Assertions.assertTrue(view.getEvents().isEmpty());
        Assertions.assertFalse(view.inMenu());
        Assertions.assertEquals(screen, view.getScreen());
    }

    @Test
    @Tag("view")
    @Tag("unit-test") @Tag("fast")
    public void testViewWindowCentering() {
        Screen screen = Mockito.mock(Screen.class);

        TerminalSize terminalSize = Mockito.mock(TerminalSize.class);

        Mockito.when(screen.getTerminalSize()).thenReturn(terminalSize);
        Mockito.when(terminalSize.getRows()).thenReturn(50);
        Mockito.when(terminalSize.getColumns()).thenReturn(30);

        MultiWindowTextGUI textGUI = Mockito.mock(MultiWindowTextGUI.class);
        LanternaMenuBuilder builder = Mockito.mock(LanternaMenuBuilder.class);
        Mockito.when(builder.getTextGUI()).thenReturn(this.textGUI);

        Circuit circuit = Mockito.mock(Circuit.class);
        Mockito.when(circuit.getWidth()).thenReturn(6);
        Mockito.when(circuit.getHeight()).thenReturn(10);

        LanternaCircuitView view = new LanternaCircuitView(screen, builder, circuit);
        // stop threads
        view.cleanup();
        Assertions.assertTrue(view.getEvents().isEmpty());

        Assertions.assertEquals(-2, view.getViewWindow().getX());
        Assertions.assertEquals(-3, view.getViewWindow().getY());
    }

    @Test
    @Tag("view")
    @Tag("unit-test") @Tag("fast")
    public void testViewWindowCentering2() {
        Screen screen = Mockito.mock(Screen.class);

        TerminalSize terminalSize = Mockito.mock(TerminalSize.class);

        Mockito.when(screen.getTerminalSize()).thenReturn(terminalSize);
        Mockito.when(terminalSize.getRows()).thenReturn(50);
        Mockito.when(terminalSize.getColumns()).thenReturn(30);

        MultiWindowTextGUI textGUI = Mockito.mock(MultiWindowTextGUI.class);
        LanternaMenuBuilder builder = Mockito.mock(LanternaMenuBuilder.class);
        Mockito.when(builder.getTextGUI()).thenReturn(this.textGUI);

        Circuit circuit = Mockito.mock(Circuit.class);
        Mockito.when(circuit.getWidth()).thenReturn(20);
        Mockito.when(circuit.getHeight()).thenReturn(20);

        LanternaCircuitView view = new LanternaCircuitView(screen, builder, circuit);
        // stop threads
        view.cleanup();
        Assertions.assertTrue(view.getEvents().isEmpty());

        Assertions.assertEquals(0, view.getViewWindow().getX());
        Assertions.assertEquals(0, view.getViewWindow().getY());
    }

    @Test
    @Tag("view")
    @Tag("unit-test") @Tag("fast")
    public void testViewWindowCentering3() {
        Screen screen = Mockito.mock(Screen.class);

        TerminalSize terminalSize = Mockito.mock(TerminalSize.class);

        Mockito.when(screen.getTerminalSize()).thenReturn(terminalSize);
        Mockito.when(terminalSize.getRows()).thenReturn(30);
        Mockito.when(terminalSize.getColumns()).thenReturn(30);

        MultiWindowTextGUI textGUI = Mockito.mock(MultiWindowTextGUI.class);
        LanternaMenuBuilder builder = Mockito.mock(LanternaMenuBuilder.class);
        Mockito.when(builder.getTextGUI()).thenReturn(this.textGUI);

        Circuit circuit = Mockito.mock(Circuit.class);
        Mockito.when(circuit.getWidth()).thenReturn(10);
        Mockito.when(circuit.getHeight()).thenReturn(10);

        LanternaCircuitView view = new LanternaCircuitView(screen, builder, circuit);
        // stop threads
        view.cleanup();
        Assertions.assertTrue(view.getEvents().isEmpty());

        Assertions.assertEquals(0, view.getViewWindow().getX());
        Assertions.assertEquals(0, view.getViewWindow().getY());
    }

    @Test
    @Tag("view")
    @Tag("unit-test") @Tag("fast")
    public void testViewWindowCentering4() {
        Screen screen = Mockito.mock(Screen.class);

        TerminalSize terminalSize = Mockito.mock(TerminalSize.class);

        Mockito.when(screen.getTerminalSize()).thenReturn(terminalSize);
        Mockito.when(terminalSize.getRows()).thenReturn(50);
        Mockito.when(terminalSize.getColumns()).thenReturn(30);

        MultiWindowTextGUI textGUI = Mockito.mock(MultiWindowTextGUI.class);
        LanternaMenuBuilder builder = Mockito.mock(LanternaMenuBuilder.class);
        Mockito.when(builder.getTextGUI()).thenReturn(this.textGUI);

        Circuit circuit = Mockito.mock(Circuit.class);
        Mockito.when(circuit.getWidth()).thenReturn(20);
        Mockito.when(circuit.getHeight()).thenReturn(10);

        LanternaCircuitView view = new LanternaCircuitView(screen, builder, circuit);
        // stop threads
        view.cleanup();
        Assertions.assertTrue(view.getEvents().isEmpty());

        Assertions.assertEquals(0, view.getViewWindow().getX());
        Assertions.assertEquals(-3, view.getViewWindow().getY());
    }

    @Test
    @Tag("view")
    @Tag("unit-test") @Tag("fast")
    public void testToggleShowPoer() {
        try {
            view.toggleShowPower();
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    @Tag("view")
    @Tag("unit-test") @Tag("fast")
    public void testMoveSelectedTile() {
        Assumptions.assumeTrue(view.getSelectedTile().getX() == 0);
        Assumptions.assumeTrue(view.getSelectedTile().getY() == 0);
        Assumptions.assumeTrue(view.getViewWindow().getX() == 0);
        Assumptions.assumeTrue(view.getViewWindow().getY() == 0);

        view.moveSelectedTile(Side.LEFT);
        Assertions.assertEquals(0, view.getSelectedTile().getX());
        Assertions.assertEquals(0, view.getSelectedTile().getY());
        Assertions.assertEquals(0, view.getViewWindow().getX());
        Assertions.assertEquals(0, view.getViewWindow().getY());

        view.moveSelectedTile(Side.RIGHT);
        Assertions.assertEquals(1, view.getSelectedTile().getX());
        Assertions.assertEquals(0, view.getSelectedTile().getY());
        Assertions.assertEquals(0, view.getViewWindow().getX());
        Assertions.assertEquals(0, view.getViewWindow().getY());

        view.moveSelectedTile(Side.UP);
        Assertions.assertEquals(1, view.getSelectedTile().getX());
        Assertions.assertEquals(0, view.getSelectedTile().getY());
        Assertions.assertEquals(0, view.getViewWindow().getX());
        Assertions.assertEquals(0, view.getViewWindow().getY());

        view.moveSelectedTile(Side.DOWN);
        Assertions.assertEquals(1, view.getSelectedTile().getX());
        Assertions.assertEquals(1, view.getSelectedTile().getY());
        Assertions.assertEquals(0, view.getViewWindow().getX());
        Assertions.assertEquals(0, view.getViewWindow().getY());

        view.moveSelectedTile(Side.LEFT);
        Assertions.assertEquals(0, view.getSelectedTile().getX());
        Assertions.assertEquals(1, view.getSelectedTile().getY());
        Assertions.assertEquals(0, view.getViewWindow().getX());
        Assertions.assertEquals(0, view.getViewWindow().getY());

        view.moveSelectedTile(Side.LEFT);
        Assertions.assertEquals(0, view.getSelectedTile().getX());
        Assertions.assertEquals(1, view.getSelectedTile().getY());
        Assertions.assertEquals(0, view.getViewWindow().getX());
        Assertions.assertEquals(0, view.getViewWindow().getY());
    }

    @Test
    @Tag("view")
    @Tag("unit-test") @Tag("fast")
    public void testMoveWindow() {
        Assumptions.assumeTrue(view.getSelectedTile().getX() == 0);
        Assumptions.assumeTrue(view.getSelectedTile().getY() == 0);
        Assumptions.assumeTrue(view.getViewWindow().getX() == 0);
        Assumptions.assumeTrue(view.getViewWindow().getY() == 0);

        view.moveViewWindow(Side.LEFT);
        Assertions.assertEquals(0, view.getSelectedTile().getX());
        Assertions.assertEquals(0, view.getSelectedTile().getY());
        Assertions.assertEquals(-1, view.getViewWindow().getX());
        Assertions.assertEquals(0, view.getViewWindow().getY());

        view.moveViewWindow(Side.RIGHT);
        Assertions.assertEquals(0, view.getSelectedTile().getX());
        Assertions.assertEquals(0, view.getSelectedTile().getY());
        Assertions.assertEquals(0, view.getViewWindow().getX());
        Assertions.assertEquals(0, view.getViewWindow().getY());

        view.moveViewWindow(Side.UP);
        Assertions.assertEquals(0, view.getSelectedTile().getX());
        Assertions.assertEquals(0, view.getSelectedTile().getY());
        Assertions.assertEquals(0, view.getViewWindow().getX());
        Assertions.assertEquals(-1, view.getViewWindow().getY());

        view.moveViewWindow(Side.DOWN);
        Assertions.assertEquals(0, view.getSelectedTile().getX());
        Assertions.assertEquals(0, view.getSelectedTile().getY());
        Assertions.assertEquals(0, view.getViewWindow().getX());
        Assertions.assertEquals(0, view.getViewWindow().getY());

        view.moveViewWindow(Side.LEFT);
        Assertions.assertEquals(0, view.getSelectedTile().getX());
        Assertions.assertEquals(0, view.getSelectedTile().getY());
        Assertions.assertEquals(-1, view.getViewWindow().getX());
        Assertions.assertEquals(0, view.getViewWindow().getY());

        view.moveViewWindow(Side.LEFT);
        Assertions.assertEquals(0, view.getSelectedTile().getX());
        Assertions.assertEquals(0, view.getSelectedTile().getY());
        Assertions.assertEquals(-2, view.getViewWindow().getX());
        Assertions.assertEquals(0, view.getViewWindow().getY());
    }

    @Test
    @Tag("view")
    @Tag("unit-test") @Tag("fast")
    public void testAdvancedMoving() {
        Assertions.assertTrue(view.getSelectedTile().getX() == 0);
        Assertions.assertTrue(view.getSelectedTile().getY() == 0);
        Assertions.assertTrue(view.getViewWindow().getX() == 0);
        Assertions.assertTrue(view.getViewWindow().getY() == 0);

        view.moveViewWindow(Side.RIGHT);
        Assertions.assertEquals(1, view.getSelectedTile().getX());
        Assertions.assertEquals(0, view.getSelectedTile().getY());
        Assertions.assertEquals(1, view.getViewWindow().getX());
        Assertions.assertEquals(0, view.getViewWindow().getY());

        view.moveSelectedTile(Side.DOWN);
        Assertions.assertEquals(1, view.getSelectedTile().getX());
        Assertions.assertEquals(1, view.getSelectedTile().getY());
        Assertions.assertEquals(1, view.getViewWindow().getX());
        Assertions.assertEquals(0, view.getViewWindow().getY());

        view.moveViewWindow(Side.DOWN);
        Assertions.assertEquals(1, view.getSelectedTile().getX());
        Assertions.assertEquals(1, view.getSelectedTile().getY());
        Assertions.assertEquals(1, view.getViewWindow().getX());
        Assertions.assertEquals(1, view.getViewWindow().getY());

        view.moveViewWindow(Side.DOWN);
        Assertions.assertEquals(1, view.getSelectedTile().getX());
        Assertions.assertEquals(2, view.getSelectedTile().getY());
        Assertions.assertEquals(1, view.getViewWindow().getX());
        Assertions.assertEquals(2, view.getViewWindow().getY());

        view.moveSelectedTile(Side.UP);
        Assertions.assertEquals(1, view.getSelectedTile().getX());
        Assertions.assertEquals(1, view.getSelectedTile().getY());
        Assertions.assertEquals(1, view.getViewWindow().getX());
        Assertions.assertEquals(1, view.getViewWindow().getY());

        view.moveSelectedTile(Side.LEFT);
        Assertions.assertEquals(0, view.getSelectedTile().getX());
        Assertions.assertEquals(1, view.getSelectedTile().getY());
        Assertions.assertEquals(0, view.getViewWindow().getX());
        Assertions.assertEquals(1, view.getViewWindow().getY());

        view.moveSelectedTile(Side.LEFT);
        Assertions.assertEquals(0, view.getSelectedTile().getX());
        Assertions.assertEquals(1, view.getSelectedTile().getY());
        Assertions.assertEquals(0, view.getViewWindow().getX());
        Assertions.assertEquals(1, view.getViewWindow().getY());

        for (int i = 0; i < 25; i++) {
            view.moveSelectedTile(Side.RIGHT);
        }

        Assertions.assertEquals(24, view.getSelectedTile().getX());
        Assertions.assertEquals(1, view.getSelectedTile().getY());
        Assertions.assertEquals(15, view.getViewWindow().getX());
        Assertions.assertEquals(1, view.getViewWindow().getY());

        view.moveSelectedTile(Side.RIGHT);

        Assertions.assertEquals(24, view.getSelectedTile().getX());
        Assertions.assertEquals(1, view.getSelectedTile().getY());
        Assertions.assertEquals(15, view.getViewWindow().getX());
        Assertions.assertEquals(1, view.getViewWindow().getY());

        Mockito.when(circuit.getHeight()).thenReturn(12);

        view.moveSelectedTile(Side.UP);

        Assertions.assertEquals(24, view.getSelectedTile().getX());
        Assertions.assertEquals(0, view.getSelectedTile().getY());
        Assertions.assertEquals(15, view.getViewWindow().getX());
        Assertions.assertEquals(0, view.getViewWindow().getY());

        for (int i = 0; i < 25; i++) {
            view.moveSelectedTile(Side.DOWN);
        }

        Assertions.assertEquals(24, view.getSelectedTile().getX());
        Assertions.assertEquals(11, view.getSelectedTile().getY());
        Assertions.assertEquals(15, view.getViewWindow().getX());
        Assertions.assertEquals(6, view.getViewWindow().getY());
    }

    @Test
    @Tag("view")
    @Tag("unit-test") @Tag("fast")
    public void testAdvancedMoving2() {
        Assertions.assertTrue(view.getSelectedTile().getX() == 0);
        Assertions.assertTrue(view.getSelectedTile().getY() == 0);
        Assertions.assertTrue(view.getViewWindow().getX() == 0);
        Assertions.assertTrue(view.getViewWindow().getY() == 0);

        for (int i = 0; i < 10; i++) {
            view.moveViewWindow(Side.LEFT);
        }

        Assertions.assertEquals(0, view.getSelectedTile().getX());
        Assertions.assertEquals(0, view.getSelectedTile().getY());
        Assertions.assertEquals(-9, view.getViewWindow().getX());
        Assertions.assertEquals(0, view.getViewWindow().getY());

        for (int i = 0; i < 34; i++) {
            view.moveViewWindow(Side.RIGHT);
        }

        Assertions.assertEquals(24, view.getSelectedTile().getX());
        Assertions.assertEquals(0, view.getSelectedTile().getY());
        Assertions.assertEquals(24, view.getViewWindow().getX());
        Assertions.assertEquals(0, view.getViewWindow().getY());

        for (int i = 0; i < 6; i++) {
            view.moveViewWindow(Side.UP);
        }

        Assertions.assertEquals(24, view.getSelectedTile().getX());
        Assertions.assertEquals(0, view.getSelectedTile().getY());
        Assertions.assertEquals(24, view.getViewWindow().getX());
        Assertions.assertEquals(-5, view.getViewWindow().getY());

        for (int i = 0; i < 17; i++) {
            view.moveViewWindow(Side.DOWN);
        }

        Assertions.assertEquals(24, view.getSelectedTile().getX());
        Assertions.assertEquals(9, view.getSelectedTile().getY());
        Assertions.assertEquals(24, view.getViewWindow().getX());
        Assertions.assertEquals(9, view.getViewWindow().getY());

    }

    @Test
    @Tag("view")
    @Tag("unit-test") @Tag("fast")
    public void testShowHelpMenu() {

        Assumptions.assumeFalse(view.inMenu());

        view.showHelpMenu();

        Assertions.assertTrue(view.inMenu());

        Mockito.verify(builder, Mockito.times(1)).addHelpWindow(Mockito.any(Runnable.class));
    }

    @Test
    @Tag("view")
    @Tag("unit-test") @Tag("fast")
    public void testShowInsertMenu() {

        Assumptions.assumeFalse(view.inMenu());

        Position position = Mockito.mock(Position.class);

        Tile tile = Mockito.mock(Tile.class);
        
        AtomicReference<Runnable> run = new AtomicReference<>();

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Assertions.assertEquals(position, invocation.getArgument(0));
            Consumer<Tile> c = invocation.getArgument(1);
            c.accept(tile);
            run.set(invocation.getArgument(2));

            return null;
        }).when(builder).addInsertMenu(Mockito.any(Position.class), Mockito.any(), Mockito.any(Runnable.class));

        view.showInsertMenu(position);

        Assertions.assertTrue(view.inMenu());

        Mockito.verify(builder, Mockito.times(1))
                .addInsertMenu(Mockito.eq(position), Mockito.any(), Mockito.any(Runnable.class));

        Assertions.assertEquals(1, view.getEvents().size());
        Assertions.assertEquals(InputEvent.ADD_TILE, view.getEvents().peek().getInputEvent());
        Assertions.assertEquals(tile, view.getEvents().peek().getObject());

        run.get().run();

        Assertions.assertFalse(view.inMenu());
    }

    @Test
    @Tag("view")
    @Tag("unit-test") @Tag("fast")
    public void testShowInsertGateMenu() {

        Assumptions.assumeFalse(view.inMenu());

        Position position = Mockito.mock(Position.class);

        Tile tile = Mockito.mock(Tile.class);

        AtomicReference<Runnable> run = new AtomicReference<>();

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Assertions.assertEquals(position, invocation.getArgument(0));
            Consumer<Tile> c = invocation.getArgument(1);
            c.accept(tile);
            run.set(invocation.getArgument(2));

            return null;
        }).when(builder).addInsertGateMenu(Mockito.any(Position.class), Mockito.any(), Mockito.any(Runnable.class));

        view.showInsertGateMenu(position);

        Assertions.assertTrue(view.inMenu());

        Mockito.verify(builder, Mockito.times(1))
                .addInsertGateMenu(Mockito.eq(position), Mockito.any(), Mockito.any(Runnable.class));

        Assertions.assertEquals(1, view.getEvents().size());
        Assertions.assertEquals(InputEvent.ADD_TILE, view.getEvents().peek().getInputEvent());
        Assertions.assertEquals(tile, view.getEvents().peek().getObject());

        run.get().run();

        Assertions.assertFalse(view.inMenu());
    }

    @Test
    @Tag("view")
    @Tag("unit-test") @Tag("fast")
    public void testShowInsertCustomMenu() {

        Assumptions.assumeFalse(view.inMenu());

        Position position = Mockito.mock(Position.class);

        Tile tile = Mockito.mock(Tile.class);

        AtomicReference<Runnable> run = new AtomicReference<>();

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Assertions.assertEquals(position, invocation.getArgument(0));
            Consumer<Tile> c = invocation.getArgument(1);
            c.accept(tile);
            run.set(invocation.getArgument(2));

            return null;
        }).when(builder).addInsertCustomMenu(Mockito.any(Position.class), Mockito.any(), Mockito.any(Runnable.class));

        view.showInsertCustomMenu(position);

        Assertions.assertTrue(view.inMenu());

        Mockito.verify(builder, Mockito.times(1))
                .addInsertCustomMenu(Mockito.eq(position), Mockito.any(), Mockito.any(Runnable.class));

        Assertions.assertEquals(1, view.getEvents().size());
        Assertions.assertEquals(InputEvent.ADD_TILE, view.getEvents().peek().getInputEvent());
        Assertions.assertEquals(tile, view.getEvents().peek().getObject());

        run.get().run();

        Assertions.assertFalse(view.inMenu());
    }

    @Test
    @Tag("view")
    @Tag("unit-test") @Tag("fast")
    public void testShowSaveCircuitMenu() {
        Mockito.when(circuit.getCircuitName()).thenReturn("default");

        Assumptions.assumeFalse(view.inMenu());

        SaveCircuitListener listener = Mockito.mock(SaveCircuitListener.class);

        AtomicReference<Runnable> run = new AtomicReference<>();

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Consumer<SaveCircuitListener> c = invocation.getArgument(0);
            c.accept(listener);

            Assertions.assertEquals("default", invocation.getArgument(1));

            run.set(invocation.getArgument(2));

            return null;
        }).when(builder).addSaveCircuitMenu(Mockito.any(), Mockito.anyString(), Mockito.any(Runnable.class));

        view.showSaveCircuitMenu();

        Assertions.assertTrue(view.inMenu());

        Mockito.verify(builder, Mockito.times(1))
                .addSaveCircuitMenu(Mockito.any(), Mockito.eq("default"), Mockito.any(Runnable.class));

        Assertions.assertEquals(1, view.getEvents().size());
        Assertions.assertEquals(InputEvent.SAVE, view.getEvents().peek().getInputEvent());
        Assertions.assertEquals(listener, view.getEvents().peek().getObject());

        run.get().run();

        Assertions.assertFalse(view.inMenu());
    }

    @Test
    @Tag("view")
    @Tag("unit-test") @Tag("fast")
    public void testShowTileInfo() {
        Tile tile = Mockito.mock(Tile.class);

        Position position = Mockito.mock(Position.class);

        Mockito.when(tile.getInfo()).thenReturn("default info");
        Mockito.when(circuit.getTile(Mockito.any(Position.class))).thenReturn(tile);

        Assumptions.assumeFalse(view.inMenu());
        // Empty tile
        Mockito.when(tile.getType()).thenReturn(TileType.NULL);

        view.showTileInfo(position);

        Assertions.assertFalse(view.inMenu());

        Mockito.verify(circuit, Mockito.times(1)).getTile(position);

        Mockito.verify(builder, Mockito.times(0))
                .addConfirmation(Mockito.anyString(), Mockito.any(Runnable.class));

        // Non empty tile
        Mockito.when(tile.getType()).thenReturn(TileType.COMPARATOR);

        view.showTileInfo(position);

        Assertions.assertTrue(view.inMenu());

        Mockito.verify(circuit, Mockito.times(2)).getTile(position);
        Mockito.verify(circuit, Mockito.times(2)).getTile(Mockito.any(Position.class));

        Mockito.verify(builder, Mockito.times(1))
                .addConfirmation(Mockito.eq("default info"), Mockito.any(Runnable.class));
    }

    @Test
    @Tag("view")
    @Tag("unit-test") @Tag("fast")
    public void testShowSetDelayMenuInvalid() {
        Tile tile = Mockito.mock(Tile.class);

        Position position = Mockito.mock(Position.class);

        Mockito.when(tile.getType()).thenReturn(TileType.REPEATER);

        Mockito.when(circuit.getTile(Mockito.any(Position.class))).thenReturn(tile);

        Assumptions.assumeFalse(view.inMenu());

        view.showSetDelayMenu(position);

        Assertions.assertFalse(view.inMenu());

        Mockito.verify(circuit, Mockito.times(1)).getTile(position);
        Mockito.verify(tile, Mockito.times(1)).getType();

        Mockito.verify(builder, Mockito.times(0))
                .addNumberInput(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyLong(), Mockito.any(Runnable.class));
    }

    @Test
    @Tag("view")
    @Tag("unit-test") @Tag("fast")
    public void testShowSetDelayMenuTimer() {
        TimerTile tile = Mockito.mock(TimerTile.class);

        Mockito.when(tile.getType()).thenReturn(TileType.TIMER);
        Mockito.when(tile.getDelay()).thenReturn(5L);

        Position position = Mockito.mock(Position.class);

        AtomicReference<Runnable> run = new AtomicReference<>();

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Consumer<Long> c = invocation.getArgument(0);
            c.accept(invocation.getArgument(3));

            Assertions.assertEquals(5L, (Long)invocation.getArgument(3));

            run.set(invocation.getArgument(4));

            return null;
        }).when(builder).addNumberInput(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyLong(), Mockito.any(Runnable.class));

        Mockito.when(circuit.getTile(Mockito.any(Position.class))).thenReturn(tile);

        Assumptions.assumeFalse(view.inMenu());

        view.showSetDelayMenu(position);

        Assertions.assertTrue(view.inMenu());

        Mockito.verify(circuit, Mockito.times(1)).getTile(position);
        Mockito.verify(tile, Mockito.times(1)).getType();

        Mockito.verify(builder, Mockito.times(1))
                .addNumberInput(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.eq(5L), Mockito.any(Runnable.class));

        Assertions.assertEquals(1, view.getEvents().size());
        Assertions.assertEquals(InputEvent.SET_DELAY, view.getEvents().peek().getInputEvent());

        Map.Entry<?, ?> entry = (Map.Entry<?, ?>) view.getEvents().peek().getObject();

        Assertions.assertEquals(position, entry.getKey());
        Assertions.assertEquals(5L, entry.getValue());

        run.get().run();

        Assertions.assertFalse(view.inMenu());
    }

    @Test
    @Tag("view")
    @Tag("unit-test") @Tag("fast")
    public void testShowSetDelayMenuCounter() {
        CounterTile tile = Mockito.mock(CounterTile.class);

        Mockito.when(tile.getType()).thenReturn(TileType.COUNTER);
        Mockito.when(tile.getDelay()).thenReturn(3L);

        Position position = Mockito.mock(Position.class);

        AtomicReference<Runnable> run = new AtomicReference<>();

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Consumer<Long> c = invocation.getArgument(0);
            c.accept(invocation.getArgument(3));

            Assertions.assertEquals(3L, (Long)invocation.getArgument(3));

            run.set(invocation.getArgument(4));

            return null;
        }).when(builder).addNumberInput(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyLong(), Mockito.any(Runnable.class));

        Mockito.when(circuit.getTile(Mockito.any(Position.class))).thenReturn(tile);

        Assumptions.assumeFalse(view.inMenu());

        view.showSetDelayMenu(position);

        Assertions.assertTrue(view.inMenu());

        Mockito.verify(circuit, Mockito.times(1)).getTile(position);
        Mockito.verify(tile, Mockito.times(1)).getType();

        Mockito.verify(builder, Mockito.times(1))
                .addNumberInput(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.eq(3L), Mockito.any(Runnable.class));

        Assertions.assertEquals(1, view.getEvents().size());
        Assertions.assertEquals(InputEvent.SET_DELAY, view.getEvents().peek().getInputEvent());

        Map.Entry<?, ?> entry = (Map.Entry<?, ?>) view.getEvents().peek().getObject();

        Assertions.assertEquals(position, entry.getKey());
        Assertions.assertEquals(3L, entry.getValue());

        run.get().run();

        Assertions.assertFalse(view.inMenu());
    }

    @Test
    @Tag("view")
    @Tag("unit-test") @Tag("fast")
    public void testRenderMenuNoException() {

        Assumptions.assumeTrue(view.getEvents().isEmpty());

        view.setInMenu(true);

        view.render();

        Assertions.assertTrue(view.getEvents().isEmpty());

        try {
            Mockito.verify(textGUI, Mockito.times(1)).processInput();
            Mockito.verify(textGUI, Mockito.times(1)).updateScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Mockito.verify(screen, Mockito.times(1)).doResizeIfNecessary();
        Mockito.verify(screen, Mockito.times(0)).clear();
        Mockito.verify(screen, Mockito.times(0)).newTextGraphics();
        try {
            Mockito.verify(screen, Mockito.times(0)).refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Tag("view")
    @Tag("unit-test") @Tag("fast")
    public void testRenderMenuOnExceptionProcessInput() {

        Assumptions.assumeTrue(view.getEvents().isEmpty());

        IOException exception = Mockito.mock(IOException.class);

        try {
            Mockito.when(textGUI.processInput()).thenThrow(exception);
        } catch (IOException e) {
            e.printStackTrace();
        }

        view.setInMenu(true);

        view.render();

        Assertions.assertEquals(1, view.getEvents().size());
        Assertions.assertEquals(InputEvent.QUIT, view.getEvents().peek().getInputEvent());

        try {
            Mockito.verify(textGUI, Mockito.times(1)).processInput();
            Mockito.verify(textGUI, Mockito.times(0)).updateScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Mockito.verify(screen, Mockito.times(1)).doResizeIfNecessary();
        Mockito.verify(screen, Mockito.times(0)).clear();
        Mockito.verify(screen, Mockito.times(0)).newTextGraphics();
        try {
            Mockito.verify(screen, Mockito.times(0)).refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Tag("view")
    @Tag("unit-test") @Tag("fast")
    public void testRenderMenuOnExceptionUpdateScreen() {

        Assumptions.assumeTrue(view.getEvents().isEmpty());

        IOException exception = Mockito.mock(IOException.class);

        try {
            Mockito.doThrow(exception).when(textGUI).updateScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }

        view.setInMenu(true);

        view.render();

        Assertions.assertEquals(1, view.getEvents().size());
        Assertions.assertEquals(InputEvent.QUIT, view.getEvents().peek().getInputEvent());

        try {
            Mockito.verify(textGUI, Mockito.times(1)).processInput();
            Mockito.verify(textGUI, Mockito.times(1)).updateScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Mockito.verify(screen, Mockito.times(1)).doResizeIfNecessary();
        Mockito.verify(screen, Mockito.times(0)).clear();
        Mockito.verify(screen, Mockito.times(0)).newTextGraphics();
        try {
            Mockito.verify(screen, Mockito.times(0)).refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
