package org.itevents.service.sendmail;

import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

/**
 * Created by ramax on 11/2/15.
 */
public interface ReminderAboutEventService {

    void execute() throws JAXBException, TransformerException, IOException;
}