package org.itevents.service;

import org.itevents.model.Filter;
import org.itevents.model.User;

import java.util.List;

/**
 * Created by vaa25 on 16.10.2015.
 */
public interface FilterService {

    Filter getFilter(int id);

    void addFilter(Filter filter);

    List<Filter> getAllFilters();

    Filter removeFilter(Filter filter);

    Filter getFilterByUser(User user);

    void addFilter(User user, Filter filter);
}
