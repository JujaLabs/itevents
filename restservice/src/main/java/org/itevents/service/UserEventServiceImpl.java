package org.itevents.service;

import org.itevents.model.Event;
import org.itevents.model.Location;
import org.itevents.model.Role;
import org.itevents.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.InitBinder;

import java.util.*;

/**
 * Created by ramax on 8/2/15.
 */
public class UserEventServiceImpl implements UserEventService {

    @Autowired
    EventService eventService;

    private Map<User, List<Event>> userEventSetMap;

    @Override
    public Map<User, List<Event>> getUserEventSetMap() {

        userEventSetMap = new HashMap<>();

        User user = new User("login@ex.ua","pass",new Role("guest"));
        List<Event> events = eventService.getAllEvents();
        events.add(new Event(1,"asd",new Date(),new Date(),"asdas","asda",new Location(12.0,12.0),"contact"));

        userEventSetMap.put(user,events);

        User user2 = new User("qwerty@ex.ua", "pass", new Role("guest"));

        userEventSetMap.put(user2,events);

        return userEventSetMap;
    }
}
