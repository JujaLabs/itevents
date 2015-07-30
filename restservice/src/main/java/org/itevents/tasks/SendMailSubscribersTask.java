package org.itevents.tasks;

import org.itevents.model.Event;
import org.itevents.model.User;
import org.itevents.service.SendGridService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.Set;

/**
 * Created by ramax on 7/30/15.
 */

public class SendMailSubscribersTask {

    @Autowired
    private SendGridService sendGridService;

    private Map<User,Set<Event>> userEventSetMap;

    public void sendMails(){

        for (Map.Entry<User,Set<Event>> entry: userEventSetMap.entrySet()){

            User user = entry.getKey();
            for (Event event: entry.getValue()) {
                String message = getMessage(event);
                sendGridService.sendMail(message,user);
            }
        }
    }

    private String getMessage(Event event) {
        return event.toString();
    }
}
