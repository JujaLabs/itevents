package org.itevents.service;

import org.itevents.model.Event;
import org.itevents.wrapper.FilterWrapper;
import org.itevents.model.User;

import java.util.List;

public interface EventService {

    void addEvent(Event event);

    Event getEvent(int id);

    List<Event> getAllEvents();

    Event removeEvent(Event event);

    List<Event> getFilteredEvents(FilterWrapper wrapper);

    void willGoToEvent(User user, Event event);

    void willNotGoToEvent(User user, Event event);

    List<User> getVisitors(Event event);
}
