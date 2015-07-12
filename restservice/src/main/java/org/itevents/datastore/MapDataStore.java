package org.itevents.datastore;

import org.itevents.model.Event;
import org.itevents.model.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapDataStore implements DataStore {

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
    public List<Event> getAllEventsInRadius(Location location, long radius) {
        return null;
    }

    @Override
    public Event removeEvent(Long id) {
        return storage.remove(id);
    }
}
