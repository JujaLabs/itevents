package org.itevents.dao.filter;

import org.itevents.model.Filter;
import org.itevents.model.User;

public interface FilterDao {
    Filter getFilterById(int id);
    Filter getFilterByUser(User user);
    void addFilter(Filter filter);
    Filter removeFilter(Filter filter);
}