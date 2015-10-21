package org.itevents.test_utils.dbunit.dataset_loader;

import org.itevents.util.time.TimeUtil;

/**
 * Created by roma on 13.10.15.
 */
public class EventDateReplacementDataSetLoader extends AbstractReplacementDataSetLoader {
    @Override
    protected void replace() {
        replace("{now-10}", TimeUtil.getFormattedNowDatePlusDays(-10));
        replace("{now-5}", TimeUtil.getFormattedNowDatePlusDays(-5));
        replace("{now+2}", TimeUtil.getFormattedNowDatePlusDays(2));
        replace("{now+3}", TimeUtil.getFormattedNowDatePlusDays(3));
        replace("{now+5}", TimeUtil.getFormattedNowDatePlusDays(5));
        replace("{now+8}", TimeUtil.getFormattedNowDatePlusDays(8));
        replace("{now+10}", TimeUtil.getFormattedNowDatePlusDays(10));
    }
}