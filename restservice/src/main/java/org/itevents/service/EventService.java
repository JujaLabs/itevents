package org.itevents.service;

import org.itevents.model.Event;
import org.itevents.model.User;
import org.itevents.wrapper.EventWrapper;

import java.util.List;

public interface EventService {

    void addEvent(Event event);

    Event getEvent(int eventId);

    List<Event> getAllEvents();

    Event removeEvent(Event event);

    List<Event> getFilteredEvents(EventWrapper wrapper);

    void assign(User user, Event event);

    void unassign(User user, Event event);

    List<User> getVisitors(Event event);
}
