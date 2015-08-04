package org.itevents.service;

import org.itevents.model.Event;

import java.util.List;

public interface RatingService {

   List<Event> chooseMostPopularEvents(int quantity);
}
