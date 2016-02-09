package org.itevents.dao.mybatis.sql_session_dao;

import org.itevents.dao.LocationDao;
import org.itevents.dao.model.Location;
import org.springframework.stereotype.Component;

/**
 * Created by vaa25 on 09.12.2015.
 */
@Component("locationDao")
public class LocationMyBatisDao extends AbstractMyBatisDao implements LocationDao {
    @Override
    public Location getEventLocation(int id) {
        return getSqlSession().selectOne("org.itevents.dao.mybatis.mapper.LocationMapper.getEventLocation", id);
    }
}
