package org.itevents.dao;

import org.itevents.model.Filter;
import org.itevents.model.User;

import java.util.List;

/**
 * Created by vaa25 on 17.10.2015.
 */
public interface FilterDao {

    Filter getFilter(int id);

    Filter getFilterByUser(User user);

    List<Filter> getAllFilters();

    void addFilter(Filter filter);

    void removeFilter(Filter filter);

    void removeFilterFromUser(User user);

    void updateFilter(Filter oldFilter, Filter newFilter);

    void addUserFilter(User user, Filter filter);
}