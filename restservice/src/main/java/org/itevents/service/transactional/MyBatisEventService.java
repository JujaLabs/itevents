package org.itevents.service.transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.itevents.dao.EventDao;
import org.itevents.model.Event;
import org.itevents.model.Filter;
import org.itevents.model.User;
import org.itevents.service.EventService;
import org.itevents.service.converter.FilterConverter;
import org.itevents.wrapper.FilterWrapper;
import org.springframework.beans.factory.annotation.Value;
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
    public void assignUserToEvent(User user, Event event) {
        eventDao.assignUserToEvent(user, event);
    }

    // TODO: 28.01.2016 дописать проверку
    @Override
    public void unassignUserFromEvent(User user, Event event, Date unassignDate, String unassignReason) {
        if (!isAssigned(user, event)) {
            throw new IllegalArgumentException("not assigned or event not found");
        } else {
            eventDao.unassignUserFromEvent(user, event, unassignDate, unassignReason);
        }
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
        return eventDao.getFilteredEvents(filterConverter.toFilter(wrapper));
    }

    public List<Event> getFilteredEventsWithRating(Filter filter){
        return eventDao.getFilteredEventsWithRating(filter);
    }

    private boolean isAssigned(User user, Event event) {
        return getEventsByUser(user).contains(event);
    }

}