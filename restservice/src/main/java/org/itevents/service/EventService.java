package org.itevents.service;

import org.itevents.model.Event;
import org.itevents.model.Location;

import java.util.List;

public interface EventService {

    void addEvent(Event event);

    Event getEvent(int id);

    List<Event> getAllEvents();

    Event removeEvent(Long id);

    List<Event> getAllEventsWithinLocation(float latitude, float longitude);
    List<Event> getFutureEventsInRadius(Location location, int radius);

    void removeEvent(int id);
}
