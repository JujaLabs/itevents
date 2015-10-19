package org.itevents.utils.mailBuilder;

import org.itevents.model.Event;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.ParseException;
import java.util.List;

public class MailBuilderUtil {

    public String buildHtmlFromEventsList(List<Event> events) throws ParseException, JAXBException, IOException
            , TransformerException {
        String  emailFormattedInHTML = buildMailFromXmlEvents(buildXmlFromEventList(events));
        return emailFormattedInHTML;
    }

    private String buildXmlFromEventList(List<Event> events) throws JAXBException {
        RootXmlNodeWrapper rootXmlNodeWrapper = new RootXmlNodeWrapper();
        rootXmlNodeWrapper.setEvents(events);

        Marshaller marshaller = JAXBContext.newInstance(RootXmlNodeWrapper.class)
                .createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

        StringWriter eventsInXmlStringWriter = new StringWriter();
        marshaller.marshal(rootXmlNodeWrapper, eventsInXmlStringWriter);
        System.out.println(eventsInXmlStringWriter.toString());
        return eventsInXmlStringWriter.toString();
    }

    private String buildMailFromXmlEvents(String eventsInXml) throws IOException, TransformerException {
        File mailXslFile = new ClassPathXmlApplicationContext("applicationContext.xml")
                .getResource("utils/mailBuilder/mail.xsl")
                .getFile();

        Transformer transformer =
                TransformerFactory.newInstance().newTransformer(
                        new StreamSource(mailXslFile)
                );

        StringWriter mailStringWriter = new StringWriter();
        transformer.transform(
                new StreamSource(new StringReader(eventsInXml)),
                new StreamResult(mailStringWriter)
        );

        return mailStringWriter.toString();
    }

    @XmlRootElement(name="events")
    @XmlAccessorType(XmlAccessType.FIELD)
    private static class RootXmlNodeWrapper{

        @XmlElement(name = "event")
        private List<Event> events;

        public RootXmlNodeWrapper() {}

        public List<Event> getEvents() {
            return events;
        }

        public void setEvents(List<Event> events) {
            this.events = events;
        }
    }
}