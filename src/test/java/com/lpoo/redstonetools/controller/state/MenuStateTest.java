package com.lpoo.redstonetools.controller.state;

import com.lpoo.redstonetools.MainController;
import com.lpoo.redstonetools.controller.event.Event;
import com.lpoo.redstonetools.controller.event.InputEvent;
import com.lpoo.redstonetools.view.MenuView;
import com.lpoo.redstonetools.view.ViewFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.LinkedList;
import java.util.Queue;

public class MenuStateTest {

    private MenuState state;

    private MainController mainController;
    private MenuView view;
/*
    @Before
    public void setup() {
        this.mainController = Mockito.mock(MainController.class);
        this.view = Mockito.mock(MenuView.class);

        ViewFactory viewFactory = Mockito.mock(ViewFactory.class);
        Mockito.when(viewFactory.getMenuView()).thenReturn(this.view);

        Mockito.when(this.mainController.getViewFactory()).thenReturn(viewFactory);

        this.state = new MenuState(this.mainController);
    }

    @Test
    public void testRender() {
        state.render();

        Mockito.verify(view, Mockito.times(1)).render();
    }

    @Test
    public void testAtExit() {
        state.atExit();

        Mockito.verify(view, Mockito.times(1)).cleanup();
    }

    private class EventLinkedList extends LinkedList<Event> { }

    @Test
    public void emptyEventQueue() {
        Queue<Event> events = Mockito.mock(EventLinkedList.class);

        Mockito.when(events.isEmpty()).thenReturn(true);

        Mockito.when(view.getEvents()).thenReturn(events);

        state.processEvents();

        Mockito.verify(events, Mockito.times(0)).remove();
    }

    @Test
    public void testProcessEventsShortCircuit() {
        Event e1 = Mockito.mock(Event.class);
        Event e2 = Mockito.mock(Event.class);

        Queue<Event> events = new LinkedList<>();
        events.add(e1); events.add(e2);

        Mockito.when(view.getEvents()).thenReturn(events);

        Mockito.when(e1.getInputEvent()).thenReturn(InputEvent.QUIT);
        Mockito.when(e2.getInputEvent()).thenReturn(InputEvent.ADD_TILE);

        Assert.assertFalse(state.exit());

        state.processEvents();

        Mockito.verify(view, Mockito.times(1)).getEvents();
        Assert.assertTrue(events.isEmpty());

        Mockito.verify(e1, Mockito.times(1)).getInputEvent();
        Mockito.verify(e2, Mockito.times(0)).getInputEvent();

        Assert.assertTrue(state.exit());
    }

    @Test
    public void testProcessEvents() {
        Event e1 = Mockito.mock(Event.class);
        Event e2 = Mockito.mock(Event.class);

        Queue<Event> events = new LinkedList<>();
        events.add(e1); events.add(e2);

        Mockito.when(view.getEvents()).thenReturn(events);

        Mockito.when(e1.getInputEvent()).thenReturn(InputEvent.ADD_TILE);
        Mockito.when(e2.getInputEvent()).thenReturn(InputEvent.QUIT);

        Assert.assertFalse(state.exit());

        state.processEvents();

        Mockito.verify(view, Mockito.times(1)).getEvents();
        Assert.assertTrue(events.isEmpty());

        Mockito.verify(e1, Mockito.times(1)).getInputEvent();
        Mockito.verify(e2, Mockito.times(1)).getInputEvent();

        Assert.assertTrue(state.exit());
    }

 */
}
