package org.itevents.dao;

import org.itevents.model.Event;
import org.itevents.model.Filter;

import java.util.List;

public interface EventDao {

    Event getEvent(int id);

    List<Event> getAllEvents();

    void addEvent(Event event);

    void addEventTechnology(Event event);

    void updateEvent(Event event);

    List<Event> getFilteredEvents(Filter params);

    // @alex-anakin: it would be good to use method getFilteredEvents(Filter params)
    // making Filter more complicated
    List<Event> getFilteredEventsWithRating(Filter params);

    void removeEvent(Event event);

    void removeEventTechnology(Event event);
}