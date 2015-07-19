package org.itevents.service;

import org.itevents.mapper.EventMapper;
import org.itevents.model.Event;
import org.itevents.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class EventServiceImpl implements EventService {

    @Autowired
    private EventMapper eventMapper;

    @Override
    public void addEvent(Event event) {
        eventMapper.addEvent(event);
    }

    @Override
    public Event getEvent(int id) {
        return eventMapper.getEvent(id);
    }

    @Override
    public List<Event> getAllEvents() {
        return eventMapper.getAllEvents();
    }

    @Override
    public List<Event> getFutureEventsInRadius(Location location, int radius) {
        return eventMapper.getFutureEventsInRadius(location, radius);
    }

    @Override
    public void removeEvent(int id) {
        eventMapper.removeEvent(id);
    @Override
    public List<Event> getAllEventsWithinLocation(float latitude, float longitude) {
        List<Event> events = dataStore.getEventsWithinLocation(latitude, longitude);
        return events;
    }

    public void setDataStore(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    public void setEventMapper(EventMapper eventMapper) {
        this.eventMapper = eventMapper;
    }
}