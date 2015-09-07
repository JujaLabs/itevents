package org.itevents.service;

import org.itevents.model.Event;
import org.itevents.model.Filter;
import org.itevents.model.User;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.*;

@Service("ratingService")
public class RatingServiceImpl implements RatingService {

    @Inject
    EventService eventService;

    @Inject
    FilterService filterService;

    @Override
    public List<Event> chooseMostPopularEventsForUser(User user) {
        Filter filter = filterService.getFilterByUser(user);

        return eventService.getFilteredEvents(filter);
    }
}
