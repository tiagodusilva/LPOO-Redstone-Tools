package com.lpoo.redstonetools.controller.event;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.Tag;
import org.junit.jupiter.api.Assertions;

public class EventTest {
    @Property
    @Tag("controller")
    @Tag("unit-test") @Tag("fast")
    public void testEvent(@ForAll InputEvent input, @ForAll Object object) {
        Event event = new Event(input, object);

        Assertions.assertEquals(input, event.getInputEvent());
        Assertions.assertEquals(object, event.getObject());
    }
}
