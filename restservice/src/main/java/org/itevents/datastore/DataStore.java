package org.itevents.datastore;

import org.itevents.model.Event;
import org.itevents.model.Location;

import java.util.List;

public interface DataStore {

    void addEvent(Event event);

    Event getEvent(Long id);

    List<Event> getAllEvents();

    List<Event> getAllEventsInRadius(Location location, long radius);

    Event removeEvent(Long id);

}
