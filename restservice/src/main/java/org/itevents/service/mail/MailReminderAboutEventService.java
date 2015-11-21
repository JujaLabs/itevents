package org.itevents.service.mail;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.itevents.dao.EventDao;
import org.itevents.dao.mybatis.mapper.EventMapper;
import org.itevents.dao.mybatis.mapper.VisitLogMapper;
import org.itevents.model.Event;
import org.itevents.model.User;
import org.itevents.model.VisitLog;
import org.itevents.parameter.FilteredEventsParameter;
import org.itevents.service.ReminderAboutEventService;
import org.itevents.service.VisitLogService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.*;

/**
 * Created by ramax on 11/2/15.
 */
@Service("reminderAboutEventService")
public class MailReminderAboutEventService implements ReminderAboutEventService {

    @Value("#{new Integer('${reminderAboutEventForThePeriod}')}")
    private int daysTillEvent;

    private MailMock mailMock;

    @Inject
    EventDao eventDao;

    @Override
    public void execute() {
        Map<User,Event> usersAndEventsToRemind = getUsersAndEventsByDaysTillEvent(daysTillEvent);
        sendEmails(usersAndEventsToRemind);
    }

    public List<Event> getEventsByDaysTillEvent(int daysTillEvent){
        FilteredEventsParameter params = new FilteredEventsParameter();
        params.setDaysTillEvent(daysTillEvent);
        return eventDao.getFilteredEvents(params);
    }

    public Map<User,Event> getUsersAndEventsByDaysTillEvent(int daysTillEvent){
        List<Event> filteredEvents = getEventsByDaysTillEvent(daysTillEvent);
        Map<User,Event> usersAndEvents = new HashMap<User,Event>();
        for(Event event : filteredEvents){
            for(User user : eventDao.getVisitors(event)) {
                usersAndEvents.put(user, event);
            }
        }
        return usersAndEvents;
    }

    public String createHTMLForMail(Event event) {
        //TODO
        return "ok";
    }

    private void sendEmails(Map<User,Event> usersAndEvents) {
        for (Map.Entry<User,Event> userAndEvent: usersAndEvents.entrySet()) {
            String htmlForMail = createHTMLForMail(userAndEvent.getValue());
            mailMock.sendEmail(htmlForMail, userAndEvent.getKey().getLogin());
        }
    }
}
