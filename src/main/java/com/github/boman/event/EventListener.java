package com.github.boman.event;

import javafx.event.Event;

public interface EventListener {
    default void onEvent(Event event) {}
}
