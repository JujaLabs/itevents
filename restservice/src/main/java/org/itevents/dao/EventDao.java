package org.itevents.dao;

import org.itevents.model.Event;
import org.itevents.model.User;
import org.itevents.parameter.FilteredEventsParameter;

import java.util.List;

public interface EventDao {

    Event getEvent(int id);

    List<Event> getAllEvents();

    void addEvent(Event event);

    void addEventTechnology(Event event);

    void updateEvent(Event event);

    List<Event> getFilteredEvents(FilteredEventsParameter params);
    
    void removeEvent(Event event);

    void removeEventTechnology(Event event);

    void assign(User user, Event event);

    void unassign(User user, Event event);

    List<Event> getEventsByUser(User user);

}