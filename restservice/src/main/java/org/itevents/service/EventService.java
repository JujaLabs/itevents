package org.itevents.service;

import org.itevents.model.Event;

import java.util.List;

public interface EventService {

    void addEvent(Event event);

    Event getEvent(Long id);

    List<Event> getAllEvents();

    Event removeEvent(Long id);

    List<Event> getAllEventsWithinLocation(float latitude, float longitude);
}
