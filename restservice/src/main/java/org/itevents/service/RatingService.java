package org.itevents.service;

import org.itevents.model.Event;
import org.itevents.model.Filter;
import org.itevents.model.User;

import java.util.List;

public interface RatingService {

   List<Event> chooseMostPopularEventsForUser(int quantity, User user);
}
