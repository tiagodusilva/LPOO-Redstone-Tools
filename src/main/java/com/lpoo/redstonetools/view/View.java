package com.lpoo.redstonetools.view;

import com.lpoo.redstonetools.controller.event.Event;

import java.util.LinkedList;
import java.util.Queue;

public abstract class View {
    protected Queue<Event> events;
    public View() {
        events = new LinkedList<>();
    }

    public abstract void render();

    public abstract void cleanup();

    public void pushEvent(Event event) { events.add(event); }

    public Queue<Event> getEvents() {
        return events;
    }
}
