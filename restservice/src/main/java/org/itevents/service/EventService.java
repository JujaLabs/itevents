package org.itevents.service;

import org.itevents.dao.model.Event;
import org.itevents.dao.model.Filter;
import org.itevents.dao.model.User;

import java.util.Date;
import java.util.List;

public interface EventService {

    void addEvent(Event event);

    Event getEvent(int eventId);

    List<Event> getAllEvents();

    List<Event> getFilteredEvents(Filter filter);

    void assignAuthorizedUserToEvent(int futureEventId);

    List<Event> getFilteredEventsWithRating(Filter filter);

    void unassignUserFromEvent(User user, Event event, Date unassignDate, String unassignReason);

    List<Event> getEventsByUser(User user);

    Event getFutureEvent(int eventId);
}
