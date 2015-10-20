package org.itevents.utils.mailBuilder;

import org.itevents.model.City;
import org.itevents.model.Currency;
import org.itevents.model.Event;
import org.itevents.model.Location;
import org.junit.Test;

import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MailBuilderUtilTest {
    @Test
    public void MailBuilderUtil() throws JAXBException, ParseException, IOException, TransformerException {
        SimpleDateFormat eventsDateFormat =  new SimpleDateFormat("dd.MM.yyyy");
        List<Event> events = new ArrayList<>();
        events.add(new Event(1, "Java", eventsDateFormat.parse("10.07.2015"), null, "http://www.java.com.ua",
                "Beresteyska", new Location(50.458585, 30.742017), "java@gmail.com", true, 0, new Currency("USD"), new City()));
        events.add(new Event(2, "Ruby", eventsDateFormat.parse("20.07.2015"), null, "http://www.ruby.com.ua", "Shulyavska",
                new Location(50.454605, 30.445495), "ruby@gmail.com", true, 0, new Currency("USD"), new City()));

        String expectedHTML = "<html>\n" +
                "<head>\n" +
                "<META http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                "<title>Ближайшие интересные Вам ивенты!</title>\n" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n" +
                "<meta name=\"robots\" content=\"noindex, nofollow\">\n" +
                "</head>\n" +
                "<body>\n" +
                "<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\" bgcolor=\"#f2f2f2\">\n" +
                "<tr>\n" +
                "<td valign=\"top\" align=\"center\">\n" +
                "<table cellspacing=\"0\" cellpadding=\"0\" width=\"560\" border=\"0\" style=\"border-width:0px;border-color:#cccccc;border-style:solid;\" bgcolor=\"#ffffff\">\n" +
                "<tr>\n" +
                "<td align=\"center\" width=\"560\">\n" +
                "<table border=\"0\" cellspacing=\"0\" width=\"560\" cellpadding=\"0\">\n" +
                "<tr>\n" +
                "<td align=\"center\" width=\"560\"><img height=\"100\" src=\"\" width=\"530\" border=\"0\" alt=\"Our logo\"></td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "<table width=\"560\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "<tr>\n" +
                "<td width=\"560\" height=\"20\"></td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "<table width=\"520\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "<tr>\n" +
                "<td align=\"left\" valign=\"top\" style=\"color:#000000;font-family:Arial, Helvetica, sans-serif;line-height:17px;font-size:11px;\">\n" +
                "<p style=\"margin:0px 0px 10px 0px; margin-bottom: 10px;\">\n" +
                "<h3 style=\"margin:0px;\">Java</h3>\n" +
                "<span style=\"font-style: italic;\">10.07.2015</span>\n" +
                "<br>\n" +
                "<span>Beresteyska - java@gmail.com</span>\n" +
                "<br>\n" +
                "<a href=\"http://www.java.com.ua\">Register to Java</a>\n" +
                "</p>\n" +
                "</td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "<table width=\"520\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "<tr>\n" +
                "<td width=\"520\" height=\"10\"></td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "<table width=\"520\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "<tr>\n" +
                "<td align=\"left\" valign=\"top\" style=\"color:#000000;font-family:Arial, Helvetica, sans-serif;line-height:17px;font-size:11px;\">\n" +
                "<p style=\"margin:0px 0px 10px 0px; margin-bottom: 10px;\">\n" +
                "<h3 style=\"margin:0px;\">Ruby</h3>\n" +
                "<span style=\"font-style: italic;\">20.07.2015</span>\n" +
                "<br>\n" +
                "<span>Shulyavska - ruby@gmail.com</span>\n" +
                "<br>\n" +
                "<a href=\"http://www.ruby.com.ua\">Register to Ruby</a>\n" +
                "</p>\n" +
                "</td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "<table width=\"520\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "<tr>\n" +
                "<td width=\"520\" height=\"10\"></td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "</td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "</td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "</body>\n" +
                "</html>\n";
        String mailHtml = new MailBuilderUtil().buildHtmlFromEventsList(events);
        assertEquals(expectedHTML, mailHtml);
    }
}
