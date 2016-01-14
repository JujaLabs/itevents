package org.itevents.service;

import org.itevents.model.Event;
import org.itevents.model.Filter;
import org.itevents.wrapper.FilterWrapper;
import org.itevents.model.User;

import java.util.List;

public interface EventService {

    void addEvent(Event event);

    Event getEvent(int eventId);

    List<Event> getAllEvents();

    List<Event> getFilteredEvents(FilterWrapper wrapper);

    List<Event> getFilteredEventsWithRating(Filter filter);

    void assign(User user, Event event);

    void unassign(User user, Event event);

    List<Event> getEventsByUser(User user);

    Event getFutureEvent(int eventId);
}
