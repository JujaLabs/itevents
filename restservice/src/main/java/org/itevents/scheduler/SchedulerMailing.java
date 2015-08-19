package org.itevents.scheduler;

import org.springframework.scheduling.annotation.Scheduled;

/**
 * Created by ramax on 7/30/15.
 */
public class SchedulerMailing {

    @Scheduled(cron = "${cron.start_sending}")
    public void startSending(){
        System.out.println("Send emails");
        //TODO Call the service method to send to user mail
    }

}
