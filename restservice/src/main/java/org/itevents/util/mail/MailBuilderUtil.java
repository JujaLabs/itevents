package org.itevents.util.mail;

import org.itevents.dao.model.Event;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.core.io.Resource;
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
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.ParseException;
import java.util.List;

@Component
public class MailBuilderUtil {
    @Value("classpath:utils/mailBuilder/recommendation-events-mail.xsl")
    private Resource emailTemplateXslResource;

    public String buildHtmlFromEventsList(List<Event> events) throws ParseException, JAXBException, IOException,
            TransformerException {
        return buildMailFromXmlEvents(buildXmlFromEventList(events));
    }

    private String buildXmlFromEventList(List<Event> events) throws JAXBException {
        EventsXmlNodeWrapper eventsXmlNodeWrapper = new EventsXmlNodeWrapper();
        eventsXmlNodeWrapper.setEvents(events);

        Marshaller marshaller = JAXBContext.newInstance(EventsXmlNodeWrapper.class)
                .createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

        StringWriter eventsInXmlStringWriter = new StringWriter();
        marshaller.marshal(eventsXmlNodeWrapper, eventsInXmlStringWriter);
        return eventsInXmlStringWriter.toString();
    }

    private String buildMailFromXmlEvents(String eventsInXml) throws IOException, TransformerException {
        Transformer transformer =
                TransformerFactory.newInstance().newTransformer(
                        new StreamSource(emailTemplateXslResource.getFile())
                );

        StringWriter mailStringWriter = new StringWriter();
        transformer.transform(
                new StreamSource(new StringReader(eventsInXml)),
                new StreamResult(mailStringWriter)
        );

        return mailStringWriter.toString();
    }

    @XmlRootElement(name = "events")
    @XmlAccessorType(XmlAccessType.FIELD)
    private static class EventsXmlNodeWrapper {

        @XmlElement(name = "event")
        private List<Event> events;

        public EventsXmlNodeWrapper() {
        }

        public List<Event> getEvents() {
            return events;
        }

        public void setEvents(List<Event> events) {
            this.events = events;
        }
    }
}