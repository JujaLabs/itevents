package org.itevents.service;

import org.itevents.mapper.EventMapper;
import org.itevents.model.Event;
import org.itevents.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
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
    public List<Event> getEventsInRadius(Location location, int radius) {
        return eventMapper.getEventsInRadius(location, radius);
    }

    @Override
    public void removeEvent(int id) {
        eventMapper.removeEvent(id);
    }

    @Override
    public Event getFutureEventById(int days, int id) {
        return eventMapper.getFutureEventById(id);
    }

    @Override
    public List<Event> getFilteredEvents(Object params) {
        return eventMapper.getFilteredEvents(params);
    }

    public void setEventMapper(EventMapper eventMapper) {
        this.eventMapper = eventMapper;
    }
}