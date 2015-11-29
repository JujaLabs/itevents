package org.itevents.service;

import org.itevents.model.Event;
import org.itevents.wrapper.FilterWrapper;
import org.itevents.model.User;

import java.util.List;

public interface EventService {

    void addEvent(Event event);

    Event getEvent(int eventId);

    List<Event> getAllEvents();

    Event removeEvent(Event event);

    List<Event> getFilteredEvents(FilterWrapper wrapper);

    void assign(User user, Event event);

    void unassign(User user, Event event);

    List<Event> getEventsByUser(User user);
}
