package com.github.boman.event;

import javafx.event.Event;
import javafx.event.EventHandler;

import java.util.ArrayList;

public class EventHandlerListener implements EventHandler {
    private ArrayList<EventListener> listeners;

    public EventHandlerListener() {
        this.listeners = new ArrayList<>();
    }

    public void addListener(EventListener listener) {
        listeners.add(listener);
    }

    public void removeListener(EventListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void handle(Event event) {
        for (EventListener listener : listeners) {
            listener.onEvent(event);
        }
    }
}
