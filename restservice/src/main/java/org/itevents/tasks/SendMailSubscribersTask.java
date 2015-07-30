package org.itevents.tasks;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ramax on 7/30/15.
 */

public class SendMailSubscribersTask {

    private static int i;

    public void sendMails(){
        System.out.println("Send mail" + i++);

    }

}
