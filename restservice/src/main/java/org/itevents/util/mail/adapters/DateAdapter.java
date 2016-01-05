package org.itevents.util.mail.adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateAdapter extends XmlAdapter<String, Date> {
    
    private SimpleDateFormat dateFormatForXml = new SimpleDateFormat("dd.MM.yyyy");

    @Override
    public String marshal(Date date) throws Exception {
        return dateFormatForXml.format(date);
    }

    @Override
    public Date unmarshal(String dateString) throws Exception {
        return dateFormatForXml.parse(dateString);
    }
}