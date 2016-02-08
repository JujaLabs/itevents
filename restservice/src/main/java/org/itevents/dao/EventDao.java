package org.itevents.dao;

import org.itevents.dao.model.Event;
import org.itevents.dao.model.Filter;
import org.itevents.dao.model.User;

import java.util.Date;
import java.util.List;

public interface EventDao {

    Event getEvent(int id);

    List<Event> getAllEvents();

    void addEvent(Event event);

    void addEventTechnology(Event event);

    void updateEvent(Event event);

    List<Event> getFilteredEvents(Filter params);

    List<Event> getFilteredEventsWithRating(Filter params);

    void removeEventTechnology(Event event);

    void assignUserToEvent(User user, Event event);

    void unassignUserFromEvent(User user, Event event, Date unassignDate, String unassignReason);

    List<Event> getEventsByUser(User user);
}