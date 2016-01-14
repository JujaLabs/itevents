package org.itevents.dao.mybatis.exception_mapper;

import org.itevents.dao.CityDao;
import org.itevents.dao.exception.EntityNotFoundDaoException;
import org.itevents.model.City;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.util.List;

/**
 * Created by vaa25 on 09.12.2015.
 */
public class CityMapper extends SqlSessionDaoSupport implements CityDao {
    @Override
    public City getCity(int id) {
        City city = getSqlSession().selectOne("org.itevents.dao.mybatis.mapper.CitySqlMapper.getCity", id);
        if (city == null) {
            throw new EntityNotFoundDaoException("getCity with id = " + id + " not found");
        }
        return city;
    }

    @Override
    public List<City> getAllCities() {
        return getSqlSession().selectList("org.itevents.dao.mybatis.mapper.CitySqlMapper.getAllCities");
    }

    @Override
    public void addCity(City city) {
        getSqlSession().insert("org.itevents.dao.mybatis.mapper.CitySqlMapper.addCity", city);
    }
}
