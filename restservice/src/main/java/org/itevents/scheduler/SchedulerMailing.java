package org.itevents.scheduler;

import org.itevents.service.NotificationEventService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Created by ramax on 7/30/15.
 */
@Component
public class SchedulerMailing {

    @Inject
    private NotificationEventService notificationEventService;

    @Scheduled(cron = "${cron.start_sending}")
    public void startSending(){
        //TODO Call the service method to send to user mail
        notificationEventService.execute();
    }

}
