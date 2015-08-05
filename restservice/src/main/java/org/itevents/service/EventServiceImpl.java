package org.itevents.service;

import org.itevents.mapper.EventMapper;
import org.itevents.model.Event;
import org.itevents.parameter.FilteredEventsParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
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
    public void removeEvent(int id) {
        eventMapper.removeEvent(id);
    }

    @Override
    public List<Event> getFilteredEvents(FilteredEventsParameter params) {
        return eventMapper.getFilteredEvents(params);
    }

}