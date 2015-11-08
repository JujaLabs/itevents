package org.itevents.service.mail;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.itevents.dao.mybatis.mapper.EventMapper;
import org.itevents.dao.mybatis.mapper.VisitLogMapper;
import org.itevents.model.Event;
import org.itevents.model.VisitLog;
import org.itevents.parameter.FilteredEventsParameter;
import org.itevents.service.ReminderAboutEventService;
import org.itevents.service.VisitLogService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by ramax on 11/2/15.
 */
@Service("reminderAboutEventService")
public class MailReminderAboutEventService implements ReminderAboutEventService {

    private static final Logger LOGGER = LogManager.getLogger();

    @Value("#{new Integer('${reminderAboutEventForThePeriod}')}")
    private int amount;

    @Inject
    private VisitLogService visitLogService;

    private MailMock mailMock;

    @Inject
    EventMapper eventMapper;

    @Override
    public void execute() {
        List<VisitLog> visitLogList = getVisitLogListByDaysToEvent(amount);
        sendEmails(visitLogList);
    }

    public List<VisitLog> getVisitLogListByDaysToEvent(int daysToEvent){
        FilteredEventsParameter params = new FilteredEventsParameter();
        params.setDaysToEvent(daysToEvent);
        List<Event> filteredEvents = eventMapper.getFilteredEvents(params);
        List<VisitLog> resultVisitLog = new ArrayList<VisitLog>();
        for(Event event : filteredEvents){
            resultVisitLog.addAll(visitLogService.getVisitLogsByEvent(event));
        }
        System.out.println(resultVisitLog.toString());
        return resultVisitLog;
    }

    public String createHTMLForMail(Event event) {
        //TODO
        return "ok";
    }

    private void sendEmails(List<VisitLog> visitLogList) {
        for (VisitLog visitLog: visitLogList) {
            String htmlForMail = createHTMLForMail( visitLog.getEvent() );
            System.out.println(visitLog.toString());
            mailMock.sendEmail(htmlForMail, visitLog.getUser().getLogin()); //refactor mock. NPE
        }
    }
}
