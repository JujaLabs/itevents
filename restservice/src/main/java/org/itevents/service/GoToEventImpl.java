package org.itevents.service;

import org.itevents.dao.Event;
import org.itevents.dao.User;
import org.springframework.security.access.prepost.PreAuthorize;


/**
 * Created by Well on 29.07.2015.
 */
public class GoToEventImpl implements GoToEvent {

    @Override
    @PreAuthorize("isAuthenticated()")
    public Long goToEvent(User user, Event event) {

        System.out.println("Login to subscribe!");


        return null;
    }
}

