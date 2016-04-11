package org.itevents.service.transactional;

import org.itevents.dao.FilterDao;
import org.itevents.dao.exception.EntityNotFoundDaoException;
import org.itevents.dao.model.Filter;
import org.itevents.dao.model.User;
import org.itevents.service.FilterService;
import org.itevents.service.exception.EntityNotFoundServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Service("filterService")
@Transactional
public class MyBatisFilterService implements FilterService {

    @Inject
    private FilterDao filterDao;

    @Override
    public Filter getFilter(int id) {
        try {
            return filterDao.getFilter(id);
        } catch (EntityNotFoundDaoException e) {
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
