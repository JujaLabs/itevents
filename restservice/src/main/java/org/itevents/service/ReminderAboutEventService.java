package org.itevents.service;

import com.google.common.collect.Multimap;
import org.itevents.model.Event;
import org.itevents.model.User;

import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * Created by ramax on 11/2/15.
 */
public interface ReminderAboutEventService {

    void execute() throws JAXBException, TransformerException, ParseException, IOException;

    List<Event> getEventsByDaysTillEvent();

    Multimap<User, Event> getUsersAndEventsByDaysTillEvent();

    void sendEmails(Multimap<User, Event> usersAndEvents) throws IOException, TransformerException, ParseException, JAXBException;

}
