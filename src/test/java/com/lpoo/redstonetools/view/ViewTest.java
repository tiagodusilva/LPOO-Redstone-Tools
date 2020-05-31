package com.lpoo.redstonetools.view;

import com.lpoo.redstonetools.controller.event.Event;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ViewTest {
    @Test
    @Tag("view")
    @Tag("unit-test") @Tag("fast")
    public void testEvents() {
        View view = Mockito.mock(View.class, Mockito.withSettings().useConstructor().defaultAnswer(Mockito.CALLS_REAL_METHODS));

        Event e1 = Mockito.mock(Event.class);
        Event e2 = Mockito.mock(Event.class);
        Event e3 = Mockito.mock(Event.class);

        Assertions.assertTrue(view.getEvents().isEmpty());

        view.pushEvent(e1);
        view.pushEvent(e2);
        view.pushEvent(e3);

        Assertions.assertEquals(3, view.getEvents().size());

        Assertions.assertEquals(e1, view.getEvents().peek());
        Assertions.assertEquals(e1, view.getEvents().remove());
        Assertions.assertEquals(e2, view.getEvents().peek());
        Assertions.assertEquals(e2, view.getEvents().remove());
        Assertions.assertEquals(e3, view.getEvents().peek());
        Assertions.assertEquals(e3, view.getEvents().remove());
    }
}
