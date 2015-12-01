package org.itevents.service.transactional;

import org.itevents.dao.EventDao;
import org.itevents.model.Event;
import org.itevents.model.Filter;
import org.itevents.service.MailFilterService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Service("mailFilterService")
@Transactional
public class MyBatisMailFilterService implements MailFilterService {
    public static final Integer FILTER_RANGE_IN_DAYS = 7;
    public static final Integer COUNT_OF_EVENTS_IN_EMAIL = 10;

    @Inject
    private EventDao eventDao;

    public List<Event> getFilteredEventsInDateRangeWithRating(Filter filter){
        filter.setRangeInDays(FILTER_RANGE_IN_DAYS);
        filter.setLimit(COUNT_OF_EVENTS_IN_EMAIL);
        return eventDao.getFilteredEvents(filter);
    }
}
