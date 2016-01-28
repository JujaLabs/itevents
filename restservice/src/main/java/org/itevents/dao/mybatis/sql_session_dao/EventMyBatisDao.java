package org.itevents.dao.mybatis.sql_session_dao;

import org.itevents.dao.EventDao;
import org.itevents.dao.exception.EntityNotFoundDaoException;
import org.itevents.model.Event;
import org.itevents.model.Filter;
import org.itevents.model.User;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.util.List;

/**
 * Created by vaa25 on 03.11.2015.
 */

public class EventMyBatisDao extends SqlSessionDaoSupport implements EventDao {

    @Override
    public Event getEvent(int id) {
        Event event = getSqlSession().selectOne("org.itevents.dao.mybatis.mapper.EventMapper.getEvent", id);
        if (event == null) {
            throw new EntityNotFoundDaoException("Event with id = " + id + " not found");
        }
        return event;
    }

    @Override
    public List<Event> getAllEvents() {
        return getSqlSession().selectList("org.itevents.dao.mybatis.mapper.EventMapper.getAllEvents");
    }

    @Override
    public void addEvent(Event event) {
        getSqlSession().insert("org.itevents.dao.mybatis.mapper.EventMapper.addEvent", event);
    }

    @Override
    public void addEventTechnology(Event event) {
        getSqlSession().insert("org.itevents.dao.mybatis.mapper.EventMapper.addEventTechnology", event);
    }

    @Override
    public void updateEvent(Event event) {
        getSqlSession().insert("org.itevents.dao.mybatis.mapper.EventMapper.updateEvent", event);
    }

    @Override
    public List<Event> getFilteredEvents(Filter filter) {
        return getSqlSession().selectList("org.itevents.dao.mybatis.mapper.EventMapper.getFilteredEvents", filter);
    }

    @Override
    public List<Event> getFilteredEventsWithRating(Filter filter) {
        return getSqlSession().selectList("org.itevents.dao.mybatis.mapper.EventMapper.getFilteredEventsWithRating", filter);
    }

    @Override
    public void removeEventTechnology(Event event) {
        getSqlSession().delete("org.itevents.dao.mybatis.mapper.EventMapper.removeEventTechnology", event);
    }

    @Override
    public void assign(User user, Event event) {
        getSqlSession().insert("org.itevents.dao.mybatis.mapper.EventMapper.assign", new UserEvent(user, event));
    }

    @Override
    public void unassign(User user, Event event) {
        getSqlSession().delete("org.itevents.dao.mybatis.mapper.EventMapper.unassign", new UserEvent(user, event));
    }

    @Override
    public List<Event> getEventsByUser(User user) {
        return getSqlSession().selectList("org.itevents.dao.mybatis.mapper.EventMapper.getEventsByUser", user);
    }

    private class UserEvent {
        private User user;
        private Event event;

        public UserEvent(User user, Event event) {
            this.user = user;
            this.event = event;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public Event getEvent() {
            return event;
        }

        public void setEvent(Event event) {
            this.event = event;
        }
    }
}
