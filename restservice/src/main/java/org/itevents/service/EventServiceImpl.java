package org.itevents.service;

import org.itevents.datastore.DataStore;
import org.itevents.model.Event;

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
    public Event removeEvent(Long id) {
        return dataStore.removeEvent(id);
    }

    @Override
    public List<Event> getAllEventsWithinLocation(float latitude, float longitude) {
        List<Event> events = dataStore.getEventsWithinLocation(latitude, longitude);
        return events;
    }

    public void setDataStore(DataStore dataStore) {
        this.dataStore = dataStore;
    }
}
