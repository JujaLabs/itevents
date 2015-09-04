package org.itevents.dao;

import org.itevents.model.Event;
import org.itevents.parameter.FilteredEventsParameter;

import java.util.List;

public interface EventDao {

    Event getEvent(int id);

    List<Event> getAllEvents();

    void addEvent(Event event);

    void updateEvent(Event event);

    void removeEvent(int id);

    List<Event> getFilteredEvents(FilteredEventsParameter params);
}