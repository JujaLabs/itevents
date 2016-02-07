package org.itevents.dao.mybatis.sql_session_dao;

import org.itevents.dao.VisitLogDao;
import org.itevents.dao.exception.EntityNotFoundDaoException;
import org.itevents.model.Event;
import org.itevents.model.VisitLog;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by vaa25 on 09.12.2015.
 */
@Component("visitLogDao")
public class VisitLogMyBatisDao extends AbstractMyBatisDao implements VisitLogDao {
    @Override
    public VisitLog getVisitLog(int id) {
        VisitLog visitLog = getSqlSession().selectOne("org.itevents.dao.mybatis.mapper.VisitLogMapper.getVisitLog", id);
        if (visitLog == null) {
            throw new EntityNotFoundDaoException("VisitLog with id = " + id + " not found");
        }
        return visitLog;
    }

    @Override
    public List<VisitLog> getAllVisitLogs() {
        return getSqlSession().selectList("org.itevents.dao.mybatis.mapper.VisitLogMapper.getAllVisitLogs");
    }

    @Override
    public List<VisitLog> getVisitLogsByEvent(Event event) {
        return getSqlSession().selectList("org.itevents.dao.mybatis.mapper.VisitLogMapper.getVisitLogsByEvent", event);
    }

    @Override
    public void addVisitLog(VisitLog visitLog) {
        getSqlSession().insert("org.itevents.dao.mybatis.mapper.VisitLogMapper.addVisitLog", visitLog);
    }
}
