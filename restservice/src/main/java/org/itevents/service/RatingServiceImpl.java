package org.itevents.service;

import org.itevents.model.Event;
import org.itevents.parameter.FilteredEventsParameter;

import java.util.*;

public class RatingServiceImpl implements RatingService {

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
            futureEvent = eventService.getFutureEventById(7, filteredEvent.getId());
            if (futureEvent != null){
                countViewers = visitLogService.getCountViewByEventId(futureEvent.getId());
                eventMap.put(futureEvent, countViewers);
            }
        }
        eventMap = sortMapEvents( -1, eventMap);


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
}
