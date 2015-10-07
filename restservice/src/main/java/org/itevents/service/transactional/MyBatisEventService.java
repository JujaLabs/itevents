package org.itevents.service.transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.itevents.dao.EventDao;
import org.itevents.model.Event;
import org.itevents.service.EventService;
import org.itevents.service.converter.EventConverter;
import org.itevents.wrapper.EventWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("eventService")
@Transactional
public class MyBatisEventService implements EventService {

    private static final Logger logger = LogManager.getLogger();

    @Inject
    private EventDao eventDao;
    @Inject
    private EventConverter eventConverter;

    @Override
    public void addEvent(Event event) {
        eventDao.addEvent(event);
        eventDao.addEventTechnology(event);
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
    public Event removeEvent(Event event) {
        Event deletingEvent = eventDao.getEvent(event.getId());
        if (deletingEvent != null) {
            eventDao.removeEventTechnology(event);
            eventDao.removeEvent(event);
        }
        return deletingEvent;
    }

    @Override
    public Set<Event> getFilteredEvents(EventWrapper wrapper) {
        Set<Event> result;
        try {
            result = eventDao.getFilteredEvents(eventConverter.convert(wrapper));
        } catch (Exception e) {
            logger.error("Exception :", e);
            result = new HashSet<>();
        }
        return result;
    }
}