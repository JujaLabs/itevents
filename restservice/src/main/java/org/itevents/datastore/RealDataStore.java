package org.itevents.datastore;

import org.apache.ibatis.session.SqlSession;
import org.itevents.model.Event;
import org.itevents.service.EventMapper;
import org.itevents.service.MyBatisUtil;

import java.util.List;

public class RealDataStore implements DataStore{
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
        EventMapper mapper = session.getMapper(EventMapper.class);
        Event event = mapper.selectEvent(id);
        session.close();
        return event;
    }

    @Override
    public List<Event> getAllEvents() {
        // Not implemented yet
        return null;
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
