package org.itevents.service;

import org.itevents.model.Event;
import org.itevents.model.User;
import org.itevents.wrapper.EventWrapper;

import java.util.List;

public interface EventService {

    void addEvent(Event event);

    Event getEvent(int eventID);

    List<Event> getAllEvents();

    Event removeEvent(Event event);

    List<Event> getFilteredEvents(EventWrapper wrapper);

    void willGoToEvent(User user, Event event);

    void willNotGoToEvent(User user, Event event);

    List<User> getVisitors(Event event);
}
