package org.itevents.service;

import org.itevents.datastore.DataStore;
import org.itevents.model.Event;
import org.itevents.model.Location;

import java.util.List;

public class EventServiceImpl implements EventService {

    private DataStore dataStore;

    @Override
    public void addEvent(Event event) {
        dataStore.addEvent(event);
    }

    @Override
    public Event getEvent(Long id) {
        return dataStore.getEvent(id);
    }

    @Override
    public List<Event> getAllEvents() {
        return dataStore.getAllEvents();
    }

    @Override
    public List<Event> getAllEventsInRadius(Location location, long radius) {
        return dataStore.getAllEventsInRadius(location, radius);
    }

    @Override
    public Event removeEvent(Long id) {
        return dataStore.removeEvent(id);
    }

    public void setDataStore(DataStore dataStore) {
        this.dataStore = dataStore;
    }
}
