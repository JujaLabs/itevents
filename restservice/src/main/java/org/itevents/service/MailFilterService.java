package org.itevents.service;

import org.itevents.model.Event;
import org.itevents.model.Filter;

import java.util.List;

public interface MailFilterService {

    List<Event> getFilteredEventsInDateRangeWithRating(Filter filter);

}
