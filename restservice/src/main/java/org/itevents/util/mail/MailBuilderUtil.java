package org.itevents.util.mail;


import org.itevents.dao.model.Event;
import org.itevents.dao.model.User;
import org.itevents.util.OneTimePassword.OneTimePassword;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

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
    @Value("classpath:utils/mailBuilder/UserOtpMail.xsl")
    private Resource emailUserOtpTemplateXslResource;

    public String buildHtmlFromEventsList(List<Event> events) throws ParseException, JAXBException, IOException,
            TransformerException {
        return buildMailFromXmlEvents(buildXmlFromEventList(events));
    }

    public String buildHtmlFromUserOtp(User user, OneTimePassword oneTimePassword)  throws Exception {
        return buildMailFromXmlUserOtp(BuildXmlFromUserOtp(user, oneTimePassword));
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

    private String BuildXmlFromUserOtp(User user, OneTimePassword oneTimePassword) throws JAXBException, IOException {
        UserOtpXmlWrapper userOtpXmlWrapper = new UserOtpXmlWrapper();
        userOtpXmlWrapper.setUser(user);
        userOtpXmlWrapper.setOneTimePassword(oneTimePassword);

        Marshaller marshaller = JAXBContext.newInstance(UserOtpXmlWrapper.class).createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

        StringWriter userOtpInXmlStringWriter = new StringWriter();
        marshaller.marshal(userOtpXmlWrapper, userOtpInXmlStringWriter);
        return userOtpInXmlStringWriter.toString();
    }

    private String buildMailFromXmlUserOtp(String userOtpUrl)  throws IOException, TransformerException {
        StringReader stringReader = new StringReader(userOtpUrl);
        StringWriter mailStringWriter = new StringWriter();
        Transformer transformer =
                TransformerFactory.newInstance().newTransformer(
                        new StreamSource(emailUserOtpTemplateXslResource.getFile())
                );
        transformer.transform(
                new StreamSource(stringReader),
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

    @XmlRootElement(name ="userOtp")
    @XmlAccessorType(XmlAccessType.FIELD)
    private static class UserOtpXmlWrapper {
        @XmlElement(name = "user")
        private User user;
        @XmlElement(name = "otp")
        private OneTimePassword oneTimePassword;
        @XmlElement(name = "url")
        private String url;

        public UserOtpXmlWrapper() {
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public OneTimePassword getOneTimePassword() {
            return oneTimePassword;
        }

        public void setOneTimePassword(OneTimePassword oneTimePassword) {
            this.oneTimePassword = oneTimePassword;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}