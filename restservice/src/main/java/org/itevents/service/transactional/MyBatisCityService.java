package org.itevents.service.transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.itevents.dao.CityDao;
import org.itevents.dao.exception.EntityNotFoundDaoException;
import org.itevents.dao.model.City;
import org.itevents.service.CityService;
import org.itevents.service.exception.EntityNotFoundServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Service("cityService")
@Transactional
public class MyBatisCityService implements CityService {

    private static final Logger LOGGER = LogManager.getLogger();

    @Inject
    private CityDao cityDao;

    @Override
    public void addCity(City city) {
        cityDao.addCity(city);
    }

    @Override
    public City getCity(int id) {
        try {
            return cityDao.getCity(id);
        } catch (EntityNotFoundDaoException e) {
            LOGGER.error(e.getMessage());
            throw new EntityNotFoundServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<City> getAllCities() {
        return cityDao.getAllCities();
    }
}
