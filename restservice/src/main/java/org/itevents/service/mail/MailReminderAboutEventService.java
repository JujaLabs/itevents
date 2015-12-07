package org.itevents.service.mail;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.itevents.dao.EventDao;
import org.itevents.dao.UserDao;
import org.itevents.model.Event;
import org.itevents.model.User;
import org.itevents.service.ReminderAboutEventService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ramax on 11/2/15.
 */
@Service("reminderAboutEventService")
public class MailReminderAboutEventService implements ReminderAboutEventService {

    @Value("#{new Integer('${reminderAboutEventForThePeriod}')}")
    private int daysTillEvent;

    private MailMock mailMock;

    @Inject
    UserDao userDao;
    @Inject
    EventDao eventDao;

    @Override
    public void execute() {
        Multimap<User,Event> usersAndEventsToRemind = getUsersAndEventsByDaysTillEvent(daysTillEvent);
        sendEmails(usersAndEventsToRemind);
    }

    public List<Event> getEventsByDaysTillEvent(int daysTillEvent){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.clear(Calendar.AM_PM);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        cal.add(Calendar.DATE,daysTillEvent);
        Date daysOfEventsToremind = new Date(cal.getTime().getTime());
        return eventDao.getEventsByDate(daysOfEventsToremind);
    }

    public Multimap<User, Event> getUsersAndEventsByDaysTillEvent(int daysTillEvent){
        List<Event> filteredEvents = getEventsByDaysTillEvent(daysTillEvent);
        Multimap <User, Event> usersAndEvents = HashMultimap.create();
        for(Event event : filteredEvents){
            for(User user : userDao.getUsersByEvent(event)) {
                usersAndEvents.put(user, event);
            }
        }
        return usersAndEvents;
    }

    public String createHTMLForMail(Event event) {
        //TODO
        return "ok";
    }

    private void sendEmails(Multimap<User, Event> usersAndEvents) {
        for (User userWhoGoToNearestEvent : usersAndEvents.keySet()) {
            Iterator nearestEventOfUserIterator = usersAndEvents.get(userWhoGoToNearestEvent).iterator();
            while(nearestEventOfUserIterator.hasNext()) {
                Event nearestEvent = (Event)nearestEventOfUserIterator.next();
                String htmlForMail = createHTMLForMail(nearestEvent);
                mailMock.sendEmail(htmlForMail, userWhoGoToNearestEvent.getLogin());
            }
        }
    }
}
