package com.lpoo.redstonetools.view.lanterna.input;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.lpoo.redstonetools.controller.event.Event;
import com.lpoo.redstonetools.controller.event.InputEvent;
import com.lpoo.redstonetools.model.tile.*;
import com.lpoo.redstonetools.model.tile.strategy.LogicGateStrategyType;
import com.lpoo.redstonetools.model.utils.Position;
import com.lpoo.redstonetools.model.utils.Side;
import com.lpoo.redstonetools.view.lanterna.LanternaCircuitView;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class LanternaInputTest {

    @Test
    @Tag("view") @Tag("model")
    @Tag("integration-test") @Tag("fast")
    public void testInputPlus() {
        LanternaCircuitView view = Mockito.mock(LanternaCircuitView.class);

        Position position = Mockito.mock(Position.class);
        Position clone = Mockito.mock(Position.class);

        Mockito.when(view.getSelectedTile()).thenReturn(position);
        Mockito.when(position.clone()).thenReturn(clone);

        Mockito.when(view.inMenu()).thenReturn(false);

        Screen screen = Mockito.mock(Screen.class);
        Mockito.when(view.getScreen()).thenReturn(screen);

        KeyStroke key = Mockito.mock(KeyStroke.class);
        try {
            Mockito.when(screen.readInput()).thenReturn(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.when(key.getKeyType()).thenReturn(KeyType.Character);
        Mockito.when(key.getCharacter()).thenReturn('+');

        LanternaInput input = Mockito.mock(LanternaInput.class, Mockito.withSettings().useConstructor(view));

        Mockito.doCallRealMethod().when(input).run();

        Mockito.when(input.isInterrupted()).thenReturn(false);

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Event event = invocation.getArgument(0);
            Assertions.assertEquals(InputEvent.ADVANCE_TICK, event.getInputEvent());
            return null;
        }).when(view).pushEvent(Mockito.any(Event.class));

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Mockito.when(input.isInterrupted()).thenReturn(true);
            return null;
        }).when(view).inMenu();

        input.run();

        Mockito.verify(input, Mockito.times(2)).isInterrupted();
        Mockito.verify(view, Mockito.times(1)).getScreen();
        try {
            Mockito.verify(screen, Mockito.times(1)).readInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.verify(key, Mockito.times(1)).getKeyType();
        Mockito.verify(key, Mockito.times(1)).getCharacter();

        Mockito.verify(view, Mockito.times(1))
                .pushEvent(Mockito.any(Event.class));
        Mockito.verify(view, Mockito.times(1))
                .pushEvent(Mockito.any());

        Mockito.verify(view, Mockito.times(0)).getSelectedTile();
        Mockito.verify(position, Mockito.times(0)).clone();

        Mockito.verify(view, Mockito.times(0)).showSaveCircuitMenu();
        Mockito.verify(view, Mockito.times(0)).showInsertCustomMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showHelpMenu();
        Mockito.verify(view, Mockito.times(0)).showTileInfo(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showSetDelayMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleAutoAdvance();
        Mockito.verify(view, Mockito.times(0)).showInsertGateMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showInsertMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleShowPower();
        Mockito.verify(view, Mockito.times(0)).moveViewWindow(Mockito.any(Side.class));
        Mockito.verify(view, Mockito.times(0)).moveSelectedTile(Mockito.any(Side.class));
    }

    @Test
    @Tag("view") @Tag("model")
    @Tag("integration-test") @Tag("fast")
    public void testInputQ() {
        LanternaCircuitView view = Mockito.mock(LanternaCircuitView.class);

        Position position = Mockito.mock(Position.class);
        Position clone = Mockito.mock(Position.class);

        Mockito.when(view.getSelectedTile()).thenReturn(position);
        Mockito.when(position.clone()).thenReturn(clone);

        Mockito.when(view.inMenu()).thenReturn(false);

        Screen screen = Mockito.mock(Screen.class);
        Mockito.when(view.getScreen()).thenReturn(screen);

        KeyStroke key = Mockito.mock(KeyStroke.class);
        try {
            Mockito.when(screen.readInput()).thenReturn(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.when(key.getKeyType()).thenReturn(KeyType.Character);
        Mockito.when(key.getCharacter()).thenReturn('q');

        LanternaInput input = Mockito.mock(LanternaInput.class, Mockito.withSettings().useConstructor(view));

        Mockito.doCallRealMethod().when(input).run();

        Mockito.when(input.isInterrupted()).thenReturn(false);

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Event event = invocation.getArgument(0);
            Assertions.assertEquals(InputEvent.ROTATE_LEFT, event.getInputEvent());
            Assertions.assertEquals(clone, event.getObject());
            return null;
        }).when(view).pushEvent(Mockito.any(Event.class));

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Mockito.when(input.isInterrupted()).thenReturn(true);
            return null;
        }).when(view).inMenu();

        input.run();

        Mockito.verify(input, Mockito.times(2)).isInterrupted();
        Mockito.verify(view, Mockito.times(1)).getScreen();
        try {
            Mockito.verify(screen, Mockito.times(1)).readInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.verify(key, Mockito.times(1)).getKeyType();
        Mockito.verify(key, Mockito.times(1)).getCharacter();

        Mockito.verify(view, Mockito.times(1))
                .pushEvent(Mockito.any(Event.class));
        Mockito.verify(view, Mockito.times(1))
                .pushEvent(Mockito.any());

        Mockito.verify(view, Mockito.times(1)).getSelectedTile();
        Mockito.verify(position, Mockito.times(1)).clone();

        Mockito.verify(view, Mockito.times(0)).showSaveCircuitMenu();
        Mockito.verify(view, Mockito.times(0)).showInsertCustomMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showHelpMenu();
        Mockito.verify(view, Mockito.times(0)).showTileInfo(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showSetDelayMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleAutoAdvance();
        Mockito.verify(view, Mockito.times(0)).showInsertGateMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showInsertMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleShowPower();
        Mockito.verify(view, Mockito.times(0)).moveViewWindow(Mockito.any(Side.class));
        Mockito.verify(view, Mockito.times(0)).moveSelectedTile(Mockito.any(Side.class));
    }

    @Test
    @Tag("view") @Tag("model")
    @Tag("integration-test") @Tag("fast")
    public void testInputE() {
        LanternaCircuitView view = Mockito.mock(LanternaCircuitView.class);

        Position position = Mockito.mock(Position.class);
        Position clone = Mockito.mock(Position.class);

        Mockito.when(view.getSelectedTile()).thenReturn(position);
        Mockito.when(position.clone()).thenReturn(clone);

        Mockito.when(view.inMenu()).thenReturn(false);

        Screen screen = Mockito.mock(Screen.class);
        Mockito.when(view.getScreen()).thenReturn(screen);

        KeyStroke key = Mockito.mock(KeyStroke.class);
        try {
            Mockito.when(screen.readInput()).thenReturn(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.when(key.getKeyType()).thenReturn(KeyType.Character);
        Mockito.when(key.getCharacter()).thenReturn('e');

        LanternaInput input = Mockito.mock(LanternaInput.class, Mockito.withSettings().useConstructor(view));

        Mockito.doCallRealMethod().when(input).run();

        Mockito.when(input.isInterrupted()).thenReturn(false);

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Event event = invocation.getArgument(0);
            Assertions.assertEquals(InputEvent.ROTATE_RIGHT, event.getInputEvent());
            Assertions.assertEquals(clone, event.getObject());
            return null;
        }).when(view).pushEvent(Mockito.any(Event.class));

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Mockito.when(input.isInterrupted()).thenReturn(true);
            return null;
        }).when(view).inMenu();

        input.run();

        Mockito.verify(input, Mockito.times(2)).isInterrupted();
        Mockito.verify(view, Mockito.times(1)).getScreen();
        try {
            Mockito.verify(screen, Mockito.times(1)).readInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.verify(key, Mockito.times(1)).getKeyType();
        Mockito.verify(key, Mockito.times(1)).getCharacter();

        Mockito.verify(view, Mockito.times(1))
                .pushEvent(Mockito.any(Event.class));
        Mockito.verify(view, Mockito.times(1))
                .pushEvent(Mockito.any());

        Mockito.verify(view, Mockito.times(1)).getSelectedTile();
        Mockito.verify(position, Mockito.times(1)).clone();

        Mockito.verify(view, Mockito.times(0)).showSaveCircuitMenu();
        Mockito.verify(view, Mockito.times(0)).showInsertCustomMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showHelpMenu();
        Mockito.verify(view, Mockito.times(0)).showTileInfo(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showSetDelayMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleAutoAdvance();
        Mockito.verify(view, Mockito.times(0)).showInsertGateMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showInsertMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleShowPower();
        Mockito.verify(view, Mockito.times(0)).moveViewWindow(Mockito.any(Side.class));
        Mockito.verify(view, Mockito.times(0)).moveSelectedTile(Mockito.any(Side.class));
    }

    @Test
    @Tag("view") @Tag("model")
    @Tag("integration-test") @Tag("fast")
    public void testInputW() {
        LanternaCircuitView view = Mockito.mock(LanternaCircuitView.class);

        Position position = Mockito.mock(Position.class);
        Position clone = Mockito.mock(Position.class);

        Mockito.when(view.getSelectedTile()).thenReturn(position);
        Mockito.when(position.clone()).thenReturn(clone);

        Mockito.when(view.inMenu()).thenReturn(false);

        Screen screen = Mockito.mock(Screen.class);
        Mockito.when(view.getScreen()).thenReturn(screen);

        KeyStroke key = Mockito.mock(KeyStroke.class);
        try {
            Mockito.when(screen.readInput()).thenReturn(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.when(key.getKeyType()).thenReturn(KeyType.Character);
        Mockito.when(key.getCharacter()).thenReturn('w');

        LanternaInput input = Mockito.mock(LanternaInput.class, Mockito.withSettings().useConstructor(view));

        Mockito.doCallRealMethod().when(input).run();

        Mockito.when(input.isInterrupted()).thenReturn(false);

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Event event = invocation.getArgument(0);
            Assertions.assertEquals(InputEvent.ADD_TILE, event.getInputEvent());
            Assertions.assertTrue(event.getObject() instanceof WireTile);
            Assertions.assertEquals(clone, ((WireTile) event.getObject()).getPosition());
            return null;
        }).when(view).pushEvent(Mockito.any(Event.class));

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Mockito.when(input.isInterrupted()).thenReturn(true);
            return null;
        }).when(view).inMenu();

        input.run();

        Mockito.verify(input, Mockito.times(2)).isInterrupted();
        Mockito.verify(view, Mockito.times(1)).getScreen();
        try {
            Mockito.verify(screen, Mockito.times(1)).readInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.verify(key, Mockito.times(1)).getKeyType();
        Mockito.verify(key, Mockito.times(1)).getCharacter();

        Mockito.verify(view, Mockito.times(1))
                .pushEvent(Mockito.any(Event.class));
        Mockito.verify(view, Mockito.times(1))
                .pushEvent(Mockito.any());

        Mockito.verify(view, Mockito.times(1)).getSelectedTile();
        Mockito.verify(position, Mockito.times(1)).clone();

        Mockito.verify(view, Mockito.times(0)).showSaveCircuitMenu();
        Mockito.verify(view, Mockito.times(0)).showInsertCustomMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showHelpMenu();
        Mockito.verify(view, Mockito.times(0)).showTileInfo(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showSetDelayMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleAutoAdvance();
        Mockito.verify(view, Mockito.times(0)).showInsertGateMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showInsertMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleShowPower();
        Mockito.verify(view, Mockito.times(0)).moveViewWindow(Mockito.any(Side.class));
        Mockito.verify(view, Mockito.times(0)).moveSelectedTile(Mockito.any(Side.class));
    }

    @Test
    @Tag("view") @Tag("model")
    @Tag("integration-test") @Tag("fast")
    public void testInputX() {
        LanternaCircuitView view = Mockito.mock(LanternaCircuitView.class);

        Position position = Mockito.mock(Position.class);
        Position clone = Mockito.mock(Position.class);

        Mockito.when(view.getSelectedTile()).thenReturn(position);
        Mockito.when(position.clone()).thenReturn(clone);

        Mockito.when(view.inMenu()).thenReturn(false);

        Screen screen = Mockito.mock(Screen.class);
        Mockito.when(view.getScreen()).thenReturn(screen);

        KeyStroke key = Mockito.mock(KeyStroke.class);
        try {
            Mockito.when(screen.readInput()).thenReturn(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.when(key.getKeyType()).thenReturn(KeyType.Character);
        Mockito.when(key.getCharacter()).thenReturn('x');

        LanternaInput input = Mockito.mock(LanternaInput.class, Mockito.withSettings().useConstructor(view));

        Mockito.doCallRealMethod().when(input).run();

        Mockito.when(input.isInterrupted()).thenReturn(false);

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Event event = invocation.getArgument(0);
            Assertions.assertEquals(InputEvent.ADD_TILE, event.getInputEvent());
            Assertions.assertTrue(event.getObject() instanceof CrossWireTile);
            Assertions.assertEquals(clone, ((CrossWireTile) event.getObject()).getPosition());
            return null;
        }).when(view).pushEvent(Mockito.any(Event.class));

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Mockito.when(input.isInterrupted()).thenReturn(true);
            return null;
        }).when(view).inMenu();

        input.run();

        Mockito.verify(input, Mockito.times(2)).isInterrupted();
        Mockito.verify(view, Mockito.times(1)).getScreen();
        try {
            Mockito.verify(screen, Mockito.times(1)).readInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.verify(key, Mockito.times(1)).getKeyType();
        Mockito.verify(key, Mockito.times(1)).getCharacter();

        Mockito.verify(view, Mockito.times(1))
                .pushEvent(Mockito.any(Event.class));
        Mockito.verify(view, Mockito.times(1))
                .pushEvent(Mockito.any());

        Mockito.verify(view, Mockito.times(1)).getSelectedTile();
        Mockito.verify(position, Mockito.times(1)).clone();

        Mockito.verify(view, Mockito.times(0)).showSaveCircuitMenu();
        Mockito.verify(view, Mockito.times(0)).showInsertCustomMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showHelpMenu();
        Mockito.verify(view, Mockito.times(0)).showTileInfo(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showSetDelayMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleAutoAdvance();
        Mockito.verify(view, Mockito.times(0)).showInsertGateMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showInsertMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleShowPower();
        Mockito.verify(view, Mockito.times(0)).moveViewWindow(Mockito.any(Side.class));
        Mockito.verify(view, Mockito.times(0)).moveSelectedTile(Mockito.any(Side.class));
    }

    @Test
    @Tag("view") @Tag("model")
    @Tag("integration-test") @Tag("fast")
    public void testInputS() {
        LanternaCircuitView view = Mockito.mock(LanternaCircuitView.class);

        Position position = Mockito.mock(Position.class);
        Position clone = Mockito.mock(Position.class);

        Mockito.when(view.getSelectedTile()).thenReturn(position);
        Mockito.when(position.clone()).thenReturn(clone);

        Mockito.when(view.inMenu()).thenReturn(false);

        Screen screen = Mockito.mock(Screen.class);
        Mockito.when(view.getScreen()).thenReturn(screen);

        KeyStroke key = Mockito.mock(KeyStroke.class);
        try {
            Mockito.when(screen.readInput()).thenReturn(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.when(key.getKeyType()).thenReturn(KeyType.Character);
        Mockito.when(key.getCharacter()).thenReturn('s');

        LanternaInput input = Mockito.mock(LanternaInput.class, Mockito.withSettings().useConstructor(view));

        Mockito.doCallRealMethod().when(input).run();

        Mockito.when(input.isInterrupted()).thenReturn(false);

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Event event = invocation.getArgument(0);
            Assertions.assertEquals(InputEvent.ADD_TILE, event.getInputEvent());
            Assertions.assertTrue(event.getObject() instanceof ConstantSourceTile);
            Assertions.assertEquals(clone, ((ConstantSourceTile) event.getObject()).getPosition());
            return null;
        }).when(view).pushEvent(Mockito.any(Event.class));

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Mockito.when(input.isInterrupted()).thenReturn(true);
            return null;
        }).when(view).inMenu();

        input.run();

        Mockito.verify(input, Mockito.times(2)).isInterrupted();
        Mockito.verify(view, Mockito.times(1)).getScreen();
        try {
            Mockito.verify(screen, Mockito.times(1)).readInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.verify(key, Mockito.times(1)).getKeyType();
        Mockito.verify(key, Mockito.times(1)).getCharacter();

        Mockito.verify(view, Mockito.times(1))
                .pushEvent(Mockito.any(Event.class));
        Mockito.verify(view, Mockito.times(1))
                .pushEvent(Mockito.any());

        Mockito.verify(view, Mockito.times(1)).getSelectedTile();
        Mockito.verify(position, Mockito.times(1)).clone();

        Mockito.verify(view, Mockito.times(0)).showSaveCircuitMenu();
        Mockito.verify(view, Mockito.times(0)).showInsertCustomMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showHelpMenu();
        Mockito.verify(view, Mockito.times(0)).showTileInfo(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showSetDelayMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleAutoAdvance();
        Mockito.verify(view, Mockito.times(0)).showInsertGateMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showInsertMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleShowPower();
        Mockito.verify(view, Mockito.times(0)).moveViewWindow(Mockito.any(Side.class));
        Mockito.verify(view, Mockito.times(0)).moveSelectedTile(Mockito.any(Side.class));
    }

    @Test
    @Tag("view") @Tag("model")
    @Tag("integration-test") @Tag("fast")
    public void testInputL() {
        LanternaCircuitView view = Mockito.mock(LanternaCircuitView.class);

        Position position = Mockito.mock(Position.class);
        Position clone = Mockito.mock(Position.class);

        Mockito.when(view.getSelectedTile()).thenReturn(position);
        Mockito.when(position.clone()).thenReturn(clone);

        Mockito.when(view.inMenu()).thenReturn(false);

        Screen screen = Mockito.mock(Screen.class);
        Mockito.when(view.getScreen()).thenReturn(screen);

        KeyStroke key = Mockito.mock(KeyStroke.class);
        try {
            Mockito.when(screen.readInput()).thenReturn(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.when(key.getKeyType()).thenReturn(KeyType.Character);
        Mockito.when(key.getCharacter()).thenReturn('l');

        LanternaInput input = Mockito.mock(LanternaInput.class, Mockito.withSettings().useConstructor(view));

        Mockito.doCallRealMethod().when(input).run();

        Mockito.when(input.isInterrupted()).thenReturn(false);

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Event event = invocation.getArgument(0);
            Assertions.assertEquals(InputEvent.ADD_TILE, event.getInputEvent());
            Assertions.assertTrue(event.getObject() instanceof LeverTile);
            Assertions.assertEquals(clone, ((LeverTile) event.getObject()).getPosition());
            return null;
        }).when(view).pushEvent(Mockito.any(Event.class));

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Mockito.when(input.isInterrupted()).thenReturn(true);
            return null;
        }).when(view).inMenu();

        input.run();

        Mockito.verify(input, Mockito.times(2)).isInterrupted();
        Mockito.verify(view, Mockito.times(1)).getScreen();
        try {
            Mockito.verify(screen, Mockito.times(1)).readInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.verify(key, Mockito.times(1)).getKeyType();
        Mockito.verify(key, Mockito.times(1)).getCharacter();

        Mockito.verify(view, Mockito.times(1))
                .pushEvent(Mockito.any(Event.class));
        Mockito.verify(view, Mockito.times(1))
                .pushEvent(Mockito.any());

        Mockito.verify(view, Mockito.times(1)).getSelectedTile();
        Mockito.verify(position, Mockito.times(1)).clone();

        Mockito.verify(view, Mockito.times(0)).showSaveCircuitMenu();
        Mockito.verify(view, Mockito.times(0)).showInsertCustomMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showHelpMenu();
        Mockito.verify(view, Mockito.times(0)).showTileInfo(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showSetDelayMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleAutoAdvance();
        Mockito.verify(view, Mockito.times(0)).showInsertGateMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showInsertMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleShowPower();
        Mockito.verify(view, Mockito.times(0)).moveViewWindow(Mockito.any(Side.class));
        Mockito.verify(view, Mockito.times(0)).moveSelectedTile(Mockito.any(Side.class));
    }

    @Test
    @Tag("view") @Tag("model")
    @Tag("integration-test") @Tag("fast")
    public void testInputR() {
        LanternaCircuitView view = Mockito.mock(LanternaCircuitView.class);

        Position position = Mockito.mock(Position.class);
        Position clone = Mockito.mock(Position.class);

        Mockito.when(view.getSelectedTile()).thenReturn(position);
        Mockito.when(position.clone()).thenReturn(clone);

        Mockito.when(view.inMenu()).thenReturn(false);

        Screen screen = Mockito.mock(Screen.class);
        Mockito.when(view.getScreen()).thenReturn(screen);

        KeyStroke key = Mockito.mock(KeyStroke.class);
        try {
            Mockito.when(screen.readInput()).thenReturn(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.when(key.getKeyType()).thenReturn(KeyType.Character);
        Mockito.when(key.getCharacter()).thenReturn('r');

        LanternaInput input = Mockito.mock(LanternaInput.class, Mockito.withSettings().useConstructor(view));

        Mockito.doCallRealMethod().when(input).run();

        Mockito.when(input.isInterrupted()).thenReturn(false);

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Event event = invocation.getArgument(0);
            Assertions.assertEquals(InputEvent.ADD_TILE, event.getInputEvent());
            Assertions.assertTrue(event.getObject() instanceof RepeaterTile);
            Assertions.assertEquals(clone, ((RepeaterTile) event.getObject()).getPosition());
            return null;
        }).when(view).pushEvent(Mockito.any(Event.class));

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Mockito.when(input.isInterrupted()).thenReturn(true);
            return null;
        }).when(view).inMenu();

        input.run();

        Mockito.verify(input, Mockito.times(2)).isInterrupted();
        Mockito.verify(view, Mockito.times(1)).getScreen();
        try {
            Mockito.verify(screen, Mockito.times(1)).readInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.verify(key, Mockito.times(1)).getKeyType();
        Mockito.verify(key, Mockito.times(1)).getCharacter();

        Mockito.verify(view, Mockito.times(1))
                .pushEvent(Mockito.any(Event.class));
        Mockito.verify(view, Mockito.times(1))
                .pushEvent(Mockito.any());

        Mockito.verify(view, Mockito.times(1)).getSelectedTile();
        Mockito.verify(position, Mockito.times(1)).clone();

        Mockito.verify(view, Mockito.times(0)).showSaveCircuitMenu();
        Mockito.verify(view, Mockito.times(0)).showInsertCustomMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showHelpMenu();
        Mockito.verify(view, Mockito.times(0)).showTileInfo(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showSetDelayMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleAutoAdvance();
        Mockito.verify(view, Mockito.times(0)).showInsertGateMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showInsertMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleShowPower();
        Mockito.verify(view, Mockito.times(0)).moveViewWindow(Mockito.any(Side.class));
        Mockito.verify(view, Mockito.times(0)).moveSelectedTile(Mockito.any(Side.class));
    }

    @Test
    @Tag("view") @Tag("model")
    @Tag("integration-test") @Tag("fast")
    public void testInput1() {
        LanternaCircuitView view = Mockito.mock(LanternaCircuitView.class);

        Position position = Mockito.mock(Position.class);
        Position clone = Mockito.mock(Position.class);

        Mockito.when(view.getSelectedTile()).thenReturn(position);
        Mockito.when(position.clone()).thenReturn(clone);

        Mockito.when(view.inMenu()).thenReturn(false);

        Screen screen = Mockito.mock(Screen.class);
        Mockito.when(view.getScreen()).thenReturn(screen);

        KeyStroke key = Mockito.mock(KeyStroke.class);
        try {
            Mockito.when(screen.readInput()).thenReturn(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.when(key.getKeyType()).thenReturn(KeyType.Character);
        Mockito.when(key.getCharacter()).thenReturn('1');

        LanternaInput input = Mockito.mock(LanternaInput.class, Mockito.withSettings().useConstructor(view));

        Mockito.doCallRealMethod().when(input).run();

        Mockito.when(input.isInterrupted()).thenReturn(false);

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Event event = invocation.getArgument(0);
            Assertions.assertEquals(InputEvent.ADD_TILE, event.getInputEvent());
            Assertions.assertTrue(event.getObject() instanceof NotGateTile);
            Assertions.assertEquals(clone, ((NotGateTile) event.getObject()).getPosition());
            return null;
        }).when(view).pushEvent(Mockito.any(Event.class));

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Mockito.when(input.isInterrupted()).thenReturn(true);
            return null;
        }).when(view).inMenu();

        input.run();

        Mockito.verify(input, Mockito.times(2)).isInterrupted();
        Mockito.verify(view, Mockito.times(1)).getScreen();
        try {
            Mockito.verify(screen, Mockito.times(1)).readInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.verify(key, Mockito.times(1)).getKeyType();
        Mockito.verify(key, Mockito.times(1)).getCharacter();

        Mockito.verify(view, Mockito.times(1))
                .pushEvent(Mockito.any(Event.class));
        Mockito.verify(view, Mockito.times(1))
                .pushEvent(Mockito.any());

        Mockito.verify(view, Mockito.times(1)).getSelectedTile();
        Mockito.verify(position, Mockito.times(1)).clone();

        Mockito.verify(view, Mockito.times(0)).showSaveCircuitMenu();
        Mockito.verify(view, Mockito.times(0)).showInsertCustomMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showHelpMenu();
        Mockito.verify(view, Mockito.times(0)).showTileInfo(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showSetDelayMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleAutoAdvance();
        Mockito.verify(view, Mockito.times(0)).showInsertGateMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showInsertMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleShowPower();
        Mockito.verify(view, Mockito.times(0)).moveViewWindow(Mockito.any(Side.class));
        Mockito.verify(view, Mockito.times(0)).moveSelectedTile(Mockito.any(Side.class));
    }

    @Test
    @Tag("view") @Tag("model")
    @Tag("integration-test") @Tag("fast")
    public void testInput2() {
        LanternaCircuitView view = Mockito.mock(LanternaCircuitView.class);

        Position position = Mockito.mock(Position.class);
        Position clone = Mockito.mock(Position.class);

        Mockito.when(view.getSelectedTile()).thenReturn(position);
        Mockito.when(position.clone()).thenReturn(clone);

        Mockito.when(view.inMenu()).thenReturn(false);

        Screen screen = Mockito.mock(Screen.class);
        Mockito.when(view.getScreen()).thenReturn(screen);

        KeyStroke key = Mockito.mock(KeyStroke.class);
        try {
            Mockito.when(screen.readInput()).thenReturn(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.when(key.getKeyType()).thenReturn(KeyType.Character);
        Mockito.when(key.getCharacter()).thenReturn('2');

        LanternaInput input = Mockito.mock(LanternaInput.class, Mockito.withSettings().useConstructor(view));

        Mockito.doCallRealMethod().when(input).run();

        Mockito.when(input.isInterrupted()).thenReturn(false);

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Event event = invocation.getArgument(0);
            Assertions.assertEquals(InputEvent.ADD_TILE, event.getInputEvent());
            Assertions.assertTrue(event.getObject() instanceof LogicGateTile);
            LogicGateTile tile = (LogicGateTile) event.getObject();
            Assertions.assertEquals(clone, tile.getPosition());
            Assertions.assertEquals(LogicGateStrategyType.AND, tile.getStrategy().getType());
            return null;
        }).when(view).pushEvent(Mockito.any(Event.class));

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Mockito.when(input.isInterrupted()).thenReturn(true);
            return null;
        }).when(view).inMenu();

        input.run();

        Mockito.verify(input, Mockito.times(2)).isInterrupted();
        Mockito.verify(view, Mockito.times(1)).getScreen();
        try {
            Mockito.verify(screen, Mockito.times(1)).readInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.verify(key, Mockito.times(1)).getKeyType();
        Mockito.verify(key, Mockito.times(1)).getCharacter();

        Mockito.verify(view, Mockito.times(1))
                .pushEvent(Mockito.any(Event.class));
        Mockito.verify(view, Mockito.times(1))
                .pushEvent(Mockito.any());

        Mockito.verify(view, Mockito.times(1)).getSelectedTile();
        Mockito.verify(position, Mockito.times(1)).clone();

        Mockito.verify(view, Mockito.times(0)).showSaveCircuitMenu();
        Mockito.verify(view, Mockito.times(0)).showInsertCustomMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showHelpMenu();
        Mockito.verify(view, Mockito.times(0)).showTileInfo(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showSetDelayMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleAutoAdvance();
        Mockito.verify(view, Mockito.times(0)).showInsertGateMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showInsertMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleShowPower();
        Mockito.verify(view, Mockito.times(0)).moveViewWindow(Mockito.any(Side.class));
        Mockito.verify(view, Mockito.times(0)).moveSelectedTile(Mockito.any(Side.class));
    }

    @Test
    @Tag("view") @Tag("model")
    @Tag("integration-test") @Tag("fast")
    public void testInput3() {
        LanternaCircuitView view = Mockito.mock(LanternaCircuitView.class);

        Position position = Mockito.mock(Position.class);
        Position clone = Mockito.mock(Position.class);

        Mockito.when(view.getSelectedTile()).thenReturn(position);
        Mockito.when(position.clone()).thenReturn(clone);

        Mockito.when(view.inMenu()).thenReturn(false);

        Screen screen = Mockito.mock(Screen.class);
        Mockito.when(view.getScreen()).thenReturn(screen);

        KeyStroke key = Mockito.mock(KeyStroke.class);
        try {
            Mockito.when(screen.readInput()).thenReturn(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.when(key.getKeyType()).thenReturn(KeyType.Character);
        Mockito.when(key.getCharacter()).thenReturn('3');

        LanternaInput input = Mockito.mock(LanternaInput.class, Mockito.withSettings().useConstructor(view));

        Mockito.doCallRealMethod().when(input).run();

        Mockito.when(input.isInterrupted()).thenReturn(false);

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Event event = invocation.getArgument(0);
            Assertions.assertEquals(InputEvent.ADD_TILE, event.getInputEvent());
            Assertions.assertTrue(event.getObject() instanceof LogicGateTile);
            LogicGateTile tile = (LogicGateTile) event.getObject();
            Assertions.assertEquals(clone, tile.getPosition());
            Assertions.assertEquals(LogicGateStrategyType.OR, tile.getStrategy().getType());
            return null;
        }).when(view).pushEvent(Mockito.any(Event.class));

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Mockito.when(input.isInterrupted()).thenReturn(true);
            return null;
        }).when(view).inMenu();

        input.run();

        Mockito.verify(input, Mockito.times(2)).isInterrupted();
        Mockito.verify(view, Mockito.times(1)).getScreen();
        try {
            Mockito.verify(screen, Mockito.times(1)).readInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.verify(key, Mockito.times(1)).getKeyType();
        Mockito.verify(key, Mockito.times(1)).getCharacter();

        Mockito.verify(view, Mockito.times(1))
                .pushEvent(Mockito.any(Event.class));
        Mockito.verify(view, Mockito.times(1))
                .pushEvent(Mockito.any());

        Mockito.verify(view, Mockito.times(1)).getSelectedTile();
        Mockito.verify(position, Mockito.times(1)).clone();

        Mockito.verify(view, Mockito.times(0)).showSaveCircuitMenu();
        Mockito.verify(view, Mockito.times(0)).showInsertCustomMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showHelpMenu();
        Mockito.verify(view, Mockito.times(0)).showTileInfo(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showSetDelayMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleAutoAdvance();
        Mockito.verify(view, Mockito.times(0)).showInsertGateMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showInsertMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleShowPower();
        Mockito.verify(view, Mockito.times(0)).moveViewWindow(Mockito.any(Side.class));
        Mockito.verify(view, Mockito.times(0)).moveSelectedTile(Mockito.any(Side.class));
    }

    @Test
    @Tag("view") @Tag("model")
    @Tag("integration-test") @Tag("fast")
    public void testInput4() {
        LanternaCircuitView view = Mockito.mock(LanternaCircuitView.class);

        Position position = Mockito.mock(Position.class);
        Position clone = Mockito.mock(Position.class);

        Mockito.when(view.getSelectedTile()).thenReturn(position);
        Mockito.when(position.clone()).thenReturn(clone);

        Mockito.when(view.inMenu()).thenReturn(false);

        Screen screen = Mockito.mock(Screen.class);
        Mockito.when(view.getScreen()).thenReturn(screen);

        KeyStroke key = Mockito.mock(KeyStroke.class);
        try {
            Mockito.when(screen.readInput()).thenReturn(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.when(key.getKeyType()).thenReturn(KeyType.Character);
        Mockito.when(key.getCharacter()).thenReturn('4');

        LanternaInput input = Mockito.mock(LanternaInput.class, Mockito.withSettings().useConstructor(view));

        Mockito.doCallRealMethod().when(input).run();

        Mockito.when(input.isInterrupted()).thenReturn(false);

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Event event = invocation.getArgument(0);
            Assertions.assertEquals(InputEvent.ADD_TILE, event.getInputEvent());
            Assertions.assertTrue(event.getObject() instanceof LogicGateTile);
            LogicGateTile tile = (LogicGateTile) event.getObject();
            Assertions.assertEquals(clone, tile.getPosition());
            Assertions.assertEquals(LogicGateStrategyType.NAND, tile.getStrategy().getType());
            return null;
        }).when(view).pushEvent(Mockito.any(Event.class));

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Mockito.when(input.isInterrupted()).thenReturn(true);
            return null;
        }).when(view).inMenu();

        input.run();

        Mockito.verify(input, Mockito.times(2)).isInterrupted();
        Mockito.verify(view, Mockito.times(1)).getScreen();
        try {
            Mockito.verify(screen, Mockito.times(1)).readInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.verify(key, Mockito.times(1)).getKeyType();
        Mockito.verify(key, Mockito.times(1)).getCharacter();

        Mockito.verify(view, Mockito.times(1))
                .pushEvent(Mockito.any(Event.class));
        Mockito.verify(view, Mockito.times(1))
                .pushEvent(Mockito.any());

        Mockito.verify(view, Mockito.times(1)).getSelectedTile();
        Mockito.verify(position, Mockito.times(1)).clone();

        Mockito.verify(view, Mockito.times(0)).showSaveCircuitMenu();
        Mockito.verify(view, Mockito.times(0)).showInsertCustomMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showHelpMenu();
        Mockito.verify(view, Mockito.times(0)).showTileInfo(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showSetDelayMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleAutoAdvance();
        Mockito.verify(view, Mockito.times(0)).showInsertGateMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showInsertMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleShowPower();
        Mockito.verify(view, Mockito.times(0)).moveViewWindow(Mockito.any(Side.class));
        Mockito.verify(view, Mockito.times(0)).moveSelectedTile(Mockito.any(Side.class));
    }

    @Test
    @Tag("view") @Tag("model")
    @Tag("integration-test") @Tag("fast")
    public void testInput5() {
        LanternaCircuitView view = Mockito.mock(LanternaCircuitView.class);

        Position position = Mockito.mock(Position.class);
        Position clone = Mockito.mock(Position.class);

        Mockito.when(view.getSelectedTile()).thenReturn(position);
        Mockito.when(position.clone()).thenReturn(clone);

        Mockito.when(view.inMenu()).thenReturn(false);

        Screen screen = Mockito.mock(Screen.class);
        Mockito.when(view.getScreen()).thenReturn(screen);

        KeyStroke key = Mockito.mock(KeyStroke.class);
        try {
            Mockito.when(screen.readInput()).thenReturn(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.when(key.getKeyType()).thenReturn(KeyType.Character);
        Mockito.when(key.getCharacter()).thenReturn('5');

        LanternaInput input = Mockito.mock(LanternaInput.class, Mockito.withSettings().useConstructor(view));

        Mockito.doCallRealMethod().when(input).run();

        Mockito.when(input.isInterrupted()).thenReturn(false);

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Event event = invocation.getArgument(0);
            Assertions.assertEquals(InputEvent.ADD_TILE, event.getInputEvent());
            Assertions.assertTrue(event.getObject() instanceof LogicGateTile);
            LogicGateTile tile = (LogicGateTile) event.getObject();
            Assertions.assertEquals(clone, tile.getPosition());
            Assertions.assertEquals(LogicGateStrategyType.NOR, tile.getStrategy().getType());
            return null;
        }).when(view).pushEvent(Mockito.any(Event.class));

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Mockito.when(input.isInterrupted()).thenReturn(true);
            return null;
        }).when(view).inMenu();

        input.run();

        Mockito.verify(input, Mockito.times(2)).isInterrupted();
        Mockito.verify(view, Mockito.times(1)).getScreen();
        try {
            Mockito.verify(screen, Mockito.times(1)).readInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.verify(key, Mockito.times(1)).getKeyType();
        Mockito.verify(key, Mockito.times(1)).getCharacter();

        Mockito.verify(view, Mockito.times(1))
                .pushEvent(Mockito.any(Event.class));
        Mockito.verify(view, Mockito.times(1))
                .pushEvent(Mockito.any());

        Mockito.verify(view, Mockito.times(1)).getSelectedTile();
        Mockito.verify(position, Mockito.times(1)).clone();

        Mockito.verify(view, Mockito.times(0)).showSaveCircuitMenu();
        Mockito.verify(view, Mockito.times(0)).showInsertCustomMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showHelpMenu();
        Mockito.verify(view, Mockito.times(0)).showTileInfo(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showSetDelayMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleAutoAdvance();
        Mockito.verify(view, Mockito.times(0)).showInsertGateMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showInsertMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleShowPower();
        Mockito.verify(view, Mockito.times(0)).moveViewWindow(Mockito.any(Side.class));
        Mockito.verify(view, Mockito.times(0)).moveSelectedTile(Mockito.any(Side.class));
    }

    @Test
    @Tag("view") @Tag("model")
    @Tag("integration-test") @Tag("fast")
    public void testInput6() {
        LanternaCircuitView view = Mockito.mock(LanternaCircuitView.class);

        Position position = Mockito.mock(Position.class);
        Position clone = Mockito.mock(Position.class);

        Mockito.when(view.getSelectedTile()).thenReturn(position);
        Mockito.when(position.clone()).thenReturn(clone);

        Mockito.when(view.inMenu()).thenReturn(false);

        Screen screen = Mockito.mock(Screen.class);
        Mockito.when(view.getScreen()).thenReturn(screen);

        KeyStroke key = Mockito.mock(KeyStroke.class);
        try {
            Mockito.when(screen.readInput()).thenReturn(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.when(key.getKeyType()).thenReturn(KeyType.Character);
        Mockito.when(key.getCharacter()).thenReturn('6');

        LanternaInput input = Mockito.mock(LanternaInput.class, Mockito.withSettings().useConstructor(view));

        Mockito.doCallRealMethod().when(input).run();

        Mockito.when(input.isInterrupted()).thenReturn(false);

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Event event = invocation.getArgument(0);
            Assertions.assertEquals(InputEvent.ADD_TILE, event.getInputEvent());
            Assertions.assertTrue(event.getObject() instanceof LogicGateTile);
            LogicGateTile tile = (LogicGateTile) event.getObject();
            Assertions.assertEquals(clone, tile.getPosition());
            Assertions.assertEquals(LogicGateStrategyType.XOR, tile.getStrategy().getType());
            return null;
        }).when(view).pushEvent(Mockito.any(Event.class));

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Mockito.when(input.isInterrupted()).thenReturn(true);
            return null;
        }).when(view).inMenu();

        input.run();

        Mockito.verify(input, Mockito.times(2)).isInterrupted();
        Mockito.verify(view, Mockito.times(1)).getScreen();
        try {
            Mockito.verify(screen, Mockito.times(1)).readInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.verify(key, Mockito.times(1)).getKeyType();
        Mockito.verify(key, Mockito.times(1)).getCharacter();

        Mockito.verify(view, Mockito.times(1))
                .pushEvent(Mockito.any(Event.class));
        Mockito.verify(view, Mockito.times(1))
                .pushEvent(Mockito.any());

        Mockito.verify(view, Mockito.times(1)).getSelectedTile();
        Mockito.verify(position, Mockito.times(1)).clone();

        Mockito.verify(view, Mockito.times(0)).showSaveCircuitMenu();
        Mockito.verify(view, Mockito.times(0)).showInsertCustomMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showHelpMenu();
        Mockito.verify(view, Mockito.times(0)).showTileInfo(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showSetDelayMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleAutoAdvance();
        Mockito.verify(view, Mockito.times(0)).showInsertGateMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showInsertMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleShowPower();
        Mockito.verify(view, Mockito.times(0)).moveViewWindow(Mockito.any(Side.class));
        Mockito.verify(view, Mockito.times(0)).moveSelectedTile(Mockito.any(Side.class));
    }

    @Test
    @Tag("view") @Tag("model")
    @Tag("integration-test") @Tag("fast")
    public void testInput7() {
        LanternaCircuitView view = Mockito.mock(LanternaCircuitView.class);

        Position position = Mockito.mock(Position.class);
        Position clone = Mockito.mock(Position.class);

        Mockito.when(view.getSelectedTile()).thenReturn(position);
        Mockito.when(position.clone()).thenReturn(clone);

        Mockito.when(view.inMenu()).thenReturn(false);

        Screen screen = Mockito.mock(Screen.class);
        Mockito.when(view.getScreen()).thenReturn(screen);

        KeyStroke key = Mockito.mock(KeyStroke.class);
        try {
            Mockito.when(screen.readInput()).thenReturn(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.when(key.getKeyType()).thenReturn(KeyType.Character);
        Mockito.when(key.getCharacter()).thenReturn('7');

        LanternaInput input = Mockito.mock(LanternaInput.class, Mockito.withSettings().useConstructor(view));

        Mockito.doCallRealMethod().when(input).run();

        Mockito.when(input.isInterrupted()).thenReturn(false);

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Event event = invocation.getArgument(0);
            Assertions.assertEquals(InputEvent.ADD_TILE, event.getInputEvent());
            Assertions.assertTrue(event.getObject() instanceof LogicGateTile);
            LogicGateTile tile = (LogicGateTile) event.getObject();
            Assertions.assertEquals(clone, tile.getPosition());
            Assertions.assertEquals(LogicGateStrategyType.XNOR, tile.getStrategy().getType());
            return null;
        }).when(view).pushEvent(Mockito.any(Event.class));

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Mockito.when(input.isInterrupted()).thenReturn(true);
            return null;
        }).when(view).inMenu();

        input.run();

        Mockito.verify(input, Mockito.times(2)).isInterrupted();
        Mockito.verify(view, Mockito.times(1)).getScreen();
        try {
            Mockito.verify(screen, Mockito.times(1)).readInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.verify(key, Mockito.times(1)).getKeyType();
        Mockito.verify(key, Mockito.times(1)).getCharacter();

        Mockito.verify(view, Mockito.times(1))
                .pushEvent(Mockito.any(Event.class));
        Mockito.verify(view, Mockito.times(1))
                .pushEvent(Mockito.any());

        Mockito.verify(view, Mockito.times(1)).getSelectedTile();
        Mockito.verify(position, Mockito.times(1)).clone();

        Mockito.verify(view, Mockito.times(0)).showSaveCircuitMenu();
        Mockito.verify(view, Mockito.times(0)).showInsertCustomMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showHelpMenu();
        Mockito.verify(view, Mockito.times(0)).showTileInfo(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showSetDelayMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleAutoAdvance();
        Mockito.verify(view, Mockito.times(0)).showInsertGateMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showInsertMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleShowPower();
        Mockito.verify(view, Mockito.times(0)).moveViewWindow(Mockito.any(Side.class));
        Mockito.verify(view, Mockito.times(0)).moveSelectedTile(Mockito.any(Side.class));
    }

    @Test
    @Tag("view") @Tag("model")
    @Tag("integration-test") @Tag("fast")
    public void testInputC() {
        LanternaCircuitView view = Mockito.mock(LanternaCircuitView.class);

        Position position = Mockito.mock(Position.class);
        Position clone = Mockito.mock(Position.class);

        Mockito.when(view.getSelectedTile()).thenReturn(position);
        Mockito.when(position.clone()).thenReturn(clone);

        Mockito.when(view.inMenu()).thenReturn(false);

        Screen screen = Mockito.mock(Screen.class);
        Mockito.when(view.getScreen()).thenReturn(screen);

        KeyStroke key = Mockito.mock(KeyStroke.class);
        try {
            Mockito.when(screen.readInput()).thenReturn(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.when(key.getKeyType()).thenReturn(KeyType.Character);
        Mockito.when(key.getCharacter()).thenReturn('c');

        LanternaInput input = Mockito.mock(LanternaInput.class, Mockito.withSettings().useConstructor(view));

        Mockito.doCallRealMethod().when(input).run();

        Mockito.when(input.isInterrupted()).thenReturn(false);

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Event event = invocation.getArgument(0);
            Assertions.assertEquals(InputEvent.ADD_TILE, event.getInputEvent());
            Assertions.assertTrue(event.getObject() instanceof ComparatorTile);
            Assertions.assertEquals(clone, ((ComparatorTile) event.getObject()).getPosition());
            return null;
        }).when(view).pushEvent(Mockito.any(Event.class));

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Mockito.when(input.isInterrupted()).thenReturn(true);
            return null;
        }).when(view).inMenu();

        input.run();

        Mockito.verify(input, Mockito.times(2)).isInterrupted();
        Mockito.verify(view, Mockito.times(1)).getScreen();
        try {
            Mockito.verify(screen, Mockito.times(1)).readInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.verify(key, Mockito.times(1)).getKeyType();
        Mockito.verify(key, Mockito.times(1)).getCharacter();

        Mockito.verify(view, Mockito.times(1))
                .pushEvent(Mockito.any(Event.class));
        Mockito.verify(view, Mockito.times(1))
                .pushEvent(Mockito.any());

        Mockito.verify(view, Mockito.times(1)).getSelectedTile();
        Mockito.verify(position, Mockito.times(1)).clone();

        Mockito.verify(view, Mockito.times(0)).showSaveCircuitMenu();
        Mockito.verify(view, Mockito.times(0)).showInsertCustomMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showHelpMenu();
        Mockito.verify(view, Mockito.times(0)).showTileInfo(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showSetDelayMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleAutoAdvance();
        Mockito.verify(view, Mockito.times(0)).showInsertGateMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showInsertMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleShowPower();
        Mockito.verify(view, Mockito.times(0)).moveViewWindow(Mockito.any(Side.class));
        Mockito.verify(view, Mockito.times(0)).moveSelectedTile(Mockito.any(Side.class));
    }

    @Test
    @Tag("view") @Tag("model")
    @Tag("integration-test") @Tag("fast")
    public void testInputN() {
        LanternaCircuitView view = Mockito.mock(LanternaCircuitView.class);

        Position position = Mockito.mock(Position.class);
        Position clone = Mockito.mock(Position.class);

        Mockito.when(view.getSelectedTile()).thenReturn(position);
        Mockito.when(position.clone()).thenReturn(clone);

        Mockito.when(view.inMenu()).thenReturn(false);

        Screen screen = Mockito.mock(Screen.class);
        Mockito.when(view.getScreen()).thenReturn(screen);

        KeyStroke key = Mockito.mock(KeyStroke.class);
        try {
            Mockito.when(screen.readInput()).thenReturn(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.when(key.getKeyType()).thenReturn(KeyType.Character);
        Mockito.when(key.getCharacter()).thenReturn('n');

        LanternaInput input = Mockito.mock(LanternaInput.class, Mockito.withSettings().useConstructor(view));

        Mockito.doCallRealMethod().when(input).run();

        Mockito.when(input.isInterrupted()).thenReturn(false);

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Event event = invocation.getArgument(0);
            Assertions.assertEquals(InputEvent.ADD_TILE, event.getInputEvent());
            Assertions.assertTrue(event.getObject() instanceof CounterTile);
            Assertions.assertEquals(clone, ((CounterTile) event.getObject()).getPosition());
            return null;
        }).when(view).pushEvent(Mockito.any(Event.class));

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Mockito.when(input.isInterrupted()).thenReturn(true);
            return null;
        }).when(view).inMenu();

        input.run();

        Mockito.verify(input, Mockito.times(2)).isInterrupted();
        Mockito.verify(view, Mockito.times(1)).getScreen();
        try {
            Mockito.verify(screen, Mockito.times(1)).readInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.verify(key, Mockito.times(1)).getKeyType();
        Mockito.verify(key, Mockito.times(1)).getCharacter();

        Mockito.verify(view, Mockito.times(1))
                .pushEvent(Mockito.any(Event.class));
        Mockito.verify(view, Mockito.times(1))
                .pushEvent(Mockito.any());

        Mockito.verify(view, Mockito.times(1)).getSelectedTile();
        Mockito.verify(position, Mockito.times(1)).clone();

        Mockito.verify(view, Mockito.times(0)).showSaveCircuitMenu();
        Mockito.verify(view, Mockito.times(0)).showInsertCustomMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showHelpMenu();
        Mockito.verify(view, Mockito.times(0)).showTileInfo(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showSetDelayMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleAutoAdvance();
        Mockito.verify(view, Mockito.times(0)).showInsertGateMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showInsertMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleShowPower();
        Mockito.verify(view, Mockito.times(0)).moveViewWindow(Mockito.any(Side.class));
        Mockito.verify(view, Mockito.times(0)).moveSelectedTile(Mockito.any(Side.class));
    }

    @Test
    @Tag("view") @Tag("model")
    @Tag("integration-test") @Tag("fast")
    public void testInputT() {
        LanternaCircuitView view = Mockito.mock(LanternaCircuitView.class);

        Position position = Mockito.mock(Position.class);
        Position clone = Mockito.mock(Position.class);

        Mockito.when(view.getSelectedTile()).thenReturn(position);
        Mockito.when(position.clone()).thenReturn(clone);

        Mockito.when(view.inMenu()).thenReturn(false);

        Screen screen = Mockito.mock(Screen.class);
        Mockito.when(view.getScreen()).thenReturn(screen);

        KeyStroke key = Mockito.mock(KeyStroke.class);
        try {
            Mockito.when(screen.readInput()).thenReturn(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.when(key.getKeyType()).thenReturn(KeyType.Character);
        Mockito.when(key.getCharacter()).thenReturn('t');

        LanternaInput input = Mockito.mock(LanternaInput.class, Mockito.withSettings().useConstructor(view));

        Mockito.doCallRealMethod().when(input).run();

        Mockito.when(input.isInterrupted()).thenReturn(false);

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Event event = invocation.getArgument(0);
            Assertions.assertEquals(InputEvent.ADD_TILE, event.getInputEvent());
            Assertions.assertTrue(event.getObject() instanceof TimerTile);
            Assertions.assertEquals(clone, ((TimerTile) event.getObject()).getPosition());
            return null;
        }).when(view).pushEvent(Mockito.any(Event.class));

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Mockito.when(input.isInterrupted()).thenReturn(true);
            return null;
        }).when(view).inMenu();

        input.run();

        Mockito.verify(input, Mockito.times(2)).isInterrupted();
        Mockito.verify(view, Mockito.times(1)).getScreen();
        try {
            Mockito.verify(screen, Mockito.times(1)).readInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.verify(key, Mockito.times(1)).getKeyType();
        Mockito.verify(key, Mockito.times(1)).getCharacter();

        Mockito.verify(view, Mockito.times(1))
                .pushEvent(Mockito.any(Event.class));
        Mockito.verify(view, Mockito.times(1))
                .pushEvent(Mockito.any());

        Mockito.verify(view, Mockito.times(1)).getSelectedTile();
        Mockito.verify(position, Mockito.times(1)).clone();

        Mockito.verify(view, Mockito.times(0)).showSaveCircuitMenu();
        Mockito.verify(view, Mockito.times(0)).showInsertCustomMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showHelpMenu();
        Mockito.verify(view, Mockito.times(0)).showTileInfo(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showSetDelayMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleAutoAdvance();
        Mockito.verify(view, Mockito.times(0)).showInsertGateMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showInsertMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleShowPower();
        Mockito.verify(view, Mockito.times(0)).moveViewWindow(Mockito.any(Side.class));
        Mockito.verify(view, Mockito.times(0)).moveSelectedTile(Mockito.any(Side.class));
    }

    @Test
    @Tag("view") @Tag("model")
    @Tag("integration-test") @Tag("fast")
    public void testInputI() {
        LanternaCircuitView view = Mockito.mock(LanternaCircuitView.class);

        Position position = Mockito.mock(Position.class);
        Position clone = Mockito.mock(Position.class);

        Mockito.when(view.getSelectedTile()).thenReturn(position);
        Mockito.when(position.clone()).thenReturn(clone);

        Mockito.when(view.inMenu()).thenReturn(false);

        Screen screen = Mockito.mock(Screen.class);
        Mockito.when(view.getScreen()).thenReturn(screen);

        KeyStroke key = Mockito.mock(KeyStroke.class);
        try {
            Mockito.when(screen.readInput()).thenReturn(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.when(key.getKeyType()).thenReturn(KeyType.Character);
        Mockito.when(key.getCharacter()).thenReturn('i');

        LanternaInput input = Mockito.mock(LanternaInput.class, Mockito.withSettings().useConstructor(view));

        Mockito.doCallRealMethod().when(input).run();

        Mockito.when(input.isInterrupted()).thenReturn(false);

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Event event = invocation.getArgument(0);
            Assertions.assertEquals(InputEvent.ADD_TILE, event.getInputEvent());
            Assertions.assertTrue(event.getObject() instanceof IOTile);
            Assertions.assertEquals(clone, ((IOTile) event.getObject()).getPosition());
            return null;
        }).when(view).pushEvent(Mockito.any(Event.class));

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Mockito.when(input.isInterrupted()).thenReturn(true);
            return null;
        }).when(view).inMenu();

        input.run();

        Mockito.verify(input, Mockito.times(2)).isInterrupted();
        Mockito.verify(view, Mockito.times(1)).getScreen();
        try {
            Mockito.verify(screen, Mockito.times(1)).readInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.verify(key, Mockito.times(1)).getKeyType();
        Mockito.verify(key, Mockito.times(1)).getCharacter();

        Mockito.verify(view, Mockito.times(1))
                .pushEvent(Mockito.any(Event.class));
        Mockito.verify(view, Mockito.times(1))
                .pushEvent(Mockito.any());

        Mockito.verify(view, Mockito.times(1)).getSelectedTile();
        Mockito.verify(position, Mockito.times(1)).clone();

        Mockito.verify(view, Mockito.times(0)).showSaveCircuitMenu();
        Mockito.verify(view, Mockito.times(0)).showInsertCustomMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showHelpMenu();
        Mockito.verify(view, Mockito.times(0)).showTileInfo(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showSetDelayMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleAutoAdvance();
        Mockito.verify(view, Mockito.times(0)).showInsertGateMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showInsertMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleShowPower();
        Mockito.verify(view, Mockito.times(0)).moveViewWindow(Mockito.any(Side.class));
        Mockito.verify(view, Mockito.times(0)).moveSelectedTile(Mockito.any(Side.class));
    }

    @Test
    @Tag("view") @Tag("model")
    @Tag("integration-test") @Tag("fast")
    public void testInputD() {
        LanternaCircuitView view = Mockito.mock(LanternaCircuitView.class);

        Position position = Mockito.mock(Position.class);
        Position clone = Mockito.mock(Position.class);

        Mockito.when(view.getSelectedTile()).thenReturn(position);
        Mockito.when(position.clone()).thenReturn(clone);

        Mockito.when(view.inMenu()).thenReturn(false);

        Screen screen = Mockito.mock(Screen.class);
        Mockito.when(view.getScreen()).thenReturn(screen);

        KeyStroke key = Mockito.mock(KeyStroke.class);
        try {
            Mockito.when(screen.readInput()).thenReturn(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.when(key.getKeyType()).thenReturn(KeyType.Character);
        Mockito.when(key.getCharacter()).thenReturn('d');

        LanternaInput input = Mockito.mock(LanternaInput.class, Mockito.withSettings().useConstructor(view));

        Mockito.doCallRealMethod().when(input).run();

        Mockito.when(input.isInterrupted()).thenReturn(false);

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Event event = invocation.getArgument(0);
            Assertions.assertEquals(InputEvent.ADD_TILE, event.getInputEvent());
            Assertions.assertTrue(event.getObject() instanceof DigitTile);
            Assertions.assertEquals(clone, ((DigitTile) event.getObject()).getPosition());
            return null;
        }).when(view).pushEvent(Mockito.any(Event.class));

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Mockito.when(input.isInterrupted()).thenReturn(true);
            return null;
        }).when(view).inMenu();

        input.run();

        Mockito.verify(input, Mockito.times(2)).isInterrupted();
        Mockito.verify(view, Mockito.times(1)).getScreen();
        try {
            Mockito.verify(screen, Mockito.times(1)).readInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.verify(key, Mockito.times(1)).getKeyType();
        Mockito.verify(key, Mockito.times(1)).getCharacter();

        Mockito.verify(view, Mockito.times(1))
                .pushEvent(Mockito.any(Event.class));
        Mockito.verify(view, Mockito.times(1))
                .pushEvent(Mockito.any());

        Mockito.verify(view, Mockito.times(1)).getSelectedTile();
        Mockito.verify(position, Mockito.times(1)).clone();

        Mockito.verify(view, Mockito.times(0)).showSaveCircuitMenu();
        Mockito.verify(view, Mockito.times(0)).showInsertCustomMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showHelpMenu();
        Mockito.verify(view, Mockito.times(0)).showTileInfo(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showSetDelayMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleAutoAdvance();
        Mockito.verify(view, Mockito.times(0)).showInsertGateMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showInsertMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleShowPower();
        Mockito.verify(view, Mockito.times(0)).moveViewWindow(Mockito.any(Side.class));
        Mockito.verify(view, Mockito.times(0)).moveSelectedTile(Mockito.any(Side.class));
    }

    @Test
    @Tag("view") @Tag("model")
    @Tag("integration-test") @Tag("fast")
    public void testInputP() {
        LanternaCircuitView view = Mockito.mock(LanternaCircuitView.class);

        Position position = Mockito.mock(Position.class);
        Position clone = Mockito.mock(Position.class);

        Mockito.when(view.getSelectedTile()).thenReturn(position);
        Mockito.when(position.clone()).thenReturn(clone);

        Mockito.when(view.inMenu()).thenReturn(false);

        Screen screen = Mockito.mock(Screen.class);
        Mockito.when(view.getScreen()).thenReturn(screen);

        KeyStroke key = Mockito.mock(KeyStroke.class);
        try {
            Mockito.when(screen.readInput()).thenReturn(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.when(key.getKeyType()).thenReturn(KeyType.Character);
        Mockito.when(key.getCharacter()).thenReturn('p');

        LanternaInput input = Mockito.mock(LanternaInput.class, Mockito.withSettings().useConstructor(view));

        Mockito.doCallRealMethod().when(input).run();

        Mockito.when(input.isInterrupted()).thenReturn(false);

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Assertions.fail("Shouldn't call this method");
            return null;
        }).when(view).pushEvent(Mockito.any(Event.class));

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Mockito.when(input.isInterrupted()).thenReturn(true);
            return null;
        }).when(view).inMenu();

        input.run();

        Mockito.verify(input, Mockito.times(2)).isInterrupted();
        Mockito.verify(view, Mockito.times(1)).getScreen();
        try {
            Mockito.verify(screen, Mockito.times(1)).readInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.verify(key, Mockito.times(1)).getKeyType();
        Mockito.verify(key, Mockito.times(1)).getCharacter();

        Mockito.verify(view, Mockito.times(0))
                .pushEvent(Mockito.any(Event.class));
        Mockito.verify(view, Mockito.times(0))
                .pushEvent(Mockito.any());

        Mockito.verify(view, Mockito.times(0)).getSelectedTile();
        Mockito.verify(position, Mockito.times(0)).clone();

        Mockito.verify(view, Mockito.times(0)).showSaveCircuitMenu();
        Mockito.verify(view, Mockito.times(0)).showInsertCustomMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showHelpMenu();
        Mockito.verify(view, Mockito.times(0)).showTileInfo(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showSetDelayMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleAutoAdvance();
        Mockito.verify(view, Mockito.times(0)).showInsertGateMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showInsertMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(1)).toggleShowPower();
        Mockito.verify(view, Mockito.times(0)).moveViewWindow(Mockito.any(Side.class));
        Mockito.verify(view, Mockito.times(0)).moveSelectedTile(Mockito.any(Side.class));
    }

    @Test
    @Tag("view") @Tag("model")
    @Tag("integration-test") @Tag("fast")
    public void testInputG() {
        LanternaCircuitView view = Mockito.mock(LanternaCircuitView.class);

        Position position = Mockito.mock(Position.class);
        Position clone = Mockito.mock(Position.class);

        Mockito.when(view.getSelectedTile()).thenReturn(position);
        Mockito.when(position.clone()).thenReturn(clone);

        Mockito.when(view.inMenu()).thenReturn(false);

        Screen screen = Mockito.mock(Screen.class);
        Mockito.when(view.getScreen()).thenReturn(screen);

        KeyStroke key = Mockito.mock(KeyStroke.class);
        try {
            Mockito.when(screen.readInput()).thenReturn(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.when(key.getKeyType()).thenReturn(KeyType.Character);
        Mockito.when(key.getCharacter()).thenReturn('g');

        LanternaInput input = Mockito.mock(LanternaInput.class, Mockito.withSettings().useConstructor(view));

        Mockito.doCallRealMethod().when(input).run();

        Mockito.when(input.isInterrupted()).thenReturn(false);

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Assertions.fail("Shouldn't call this method");
            return null;
        }).when(view).pushEvent(Mockito.any(Event.class));

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Mockito.when(input.isInterrupted()).thenReturn(true);
            return null;
        }).when(view).inMenu();

        input.run();

        Mockito.verify(input, Mockito.times(2)).isInterrupted();
        Mockito.verify(view, Mockito.times(1)).getScreen();
        try {
            Mockito.verify(screen, Mockito.times(1)).readInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.verify(key, Mockito.times(1)).getKeyType();
        Mockito.verify(key, Mockito.times(1)).getCharacter();

        Mockito.verify(view, Mockito.times(0))
                .pushEvent(Mockito.any(Event.class));
        Mockito.verify(view, Mockito.times(0))
                .pushEvent(Mockito.any());

        Mockito.verify(view, Mockito.times(0)).getSelectedTile();
        Mockito.verify(position, Mockito.times(0)).clone();

        Mockito.verify(view, Mockito.times(1)).showSaveCircuitMenu();
        Mockito.verify(view, Mockito.times(0)).showInsertCustomMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showHelpMenu();
        Mockito.verify(view, Mockito.times(0)).showTileInfo(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showSetDelayMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleAutoAdvance();
        Mockito.verify(view, Mockito.times(0)).showInsertGateMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showInsertMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleShowPower();
        Mockito.verify(view, Mockito.times(0)).moveViewWindow(Mockito.any(Side.class));
        Mockito.verify(view, Mockito.times(0)).moveSelectedTile(Mockito.any(Side.class));
    }

    @Test
    @Tag("view") @Tag("model")
    @Tag("integration-test") @Tag("fast")
    public void testInputO() {
        LanternaCircuitView view = Mockito.mock(LanternaCircuitView.class);

        Position position = Mockito.mock(Position.class);
        Position clone = Mockito.mock(Position.class);

        Mockito.when(view.getSelectedTile()).thenReturn(position);
        Mockito.when(position.clone()).thenReturn(clone);

        Mockito.when(view.inMenu()).thenReturn(false);

        Screen screen = Mockito.mock(Screen.class);
        Mockito.when(view.getScreen()).thenReturn(screen);

        KeyStroke key = Mockito.mock(KeyStroke.class);
        try {
            Mockito.when(screen.readInput()).thenReturn(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.when(key.getKeyType()).thenReturn(KeyType.Character);
        Mockito.when(key.getCharacter()).thenReturn('o');

        LanternaInput input = Mockito.mock(LanternaInput.class, Mockito.withSettings().useConstructor(view));

        Mockito.doCallRealMethod().when(input).run();

        Mockito.when(input.isInterrupted()).thenReturn(false);

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Assertions.fail("Shouldn't call this method");
            return null;
        }).when(view).pushEvent(Mockito.any(Event.class));

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Mockito.when(input.isInterrupted()).thenReturn(true);
            return null;
        }).when(view).inMenu();

        input.run();

        Mockito.verify(input, Mockito.times(2)).isInterrupted();
        Mockito.verify(view, Mockito.times(1)).getScreen();
        try {
            Mockito.verify(screen, Mockito.times(1)).readInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.verify(key, Mockito.times(1)).getKeyType();
        Mockito.verify(key, Mockito.times(1)).getCharacter();

        Mockito.verify(view, Mockito.times(0))
                .pushEvent(Mockito.any(Event.class));
        Mockito.verify(view, Mockito.times(0))
                .pushEvent(Mockito.any());

        Mockito.verify(view, Mockito.times(1)).getSelectedTile();
        Mockito.verify(position, Mockito.times(1)).clone();

        Mockito.verify(view, Mockito.times(0)).showSaveCircuitMenu();
        Mockito.verify(view, Mockito.times(1)).showInsertCustomMenu(clone);
        Mockito.verify(view, Mockito.times(1)).showInsertCustomMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showHelpMenu();
        Mockito.verify(view, Mockito.times(0)).showTileInfo(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showSetDelayMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleAutoAdvance();
        Mockito.verify(view, Mockito.times(0)).showInsertGateMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showInsertMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleShowPower();
        Mockito.verify(view, Mockito.times(0)).moveViewWindow(Mockito.any(Side.class));
        Mockito.verify(view, Mockito.times(0)).moveSelectedTile(Mockito.any(Side.class));
    }

    @Test
    @Tag("view") @Tag("model")
    @Tag("integration-test") @Tag("fast")
    public void testInputH() {
        LanternaCircuitView view = Mockito.mock(LanternaCircuitView.class);

        Position position = Mockito.mock(Position.class);
        Position clone = Mockito.mock(Position.class);

        Mockito.when(view.getSelectedTile()).thenReturn(position);
        Mockito.when(position.clone()).thenReturn(clone);

        Mockito.when(view.inMenu()).thenReturn(false);

        Screen screen = Mockito.mock(Screen.class);
        Mockito.when(view.getScreen()).thenReturn(screen);

        KeyStroke key = Mockito.mock(KeyStroke.class);
        try {
            Mockito.when(screen.readInput()).thenReturn(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.when(key.getKeyType()).thenReturn(KeyType.Character);
        Mockito.when(key.getCharacter()).thenReturn('h');

        LanternaInput input = Mockito.mock(LanternaInput.class, Mockito.withSettings().useConstructor(view));

        Mockito.doCallRealMethod().when(input).run();

        Mockito.when(input.isInterrupted()).thenReturn(false);

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Assertions.fail("Shouldn't call this method");
            return null;
        }).when(view).pushEvent(Mockito.any(Event.class));

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Mockito.when(input.isInterrupted()).thenReturn(true);
            return null;
        }).when(view).inMenu();

        input.run();

        Mockito.verify(input, Mockito.times(2)).isInterrupted();
        Mockito.verify(view, Mockito.times(1)).getScreen();
        try {
            Mockito.verify(screen, Mockito.times(1)).readInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.verify(key, Mockito.times(1)).getKeyType();
        Mockito.verify(key, Mockito.times(1)).getCharacter();

        Mockito.verify(view, Mockito.times(0))
                .pushEvent(Mockito.any(Event.class));
        Mockito.verify(view, Mockito.times(0))
                .pushEvent(Mockito.any());

        Mockito.verify(view, Mockito.times(0)).getSelectedTile();
        Mockito.verify(position, Mockito.times(0)).clone();

        Mockito.verify(view, Mockito.times(0)).showSaveCircuitMenu();
        Mockito.verify(view, Mockito.times(0)).showInsertCustomMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(1)).showHelpMenu();
        Mockito.verify(view, Mockito.times(0)).showTileInfo(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showSetDelayMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleAutoAdvance();
        Mockito.verify(view, Mockito.times(0)).showInsertGateMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showInsertMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleShowPower();
        Mockito.verify(view, Mockito.times(0)).moveViewWindow(Mockito.any(Side.class));
        Mockito.verify(view, Mockito.times(0)).moveSelectedTile(Mockito.any(Side.class));
    }

    @Test
    @Tag("view") @Tag("model")
    @Tag("integration-test") @Tag("fast")
    public void testInputF() {
        LanternaCircuitView view = Mockito.mock(LanternaCircuitView.class);

        Position position = Mockito.mock(Position.class);
        Position clone = Mockito.mock(Position.class);

        Mockito.when(view.getSelectedTile()).thenReturn(position);
        Mockito.when(position.clone()).thenReturn(clone);

        Mockito.when(view.inMenu()).thenReturn(false);

        Screen screen = Mockito.mock(Screen.class);
        Mockito.when(view.getScreen()).thenReturn(screen);

        KeyStroke key = Mockito.mock(KeyStroke.class);
        try {
            Mockito.when(screen.readInput()).thenReturn(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.when(key.getKeyType()).thenReturn(KeyType.Character);
        Mockito.when(key.getCharacter()).thenReturn('f');

        LanternaInput input = Mockito.mock(LanternaInput.class, Mockito.withSettings().useConstructor(view));

        Mockito.doCallRealMethod().when(input).run();

        Mockito.when(input.isInterrupted()).thenReturn(false);

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Assertions.fail("Shouldn't call this method");
            return null;
        }).when(view).pushEvent(Mockito.any(Event.class));

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Mockito.when(input.isInterrupted()).thenReturn(true);
            return null;
        }).when(view).inMenu();

        input.run();

        Mockito.verify(input, Mockito.times(2)).isInterrupted();
        Mockito.verify(view, Mockito.times(1)).getScreen();
        try {
            Mockito.verify(screen, Mockito.times(1)).readInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.verify(key, Mockito.times(1)).getKeyType();
        Mockito.verify(key, Mockito.times(1)).getCharacter();

        Mockito.verify(view, Mockito.times(0))
                .pushEvent(Mockito.any(Event.class));
        Mockito.verify(view, Mockito.times(0))
                .pushEvent(Mockito.any());

        Mockito.verify(view, Mockito.times(1)).getSelectedTile();
        Mockito.verify(position, Mockito.times(1)).clone();

        Mockito.verify(view, Mockito.times(0)).showSaveCircuitMenu();
        Mockito.verify(view, Mockito.times(0)).showInsertCustomMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showHelpMenu();
        Mockito.verify(view, Mockito.times(1)).showTileInfo(clone);
        Mockito.verify(view, Mockito.times(1)).showTileInfo(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showSetDelayMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleAutoAdvance();
        Mockito.verify(view, Mockito.times(0)).showInsertGateMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showInsertMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleShowPower();
        Mockito.verify(view, Mockito.times(0)).moveViewWindow(Mockito.any(Side.class));
        Mockito.verify(view, Mockito.times(0)).moveSelectedTile(Mockito.any(Side.class));
    }

    @Test
    @Tag("view") @Tag("model")
    @Tag("integration-test") @Tag("fast")
    public void testInputM() {
        LanternaCircuitView view = Mockito.mock(LanternaCircuitView.class);

        Position position = Mockito.mock(Position.class);
        Position clone = Mockito.mock(Position.class);

        Mockito.when(view.getSelectedTile()).thenReturn(position);
        Mockito.when(position.clone()).thenReturn(clone);

        Mockito.when(view.inMenu()).thenReturn(false);

        Screen screen = Mockito.mock(Screen.class);
        Mockito.when(view.getScreen()).thenReturn(screen);

        KeyStroke key = Mockito.mock(KeyStroke.class);
        try {
            Mockito.when(screen.readInput()).thenReturn(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.when(key.getKeyType()).thenReturn(KeyType.Character);
        Mockito.when(key.getCharacter()).thenReturn('m');

        LanternaInput input = Mockito.mock(LanternaInput.class, Mockito.withSettings().useConstructor(view));

        Mockito.doCallRealMethod().when(input).run();

        Mockito.when(input.isInterrupted()).thenReturn(false);

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Assertions.fail("Shouldn't call this method");
            return null;
        }).when(view).pushEvent(Mockito.any(Event.class));

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Mockito.when(input.isInterrupted()).thenReturn(true);
            return null;
        }).when(view).inMenu();

        input.run();

        Mockito.verify(input, Mockito.times(2)).isInterrupted();
        Mockito.verify(view, Mockito.times(1)).getScreen();
        try {
            Mockito.verify(screen, Mockito.times(1)).readInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.verify(key, Mockito.times(1)).getKeyType();
        Mockito.verify(key, Mockito.times(1)).getCharacter();

        Mockito.verify(view, Mockito.times(0))
                .pushEvent(Mockito.any(Event.class));
        Mockito.verify(view, Mockito.times(0))
                .pushEvent(Mockito.any());

        Mockito.verify(view, Mockito.times(1)).getSelectedTile();
        Mockito.verify(position, Mockito.times(1)).clone();

        Mockito.verify(view, Mockito.times(0)).showSaveCircuitMenu();
        Mockito.verify(view, Mockito.times(0)).showInsertCustomMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showHelpMenu();
        Mockito.verify(view, Mockito.times(0)).showTileInfo(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(1)).showSetDelayMenu(clone);
        Mockito.verify(view, Mockito.times(1)).showSetDelayMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleAutoAdvance();
        Mockito.verify(view, Mockito.times(0)).showInsertGateMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showInsertMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleShowPower();
        Mockito.verify(view, Mockito.times(0)).moveViewWindow(Mockito.any(Side.class));
        Mockito.verify(view, Mockito.times(0)).moveSelectedTile(Mockito.any(Side.class));
    }

    @Test
    @Tag("view") @Tag("model")
    @Tag("integration-test") @Tag("fast")
    public void testInputA() {
        LanternaCircuitView view = Mockito.mock(LanternaCircuitView.class);

        Position position = Mockito.mock(Position.class);
        Position clone = Mockito.mock(Position.class);

        Mockito.when(view.getSelectedTile()).thenReturn(position);
        Mockito.when(position.clone()).thenReturn(clone);

        Mockito.when(view.inMenu()).thenReturn(false);

        Screen screen = Mockito.mock(Screen.class);
        Mockito.when(view.getScreen()).thenReturn(screen);

        KeyStroke key = Mockito.mock(KeyStroke.class);
        try {
            Mockito.when(screen.readInput()).thenReturn(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.when(key.getKeyType()).thenReturn(KeyType.Character);
        Mockito.when(key.getCharacter()).thenReturn('a');

        LanternaInput input = Mockito.mock(LanternaInput.class, Mockito.withSettings().useConstructor(view));

        Mockito.doCallRealMethod().when(input).run();

        Mockito.when(input.isInterrupted()).thenReturn(false);

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Assertions.fail("Shouldn't call this method");
            return null;
        }).when(view).pushEvent(Mockito.any(Event.class));

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Mockito.when(input.isInterrupted()).thenReturn(true);
            return null;
        }).when(view).inMenu();

        input.run();

        Mockito.verify(input, Mockito.times(2)).isInterrupted();
        Mockito.verify(view, Mockito.times(1)).getScreen();
        try {
            Mockito.verify(screen, Mockito.times(1)).readInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.verify(key, Mockito.times(1)).getKeyType();
        Mockito.verify(key, Mockito.times(1)).getCharacter();

        Mockito.verify(view, Mockito.times(0))
                .pushEvent(Mockito.any(Event.class));
        Mockito.verify(view, Mockito.times(0))
                .pushEvent(Mockito.any());

        Mockito.verify(view, Mockito.times(0)).getSelectedTile();
        Mockito.verify(position, Mockito.times(0)).clone();

        Mockito.verify(view, Mockito.times(0)).showSaveCircuitMenu();
        Mockito.verify(view, Mockito.times(0)).showInsertCustomMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showHelpMenu();
        Mockito.verify(view, Mockito.times(0)).showTileInfo(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showSetDelayMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(1)).toggleAutoAdvance();
        Mockito.verify(view, Mockito.times(0)).showInsertGateMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showInsertMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleShowPower();
        Mockito.verify(view, Mockito.times(0)).moveViewWindow(Mockito.any(Side.class));
        Mockito.verify(view, Mockito.times(0)).moveSelectedTile(Mockito.any(Side.class));
    }

    @Test
    @Tag("view") @Tag("model")
    @Tag("integration-test") @Tag("fast")
    public void testInput0() {
        LanternaCircuitView view = Mockito.mock(LanternaCircuitView.class);

        Position position = Mockito.mock(Position.class);
        Position clone = Mockito.mock(Position.class);

        Mockito.when(view.getSelectedTile()).thenReturn(position);
        Mockito.when(position.clone()).thenReturn(clone);

        Mockito.when(view.inMenu()).thenReturn(false);

        Screen screen = Mockito.mock(Screen.class);
        Mockito.when(view.getScreen()).thenReturn(screen);

        KeyStroke key = Mockito.mock(KeyStroke.class);
        try {
            Mockito.when(screen.readInput()).thenReturn(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.when(key.getKeyType()).thenReturn(KeyType.Character);
        Mockito.when(key.getCharacter()).thenReturn('0');

        LanternaInput input = Mockito.mock(LanternaInput.class, Mockito.withSettings().useConstructor(view));

        Mockito.doCallRealMethod().when(input).run();

        Mockito.when(input.isInterrupted()).thenReturn(false);

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Assertions.fail("Shouldn't call this method");
            return null;
        }).when(view).pushEvent(Mockito.any(Event.class));

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Mockito.when(input.isInterrupted()).thenReturn(true);
            return null;
        }).when(view).inMenu();

        input.run();

        Mockito.verify(input, Mockito.times(2)).isInterrupted();
        Mockito.verify(view, Mockito.times(1)).getScreen();
        try {
            Mockito.verify(screen, Mockito.times(1)).readInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.verify(key, Mockito.times(1)).getKeyType();
        Mockito.verify(key, Mockito.times(1)).getCharacter();

        Mockito.verify(view, Mockito.times(0))
                .pushEvent(Mockito.any(Event.class));
        Mockito.verify(view, Mockito.times(0))
                .pushEvent(Mockito.any());

        Mockito.verify(view, Mockito.times(1)).getSelectedTile();
        Mockito.verify(position, Mockito.times(1)).clone();

        Mockito.verify(view, Mockito.times(0)).showSaveCircuitMenu();
        Mockito.verify(view, Mockito.times(0)).showInsertCustomMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showHelpMenu();
        Mockito.verify(view, Mockito.times(0)).showTileInfo(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showSetDelayMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleAutoAdvance();
        Mockito.verify(view, Mockito.times(1)).showInsertGateMenu(clone);
        Mockito.verify(view, Mockito.times(1)).showInsertGateMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showInsertMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleShowPower();
        Mockito.verify(view, Mockito.times(0)).moveViewWindow(Mockito.any(Side.class));
        Mockito.verify(view, Mockito.times(0)).moveSelectedTile(Mockito.any(Side.class));
    }

    @Test
    @Tag("view") @Tag("model")
    @Tag("integration-test") @Tag("fast")
    public void testInputArrowLeftMoveSelected() {
        LanternaCircuitView view = Mockito.mock(LanternaCircuitView.class);

        Position position = Mockito.mock(Position.class);
        Position clone = Mockito.mock(Position.class);

        Mockito.when(view.getSelectedTile()).thenReturn(position);
        Mockito.when(position.clone()).thenReturn(clone);

        Mockito.when(view.inMenu()).thenReturn(false);

        Screen screen = Mockito.mock(Screen.class);
        Mockito.when(view.getScreen()).thenReturn(screen);

        KeyStroke key = Mockito.mock(KeyStroke.class);
        try {
            Mockito.when(screen.readInput()).thenReturn(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.when(key.getKeyType()).thenReturn(KeyType.ArrowLeft);
        Mockito.when(key.getCharacter()).thenReturn('f');

        LanternaInput input = Mockito.mock(LanternaInput.class, Mockito.withSettings().useConstructor(view));

        Mockito.doCallRealMethod().when(input).run();

        Mockito.when(input.isInterrupted()).thenReturn(false);

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Assertions.fail("Shouldn't call this method");
            return null;
        }).when(view).pushEvent(Mockito.any(Event.class));

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Mockito.when(input.isInterrupted()).thenReturn(true);
            return null;
        }).when(view).inMenu();

        input.run();

        Mockito.verify(input, Mockito.times(2)).isInterrupted();
        Mockito.verify(view, Mockito.times(1)).getScreen();
        try {
            Mockito.verify(screen, Mockito.times(1)).readInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.verify(key, Mockito.times(1)).getKeyType();
        Mockito.verify(key, Mockito.times(0)).getCharacter();

        Mockito.verify(view, Mockito.times(0))
                .pushEvent(Mockito.any(Event.class));
        Mockito.verify(view, Mockito.times(0))
                .pushEvent(Mockito.any());

        Mockito.verify(view, Mockito.times(0)).getSelectedTile();
        Mockito.verify(position, Mockito.times(0)).clone();

        Mockito.verify(view, Mockito.times(0)).showSaveCircuitMenu();
        Mockito.verify(view, Mockito.times(0)).showInsertCustomMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showHelpMenu();
        Mockito.verify(view, Mockito.times(0)).showTileInfo(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showSetDelayMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleAutoAdvance();
        Mockito.verify(view, Mockito.times(0)).showInsertGateMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showInsertMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleShowPower();
        Mockito.verify(view, Mockito.times(0)).moveViewWindow(Mockito.any(Side.class));
        Mockito.verify(view, Mockito.times(1)).moveSelectedTile(Side.LEFT);
        Mockito.verify(view, Mockito.times(1)).moveSelectedTile(Mockito.any(Side.class));
    }

    @Test
    @Tag("view") @Tag("model")
    @Tag("integration-test") @Tag("fast")
    public void testInputArrowLeftMoveView() {
        LanternaCircuitView view = Mockito.mock(LanternaCircuitView.class);

        Position position = Mockito.mock(Position.class);
        Position clone = Mockito.mock(Position.class);

        Mockito.when(view.getSelectedTile()).thenReturn(position);
        Mockito.when(position.clone()).thenReturn(clone);

        Mockito.when(view.inMenu()).thenReturn(false);

        Screen screen = Mockito.mock(Screen.class);
        Mockito.when(view.getScreen()).thenReturn(screen);

        KeyStroke key = Mockito.mock(KeyStroke.class);
        try {
            Mockito.when(screen.readInput()).thenReturn(key);
        } catch (IOException e) {
            e.printStackTrace();
        }

        AtomicInteger callsToInMenu = new AtomicInteger(0);

        LanternaInput input = Mockito.mock(LanternaInput.class, Mockito.withSettings().useConstructor(view));

        Mockito.doCallRealMethod().when(input).run();

        Mockito.when(input.isInterrupted()).thenReturn(false);

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Assertions.fail("Shouldn't call this method");
            return null;
        }).when(view).pushEvent(Mockito.any(Event.class));

        Mockito.doAnswer((Answer<Void>) invocation -> {
            if (callsToInMenu.incrementAndGet() >= 2) {
                Mockito.when(key.getKeyType()).thenReturn(KeyType.ArrowLeft);
                Mockito.when(key.getCharacter()).thenReturn('f');
                Mockito.when(input.isInterrupted()).thenReturn(true);
            }
            return null;
        }).when(view).inMenu();

        Mockito.when(key.getKeyType()).thenReturn(KeyType.Character);
        Mockito.when(key.getCharacter()).thenReturn('z');

        input.run();

        Mockito.verify(input, Mockito.times(3)).isInterrupted();
        Mockito.verify(view, Mockito.times(2)).getScreen();
        try {
            Mockito.verify(screen, Mockito.times(2)).readInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.verify(key, Mockito.times(2)).getKeyType();
        Mockito.verify(key, Mockito.times(1)).getCharacter();

        Mockito.verify(view, Mockito.times(0))
                .pushEvent(Mockito.any(Event.class));
        Mockito.verify(view, Mockito.times(0))
                .pushEvent(Mockito.any());

        Mockito.verify(view, Mockito.times(0)).getSelectedTile();
        Mockito.verify(position, Mockito.times(0)).clone();

        Mockito.verify(view, Mockito.times(0)).showSaveCircuitMenu();
        Mockito.verify(view, Mockito.times(0)).showInsertCustomMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showHelpMenu();
        Mockito.verify(view, Mockito.times(0)).showTileInfo(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showSetDelayMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleAutoAdvance();
        Mockito.verify(view, Mockito.times(0)).showInsertGateMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showInsertMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleShowPower();
        Mockito.verify(view, Mockito.times(1)).moveViewWindow(Side.RIGHT);
        Mockito.verify(view, Mockito.times(1)).moveViewWindow(Mockito.any(Side.class));
        Mockito.verify(view, Mockito.times(0)).moveSelectedTile(Mockito.any(Side.class));
    }

    @Test
    @Tag("view") @Tag("model")
    @Tag("integration-test") @Tag("fast")
    public void testInputArrowRightMoveSelected() {
        LanternaCircuitView view = Mockito.mock(LanternaCircuitView.class);

        Position position = Mockito.mock(Position.class);
        Position clone = Mockito.mock(Position.class);

        Mockito.when(view.getSelectedTile()).thenReturn(position);
        Mockito.when(position.clone()).thenReturn(clone);

        Mockito.when(view.inMenu()).thenReturn(false);

        Screen screen = Mockito.mock(Screen.class);
        Mockito.when(view.getScreen()).thenReturn(screen);

        KeyStroke key = Mockito.mock(KeyStroke.class);
        try {
            Mockito.when(screen.readInput()).thenReturn(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.when(key.getKeyType()).thenReturn(KeyType.ArrowRight);
        Mockito.when(key.getCharacter()).thenReturn('f');

        LanternaInput input = Mockito.mock(LanternaInput.class, Mockito.withSettings().useConstructor(view));

        Mockito.doCallRealMethod().when(input).run();

        Mockito.when(input.isInterrupted()).thenReturn(false);

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Assertions.fail("Shouldn't call this method");
            return null;
        }).when(view).pushEvent(Mockito.any(Event.class));

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Mockito.when(input.isInterrupted()).thenReturn(true);
            return null;
        }).when(view).inMenu();

        input.run();

        Mockito.verify(input, Mockito.times(2)).isInterrupted();
        Mockito.verify(view, Mockito.times(1)).getScreen();
        try {
            Mockito.verify(screen, Mockito.times(1)).readInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.verify(key, Mockito.times(1)).getKeyType();
        Mockito.verify(key, Mockito.times(0)).getCharacter();

        Mockito.verify(view, Mockito.times(0))
                .pushEvent(Mockito.any(Event.class));
        Mockito.verify(view, Mockito.times(0))
                .pushEvent(Mockito.any());

        Mockito.verify(view, Mockito.times(0)).getSelectedTile();
        Mockito.verify(position, Mockito.times(0)).clone();

        Mockito.verify(view, Mockito.times(0)).showSaveCircuitMenu();
        Mockito.verify(view, Mockito.times(0)).showInsertCustomMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showHelpMenu();
        Mockito.verify(view, Mockito.times(0)).showTileInfo(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showSetDelayMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleAutoAdvance();
        Mockito.verify(view, Mockito.times(0)).showInsertGateMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showInsertMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleShowPower();
        Mockito.verify(view, Mockito.times(0)).moveViewWindow(Mockito.any(Side.class));
        Mockito.verify(view, Mockito.times(1)).moveSelectedTile(Side.RIGHT);
        Mockito.verify(view, Mockito.times(1)).moveSelectedTile(Mockito.any(Side.class));
    }

    @Test
    @Tag("view") @Tag("model")
    @Tag("integration-test") @Tag("fast")
    public void testInputArrowRightMoveView() {
        LanternaCircuitView view = Mockito.mock(LanternaCircuitView.class);

        Position position = Mockito.mock(Position.class);
        Position clone = Mockito.mock(Position.class);

        Mockito.when(view.getSelectedTile()).thenReturn(position);
        Mockito.when(position.clone()).thenReturn(clone);

        Mockito.when(view.inMenu()).thenReturn(false);

        Screen screen = Mockito.mock(Screen.class);
        Mockito.when(view.getScreen()).thenReturn(screen);

        KeyStroke key = Mockito.mock(KeyStroke.class);
        try {
            Mockito.when(screen.readInput()).thenReturn(key);
        } catch (IOException e) {
            e.printStackTrace();
        }

        AtomicInteger callsToInMenu = new AtomicInteger(0);

        LanternaInput input = Mockito.mock(LanternaInput.class, Mockito.withSettings().useConstructor(view));

        Mockito.doCallRealMethod().when(input).run();

        Mockito.when(input.isInterrupted()).thenReturn(false);

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Assertions.fail("Shouldn't call this method");
            return null;
        }).when(view).pushEvent(Mockito.any(Event.class));

        Mockito.doAnswer((Answer<Void>) invocation -> {
            if (callsToInMenu.incrementAndGet() >= 2) {
                Mockito.when(key.getKeyType()).thenReturn(KeyType.ArrowRight);
                Mockito.when(key.getCharacter()).thenReturn('f');
                Mockito.when(input.isInterrupted()).thenReturn(true);
            }
            return null;
        }).when(view).inMenu();

        Mockito.when(key.getKeyType()).thenReturn(KeyType.Character);
        Mockito.when(key.getCharacter()).thenReturn('z');

        input.run();

        Mockito.verify(input, Mockito.times(3)).isInterrupted();
        Mockito.verify(view, Mockito.times(2)).getScreen();
        try {
            Mockito.verify(screen, Mockito.times(2)).readInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.verify(key, Mockito.times(2)).getKeyType();
        Mockito.verify(key, Mockito.times(1)).getCharacter();

        Mockito.verify(view, Mockito.times(0))
                .pushEvent(Mockito.any(Event.class));
        Mockito.verify(view, Mockito.times(0))
                .pushEvent(Mockito.any());

        Mockito.verify(view, Mockito.times(0)).getSelectedTile();
        Mockito.verify(position, Mockito.times(0)).clone();

        Mockito.verify(view, Mockito.times(0)).showSaveCircuitMenu();
        Mockito.verify(view, Mockito.times(0)).showInsertCustomMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showHelpMenu();
        Mockito.verify(view, Mockito.times(0)).showTileInfo(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showSetDelayMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleAutoAdvance();
        Mockito.verify(view, Mockito.times(0)).showInsertGateMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showInsertMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleShowPower();
        Mockito.verify(view, Mockito.times(1)).moveViewWindow(Side.LEFT);
        Mockito.verify(view, Mockito.times(1)).moveViewWindow(Mockito.any(Side.class));
        Mockito.verify(view, Mockito.times(0)).moveSelectedTile(Mockito.any(Side.class));
    }

    @Test
    @Tag("view") @Tag("model")
    @Tag("integration-test") @Tag("fast")
    public void testInputArrowUpMoveSelected() {
        LanternaCircuitView view = Mockito.mock(LanternaCircuitView.class);

        Position position = Mockito.mock(Position.class);
        Position clone = Mockito.mock(Position.class);

        Mockito.when(view.getSelectedTile()).thenReturn(position);
        Mockito.when(position.clone()).thenReturn(clone);

        Mockito.when(view.inMenu()).thenReturn(false);

        Screen screen = Mockito.mock(Screen.class);
        Mockito.when(view.getScreen()).thenReturn(screen);

        KeyStroke key = Mockito.mock(KeyStroke.class);
        try {
            Mockito.when(screen.readInput()).thenReturn(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.when(key.getKeyType()).thenReturn(KeyType.ArrowUp);
        Mockito.when(key.getCharacter()).thenReturn('f');

        LanternaInput input = Mockito.mock(LanternaInput.class, Mockito.withSettings().useConstructor(view));

        Mockito.doCallRealMethod().when(input).run();

        Mockito.when(input.isInterrupted()).thenReturn(false);

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Assertions.fail("Shouldn't call this method");
            return null;
        }).when(view).pushEvent(Mockito.any(Event.class));

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Mockito.when(input.isInterrupted()).thenReturn(true);
            return null;
        }).when(view).inMenu();

        input.run();

        Mockito.verify(input, Mockito.times(2)).isInterrupted();
        Mockito.verify(view, Mockito.times(1)).getScreen();
        try {
            Mockito.verify(screen, Mockito.times(1)).readInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.verify(key, Mockito.times(1)).getKeyType();
        Mockito.verify(key, Mockito.times(0)).getCharacter();

        Mockito.verify(view, Mockito.times(0))
                .pushEvent(Mockito.any(Event.class));
        Mockito.verify(view, Mockito.times(0))
                .pushEvent(Mockito.any());

        Mockito.verify(view, Mockito.times(0)).getSelectedTile();
        Mockito.verify(position, Mockito.times(0)).clone();

        Mockito.verify(view, Mockito.times(0)).showSaveCircuitMenu();
        Mockito.verify(view, Mockito.times(0)).showInsertCustomMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showHelpMenu();
        Mockito.verify(view, Mockito.times(0)).showTileInfo(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showSetDelayMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleAutoAdvance();
        Mockito.verify(view, Mockito.times(0)).showInsertGateMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showInsertMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleShowPower();
        Mockito.verify(view, Mockito.times(0)).moveViewWindow(Mockito.any(Side.class));
        Mockito.verify(view, Mockito.times(1)).moveSelectedTile(Side.UP);
        Mockito.verify(view, Mockito.times(1)).moveSelectedTile(Mockito.any(Side.class));
    }

    @Test
    @Tag("view") @Tag("model")
    @Tag("integration-test") @Tag("fast")
    public void testInputArrowUpMoveView() {
        LanternaCircuitView view = Mockito.mock(LanternaCircuitView.class);

        Position position = Mockito.mock(Position.class);
        Position clone = Mockito.mock(Position.class);

        Mockito.when(view.getSelectedTile()).thenReturn(position);
        Mockito.when(position.clone()).thenReturn(clone);

        Mockito.when(view.inMenu()).thenReturn(false);

        Screen screen = Mockito.mock(Screen.class);
        Mockito.when(view.getScreen()).thenReturn(screen);

        KeyStroke key = Mockito.mock(KeyStroke.class);
        try {
            Mockito.when(screen.readInput()).thenReturn(key);
        } catch (IOException e) {
            e.printStackTrace();
        }

        AtomicInteger callsToInMenu = new AtomicInteger(0);

        LanternaInput input = Mockito.mock(LanternaInput.class, Mockito.withSettings().useConstructor(view));

        Mockito.doCallRealMethod().when(input).run();

        Mockito.when(input.isInterrupted()).thenReturn(false);

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Assertions.fail("Shouldn't call this method");
            return null;
        }).when(view).pushEvent(Mockito.any(Event.class));

        Mockito.doAnswer((Answer<Void>) invocation -> {
            if (callsToInMenu.incrementAndGet() >= 2) {
                Mockito.when(key.getKeyType()).thenReturn(KeyType.ArrowUp);
                Mockito.when(key.getCharacter()).thenReturn('f');
                Mockito.when(input.isInterrupted()).thenReturn(true);
            }
            return null;
        }).when(view).inMenu();

        Mockito.when(key.getKeyType()).thenReturn(KeyType.Character);
        Mockito.when(key.getCharacter()).thenReturn('z');

        input.run();

        Mockito.verify(input, Mockito.times(3)).isInterrupted();
        Mockito.verify(view, Mockito.times(2)).getScreen();
        try {
            Mockito.verify(screen, Mockito.times(2)).readInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.verify(key, Mockito.times(2)).getKeyType();
        Mockito.verify(key, Mockito.times(1)).getCharacter();

        Mockito.verify(view, Mockito.times(0))
                .pushEvent(Mockito.any(Event.class));
        Mockito.verify(view, Mockito.times(0))
                .pushEvent(Mockito.any());

        Mockito.verify(view, Mockito.times(0)).getSelectedTile();
        Mockito.verify(position, Mockito.times(0)).clone();

        Mockito.verify(view, Mockito.times(0)).showSaveCircuitMenu();
        Mockito.verify(view, Mockito.times(0)).showInsertCustomMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showHelpMenu();
        Mockito.verify(view, Mockito.times(0)).showTileInfo(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showSetDelayMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleAutoAdvance();
        Mockito.verify(view, Mockito.times(0)).showInsertGateMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showInsertMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleShowPower();
        Mockito.verify(view, Mockito.times(1)).moveViewWindow(Side.DOWN);
        Mockito.verify(view, Mockito.times(1)).moveViewWindow(Mockito.any(Side.class));
        Mockito.verify(view, Mockito.times(0)).moveSelectedTile(Mockito.any(Side.class));
    }

    @Test
    @Tag("view") @Tag("model")
    @Tag("integration-test") @Tag("fast")
    public void testInputArrowDownMoveSelected() {
        LanternaCircuitView view = Mockito.mock(LanternaCircuitView.class);

        Position position = Mockito.mock(Position.class);
        Position clone = Mockito.mock(Position.class);

        Mockito.when(view.getSelectedTile()).thenReturn(position);
        Mockito.when(position.clone()).thenReturn(clone);

        Mockito.when(view.inMenu()).thenReturn(false);

        Screen screen = Mockito.mock(Screen.class);
        Mockito.when(view.getScreen()).thenReturn(screen);

        KeyStroke key = Mockito.mock(KeyStroke.class);
        try {
            Mockito.when(screen.readInput()).thenReturn(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.when(key.getKeyType()).thenReturn(KeyType.ArrowDown);
        Mockito.when(key.getCharacter()).thenReturn('f');

        LanternaInput input = Mockito.mock(LanternaInput.class, Mockito.withSettings().useConstructor(view));

        Mockito.doCallRealMethod().when(input).run();

        Mockito.when(input.isInterrupted()).thenReturn(false);

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Assertions.fail("Shouldn't call this method");
            return null;
        }).when(view).pushEvent(Mockito.any(Event.class));

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Mockito.when(input.isInterrupted()).thenReturn(true);
            return null;
        }).when(view).inMenu();

        input.run();

        Mockito.verify(input, Mockito.times(2)).isInterrupted();
        Mockito.verify(view, Mockito.times(1)).getScreen();
        try {
            Mockito.verify(screen, Mockito.times(1)).readInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.verify(key, Mockito.times(1)).getKeyType();
        Mockito.verify(key, Mockito.times(0)).getCharacter();

        Mockito.verify(view, Mockito.times(0))
                .pushEvent(Mockito.any(Event.class));
        Mockito.verify(view, Mockito.times(0))
                .pushEvent(Mockito.any());

        Mockito.verify(view, Mockito.times(0)).getSelectedTile();
        Mockito.verify(position, Mockito.times(0)).clone();

        Mockito.verify(view, Mockito.times(0)).showSaveCircuitMenu();
        Mockito.verify(view, Mockito.times(0)).showInsertCustomMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showHelpMenu();
        Mockito.verify(view, Mockito.times(0)).showTileInfo(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showSetDelayMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleAutoAdvance();
        Mockito.verify(view, Mockito.times(0)).showInsertGateMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showInsertMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleShowPower();
        Mockito.verify(view, Mockito.times(0)).moveViewWindow(Mockito.any(Side.class));
        Mockito.verify(view, Mockito.times(1)).moveSelectedTile(Side.DOWN);
        Mockito.verify(view, Mockito.times(1)).moveSelectedTile(Mockito.any(Side.class));
    }

    @Test
    @Tag("view") @Tag("model")
    @Tag("integration-test") @Tag("fast")
    public void testInputArrowDownMoveView() {
        LanternaCircuitView view = Mockito.mock(LanternaCircuitView.class);

        Position position = Mockito.mock(Position.class);
        Position clone = Mockito.mock(Position.class);

        Mockito.when(view.getSelectedTile()).thenReturn(position);
        Mockito.when(position.clone()).thenReturn(clone);

        Mockito.when(view.inMenu()).thenReturn(false);

        Screen screen = Mockito.mock(Screen.class);
        Mockito.when(view.getScreen()).thenReturn(screen);

        KeyStroke key = Mockito.mock(KeyStroke.class);
        try {
            Mockito.when(screen.readInput()).thenReturn(key);
        } catch (IOException e) {
            e.printStackTrace();
        }

        AtomicInteger callsToInMenu = new AtomicInteger(0);

        LanternaInput input = Mockito.mock(LanternaInput.class, Mockito.withSettings().useConstructor(view));

        Mockito.doCallRealMethod().when(input).run();

        Mockito.when(input.isInterrupted()).thenReturn(false);

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Assertions.fail("Shouldn't call this method");
            return null;
        }).when(view).pushEvent(Mockito.any(Event.class));

        Mockito.doAnswer((Answer<Void>) invocation -> {
            if (callsToInMenu.incrementAndGet() >= 2) {
                Mockito.when(key.getKeyType()).thenReturn(KeyType.ArrowDown);
                Mockito.when(key.getCharacter()).thenReturn('f');
                Mockito.when(input.isInterrupted()).thenReturn(true);
            }
            return null;
        }).when(view).inMenu();

        Mockito.when(key.getKeyType()).thenReturn(KeyType.Character);
        Mockito.when(key.getCharacter()).thenReturn('z');

        input.run();

        Mockito.verify(input, Mockito.times(3)).isInterrupted();
        Mockito.verify(view, Mockito.times(2)).getScreen();
        try {
            Mockito.verify(screen, Mockito.times(2)).readInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.verify(key, Mockito.times(2)).getKeyType();
        Mockito.verify(key, Mockito.times(1)).getCharacter();

        Mockito.verify(view, Mockito.times(0))
                .pushEvent(Mockito.any(Event.class));
        Mockito.verify(view, Mockito.times(0))
                .pushEvent(Mockito.any());

        Mockito.verify(view, Mockito.times(0)).getSelectedTile();
        Mockito.verify(position, Mockito.times(0)).clone();

        Mockito.verify(view, Mockito.times(0)).showSaveCircuitMenu();
        Mockito.verify(view, Mockito.times(0)).showInsertCustomMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showHelpMenu();
        Mockito.verify(view, Mockito.times(0)).showTileInfo(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showSetDelayMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleAutoAdvance();
        Mockito.verify(view, Mockito.times(0)).showInsertGateMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showInsertMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleShowPower();
        Mockito.verify(view, Mockito.times(1)).moveViewWindow(Side.UP);
        Mockito.verify(view, Mockito.times(1)).moveViewWindow(Mockito.any(Side.class));
        Mockito.verify(view, Mockito.times(0)).moveSelectedTile(Mockito.any(Side.class));
    }

    @Test
    @Tag("view") @Tag("model")
    @Tag("integration-test") @Tag("fast")
    public void testInputEnter() {
        LanternaCircuitView view = Mockito.mock(LanternaCircuitView.class);

        Position position = Mockito.mock(Position.class);
        Position clone = Mockito.mock(Position.class);

        Mockito.when(view.getSelectedTile()).thenReturn(position);
        Mockito.when(position.clone()).thenReturn(clone);

        Mockito.when(view.inMenu()).thenReturn(false);

        Screen screen = Mockito.mock(Screen.class);
        Mockito.when(view.getScreen()).thenReturn(screen);

        KeyStroke key = Mockito.mock(KeyStroke.class);
        try {
            Mockito.when(screen.readInput()).thenReturn(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.when(key.getKeyType()).thenReturn(KeyType.Enter);
        Mockito.when(key.getCharacter()).thenReturn('f');

        LanternaInput input = Mockito.mock(LanternaInput.class, Mockito.withSettings().useConstructor(view));

        Mockito.doCallRealMethod().when(input).run();

        Mockito.when(input.isInterrupted()).thenReturn(false);

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Event event = invocation.getArgument(0);
            Assertions.assertEquals(InputEvent.INTERACT, event.getInputEvent());
            Assertions.assertTrue(event.getObject() instanceof Position);
            Assertions.assertEquals(clone, event.getObject());
            return null;
        }).when(view).pushEvent(Mockito.any(Event.class));

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Mockito.when(input.isInterrupted()).thenReturn(true);
            return null;
        }).when(view).inMenu();

        input.run();

        Mockito.verify(input, Mockito.times(2)).isInterrupted();
        Mockito.verify(view, Mockito.times(1)).getScreen();
        try {
            Mockito.verify(screen, Mockito.times(1)).readInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.verify(key, Mockito.times(1)).getKeyType();
        Mockito.verify(key, Mockito.times(0)).getCharacter();

        Mockito.verify(view, Mockito.times(1))
                .pushEvent(Mockito.any(Event.class));
        Mockito.verify(view, Mockito.times(1))
                .pushEvent(Mockito.any());

        Mockito.verify(view, Mockito.times(1)).getSelectedTile();
        Mockito.verify(position, Mockito.times(1)).clone();

        Mockito.verify(view, Mockito.times(0)).showSaveCircuitMenu();
        Mockito.verify(view, Mockito.times(0)).showInsertCustomMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showHelpMenu();
        Mockito.verify(view, Mockito.times(0)).showTileInfo(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showSetDelayMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleAutoAdvance();
        Mockito.verify(view, Mockito.times(0)).showInsertGateMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showInsertMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleShowPower();
        Mockito.verify(view, Mockito.times(0)).moveViewWindow(Mockito.any(Side.class));
        Mockito.verify(view, Mockito.times(0)).moveSelectedTile(Mockito.any(Side.class));
    }

    @Test
    @Tag("view") @Tag("model")
    @Tag("integration-test") @Tag("fast")
    public void testInputInsert() {
        LanternaCircuitView view = Mockito.mock(LanternaCircuitView.class);

        Position position = Mockito.mock(Position.class);
        Position clone = Mockito.mock(Position.class);

        Mockito.when(view.getSelectedTile()).thenReturn(position);
        Mockito.when(position.clone()).thenReturn(clone);

        Mockito.when(view.inMenu()).thenReturn(false);

        Screen screen = Mockito.mock(Screen.class);
        Mockito.when(view.getScreen()).thenReturn(screen);

        KeyStroke key = Mockito.mock(KeyStroke.class);
        try {
            Mockito.when(screen.readInput()).thenReturn(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.when(key.getKeyType()).thenReturn(KeyType.Insert);
        Mockito.when(key.getCharacter()).thenReturn('f');

        LanternaInput input = Mockito.mock(LanternaInput.class, Mockito.withSettings().useConstructor(view));

        Mockito.doCallRealMethod().when(input).run();

        Mockito.when(input.isInterrupted()).thenReturn(false);

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Assertions.fail("Shouldn't call this method");
            return null;
        }).when(view).pushEvent(Mockito.any(Event.class));

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Mockito.when(input.isInterrupted()).thenReturn(true);
            return null;
        }).when(view).inMenu();

        input.run();

        Mockito.verify(input, Mockito.times(2)).isInterrupted();
        Mockito.verify(view, Mockito.times(1)).getScreen();
        try {
            Mockito.verify(screen, Mockito.times(1)).readInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.verify(key, Mockito.times(1)).getKeyType();
        Mockito.verify(key, Mockito.times(0)).getCharacter();

        Mockito.verify(view, Mockito.times(0))
                .pushEvent(Mockito.any(Event.class));
        Mockito.verify(view, Mockito.times(0))
                .pushEvent(Mockito.any());

        Mockito.verify(view, Mockito.times(1)).getSelectedTile();
        Mockito.verify(position, Mockito.times(1)).clone();

        Mockito.verify(view, Mockito.times(0)).showSaveCircuitMenu();
        Mockito.verify(view, Mockito.times(0)).showInsertCustomMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showHelpMenu();
        Mockito.verify(view, Mockito.times(0)).showTileInfo(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showSetDelayMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleAutoAdvance();
        Mockito.verify(view, Mockito.times(0)).showInsertGateMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(1)).showInsertMenu(clone);
        Mockito.verify(view, Mockito.times(1)).showInsertMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleShowPower();
        Mockito.verify(view, Mockito.times(0)).moveViewWindow(Mockito.any(Side.class));
        Mockito.verify(view, Mockito.times(0)).moveSelectedTile(Mockito.any(Side.class));
    }

    @Test
    @Tag("view") @Tag("model")
    @Tag("integration-test") @Tag("fast")
    public void testInputDelete() {
        LanternaCircuitView view = Mockito.mock(LanternaCircuitView.class);

        Position position = Mockito.mock(Position.class);
        Position clone = Mockito.mock(Position.class);

        Mockito.when(view.getSelectedTile()).thenReturn(position);
        Mockito.when(position.clone()).thenReturn(clone);

        Mockito.when(view.inMenu()).thenReturn(false);

        Screen screen = Mockito.mock(Screen.class);
        Mockito.when(view.getScreen()).thenReturn(screen);

        KeyStroke key = Mockito.mock(KeyStroke.class);
        try {
            Mockito.when(screen.readInput()).thenReturn(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.when(key.getKeyType()).thenReturn(KeyType.Delete);
        Mockito.when(key.getCharacter()).thenReturn('f');

        LanternaInput input = Mockito.mock(LanternaInput.class, Mockito.withSettings().useConstructor(view));

        Mockito.doCallRealMethod().when(input).run();

        Mockito.when(input.isInterrupted()).thenReturn(false);

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Event event = invocation.getArgument(0);
            Assertions.assertEquals(InputEvent.ADD_TILE, event.getInputEvent());
            Assertions.assertTrue(event.getObject() instanceof NullTile);
            Assertions.assertEquals(clone, ((NullTile) event.getObject()).getPosition());
            return null;
        }).when(view).pushEvent(Mockito.any(Event.class));

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Mockito.when(input.isInterrupted()).thenReturn(true);
            return null;
        }).when(view).inMenu();

        input.run();

        Mockito.verify(input, Mockito.times(2)).isInterrupted();
        Mockito.verify(view, Mockito.times(1)).getScreen();
        try {
            Mockito.verify(screen, Mockito.times(1)).readInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.verify(key, Mockito.times(1)).getKeyType();
        Mockito.verify(key, Mockito.times(0)).getCharacter();

        Mockito.verify(view, Mockito.times(1))
                .pushEvent(Mockito.any(Event.class));
        Mockito.verify(view, Mockito.times(1))
                .pushEvent(Mockito.any());

        Mockito.verify(view, Mockito.times(1)).getSelectedTile();
        Mockito.verify(position, Mockito.times(1)).clone();

        Mockito.verify(view, Mockito.times(0)).showSaveCircuitMenu();
        Mockito.verify(view, Mockito.times(0)).showInsertCustomMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showHelpMenu();
        Mockito.verify(view, Mockito.times(0)).showTileInfo(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showSetDelayMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleAutoAdvance();
        Mockito.verify(view, Mockito.times(0)).showInsertGateMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showInsertMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleShowPower();
        Mockito.verify(view, Mockito.times(0)).moveViewWindow(Mockito.any(Side.class));
        Mockito.verify(view, Mockito.times(0)).moveSelectedTile(Mockito.any(Side.class));
    }

    @Test
    @Tag("view") @Tag("model")
    @Tag("integration-test") @Tag("fast")
    public void testInputEscape() {
        LanternaCircuitView view = Mockito.mock(LanternaCircuitView.class);

        Position position = Mockito.mock(Position.class);
        Position clone = Mockito.mock(Position.class);

        Mockito.when(view.getSelectedTile()).thenReturn(position);
        Mockito.when(position.clone()).thenReturn(clone);

        Mockito.when(view.inMenu()).thenReturn(false);

        Screen screen = Mockito.mock(Screen.class);
        Mockito.when(view.getScreen()).thenReturn(screen);

        KeyStroke key = Mockito.mock(KeyStroke.class);
        try {
            Mockito.when(screen.readInput()).thenReturn(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.when(key.getKeyType()).thenReturn(KeyType.Escape);
        Mockito.when(key.getCharacter()).thenReturn('f');

        LanternaInput input = Mockito.mock(LanternaInput.class, Mockito.withSettings().useConstructor(view));

        Mockito.doCallRealMethod().when(input).run();

        Mockito.when(input.isInterrupted()).thenReturn(false);

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Event event = invocation.getArgument(0);
            Assertions.assertEquals(InputEvent.ENTER_STATE, event.getInputEvent());
            return null;
        }).when(view).pushEvent(Mockito.any(Event.class));

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Mockito.when(input.isInterrupted()).thenReturn(true);
            return null;
        }).when(view).inMenu();

        input.run();

        Mockito.verify(input, Mockito.times(2)).isInterrupted();
        Mockito.verify(view, Mockito.times(1)).getScreen();
        try {
            Mockito.verify(screen, Mockito.times(1)).readInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.verify(key, Mockito.times(1)).getKeyType();
        Mockito.verify(key, Mockito.times(0)).getCharacter();

        Mockito.verify(view, Mockito.times(1))
                .pushEvent(Mockito.any(Event.class));
        Mockito.verify(view, Mockito.times(1))
                .pushEvent(Mockito.any());

        Mockito.verify(view, Mockito.times(0)).getSelectedTile();
        Mockito.verify(position, Mockito.times(0)).clone();

        Mockito.verify(view, Mockito.times(0)).showSaveCircuitMenu();
        Mockito.verify(view, Mockito.times(0)).showInsertCustomMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showHelpMenu();
        Mockito.verify(view, Mockito.times(0)).showTileInfo(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showSetDelayMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleAutoAdvance();
        Mockito.verify(view, Mockito.times(0)).showInsertGateMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showInsertMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleShowPower();
        Mockito.verify(view, Mockito.times(0)).moveViewWindow(Mockito.any(Side.class));
        Mockito.verify(view, Mockito.times(0)).moveSelectedTile(Mockito.any(Side.class));
    }

    @Test
    @Tag("view") @Tag("model")
    @Tag("integration-test") @Tag("fast")
    public void testInputEOF() {
        LanternaCircuitView view = Mockito.mock(LanternaCircuitView.class);

        Position position = Mockito.mock(Position.class);
        Position clone = Mockito.mock(Position.class);

        Mockito.when(view.getSelectedTile()).thenReturn(position);
        Mockito.when(position.clone()).thenReturn(clone);

        Mockito.when(view.inMenu()).thenReturn(false);

        Screen screen = Mockito.mock(Screen.class);
        Mockito.when(view.getScreen()).thenReturn(screen);

        KeyStroke key = Mockito.mock(KeyStroke.class);
        try {
            Mockito.when(screen.readInput()).thenReturn(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.when(key.getKeyType()).thenReturn(KeyType.EOF);
        Mockito.when(key.getCharacter()).thenReturn('f');

        LanternaInput input = Mockito.mock(LanternaInput.class, Mockito.withSettings().useConstructor(view));

        Mockito.doCallRealMethod().when(input).run();

        Mockito.when(input.isInterrupted()).thenReturn(false);

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Event event = invocation.getArgument(0);
            Assertions.assertEquals(InputEvent.QUIT, event.getInputEvent());
            return null;
        }).when(view).pushEvent(Mockito.any(Event.class));

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Mockito.when(input.isInterrupted()).thenReturn(true);
            return null;
        }).when(view).inMenu();

        input.run();

        Mockito.verify(input, Mockito.times(2)).isInterrupted();
        Mockito.verify(view, Mockito.times(1)).getScreen();
        try {
            Mockito.verify(screen, Mockito.times(1)).readInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.verify(key, Mockito.times(1)).getKeyType();
        Mockito.verify(key, Mockito.times(0)).getCharacter();

        Mockito.verify(view, Mockito.times(1))
                .pushEvent(Mockito.any(Event.class));
        Mockito.verify(view, Mockito.times(1))
                .pushEvent(Mockito.any());

        Mockito.verify(view, Mockito.times(0)).getSelectedTile();
        Mockito.verify(position, Mockito.times(0)).clone();

        Mockito.verify(view, Mockito.times(0)).showSaveCircuitMenu();
        Mockito.verify(view, Mockito.times(0)).showInsertCustomMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showHelpMenu();
        Mockito.verify(view, Mockito.times(0)).showTileInfo(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showSetDelayMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleAutoAdvance();
        Mockito.verify(view, Mockito.times(0)).showInsertGateMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).showInsertMenu(Mockito.any(Position.class));
        Mockito.verify(view, Mockito.times(0)).toggleShowPower();
        Mockito.verify(view, Mockito.times(0)).moveViewWindow(Mockito.any(Side.class));
        Mockito.verify(view, Mockito.times(0)).moveSelectedTile(Mockito.any(Side.class));
    }
}
