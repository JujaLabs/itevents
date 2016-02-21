package org.itevents.test_utils.dbunit.dataset_loader;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.ITable;
import org.itevents.util.time.DateTimeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by roma on 13.10.15.
 */
public class EventDateReplacementDataSetLoader extends AbstractReplacementDataSetLoader {

    private static final String EVENT_TABLE_NAME = "event";
    private static final String EVENT_DATE_COLUMN_NAME = "event_date";
    private static final String DATE_TEMPLATE_REGEXP = "\\{now([+-]\\d+)\\}"; // {now+5}, {now-10}
    private static final String DATE_TIME_FORMAT_FOR_DATABASE = "yyyy-MM-dd HH:mm:ss.SSS";

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
        Matcher regexMatcher = Pattern.compile(DATE_TEMPLATE_REGEXP)
                .matcher(dateTemplate);
        if (regexMatcher.find()) {
            return regexMatcher.replaceAll(
                    DateTimeUtil.getFormattedNowDatePlusDays(
                            Integer.parseInt(regexMatcher.group(1)), DATE_TIME_FORMAT_FOR_DATABASE
                    )
            );
        } else {
            return dateTemplate;
        }
    }
}