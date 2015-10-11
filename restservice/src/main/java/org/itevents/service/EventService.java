package org.itevents.service;

import org.itevents.model.Event;
import org.itevents.wrapper.EventWrapper;

import java.util.List;
import java.util.Set;

public interface EventService {

    void addEvent(Event event);

    Event getEvent(int id);

    List<Event> getAllEvents();

    Event removeEvent(Event event);

    Set<Event> getFilteredEvents(EventWrapper wrapper);
}
