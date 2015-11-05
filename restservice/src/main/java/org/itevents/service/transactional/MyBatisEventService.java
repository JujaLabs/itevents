package org.itevents.service.transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.itevents.dao.EventDao;
import org.itevents.dao.exception.EntityNotFoundDaoException;
import org.itevents.model.Event;
import org.itevents.service.EventService;
import org.itevents.service.converter.FilterConverter;
import org.itevents.service.exception.EntityNotFoundServiceException;
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