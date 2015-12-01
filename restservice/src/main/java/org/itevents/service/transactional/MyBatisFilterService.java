package org.itevents.service.transactional;

import org.itevents.dao.FilterDao;
import org.itevents.model.Filter;
import org.itevents.model.User;
import org.itevents.service.FilterService;
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
        return filterDao.getFilter(id);
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
