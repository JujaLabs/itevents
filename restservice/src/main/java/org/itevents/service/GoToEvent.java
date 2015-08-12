package org.itevents.service;

import org.itevents.dao.Event;
import org.itevents.dao.User;

/**
 * Created by Well on 29.07.2015.
 */
public interface GoToEvent {

 Long goToEvent (User user, Event event);

}
