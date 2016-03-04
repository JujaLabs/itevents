package org.itevents.service.sendmail;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.itevents.dao.EventDao;
import org.itevents.dao.UserDao;
import org.itevents.dao.model.Event;
import org.itevents.dao.model.User;
import org.itevents.util.mail.MailBuilderUtil;
import org.itevents.util.time.DateTimeUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.*;

/**
 * Created by ramax on 11/2/15.
 */
@Service
public class MailEventReminderService implements EventReminderService {

    @Value("${days.till.event.to.remind}")
    private int daysTillEvent;

    @Inject
    private UserDao userDao;
    @Inject
    private EventDao eventDao;
    @Inject
    private  MailBuilderUtil mailBuilderUtil;
    @Inject
    private MailService mailService;

    private static final Logger LOGGER = LogManager.getLogger(MailEventReminderService.class);

    @Override
    public void remind() {
        Multimap<User,Event> usersAndEventsToRemind = getUsersAndEventsByDaysTillEvent();
        sendEmails(usersAndEventsToRemind);
    }

    private List<Event> getEventsByDaysTillEvent(){
        return eventDao.getEventsByDate(DateTimeUtil.getDateWithoutTime(DateTimeUtil.addDaysToDate(new Date(),daysTillEvent)));
    }

    private Multimap<User, Event> getUsersAndEventsByDaysTillEvent(){
        List<Event> filteredEvents = getEventsByDaysTillEvent();
        Multimap <User, Event> usersAndEvents = HashMultimap.create();
        for(Event event : filteredEvents){
            for(User user : userDao.getUsersByEvent(event)) {
                usersAndEvents.put(user, event);
            }
        }
        return usersAndEvents;
    }

    private String buildMail(List<Event> events) {
        try {
            return mailBuilderUtil.buildHtmlFromEventsList(events);
        } catch (Exception e) {
            LOGGER.error("Error build mail for user");
            throw new BuildMailException("Build mail for user error:", e);
        }
    }

    private void sendEmails(Multimap<User, Event> usersAndEvents){
        for (User user : usersAndEvents.keySet()) {
            List<Event> nearestEvent = new ArrayList<>(usersAndEvents.get(user));
            String htmlLetter = buildMail(nearestEvent);
            mailService.sendMail(htmlLetter, user.getLogin());
        }
    }
}
