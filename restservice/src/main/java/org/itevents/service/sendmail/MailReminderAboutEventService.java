package org.itevents.service.sendmail;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.itevents.dao.EventDao;
import org.itevents.dao.UserDao;
import org.itevents.model.Event;
import org.itevents.model.User;
import org.itevents.util.mail.MailBuilderUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.*;

/**
 * Created by ramax on 11/2/15.
 */
@Service("reminderAboutEventService")
public class MailReminderAboutEventService implements ReminderAboutEventService {

    @Value("${reminderAboutEventForThePeriod}")
    private int daysTillEvent;

    @Inject
    private UserDao userDao;
    @Inject
    private EventDao eventDao;
    @Inject
    private  MailBuilderUtil mailBuilderUtil;
    @Inject
    private MailService sendGridMailService;

    @Override
    public void execute() throws JAXBException, TransformerException, IOException {
        Multimap<User,Event> usersAndEventsToRemind = getUsersAndEventsByDaysTillEvent();
        sendEmails(usersAndEventsToRemind);
    }

    private List<Event> getEventsByDaysTillEvent(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.clear(Calendar.AM_PM);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        cal.add(Calendar.DATE,daysTillEvent);
        Date dateOfEventsToRemind = new Date(cal.getTime().getTime());
        return eventDao.getEventsByDate(dateOfEventsToRemind);
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

    private void sendEmails(Multimap<User, Event> usersAndEvents) throws IOException, TransformerException, JAXBException {
        for (User userWhoGoToNearestEvent : usersAndEvents.keySet()) {
            List<Event> nearestEvent = new ArrayList<>(usersAndEvents.get(userWhoGoToNearestEvent));
            String htmlLetter = mailBuilderUtil.buildHtmlFromEventsList(nearestEvent);
            sendGridMailService.sendMail(htmlLetter, userWhoGoToNearestEvent.getLogin());
        }
    }
}
