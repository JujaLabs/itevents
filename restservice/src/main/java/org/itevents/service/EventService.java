package org.itevents.service;

import org.itevents.model.Event;
import org.itevents.model.User;
import org.itevents.wrapper.FilterWrapper;

import java.util.List;

public interface EventService {

    void addEvent(Event event);

    Event getEvent(int eventId);

    List<Event> getAllEvents();

    List<Event> getFilteredEvents(FilterWrapper wrapper);

    void assign(User user, Event event);

    void unassign(User user, Event event);

    List<Event> getEventsByUser(User user);

    Event getFutureEvent(int eventId);
}
