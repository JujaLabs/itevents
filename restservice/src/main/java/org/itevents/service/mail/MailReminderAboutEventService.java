package org.itevents.service.mail;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.itevents.model.Event;
import org.itevents.model.VisitLog;
import org.itevents.service.ReminderAboutEventService;
import org.itevents.service.VisitLogService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by ramax on 11/2/15.
 */
@Service("reminderAboutEventService")
public class MailReminderAboutEventService implements ReminderAboutEventService {

    private static final Logger LOGGER = LogManager.getLogger();

    @Value("reminderAboutEventForThePeriod")
    private int amount;

    @Inject
    private VisitLogService visitLogService;

    private MailMock mailMock;

    @Override
    public void execute() {
        Date date = getDateToReminder();
        List<VisitLog> visitLogList = visitLogService.getVisitLogsByDate( date );
        sendEmails(visitLogList);
    }

    public String createHTMLForMail(Event event) {
        //TODO
        return "ok";
    }

    private void sendEmails(List<VisitLog> visitLogList) {
        for (VisitLog v: visitLogList) {
            String htmlForMail = createHTMLForMail( v.getEvent() );
            mailMock.sendEmail(htmlForMail, v.getUser().getLogin());
        }
    }

    private Date getDateToReminder() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(calendar.DAY_OF_MONTH, amount);
        return calendar.getTime();
    }
}
