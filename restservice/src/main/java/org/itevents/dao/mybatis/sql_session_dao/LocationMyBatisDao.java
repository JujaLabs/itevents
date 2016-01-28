package org.itevents.dao.mybatis.sql_session_dao;

import org.itevents.dao.LocationDao;
import org.itevents.model.Location;
import org.mybatis.spring.support.SqlSessionDaoSupport;

/**
 * Created by vaa25 on 09.12.2015.
 */
public class LocationMyBatisDao extends SqlSessionDaoSupport implements LocationDao {
    @Override
    public Location getEventLocation(int id) {
        return getSqlSession().selectOne("org.itevents.dao.mybatis.mapper.LocationMapper.getEventLocation", id);
    }
}
