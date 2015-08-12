package org.itevents.dao;

import java.util.List;

/**
 * Created by Well on 29.07.2015.
 */
public interface Event {

    Long create(Event event);
    Event read(Long id);
    boolean update(Event event);
    boolean delete(Event event);
    List findAll();

}
