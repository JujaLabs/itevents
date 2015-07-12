package org.itevents.service;

import org.itevents.model.Event;
import org.itevents.model.Location;

import java.util.List;

public interface EventService {

    void addEvent(Event event);

    Event getEvent(long id);

    List<Event> getAllEvents();

    List<Event> getFutureEventsInRadius(Location location, long radius);

    void removeEvent(long id);
}
