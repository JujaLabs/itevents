package org.itevents.service;

import org.itevents.model.Event;
import org.itevents.model.Filter;
import org.itevents.model.User;
import org.itevents.wrapper.FilterWrapper;

import java.util.Date;
import java.util.List;

public interface EventService {

    void addEvent(Event event);

    Event getEvent(int eventId);

    List<Event> getAllEvents();

    List<Event> getFilteredEvents(Filter filter);

    void assignUserToEvent(User user, Event event);

    List<Event> getFilteredEventsWithRating(Filter filter);

    void unassignUserFromEvent(User user, Event event, Date unassignDate, String unassignReason);

    List<Event> getEventsByUser(User user);

    Event getFutureEvent(int eventId);

    List<Event> getEventsByDate(Date eventDate);
}
