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
import javax.xml.transform.TransformerFactory;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MailService {

    public String buildHtmlFromEventList(List<Event> events) throws ParseException, JAXBException {

        EventsList<Event> eventsList = new EventsList<>(events);
        JAXBContext jaxbContext = JAXBContext.newInstance(EventsList.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(eventsList, stringWriter); // todo fixme

        String xmlString = stringWriter.toString();

        /***************************************/
        String htmlString = "";
        StringWriter writer = new StringWriter();
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        ClassLoader classLoader = getClass().getClassLoader();
        File xslFile = null;
        Resource resource = context.getResource("mail.xsl");
//        try {
//            xslFile = ResourceUtils.getFile("classpath:mail.xsl");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }

        try {

            TransformerFactory tFactory = TransformerFactory.newInstance();

            Transformer transformer =
                    tFactory.newTransformer
                            (new javax.xml.transform.stream.StreamSource
                                    (resource.getFile())); // todo fixme filepath

            transformer.transform
                    (new javax.xml.transform.stream.StreamSource
                                    (new StringReader(xmlString)),
                            new javax.xml.transform.stream.StreamResult
                                    (writer));
        }
        catch (Exception e) {
            e.printStackTrace( ); // todo fixme
        }
        htmlString = writer.toString();

        return htmlString;// todo fixme
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