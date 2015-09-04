package org.itevents.dao;

import org.itevents.model.Event;
import org.itevents.model.Location;
import org.itevents.parameter.FilteredEventsParameter;

import java.util.Date;
import java.util.List;

public interface EventDao {

    Event getEvent(int id);

    List<Event> getAllEvents();

    List<Event> getEventsInRadius(Location location, int radius);

    void addEvent(Event event);

    void updateEvent(Event event);

    List<Event> getFilteredEvents(FilteredEventsParameter params);

    List<Event> getFutureFilteredEvents(FilteredEventsParameter params, Date date);

    void removeEvent(Event event);
}