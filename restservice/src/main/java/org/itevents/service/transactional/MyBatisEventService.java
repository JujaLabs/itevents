package org.itevents.service.transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.itevents.dao.EventDao;
import org.itevents.dao.exception.EntityNotFoundDaoException;
import org.itevents.model.Event;
import org.itevents.model.User;
import org.itevents.service.EventService;
import org.itevents.service.converter.FilterConverter;
import org.itevents.service.exception.EntityNotFoundServiceException;
import org.itevents.service.exception.TimeCollisionServiceException;
import org.itevents.wrapper.FilterWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
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
    public Event getEvent(int id) {
        try {
            return eventDao.getEvent(id);
        } catch (EntityNotFoundDaoException e) {
            LOGGER.error(e.getMessage());
            throw new EntityNotFoundServiceException(e.getMessage(), e);
        }
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
    public Event getFutureEvent(int eventId) {
        Event event = getEvent(eventId);
        if (!new Date().after(event.getEventDate())) {
            String message = String.format("Try to get event id=%s with date %s as future event", eventId, event.getEventDate().toString());
            LOGGER.error(message);
            throw new TimeCollisionServiceException(message);
        }
        return event;
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