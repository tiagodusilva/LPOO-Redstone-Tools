package com.lpoo.redstonetools.controller.state;

import com.lpoo.redstonetools.MainController;
import com.lpoo.redstonetools.controller.event.Event;
import com.lpoo.redstonetools.controller.event.InputEvent;
import com.lpoo.redstonetools.model.circuit.Circuit;
import com.lpoo.redstonetools.view.MenuView;
import com.lpoo.redstonetools.view.ViewFactory;
import net.jqwik.api.*;
import net.jqwik.api.lifecycle.BeforeProperty;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MenuStateTest {

    private MenuState state;

    private MainController mainController;
    private MenuView view;

    @BeforeEach
    @BeforeProperty
    public void setup() {
        this.mainController = Mockito.mock(MainController.class);
        this.view = Mockito.mock(MenuView.class);

        ViewFactory viewFactory = Mockito.mock(ViewFactory.class);
        Mockito.when(viewFactory.getMenuView()).thenReturn(this.view);

        Mockito.when(this.mainController.getViewFactory()).thenReturn(viewFactory);

        this.state = new MenuState(this.mainController);
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
        Event e3 = Mockito.mock(Event.class);

        Queue<Event> events = new LinkedList<>();
        events.add(e1); events.add(e2);
        events.add(e2); events.add(e1);
        events.add(e2); events.add(e1);
        events.add(e1); events.add(e3);
        events.add(e1); events.add(e3);

        Mockito.when(view.getEvents()).thenReturn(events);

        Mockito.when(e1.getInputEvent()).thenReturn(InputEvent.INTERACT);
        Mockito.when(e2.getInputEvent()).thenReturn(InputEvent.ADD_TILE);
        Mockito.when(e3.getInputEvent()).thenReturn(InputEvent.QUIT);


        Assertions.assertFalse(state.exit());

        state.processEvents();

        Mockito.verify(view, Mockito.times(1)).getEvents();
        Assertions.assertTrue(events.isEmpty());

        Mockito.verify(e1, Mockito.times(4)).getInputEvent();
        Mockito.verify(e2, Mockito.times(3)).getInputEvent();
        Mockito.verify(e3, Mockito.times(1)).getInputEvent();

        Assertions.assertTrue(state.exit());
    }

    @Test
    @Tag("controller")
    @Tag("unit-test") @Tag("fast")
    public void testProcessEventsMaxEventsPerProcess() {
        Event e1 = Mockito.mock(Event.class);
        Event e2 = Mockito.mock(Event.class);
        Event e3 = Mockito.mock(Event.class);

        Queue<Event> events = new LinkedList<>();
        events.add(e1); events.add(e2);
        events.add(e2); events.add(e1);
        events.add(e2); events.add(e1);
        events.add(e1); events.add(e1);
        events.add(e1); events.add(e3);

        Mockito.when(view.getEvents()).thenReturn(events);

        Mockito.when(e1.getInputEvent()).thenReturn(InputEvent.INTERACT);
        Mockito.when(e2.getInputEvent()).thenReturn(InputEvent.ADD_TILE);
        Mockito.when(e3.getInputEvent()).thenReturn(InputEvent.QUIT);


        Assertions.assertFalse(state.exit());

        state.processEvents();

        Mockito.verify(view, Mockito.times(1)).getEvents();
        Assertions.assertFalse(events.isEmpty());

        Mockito.verify(e1, Mockito.times(5)).getInputEvent();
        Mockito.verify(e2, Mockito.times(3)).getInputEvent();
        Mockito.verify(e3, Mockito.times(0)).getInputEvent();

        Assertions.assertFalse(state.exit());
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

        Mockito.when(e1.getInputEvent()).thenReturn(InputEvent.ADD_TILE);
        Mockito.when(e2.getInputEvent()).thenReturn(InputEvent.QUIT);

        Assertions.assertFalse(state.exit());

        state.processEvents();

        Mockito.verify(view, Mockito.times(1)).getEvents();
        Assertions.assertTrue(events.isEmpty());

        Mockito.verify(e1, Mockito.times(1)).getInputEvent();
        Mockito.verify(e2, Mockito.times(1)).getInputEvent();

        Assertions.assertTrue(state.exit());
    }

    @Provide
    public Arbitrary<Event> randomEvent() {
        Arbitrary<Integer> widths = Arbitraries.integers().greaterOrEqual(0);
        Arbitrary<Integer> heights = Arbitraries.integers().greaterOrEqual(0);

        Arbitrary<Circuit> circuits = Combinators
                                        .combine(widths, heights)
                                        .as((width, height) -> new Circuit(width, height));

        Arbitrary<Object> objects = Arbitraries.frequency(
                Tuple.of(2, circuits),
                Tuple.of(4, Arbitraries.integers()),
                Tuple.of(6, Arbitraries.floats()),
                Tuple.of(1, Arbitraries.strings())
        );

        return Combinators
                .combine(Arbitraries.of(InputEvent.class), objects)
                .as(Event::new)
                .filter(event -> event.getInputEvent() != InputEvent.ENTER_STATE
                                || event.getObject() instanceof Circuit);
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
}
