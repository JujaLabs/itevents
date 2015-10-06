package org.itevents.dao;

import org.itevents.model.Event;
import org.itevents.parameter.FilteredEventsParameter;

import java.util.List;
import java.util.Set;

public interface EventDao {

    Event getEvent(int id);

    List<Event> getAllEvents();

    void addEvent(Event event);

    void addEventTechnology(Event event);

    void updateEvent(Event event);

    Set<Event> getFilteredEvents(FilteredEventsParameter params);
    
    void removeEvent(Event event);
}