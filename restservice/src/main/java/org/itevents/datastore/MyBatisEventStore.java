package org.itevents.datastore;

import org.apache.ibatis.session.SqlSession;
import org.itevents.model.Event;
import org.itevents.model.Location;
import org.itevents.service.EventMapper;
import org.itevents.service.MyBatisUtil;

import java.util.List;

public class MyBatisEventStore implements DataStore{
    @Override
    public void addEvent(Event event) {
        SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
        EventMapper mapper = session.getMapper(EventMapper.class);
        mapper.insertEvent(event);
        session.commit();
        session.close();
    }

    @Override
    public Event getEvent(Long id) {
        SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
        EventMapper eventMapper = session.getMapper(EventMapper.class);
        Event event = eventMapper.selectEvent(id);
        session.close();
        return event;
    }

    @Override
    public List<Event> getAllEvents() {
        SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
        EventMapper eventMapper = session.getMapper(EventMapper.class);
        List<Event> events = eventMapper.selectAllEvents();
        session.close();
        return events;
    }

    @Override
    public List<Event> getAllEventsInRadius(Location location, Long radius) {
        SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
        EventMapper eventMapper = session.getMapper(EventMapper.class);
        List<Event> events = eventMapper.selectAllEventsInRadius(location, radius);
        System.out.println(events);//fixme
        session.close();
        return events;
    }

    @Override
    public Event removeEvent(Long id) {
        SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
        EventMapper mapper = session.getMapper(EventMapper.class);
        Event event = mapper.selectEvent(id);
        mapper.deleteEvent(id);
        session.commit();
        session.close();
        return event;
    }
}
