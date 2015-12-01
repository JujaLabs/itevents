package org.itevents.service.transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.itevents.dao.EventDao;
import org.itevents.model.Event;
import org.itevents.model.User;
import org.itevents.service.EventService;
import org.itevents.service.converter.FilterConverter;
import org.itevents.wrapper.FilterWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service("eventService")
@Transactional
public class MyBatisEventService implements EventService {

    private static final Logger LOGGER = LogManager.getLogger();

    @Inject
    private EventDao eventDao;
    @Inject
    private FilterConverter filterConverter;

    @Override
    public void addEvent(Event event) {
        eventDao.addEvent(event);
        eventDao.addEventTechnology(event);
    }

    @Override
    public Event getEvent(int eventId) {
        return eventDao.getEvent(eventId);
    }

    @Override
    public List<Event> getAllEvents() {
        return eventDao.getAllEvents();
    }

    @Override
    public void assign(User user, Event event) {
        eventDao.assign(user, event);
    }

    @Override
    public void unassign(User user, Event event) {
            eventDao.unassign(user, event);
    }

    @Override
    public List<Event> getEventsByUser(User user) {
        return eventDao.getEventsByUser(user);
    }

    @Override
    public Event removeEvent(Event event) {
        Event deletingEvent = eventDao.getEvent(event.getId());
        if (deletingEvent != null) {
            eventDao.removeEventTechnology(event);
            eventDao.removeEvent(event);
        }
        return deletingEvent;
    }

    @Override
    public List<Event> getFilteredEvents(FilterWrapper wrapper) {
        List<Event> result;
        try {
            result = eventDao.getFilteredEvents(filterConverter.toFilter(wrapper));
        } catch (Exception e) {
            LOGGER.error("getFilteredEvents Exception :", e);
            result = new ArrayList<>();
        }
        return result;
    }
}