package org.itevents.test_utils.dbunit.dataset_loader;

import org.itevents.util.time.DateTimeUtil;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by roma on 13.10.15.
 */
public class EventDateReplacementDataSetLoader extends AbstractReplacementDataSetLoader {
    @Override
    protected void replace() {
        String dateTimeFormatForDatabase = "yyyy-MM-dd HH:mm:ss";
        //todo use regexp
        Map<String, Integer> replacementMap = new HashMap<>();
        replacementMap.put("{now-10}", -10);
        replacementMap.put("{now-5}", -5);
        replacementMap.put("{now+2}", +2);
        replacementMap.put("{now+3}", +3);
        replacementMap.put("{now+5}", +5);
        replacementMap.put("{now+8}", +8);
        replacementMap.put("{now+10}", +10);

        for (String replaceFrom : replacementMap.keySet()) {
            String replaceTo = DateTimeUtil.getFormattedNowDatePlusDays(replacementMap.get(replaceFrom),
                    dateTimeFormatForDatabase);
            replace(replaceFrom, replaceTo);
        }
    }
}