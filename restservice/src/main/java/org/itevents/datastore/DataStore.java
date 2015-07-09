package org.itevents.datastore;

import org.itevents.model.Event;

import java.util.List;

public interface DataStore {

    void addEvent(Event event);

    Event getEvent(Long id);

    List<Event> getAllEvents();

    Event removeEvent(Long id);

    List<Event> getEventsWithinLocation(float latitude, float longitude);
}
