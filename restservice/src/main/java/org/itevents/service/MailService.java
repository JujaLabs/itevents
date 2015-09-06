package org.itevents.service;

import org.itevents.model.Event;
import org.itevents.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;

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
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MailService {

    public String buildHtmlFromEventList(List<Event> events) throws ParseException, JAXBException, IOException, TransformerException {
        String  html = buildHtmlFromXml(buildXmlFromEventsList(events));
        return html;
    }

    private String buildXmlFromEventsList(List<Event> events) throws JAXBException {
        EventsList<Event> eventsList = new EventsList<>(events);
        JAXBContext jaxbContext = JAXBContext.newInstance(EventsList.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        StringWriter stringWriter = new StringWriter();

        marshaller.marshal(eventsList, stringWriter);

        return stringWriter.toString();
    }

    private String buildHtmlFromXml(String xml) throws IOException, TransformerException {
        String htmlString = "";
        StringWriter writer = new StringWriter();
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        ClassLoader classLoader = getClass().getClassLoader();
        File xslFile = null;
        Resource resource = context.getResource("mail.xsl");
        TransformerFactory tFactory = TransformerFactory.newInstance();

        Transformer transformer =
                    tFactory.newTransformer
                            (new javax.xml.transform.stream.StreamSource
                                    (resource.getFile())); // todo fixme filepath

        transformer.transform
                (new javax.xml.transform.stream.StreamSource
                                (new StringReader(xml)),
                        new javax.xml.transform.stream.StreamResult
                                (writer));

        return writer.toString();
    }

    @XmlRootElement(name="events")
    @XmlAccessorType(XmlAccessType.FIELD)
    private static class EventsList<Events>{

        @XmlElement(name = "event")
        private List<Event> list;

        public EventsList() {
        }

        public EventsList(List<Event> list) {
            this.list = list;
        }

        public List<Event> getList() {
            return list;
        }

        public void setList(List<Event> list) {
            this.list = list;
        }
    }
}