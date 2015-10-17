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
    public void addFilter(Filter filter) {
        filterDao.addFilter(filter);
    }

    @Override
    public Filter getFilter(int id) {
        return filterDao.getFilter(id);
    }

    @Override
    public List<Filter> getAllFilters() {
        return filterDao.getAllFilters();
    }

    @Override
    public Filter removeFilter(Filter filter) {
        Filter deletingFilter = filterDao.getFilter(filter.getId());
        if (deletingFilter != null) {
            filterDao.removeFilter(deletingFilter);
        }
        return deletingFilter;
    }

    @Override
    public Filter getFilterByUser(User user) {
        return filterDao.getFilterByUser(user);
    }

    @Override
    public void putFilter(User user, Filter filter) {
        Filter oldFilter = filterDao.getFilterByUser(user);
        if (oldFilter != null) {
            filterDao.updateFilter(oldFilter, filter);
        } else {
            filterDao.addFilter(filter);
            filterDao.addUserFilter(user, filter);
        }
    }

    @Override
    public Filter removeFilterByUser(User user) {
        Filter oldFilter = filterDao.getFilterByUser(user);
        if (oldFilter != null) {
            filterDao.removeFilterFromUser(user);
            filterDao.removeFilter(oldFilter);
            return oldFilter;
        } else {
            return null;
        }
    }
}
