package org.itevents.dao.mybatis.sql_session_dao;

import org.itevents.dao.TechnologyDao;
import org.itevents.dao.exception.EntityNotFoundDaoException;
import org.itevents.model.Technology;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.util.List;

/**
 * Created by vaa25 on 09.12.2015.
 */
public class TechnologyMyBatisDao extends SqlSessionDaoSupport implements TechnologyDao {
    @Override
    public Technology getTechnology(int id) {
        Technology technology = getSqlSession().selectOne("org.itevents.dao.mybatis.mapper.TechnologyMapper.getTechnology", id);
        if (technology == null) {
            throw new EntityNotFoundDaoException("Technology with id = " + id + " not found");
        }
        return technology;
    }

    @Override
    public List<Technology> getAllTechnologies() {
        return getSqlSession().selectList("org.itevents.dao.mybatis.mapper.TechnologyMapper.getAllTechnologies");
    }

    @Override
    public List<Technology> getTechnologiesByNames(String[] names) {
        return getSqlSession().selectList("org.itevents.dao.mybatis.mapper.TechnologyMapper.getTechnologiesByNames", names);
    }

    @Override
    public void addTechnology(Technology technology) {
        getSqlSession().insert("org.itevents.dao.mybatis.mapper.TechnologyMapper.addTechnology", technology);
    }

    public List<Technology> getTechnologiesByEventId(int eventId) {
        return getSqlSession().selectList("org.itevents.dao.mybatis.mapper.TechnologyMapper.getTechnologiesByEventId", eventId);
    }
}
