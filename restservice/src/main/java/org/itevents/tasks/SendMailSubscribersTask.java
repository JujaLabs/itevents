package org.itevents.tasks;

import org.itevents.model.Event;
import org.itevents.model.User;
import org.itevents.service.SendGridService;
import org.itevents.service.UserEventService;
import org.itevents.service.UserEventServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by ramax on 7/30/15.
 */
public class SendMailSubscribersTask {

    @Autowired
    private SendGridService sendGridService;

    @Autowired
    private UserEventService userEventService;

    public void sendMails(){

        Map<User, List<Event>> userEventSetMap = userEventService.getUserEventSetMap();

        for (Map.Entry<User, List<Event>> entry: userEventSetMap.entrySet()){

            User user = entry.getKey();
            for (Event event: entry.getValue()) {
                String message = getMessage(event);
//                sendGridService.sendMail(message,user);
                System.out.println("Send mail to " + user.getLogin() + "value: " + message);
            }
        }

    }

    private String getMessage(Event event) {
        return event.toString();
    }
}
