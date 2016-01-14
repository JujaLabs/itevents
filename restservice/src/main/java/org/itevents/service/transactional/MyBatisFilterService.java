package org.itevents.service.transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.itevents.dao.FilterDao;
import org.itevents.dao.exception.EntityNotFoundDaoException;
import org.itevents.model.Filter;
import org.itevents.model.User;
import org.itevents.service.FilterService;
import org.itevents.service.exception.EntityNotFoundServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Service("filterService")
@Transactional
public class MyBatisFilterService implements FilterService {

    private static final Logger LOGGER = LogManager.getLogger();

    @Inject
    private FilterDao filterDao;

    @Override
    public Filter getFilter(int id) {
        try {
            return filterDao.getFilter(id);
        } catch (EntityNotFoundDaoException e) {
            LOGGER.error(e.getMessage());
            throw new EntityNotFoundServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Filter> getAllFilters() {
        return filterDao.getAllFilters();
    }

    @Override
    public Filter getLastFilterByUser(User user) {
        return filterDao.getLastFilterByUser(user);
    }

    @Override
    public void addFilter(User user, Filter filter) {
        filterDao.addFilter(filter);
        filterDao.addFilterTechnology(filter);
        filterDao.addUserFilter(user, filter);
    }
}
