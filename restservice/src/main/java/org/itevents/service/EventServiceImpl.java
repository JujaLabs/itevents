package org.itevents.service;

import org.itevents.dao.EventDao;
import org.itevents.model.Event;
import org.itevents.parameter.FilteredEventsParameter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service("eventService")
@Transactional
public class EventServiceImpl implements EventService {

    @Inject
    private EventDao eventDao;

    @Override
    public void addEvent(Event event) {
        eventDao.addEvent(event);
    }

    @Override
    public Event getEvent(int id) {
        return eventDao.getEvent(id);
    }

    @Override
    public List<Event> getAllEvents() {
        return eventDao.getAllEvents();
    }

    @Override
    public List<Event> getEventsInRadius(Location location, int radius) {
        return eventDao.getEventsInRadius(location, radius);
    }

    @Override
    public List<Event> getFilteredEvents(FilteredEventsParameter params) {
        List<Event> result;
        try {
            result = eventDao.getFilteredEvents(params);
        } catch (Exception e) {
            result = new ArrayList<>();
        }
        return result;
    }
    
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

    @Override
    public void removeEvent(int id) {
        eventMapper.removeEvent(id);
    }

    public void setEventMapper(EventMapper eventMapper) {
        this.eventMapper = eventMapper;
    }
}