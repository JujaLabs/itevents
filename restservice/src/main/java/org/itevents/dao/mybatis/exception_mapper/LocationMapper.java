package org.itevents.dao.mybatis.exception_mapper;

import org.itevents.dao.LocationDao;
import org.itevents.model.Location;
import org.mybatis.spring.support.SqlSessionDaoSupport;

/**
 * Created by vaa25 on 09.12.2015.
 */
public class LocationMapper extends SqlSessionDaoSupport implements LocationDao {
    @Override
    public Location getEventLocation(int id) {
        return getSqlSession().selectOne("org.itevents.dao.mybatis.mapper.LocationSqlMapper.getEventLocation", id);
    }
}
