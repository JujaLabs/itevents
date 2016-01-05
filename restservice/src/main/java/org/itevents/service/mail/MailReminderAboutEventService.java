package org.itevents.service.mail;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.itevents.dao.EventDao;
import org.itevents.dao.UserDao;
import org.itevents.model.Event;
import org.itevents.model.User;
import org.itevents.service.ReminderAboutEventService;
import org.itevents.service.sendmail.MailService;
import org.itevents.util.mail.MailBuilderUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

/**
 * Created by ramax on 11/2/15.
 */
@Service("reminderAboutEventService")
public class MailReminderAboutEventService implements ReminderAboutEventService {

    @Value("#{new Integer('${reminderAboutEventForThePeriod}')}")
    private int daysTillEvent;

    @Inject
    UserDao userDao;
    @Inject
    EventDao eventDao;
    @Inject
    MailBuilderUtil mailBuilderUtil;
    @Inject
    MailService sendGridMailService;

    @Override
    public void execute() throws JAXBException, TransformerException, ParseException, IOException {
        Multimap<User,Event> usersAndEventsToRemind = getUsersAndEventsByDaysTillEvent();
        sendEmails(usersAndEventsToRemind);
    }

    public List<Event> getEventsByDaysTillEvent(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.clear(Calendar.AM_PM);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        cal.add(Calendar.DATE,daysTillEvent);
        Date daysOfEventsToRemind = new Date(cal.getTime().getTime());
        return eventDao.getEventsByDate(daysOfEventsToRemind);
    }

    public Multimap<User, Event> getUsersAndEventsByDaysTillEvent(){
        List<Event> filteredEvents = getEventsByDaysTillEvent();
        Multimap <User, Event> usersAndEvents = HashMultimap.create();
        for(Event event : filteredEvents){
            for(User user : userDao.getUsersByEvent(event)) {
                usersAndEvents.put(user, event);
            }
        }
        return usersAndEvents;
    }

    @Override
    public void sendEmails(Multimap<User, Event> usersAndEvents) throws IOException, TransformerException, ParseException, JAXBException {
        for (User userWhoGoToNearestEvent : usersAndEvents.keySet()) {
            List<Event> nearestEvent = new ArrayList<>(usersAndEvents.get(userWhoGoToNearestEvent));
            String htmlLetter = mailBuilderUtil.buildHtmlFromEventsList(nearestEvent);
            sendGridMailService.sendMail(htmlLetter, userWhoGoToNearestEvent.getLogin());
        }
    }
}
