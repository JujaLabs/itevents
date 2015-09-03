package org.itevents.service;

import org.itevents.model.Event;
import org.itevents.parameter.FilteredEventsParameter;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.*;

@Service("ratingService")
public class RatingServiceImpl implements RatingService {

    private static final int ORDER_DESCENDING = -1;
//    private static final int DAYS_FOR_FUTURE_EVENT = 7;

    @Inject
    VisitLogService visitLogService;

    @Inject
    EventService eventService;

    @Override
    public List<Event> chooseMostPopularEventsForUser(int quantity, int user_id, FilteredEventsParameter parameters) {
        List<Event> filteredEvents = eventService.getFilteredEvents(parameters);
        Integer countViewers;

        Map<Event, Integer> eventMap = new HashMap<>();
        for (Event filteredEvent : filteredEvents) {
            countViewers = visitLogService.getCountViewByEventId(filteredEvent.getId());
            eventMap.put(filteredEvent, countViewers);
        }
        eventMap = sortEventsMap(ORDER_DESCENDING, eventMap);

        return getListEventByRating(quantity, eventMap);
    }

    /*
    parameter order -   +1 return sorted map by descending
                        -1 return sorted map by ascending
    */
    public Map<Event, Integer> sortEventsMap(int order, Map<Event, Integer> map){
        final int orderInner = order;
        List<Map.Entry<Event, Integer>> list = new LinkedList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Event, Integer>>() {
            @Override
            public int compare(Map.Entry<Event, Integer> o1, Map.Entry<Event, Integer> o2) {
                return orderInner * (o1.getValue()).compareTo(o2.getValue());
            }
        });

        Map<Event, Integer> resultMap = new LinkedHashMap<>();
        for (Map.Entry<Event, Integer> entry : list)
        {
            resultMap.put(entry.getKey(), entry.getValue());
        }
        return resultMap;
    }

    public List<Event> getListEventByRating(int quantityEvents, Map<Event, Integer> map){
        if (map.size() <= quantityEvents){
            return new ArrayList<>(map.keySet());
        }
        List<Event> returnedList = new ArrayList<>();
        for (Event event : map.keySet()) {
            if (returnedList.size() >= quantityEvents){
                break;
            }
            returnedList.add(event);
        }
        return returnedList;
    }
}
