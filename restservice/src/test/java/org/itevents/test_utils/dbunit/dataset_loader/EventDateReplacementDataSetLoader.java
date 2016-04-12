package org.itevents.test_utils.dbunit.dataset_loader;

import com.github.springtestdbunit.dataset.DataSetLoader;
import com.github.springtestdbunit.dataset.FlatXmlDataSetLoader;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ReplacementDataSet;
import org.itevents.util.time.DateTimeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EventDateReplacementDataSetLoader implements DataSetLoader {

    private static final String EVENT_TABLE_NAME = "event";
    private static final String EVENT_DATE_COLUMN_NAME = "event_date";
    private static final String DATE_TEMPLATE_REGEXP = "\\{now([+-]\\d+)\\}"; // {now+5}, {now-10}
    private static final String DATE_TIME_FORMAT_FOR_DATABASE = "yyyy-MM-dd HH:mm:ss.SSS";

    @Override
    public IDataSet loadDataSet(Class<?> testClass, String location) throws Exception {
        IDataSet dataSetFromXml = new FlatXmlDataSetLoader().loadDataSet(testClass, location);
        List<String> dateTemplates = getDateTemplatesFromDataSet(dataSetFromXml);
        return buildReplacementDataSetFromDateTemplates(dataSetFromXml, dateTemplates);
    }

    private List<String> getDateTemplatesFromDataSet(IDataSet dataSet) throws DataSetException {
        List<String> dateTemplates = new ArrayList<>();
        ITable eventTable = dataSet.getTable(EVENT_TABLE_NAME);
        for (int rowIndex = 0; rowIndex < eventTable.getRowCount(); rowIndex++) {
            dateTemplates.add(eventTable.getValue(rowIndex, EVENT_DATE_COLUMN_NAME).toString());
        }
        return dateTemplates;
    }

    private ReplacementDataSet buildReplacementDataSetFromDateTemplates(IDataSet dataSet, List<String> dateTemplates) {
        ReplacementDataSet replacementDataSet = new ReplacementDataSet(dataSet);
        for (String dateTemplate : dateTemplates) {
            String formattedDateFromDateTemplate =  buildFormattedDateFromDateTemplate(dateTemplate);
            replacementDataSet.addReplacementSubstring(dateTemplate, formattedDateFromDateTemplate);
        }
        return replacementDataSet;
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