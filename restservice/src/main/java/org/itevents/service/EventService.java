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

    String WillGo(int eventID, int userID);

    String WillNotGo(int eventID, int userID);

    List<User> getVisitors(int eventID);
}
