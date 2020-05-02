package com.lpoo.redstonetools.controller.event;

public class Event {

    InputEvent inputEvent;
    Object object;

    public Event(InputEvent inputEvent, Object object) {
        this.inputEvent = inputEvent;
        this.object = object;
    }

    public InputEvent getInputEvent() {
        return inputEvent;
    }

    public Object getObject() {
        return object;
    }

}
