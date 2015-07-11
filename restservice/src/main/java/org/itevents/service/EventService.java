package org.itevents.service;

import org.itevents.model.Event;
import org.itevents.model.Location;

import java.util.List;

public interface EventService {

    void addEvent(Event event);

    Event getEvent(Long id);

    List<Event> getAllEvents();

    List<Event> getAllEventsInRadius(Location location, long radius);

    Event removeEvent(Long id);
}
