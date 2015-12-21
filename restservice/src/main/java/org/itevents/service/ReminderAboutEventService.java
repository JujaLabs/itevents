package org.itevents.service;

import com.google.common.collect.Multimap;
import org.itevents.model.Event;
import org.itevents.model.User;

import java.util.List;

/**
 * Created by ramax on 11/2/15.
 */
public interface ReminderAboutEventService {

    void execute();

    List<Event> getEventsByDaysTillEvent();

    Multimap<User, Event> getUsersAndEventsByDaysTillEvent();

    String createHtmlForMail(Event event);

    void sendEmails(Multimap<User, Event> usersAndEvents);

}
