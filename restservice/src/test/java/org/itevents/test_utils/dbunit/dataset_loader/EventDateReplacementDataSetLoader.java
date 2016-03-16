package org.itevents.test_utils.dbunit.dataset_loader;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.ITable;
import org.itevents.util.time.Clock;
import org.itevents.util.time.CustomDateTime;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by roma on 13.10.15.
 */
@Component
public class EventDateReplacementDataSetLoader extends AbstractReplacementDataSetLoader {

    private static final String EVENT_TABLE_NAME = "event";
    private static final String EVENT_DATE_COLUMN_NAME = "event_date";
    private static final String DATE_TEMPLATE_REGEXP = "\\{now([+-]\\d+)\\}"; // {now+5}, {now-10}
    private static final String DATE_TIME_FORMAT_FOR_DATABASE = "yyyy-MM-dd HH:mm:ss";

    @Inject
    private Clock clock;

    @Override
    protected void replace() throws DataSetException {
        List<String> dateTemplates = getDateTemplatesFromDataSet();
        for (String dateTemplate : dateTemplates) {
            // {now+5} > 18.10.15 17:23:45
            replace(dateTemplate, buildFormattedDateFromDateTemplate(dateTemplate));
        }
    }

    private List<String> getDateTemplatesFromDataSet() throws DataSetException {
        List<String> dateTemplates = new ArrayList<>();
        ITable eventTable = this.dataSet.getTable(EVENT_TABLE_NAME);
        for (int rowIndex = 0; rowIndex < eventTable.getRowCount(); rowIndex++) {
            dateTemplates.add(eventTable.getValue(rowIndex, EVENT_DATE_COLUMN_NAME).toString());
        }
        return dateTemplates;
    }

    private String buildFormattedDateFromDateTemplate(String dateTemplate) {
        Matcher regexDateTemplateMatcher = Pattern.compile(DATE_TEMPLATE_REGEXP)
                .matcher(dateTemplate);
        String formattedDateTimeString;
        if (regexDateTemplateMatcher.find()) {
            int incrementingDaysCount = Integer.parseInt(regexDateTemplateMatcher.group(1));
            CustomDateTime incrementedDateTime = new CustomDateTime()
                    .withLocalDateTime(clock.getNowLocalDateTime().plusDays(incrementingDaysCount))
                    .withFormat(DATE_TIME_FORMAT_FOR_DATABASE);
            formattedDateTimeString = incrementedDateTime.toString();
        } else {
            formattedDateTimeString =  dateTemplate;
        }
        return formattedDateTimeString;
    }
}