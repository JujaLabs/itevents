package org.itevents.service;

import org.itevents.datastore.DataStore;

public class InitDataStoreService {

    private DataStore dataStore;

    public void init() {
//        Event first = new Event(
//                1, "IT-Forum for Juniors", new Location(50.4505F, 30.523F), new Date(1441123200000L), "http://www.dou.ua");
//        Event second = new Event(
//                2, "Java-Party", new Location(49.833333F, 24F), new Date(1442152800000L), "http://www.google.com");
//        Event third = new Event(
//                3, "Developers Happy NY", new Location(48.466601F, 35.018155F), new Date(1451091600000L), "http://www.juja.com.ua");
//        Event forth = new Event(
//                4, "Java Mentors Day", new Location(50F, 36.229167F), new Date(1444086000000L), "http://www.facebook.com");
//
//        dataStore.addEvent(first);
//        dataStore.addEvent(second);
//        dataStore.addEvent(third);
//        dataStore.addEvent(forth);
    }

    public void setDataStore(DataStore dataStore) {
        this.dataStore = dataStore;
    }
}
