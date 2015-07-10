package org.itevents.datastore;

public interface StatisticStore {

    void addEvent(Long event_id);

    void incCount(Long event_id);

    int getCount(Long event_id);


}
