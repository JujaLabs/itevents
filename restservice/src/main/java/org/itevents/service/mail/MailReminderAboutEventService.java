package org.itevents.service.mail;

import org.itevents.model.Event;
import org.itevents.model.VisitLog;
import org.itevents.service.ReminderAboutEventService;
import org.itevents.service.SubscriptionUserService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by ramax on 11/2/15.
 */
@Service("reminderAboutEventService")
public class MailReminderAboutEventService implements ReminderAboutEventService {

    private SubscriptionUserService subscriptionService;
    private MailMock mailMock;

    @Override
    public void execute() {
        Date date = getDateToReminder();
        List<VisitLog> visitLogList = subscriptionService.getVisitLogsByDate(date);
        boolean isSent = sendEmails(visitLogList);

        if (!isSent)
            System.err.append("sent failed");
    }

    public String createHTMLForMail(Event event) {
        //TODO
        return "ok";
    }

    public boolean sendEmails(List<VisitLog> visitLogList) {
        for (VisitLog v: visitLogList) {
            String htmlForMail = createHTMLForMail( v.getEvent() );
            mailMock.sendEmail(htmlForMail, v.getUser().getLogin());
        }
        return true;
    }

    private Date getDateToReminder() {
        //TODO
        return new Date();
    }
}
