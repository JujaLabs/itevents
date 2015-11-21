package org.itevents.dao;

import org.itevents.model.Event;
import org.itevents.model.Filter;
import org.itevents.model.User;

import java.util.List;

public interface EventDao {

    Event getEvent(int id);

    List<Event> getAllEvents();

    void addEvent(Event event);

    void addEventTechnology(Event event);

    void updateEvent(Event event);

    List<Event> getFilteredEvents(Filter params);

    List<Event> getFilteredEventsWithRating(Filter params);

    void removeEvent(Event event);

    void removeEventTechnology(Event event);

    void willGoToEvent(User user, Event event);

    void willNotGoToEvent(User user, Event event);

    List<User> getVisitors(Event event);

}