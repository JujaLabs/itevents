package org.itevents.service;

import org.itevents.dao.Event;
import org.itevents.dao.User;


/**
 * Created by Well on 29.07.2015.
 */
public class GoToEventImpl implements GoToEvent {

    private boolean isLogged(User user){
        // проверить сессию

        return false;
    }

    @Override
    public Long goToEvent(User user, Event event) {
       if (!isLogged(user)){
           System.out.println("Login to subscribe!");
       }




        return null;
    }
}

