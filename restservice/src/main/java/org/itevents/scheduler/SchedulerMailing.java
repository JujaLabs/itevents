package org.itevents.scheduler;

import org.itevents.model.Event;
import org.itevents.model.User;
import org.itevents.service.SendGridService;
import org.itevents.service.UserEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.Map;

/**
 * Created by ramax on 7/30/15.
 */
public class SchedulerMailing {

//    @Autowired
//    private SendGridService sendGridService;

    public void startSending(){
        System.out.println("Send emails");
        //TODO Call the service method to send to user mail
    }

}
