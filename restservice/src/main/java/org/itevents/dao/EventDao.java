package org.itevents.dao;

import org.itevents.model.Event;
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
}