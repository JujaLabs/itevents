package org.itevents.service;

import org.itevents.mapper.EventMapper;
import org.itevents.model.Event;
import org.itevents.model.Location;

import java.util.List;

public class EventServiceImpl implements EventService {

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
    public List<Event> getFutureEventsInRadius(Location location, long radius) {
        return eventMapper.getFutureEventsInRadius(location, radius);
    }

    @Override
    public void removeEvent(int id) {
        eventMapper.removeEvent(id);
    }

    public void setEventMapper(EventMapper eventMapper) {
        this.eventMapper = eventMapper;
    }
}