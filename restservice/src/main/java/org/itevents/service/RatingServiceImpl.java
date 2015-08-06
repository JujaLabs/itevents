package org.itevents.service;

import org.itevents.model.Event;
import org.itevents.parameter.FilteredEventsParameter;

import java.util.*;

public class RatingServiceImpl implements RatingService {

    private static final int ORDER_DESCENDING = -1;
    private static final int DAYS_FOR_FUTURE_EVENT = 7;

    @Override
    public Map<Event, Integer> chooseMostPopularEvents(int quantity) {
        EventService eventService = new EventServiceImpl();
        VisitLogService visitLogService = new VisitLogServiceImpl();
        FilteredEventsParameter parameters = new FilteredEventsParameter();
        List<Event> filteredEvents = eventService.getFilteredEvents(parameters);
        Event futureEvent = null;
        Integer countViewers = null;

        Map<Event, Integer> eventMap = new HashMap<>();
        for (Event filteredEvent : filteredEvents) {
            futureEvent = eventService.getFutureEventById(DAYS_FOR_FUTURE_EVENT, filteredEvent.getId());
            if (futureEvent != null){
                countViewers = visitLogService.getCountViewByEventId(futureEvent.getId());
                eventMap.put(futureEvent, countViewers);
            }
        }
        eventMap = sortMapEvents(ORDER_DESCENDING, eventMap);
        eventMap = trimToSizeMap(quantity, eventMap);

        return eventMap;
    }

    /*
    parameter order -   +1 return sorted map by descending
                        -1 return sorted map by ascending
    */
    private Map<Event, Integer> sortMapEvents(int order, Map<Event, Integer> map){
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

    private Map<Event, Integer> trimToSizeMap(int quantity, Map<Event, Integer> map){
        if (map.size() <= quantity){
            return map;
        }
        int count = 0;
        Map<Event, Integer> returnedMap = new HashMap<>();
        for (Map.Entry<Event, Integer> entry : map.entrySet()) {
            returnedMap.put(entry.getKey(), entry.getValue());
            count++;
            if (count >= quantity){
                break;
            }
        }
        return returnedMap;
    }
}
