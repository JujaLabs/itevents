package org.itevents.service;

import org.itevents.model.Event;
import org.itevents.model.User;
import org.itevents.wrapper.EventWrapper;

import java.util.List;

public interface EventService {

    void addEvent(Event event);

    Event getEvent(int id);

    List<Event> getAllEvents();

    Event removeEvent(Event event);

    List<Event> getFilteredEvents(EventWrapper wrapper);

    String WillGo(int id, int userID);

    String WillNotGo(int id, int userID);

    List<User> getVisitors(int id);
}
