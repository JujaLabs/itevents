package org.itevents.scheduler;

import org.itevents.service.NotificationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Created by ramax on 7/30/15.
 */
@Component
public class MailSenderScheduler {

    @Inject
    private NotificationService notificationService;

    @Scheduled(cron = "${cron_start_sending}")
    public void startSending(){
        notificationService.performNotify();
    }
}
