package com.lpoo.redstonetools.view.lanterna.input;

import com.lpoo.redstonetools.controller.event.Event;
import com.lpoo.redstonetools.controller.event.InputEvent;
import com.lpoo.redstonetools.view.lanterna.LanternaCircuitView;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import java.util.concurrent.atomic.AtomicInteger;

public class LanternaAutoAdvanceTimeTest {

    @Test
    @Tag("view")
    @Tag("unit-test") @Tag("slow")
    public void testLanternaAutoAdvanceTime() {

        LanternaCircuitView view = Mockito.mock(LanternaCircuitView.class);

        LanternaAutoAdvanceTime autoTime = Mockito.mock(LanternaAutoAdvanceTime.class, Mockito.withSettings().useConstructor(view));

        Mockito.doCallRealMethod().when(autoTime).run();

        Mockito.when(autoTime.isInterrupted()).thenReturn(false);

        AtomicInteger calls = new AtomicInteger(0);

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Event event = invocation.getArgument(0);
            Assertions.assertEquals(InputEvent.ADVANCE_TICK, event.getInputEvent());
            if (calls.incrementAndGet() >= 5)
                Mockito.when(autoTime.isInterrupted()).thenReturn(true);
            return null;
        }).when(view).pushEvent(Mockito.any(Event.class));

        autoTime.run();

        Mockito.verify(autoTime, Mockito.times(6)).isInterrupted();

        Mockito.verify(view, Mockito.times(5))
                .pushEvent(Mockito.any(Event.class));
        Mockito.verify(view, Mockito.times(5))
                .pushEvent(Mockito.any());
    }
}
