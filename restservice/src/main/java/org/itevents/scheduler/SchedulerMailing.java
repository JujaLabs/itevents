package org.itevents.scheduler;

import org.itevents.model.Event;
import org.itevents.model.User;
import org.itevents.service.SendGridService;
import org.itevents.service.UserEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.config.Task;
import org.springframework.scheduling.support.CronSequenceGenerator;
import org.springframework.scheduling.support.CronTrigger;

import java.util.List;
import java.util.Map;

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
