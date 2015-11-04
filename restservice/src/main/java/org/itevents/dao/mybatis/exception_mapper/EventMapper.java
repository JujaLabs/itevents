package org.itevents.dao.mybatis.exception_mapper;

import org.itevents.dao.EventDao;
import org.itevents.dao.exception.EventNotFoundDaoException;
import org.itevents.model.Event;
import org.itevents.model.Filter;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.util.List;

/**
 * Created by vaa25 on 03.11.2015.
 */

public class EventMapper extends SqlSessionDaoSupport implements EventDao {

    @Override
    public Event getEvent(int id) {
        Event event = getSqlSession().selectOne("org.itevents.dao.mybatis.mapper.EventMapperSql.getEvent", id);
        throwExceptionIfNull(event);
        return event;
    }

    private void throwExceptionIfNull(Object object) {
        if (object == null) {
            throw new EventNotFoundDaoException();
        }
    }

    @Override
    public List<Event> getAllEvents() {
        List<Event> events = getSqlSession().selectList("org.itevents.dao.mybatis.mapper.EventMapperSql.getAllEvents");
        throwExceptionIfNull(events);
        return events;
    }

    @Override
    public void addEvent(Event event) {
        getSqlSession().insert("org.itevents.dao.mybatis.mapper.EventMapperSql.addEvent", event);
    }

    @Override
    public void addEventTechnology(Event event) {
        getSqlSession().insert("org.itevents.dao.mybatis.mapper.EventMapperSql.addEventTechnology", event);
    }

    @Override
    public void updateEvent(Event event) {
        getSqlSession().insert("org.itevents.dao.mybatis.mapper.EventMapperSql.updateEvent", event);
    }

    @Override
    public List<Event> getFilteredEvents(Filter filter) {
        List<Event> events = getSqlSession().selectList("org.itevents.dao.mybatis.mapper.EventMapperSql.getFilteredEvents", filter);
        throwExceptionIfNull(events);
        return events;
    }
}
