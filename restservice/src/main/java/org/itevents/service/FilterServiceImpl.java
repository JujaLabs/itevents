package org.itevents.service;

import org.itevents.dao.filter.Filter2TechnologyDao;
import org.itevents.dao.filter.FilterDao;
import org.itevents.model.Filter;
import org.itevents.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service("filterService")
@Transactional
public class FilterServiceImpl implements FilterService {

    @Inject
    private FilterDao filterDao;

//    @Inject
//    private Filter2TechnologyDao filter2TechnologyDao;

    @Override
    public void addFilter(Filter filter) {
        //filterDao.addFilter(filter);
        //filter2TechnologyDao.addTechnologies(filter);
    }

    @Override
    public Filter getFilterById(int id) {
        return filterDao.getFilterById(id);
    }

    @Override
    public Filter getFilterByUser(User user) {
        //return filterDao.getFilterByUser(user); //todo process null
        return null;
    }

    @Override
    public Filter removeFilter(Filter filter) {
//        Filter deletingFilter = filterDao.getFilterById(filter.getId());
//        if (deletingFilter != null) {
//            filterDao.removeFilter(filter);
//        }
//        return deletingFilter;
        return null;
    }
}
