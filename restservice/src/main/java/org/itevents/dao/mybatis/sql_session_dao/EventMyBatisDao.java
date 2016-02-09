package org.itevents.dao.mybatis.sql_session_dao;

import org.itevents.dao.EventDao;
import org.itevents.dao.exception.EntityNotFoundDaoException;
import org.itevents.dao.model.Event;
import org.itevents.dao.model.Filter;
import org.itevents.dao.model.User;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by vaa25 on 03.11.2015.
 */
@Component("eventDao")
public class EventMyBatisDao extends AbstractMyBatisDao implements EventDao {

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
    public void assignUserToEvent(User user, Event event) {
        getSqlSession().insert("org.itevents.dao.mybatis.mapper.EventMapper.assignUserToEvent", new AssignData(user, event));
    }

    @Override
    public void unassignUserFromEvent(User user, Event event, Date unassignDate, String unassignReason) {
        getSqlSession().update("org.itevents.dao.mybatis.mapper.EventMapper.unassignUserFromEvent",
                new UnassignData(user, event, unassignDate, unassignReason));
    }

    @Override
    public List<Event> getEventsByUser(User user) {
        return getSqlSession().selectList("org.itevents.dao.mybatis.mapper.EventMapper.getEventsByUser", user);
    }

    private class AssignData {
        private User user;
        private Event event;

        public AssignData(User user, Event event) {
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

    private class UnassignData{
        private User user;
        private Event event;
        private Date unassignDate;
        private String unassignReason;

        public UnassignData(User user, Event event, Date unassignDate, String unassignReason) {
            this.user = user;
            this.event = event;
            this.unassignDate = unassignDate;
            this.unassignReason = unassignReason;
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

        public Date getUnassignDate() {
            return unassignDate;
        }

        public void setUnassignDate(Date unassignDate) {
            this.unassignDate = unassignDate;
        }

        public String getUnassignReason() {
            return unassignReason;
        }

        public void setUnassignReason(String unassignReason) {
            this.unassignReason = unassignReason;
        }
    }
}
