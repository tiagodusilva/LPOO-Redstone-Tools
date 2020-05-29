package com.lpoo.redstonetools.controller.state;

import com.lpoo.redstonetools.MainController;
import com.lpoo.redstonetools.controller.circuit.CircuitController;
import com.lpoo.redstonetools.controller.event.Event;
import com.lpoo.redstonetools.controller.event.InputEvent;
import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.model.tile.*;
import com.lpoo.redstonetools.model.utils.Position;
import com.lpoo.redstonetools.view.CircuitView;
import com.lpoo.redstonetools.view.SaveCircuitListener;
import com.lpoo.redstonetools.view.ViewFactory;
import net.jqwik.api.*;
import net.jqwik.api.lifecycle.BeforeProperty;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class CircuitStateTest {

    private CircuitState state;

    private MainController mainController;
    private Circuit model;
    private CircuitView view;
    private CircuitController controller;
    
    @BeforeEach
    @BeforeProperty
    public void setup() {
        this.mainController = Mockito.mock(MainController.class);
        this.model = Mockito.mock(Circuit.class);
        this.view = Mockito.mock(CircuitView.class);
        this.controller = Mockito.mock(CircuitController.class);

        ViewFactory viewFactory = Mockito.mock(ViewFactory.class);
        Mockito.when(viewFactory.getCircuitView(this.model)).thenReturn(this.view);

        Mockito.when(this.mainController.getCircuitController()).thenReturn(this.controller);
        Mockito.when(this.mainController.getViewFactory()).thenReturn(viewFactory);

        this.state = new CircuitState(this.model, this.mainController);
    }

    @Test
    @Tag("controller")
    @Tag("unit-test") @Tag("fast")
    public void testRender() {
        state.render();

        Mockito.verify(view, Mockito.times(1)).render();
    }

    @Test
    @Tag("controller")
    @Tag("unit-test") @Tag("fast")
    public void testAtExit() {
        state.atExit();

        Mockito.verify(view, Mockito.times(1)).cleanup();
    }

    private class EventLinkedList extends LinkedList<Event> { }

    @Test
    @Tag("controller")
    @Tag("unit-test") @Tag("fast")
    public void emptyEventQueue() {
        Queue<Event> events = Mockito.mock(EventLinkedList.class);

        Mockito.when(events.isEmpty()).thenReturn(true);

        Mockito.when(view.getEvents()).thenReturn(events);

        state.processEvents();

        Mockito.verify(events, Mockito.times(0)).remove();
    }

    @Test
    @Tag("controller")
    @Tag("unit-test") @Tag("fast")
    public void testProcessEventsShortCircuit() {
        Event e1 = Mockito.mock(Event.class);
        Event e2 = Mockito.mock(Event.class);

        Queue<Event> events = new LinkedList<>();
        events.add(e1); events.add(e2);

        Mockito.when(view.getEvents()).thenReturn(events);

        Mockito.when(e1.getInputEvent()).thenReturn(InputEvent.QUIT);
        Mockito.when(e2.getInputEvent()).thenReturn(InputEvent.ENTER_STATE);

        Assertions.assertFalse(state.exit());

        state.processEvents();

        Mockito.verify(view, Mockito.times(1)).getEvents();
        Assertions.assertTrue(events.isEmpty());

        Mockito.verify(e1, Mockito.times(1)).getInputEvent();
        Mockito.verify(e2, Mockito.times(0)).getInputEvent();

        Assertions.assertTrue(state.exit());

    }

    @Test
    @Tag("controller")
    @Tag("unit-test") @Tag("fast")
    public void testBasicProcessEvents() {
        Event e1 = Mockito.mock(Event.class);
        Event e2 = Mockito.mock(Event.class);

        Queue<Event> events = new LinkedList<>();
        events.add(e1); events.add(e2);

        Mockito.when(view.getEvents()).thenReturn(events);

        Mockito.when(e1.getInputEvent()).thenReturn(InputEvent.ENTER_STATE);
        Mockito.when(e2.getInputEvent()).thenReturn(InputEvent.QUIT);

        Assertions.assertFalse(state.exit());

        state.processEvents();

        Mockito.verify(view, Mockito.times(1)).getEvents();
        Assertions.assertTrue(events.isEmpty());

        Mockito.verify(e1, Mockito.times(1)).getInputEvent();
        Mockito.verify(e2, Mockito.times(1)).getInputEvent();

        state.processEvents();

        Assertions.assertTrue(state.exit());
    }

    @Provide
    public Arbitrary<Event> randomEvent() {
        List<Class<? extends Tile>> tileClasses = Arrays.asList(
                WireTile.class,
                ComparatorTile.class,
                DigitTile.class,
                IOTile.class,
                NullTile.class
        );

        Arbitrary<Integer> widths = Arbitraries.integers().greaterOrEqual(0);
        Arbitrary<Integer> heights = Arbitraries.integers().greaterOrEqual(0);

        Arbitrary<Integer> x_coords = Arbitraries.integers().greaterOrEqual(0);
        Arbitrary<Integer> y_coords = Arbitraries.integers().greaterOrEqual(0);

        Arbitrary<Long> delays = Arbitraries.longs().greaterOrEqual(0);

        Arbitrary<Circuit> circuits = Combinators
                .combine(widths, heights, x_coords, y_coords)
                .as((width, height, x, y) -> new Circuit(width, height, new Position(x, y)));

        Random rng = new Random();

        Arbitrary<Tile> tiles = Combinators
                .combine(x_coords, y_coords)
                .as((x, y) -> {
                    try {
                        return tileClasses.get(rng.nextInt(tileClasses.size()))
                                .getConstructor(Position.class)
                                .newInstance(new Position(x, y));
                    } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    return new NullTile(new Position(x, y));
                });

        Arbitrary<Position> positions = Combinators
                .combine(x_coords, y_coords)
                .as(Position::new);

        Arbitrary<Map.Entry<Position, Long>> entries = Combinators
                .combine(x_coords, y_coords, delays)
                .as((x, y, delay) -> new AbstractMap.SimpleEntry<>(new Position(x, y), delay));

        Arbitrary<Object> objects = Arbitraries.frequency(
                Tuple.of(3, circuits),
                Tuple.of(8, positions),
                Tuple.of(5, tiles),
                Tuple.of(2, entries),
                Tuple.of(1, Arbitraries.integers()),
                Tuple.of(4, Arbitraries.floats()),
                Tuple.of(1, Arbitraries.strings())
        );

        return Combinators
                .combine(Arbitraries.of(InputEvent.class), objects)
                .as(Event::new)
                .filter(event -> {
                    switch (event.getInputEvent()) {
                        case INTERACT:
                        case ROTATE_LEFT:
                        case ROTATE_RIGHT:
                            return event.getObject() instanceof Position;
                        case ADD_TILE:
                            return event.getObject() instanceof Tile;
                        case SET_DELAY:
                            return event.getObject() instanceof Map.Entry<?, ?>;
                        case SAVE:
                            return false;
                        default:
                            return true;
                    }
                });
    }

    @Property
    @net.jqwik.api.Tag("controller") @net.jqwik.api.Tag("model")
    @net.jqwik.api.Tag("integration-test") @net.jqwik.api.Tag("slow")
    public void testProcessEvents(@ForAll List<@From("randomEvent") Event> events) {
        Queue<Event> queue = new LinkedList<>();

        state.exit = false;

        boolean has_quit = false;
        int size = events.size();
        for (int i = 0; i < size; i++) {
            queue.add(events.get(i));
            if (i < state.processed_per_frame && events.get(i).getInputEvent() == InputEvent.QUIT)
                has_quit = true;
        }

        Mockito.when(view.getEvents()).thenReturn(queue);

        state.processEvents();

        if (has_quit) {
            Assertions.assertTrue(queue.isEmpty());
            Assertions.assertTrue(state.exit());
        } else {
            int expected = Math.max(0, size - state.processed_per_frame);
            Assertions.assertEquals(expected, queue.size());
            Assertions.assertFalse(state.exit());
        }
    }

    @Test
    @Tag("controller")
    @Tag("integration-test") @Tag("slow")
    public void testSaveEventOnFailure() {
        File testDir;
        Random rng = new Random();

        do {
            testDir = new File(rng.nextInt() + ".ser");
        } while(testDir.exists());

        testDir.mkdir();

        testDir.deleteOnExit();

        SaveCircuitListener listener = Mockito.mock(SaveCircuitListener.class);
        Mockito.when(listener.getFileName()).thenReturn(testDir.getPath());

        Queue<Event> queue = new LinkedList<>();

        Event event = Mockito.mock(Event.class);
        Mockito.when(event.getInputEvent()).thenReturn(InputEvent.SAVE);
        Mockito.when(event.getObject()).thenReturn(listener);

        queue.add(event);
        Mockito.when(view.getEvents()).thenReturn(queue);

        state.processEvents();

        Assertions.assertTrue(queue.isEmpty());
        Assertions.assertFalse(state.exit);
        Mockito.verify(listener, Mockito.times(1)).notifyFailure();
        Mockito.verify(listener, Mockito.times(0)).notifySuccess();
    }

    @Test
    @Tag("controller")
    @Tag("integration-test") @Tag("slow")
    public void testSaveEventOnSucess() {
        File testDir;
        Random rng = new Random();

        do {
            testDir = new File(rng.nextInt() + ".ser");
        } while(testDir.exists());

        testDir.mkdir();

        File testFile = new File(testDir, "test.ser");

        testDir.deleteOnExit();
        testFile.deleteOnExit();

        SaveCircuitListener listener = Mockito.mock(SaveCircuitListener.class);
        Mockito.when(listener.getFileName()).thenReturn(testFile.getPath());

        Queue<Event> queue = new LinkedList<>();

        Event event = Mockito.mock(Event.class);
        Mockito.when(event.getInputEvent()).thenReturn(InputEvent.SAVE);
        Mockito.when(event.getObject()).thenReturn(listener);

        queue.add(event);
        Mockito.when(view.getEvents()).thenReturn(queue);

        state.processEvents();

        Assertions.assertTrue(queue.isEmpty());
        Assertions.assertFalse(state.exit);
        Mockito.verify(listener, Mockito.times(0)).notifyFailure();
        Mockito.verify(listener, Mockito.times(1)).notifySuccess();
    }
}
