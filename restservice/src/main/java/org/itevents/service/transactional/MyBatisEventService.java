package org.itevents.service.transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.itevents.dao.EventDao;
import org.itevents.dao.UserDao;
import org.itevents.model.Event;
import org.itevents.model.Location;
import org.itevents.model.User;
import org.itevents.service.EventService;
import org.itevents.service.converter.EventConverter;
import org.itevents.wrapper.EventWrapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("eventService")
@Transactional
public class MyBatisEventService implements EventService {

    private static final Logger logger = LogManager.getLogger();

    @Inject
    private UserDao userDao;
    @Inject
    private EventDao eventDao;
    @Inject
    private EventConverter eventConverter;

    @Override
    public void addEvent(Event event) {
        eventDao.addEvent(event);
    }

    @Override
    public Event getEvent(int id) {
        return eventDao.getEvent(id);
    }

    @Override
//    @PreAuthorize("isAuthenticated()")
    public String WillGo(int id) {
        try {
//        if (event != null) {
//            User user = userDao.getUserByName(SecurityContextHolder.getContext().getAuthentication().getName());
//        }
            Event event = eventDao.getEvent(id);
            User user = userDao.getUser(1);
            if (new Date().after(event.getEventDate())){
                eventDao.willGoToEvent(user, event);
            }else return "Event date passed";
        }catch (Exception e) {
            return "Something went wrong. May be already subscribed";
        }
        return "successfully subscribed";
    }

    @Override
//    @PreAuthorize("isAuthenticated()")
    public String WillNotGo(int id) {
        try {
//        if (event != null) {
//            User user = userDao.getUserByName(SecurityContextHolder.getContext().getAuthentication().getName());
//        }
            Event event = eventDao.getEvent(id);
            User user = userDao.getUser(1);
            eventDao.willNotGoToEvent(user, event);
        }catch (Exception e) {
            return "Something went wrong. May be already unsubscribed";
        }
        return "successfully unsubscribed";

    }

    @Override
    public List<User> getVisitors(int id) {
        return eventDao.getVisitors(getEvent(id));
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
    public Event removeEvent(Event event) {
        Event deletingEvent = eventDao.getEvent(event.getId());
        if (deletingEvent != null) {
            eventDao.removeEvent(event);
        }
        return deletingEvent;
    }

    @Override
    public List<Event> getFilteredEvents(EventWrapper wrapper) {
        List<Event> result;
        try {
            result = eventDao.getFilteredEvents(eventConverter.convert(wrapper));
        } catch (Exception e) {
            logger.error("Exception :", e);
            result = new ArrayList<>();
        }
        return result;
    }
}