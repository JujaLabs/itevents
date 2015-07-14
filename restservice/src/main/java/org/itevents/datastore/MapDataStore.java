package org.itevents.datastore;

import org.itevents.model.Event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapDataStore implements DataStore {

    private static final float AREA_RADIUS = 0.500f;
    private final Map<Long, Event> storage;

    public MapDataStore() {
        this.storage  = new HashMap<Long, Event>();
    }

    @Override
    public void addEvent(Event event) {
        storage.put(event.getId(), event);
    }

    @Override
    public Event getEvent(Long id) {
        return storage.get(id);
    }

    @Override
    public List<Event> getAllEvents() {
        return new ArrayList<Event>(storage.values());
    }

    @Override
    public Event removeEvent(Long id) {
        return storage.remove(id);
    }

    @Override
    public List<Event> getEventsWithinLocation(float latitude, float longitude) {
        List<Event> allEvents = getAllEvents();
        /**
         * There must be realisation of the selection from data store.
         * We need to return List of Events within defined location
         */

        return allEvents;
    }
}