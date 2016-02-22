package org.itevents.dao.mybatis.sql_session_dao;

import org.itevents.dao.CityDao;
import org.itevents.dao.exception.EntityNotFoundDaoException;
import org.itevents.dao.model.City;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by vaa25 on 09.12.2015.
 */
@Component("cityDao")
public class CityMyBatisDao extends AbstractMyBatisDao implements CityDao {
    @Override
    public City getCity(int id) {
        City city = getSqlSession().selectOne("org.itevents.dao.mybatis.mapper.CityMapper.getCity", id);
        if (city == null) {
            throw new EntityNotFoundDaoException("getCity with id = " + id + " not found");
        }
        return city;
    }

    @Override
    public List<City> getAllCities() {
        return getSqlSession().selectList("org.itevents.dao.mybatis.mapper.CityMapper.getAllCities");
    }

    @Override
    public void addCity(City city) {
        getSqlSession().insert("org.itevents.dao.mybatis.mapper.CityMapper.addCity", city);
    }
}
