package org.itevents.service;

import org.itevents.model.Event;
import org.itevents.wrapper.FilterWrapper;

import java.util.List;

public interface EventService {

    void addEvent(Event event);

    Event getEvent(int id);

    List<Event> getAllEvents();

    List<Event> getFilteredEvents(FilterWrapper wrapper);
}
