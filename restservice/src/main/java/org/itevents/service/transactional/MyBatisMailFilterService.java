package org.itevents.service.transactional;

import org.itevents.dao.EventDao;
import org.itevents.model.Event;
import org.itevents.model.Filter;
import org.itevents.service.MailFilterService;
import org.itevents.util.TimeUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

@Service("mailFilterService")
@Transactional
public class MyBatisMailFilterService implements MailFilterService {
    private static final int FILTER_RANGE_IN_DAYS = 7;
    private static final int COUNT_OF_EVENTS_IN_EMAIL = 10;

    @Inject
    private EventDao eventDao;

    public List<Event> getFilteredEventsInDateRangeWithRating(Filter filter){
        Date maximumDate = TimeUtil.addDaysToDate(TimeUtil.getNowDate(), FILTER_RANGE_IN_DAYS);
        filter.setMaximumDate(maximumDate);
        filter.setLimit(COUNT_OF_EVENTS_IN_EMAIL);
        filter.setOffset(0);
        return eventDao.getFilteredEventsWithRating(filter);
    }
}
