package org.itevents.scheduler;

import org.itevents.service.NotificationService;
import org.itevents.service.sendmail.ReminderAboutEventService;
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

    @Inject
    private ReminderAboutEventService reminderAboutEventService;

    @Scheduled(cron = "${cron.start.sending}")
    public void startSending(){
        notificationService.performNotify();
    }

    @Scheduled(cron = "${cron.remind.about.event}")
    public void startRemind(){
        reminderAboutEventService.execute();
    }

}
