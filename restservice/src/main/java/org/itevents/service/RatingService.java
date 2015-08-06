package org.itevents.service;

import org.itevents.model.Event;

import java.util.Map;

public interface RatingService {

   Map<Event, Integer> chooseMostPopularEvents(int quantity);
}
