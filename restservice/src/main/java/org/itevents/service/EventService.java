package org.itevents.service;

import org.itevents.controller.FilterEventParams;
import org.itevents.model.Event;

import java.util.List;

public interface EventService {

    void addEvent(Event event);

    Event getEvent(int id);

    List<Event> getAllEvents();

    void removeEvent(int id);

    List<Event> getFilteredEvents(FilterEventParams params);
}
