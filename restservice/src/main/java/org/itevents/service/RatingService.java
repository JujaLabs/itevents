package org.itevents.service;

import org.itevents.model.Event;
import org.itevents.parameter.FilteredEventsParameter;

import java.util.List;

public interface RatingService {

   List<Event> chooseMostPopularEventsForUser(int quantity, int user_id, FilteredEventsParameter parameters);
}
