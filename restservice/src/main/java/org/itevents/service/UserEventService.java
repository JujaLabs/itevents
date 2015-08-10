package org.itevents.service;

import org.itevents.model.Event;
import org.itevents.model.User;

import java.util.List;
import java.util.Map;

/**
 * Created by ramax on 8/2/15.
 */
public interface UserEventService {

    Map<User,List<Event>> getUserEventSetMap();
}
