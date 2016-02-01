package org.itevents.service;

import org.itevents.model.Event;
import org.itevents.model.Filter;
import org.itevents.wrapper.FilterWrapper;
import org.itevents.model.User;
import org.itevents.wrapper.FilterWrapper;

import java.util.Date;
import java.util.List;

public interface EventService {

    void addEvent(Event event);

    Event getEvent(int eventId);

    List<Event> getAllEvents();

    Event removeEvent(Event event);

    List<Event> getFilteredEvents(FilterWrapper wrapper);

    void assignUserToEvent(User user, Event event);
    List<Event> getFilteredEventsWithRating(Filter filter);

    void unassignUserFromEvent(User user, Event event, Date unassignDate, String unassignReason);

    List<Event> getEventsByUser(User user);

    List<Event> getEventsByDate(Date eventDate);
}
