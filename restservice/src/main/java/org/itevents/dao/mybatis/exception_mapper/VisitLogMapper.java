package org.itevents.dao.mybatis.exception_mapper;

import org.itevents.dao.VisitLogDao;
import org.itevents.dao.exception.EntityNotFoundDaoException;
import org.itevents.model.Event;
import org.itevents.model.VisitLog;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.util.List;

/**
 * Created by vaa25 on 09.12.2015.
 */
public class VisitLogMapper extends SqlSessionDaoSupport implements VisitLogDao {
    @Override
    public VisitLog getVisitLog(int id) {
        VisitLog visitLog = getSqlSession().selectOne("org.itevents.dao.mybatis.mapper.VisitLogSqlMapper.getVisitLog", id);
        if (visitLog == null) {
            throw new EntityNotFoundDaoException("VisitLog with id = " + id + " not found");
        }
        return visitLog;
    }

    @Override
    public List<VisitLog> getAllVisitLogs() {
        return getSqlSession().selectList("org.itevents.dao.mybatis.mapper.VisitLogSqlMapper.getAllVisitLogs");
    }

    @Override
    public List<VisitLog> getVisitLogsByEvent(Event event) {
        return getSqlSession().selectList("org.itevents.dao.mybatis.mapper.VisitLogSqlMapper.getVisitLogsByEvent", event);
    }

    @Override
    public void addVisitLog(VisitLog visitLog) {
        getSqlSession().insert("org.itevents.dao.mybatis.mapper.VisitLogSqlMapper.addVisitLog", visitLog);
    }
}
