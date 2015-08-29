package org.itevents.service;

import org.itevents.model.Event;
import org.itevents.model.Filter;

import java.util.List;

public interface RatingService {

   List<Event> chooseMostPopularEventsForUser(int quantity, int user_id, Filter parameters);
}
