package org.itevents;

import org.itevents.model.Event;
import org.itevents.model.Location;
import org.itevents.service.MailService;
import org.junit.Assert;
import org.junit.Test;

import javax.xml.bind.JAXBException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MailServiceTest {
    @Test
    public void testGetEventById() throws JAXBException, ParseException {
        SimpleDateFormat formatter =  new SimpleDateFormat("dd.MM.yyyy");
        List<Event> events = new ArrayList<>();
        events.add(new Event(1, "Java", formatter.parse("10.07.2015"), null, "http://www.java.com.ua",
                "Beresteyska", new Location(50.458585, 30.742017), "java@gmail.com"));
        events.add(new Event(2, "Ruby", formatter.parse("20.07.2015"), null, "http://www.ruby.com.ua", "Shulyavska",
                new Location(50.454605, 30.445495), "ruby@gmail.com"));

        String expectedHTML = "<html>\n" +
                "<body>\n" +
                "<h2>Events list</h2>\n" +
                "<table border=\"1\" width=\"100%\" style=\"margin-bottom:20px;\">\n" +
                "<tr>\n" +
                "<td>10.07.2015 - Beresteyska - java@gmail.com</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td><a href=\"http://www.java.com.ua\">Java</a></td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "<table border=\"1\" width=\"100%\" style=\"margin-bottom:20px;\">\n" +
                "<tr>\n" +
                "<td>20.07.2015 - Shulyavska - ruby@gmail.com</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td><a href=\"http://www.ruby.com.ua\">Ruby</a></td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "</body>\n" +
                "</html>\n";

        MailService mailService = new MailService();
        String generatedHtml = mailService.buildHtmlFromEventList(events);
        assertEquals(expectedHTML, generatedHtml);
    }
}
