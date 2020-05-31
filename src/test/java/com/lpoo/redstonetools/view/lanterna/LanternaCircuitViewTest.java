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
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.io.IOException;

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
    }

    @Test
    @Tag("view")
    @Tag("unit-test") @Tag("fast")
    public void testToggleShowPoer() {
        try {
            view.toggleShowPower();
        } catch (Exception e) {
            e.printStackTrace();
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
        Assumptions.assumeTrue(view.getSelectedTile().getX() == 0);
        Assumptions.assumeTrue(view.getSelectedTile().getY() == 0);
        Assumptions.assumeTrue(view.getViewWindow().getX() == 0);
        Assumptions.assumeTrue(view.getViewWindow().getY() == 0);

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

        view.showInsertMenu(position);

        Assertions.assertTrue(view.inMenu());

        Mockito.verify(builder, Mockito.times(1))
                .addInsertMenu(Mockito.eq(position), Mockito.any(), Mockito.any(Runnable.class));
    }

    @Test
    @Tag("view")
    @Tag("unit-test") @Tag("fast")
    public void testShowInsertGateMenu() {

        Assumptions.assumeFalse(view.inMenu());

        Position position = Mockito.mock(Position.class);

        view.showInsertGateMenu(position);

        Assertions.assertTrue(view.inMenu());

        Mockito.verify(builder, Mockito.times(1))
                .addInsertGateMenu(Mockito.eq(position), Mockito.any(), Mockito.any(Runnable.class));
    }

    @Test
    @Tag("view")
    @Tag("unit-test") @Tag("fast")
    public void testShowInsertCustomMenu() {

        Assumptions.assumeFalse(view.inMenu());

        Position position = Mockito.mock(Position.class);

        view.showInsertCustomMenu(position);

        Assertions.assertTrue(view.inMenu());

        Mockito.verify(builder, Mockito.times(1))
                .addInsertCustomMenu(Mockito.eq(position), Mockito.any(), Mockito.any(Runnable.class));
    }

    @Test
    @Tag("view")
    @Tag("unit-test") @Tag("fast")
    public void testShowSaveCircuitMenu() {
        Mockito.when(circuit.getCircuitName()).thenReturn("default");

        Assumptions.assumeFalse(view.inMenu());

        view.showSaveCircuitMenu();

        Assertions.assertTrue(view.inMenu());

        Mockito.verify(builder, Mockito.times(1))
                .addSaveCircuitMenu(Mockito.any(), Mockito.eq("default"), Mockito.any(Runnable.class));
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

        Position position = Mockito.mock(Position.class);

        Mockito.when(tile.getType()).thenReturn(TileType.TIMER);
        Mockito.when(tile.getDelay()).thenReturn(5L);

        Mockito.when(circuit.getTile(Mockito.any(Position.class))).thenReturn(tile);

        Assumptions.assumeFalse(view.inMenu());

        view.showSetDelayMenu(position);

        Assertions.assertTrue(view.inMenu());

        Mockito.verify(circuit, Mockito.times(1)).getTile(position);
        Mockito.verify(tile, Mockito.times(1)).getType();

        Mockito.verify(builder, Mockito.times(1))
                .addNumberInput(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.eq(5L), Mockito.any(Runnable.class));
    }

    @Test
    @Tag("view")
    @Tag("unit-test") @Tag("fast")
    public void testShowSetDelayMenuCounter() {
        CounterTile tile = Mockito.mock(CounterTile.class);

        Position position = Mockito.mock(Position.class);

        Mockito.when(tile.getType()).thenReturn(TileType.COUNTER);
        Mockito.when(tile.getDelay()).thenReturn(3L);

        Mockito.when(circuit.getTile(Mockito.any(Position.class))).thenReturn(tile);

        Assumptions.assumeFalse(view.inMenu());

        view.showSetDelayMenu(position);

        Assertions.assertTrue(view.inMenu());

        Mockito.verify(circuit, Mockito.times(1)).getTile(position);
        Mockito.verify(tile, Mockito.times(1)).getType();

        Mockito.verify(builder, Mockito.times(1))
                .addNumberInput(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.eq(3L), Mockito.any(Runnable.class));
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
