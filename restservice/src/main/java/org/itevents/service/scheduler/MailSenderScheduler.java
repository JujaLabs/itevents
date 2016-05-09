package org.itevents.service.scheduler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.itevents.service.NotificationService;
import org.itevents.service.sendmail.EventReminderService;
import org.itevents.service.sendmail.NotificationServiceException;
import org.itevents.util.mail.MailBuilderUtilException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Created by ramax on 7/30/15.
 */
@Component
public class MailSenderScheduler {

    private static final Logger LOGGER = LogManager.getLogger();

    @Inject
    private NotificationService notificationService;

    @Inject
    private EventReminderService eventReminderService;

    @Scheduled(cron = "${cron.start.sending}")
    public void startSending(){
        notificationService.performNotify();
    }

    @Scheduled(cron = "${cron.remind.about.event}")
    public void startRemind(){
        try {
            eventReminderService.remind();
        }catch (MailBuilderUtilException e){
            throw new NotificationServiceException(e.getMessage(), e);
            /*
            * @TODO: need to send mail to Admin with information, that mail to user wasn't built and sent.
            * issue53
            * https://github.com/JuniorsJava/itevents/issues/53
            */
        }
    }
}
