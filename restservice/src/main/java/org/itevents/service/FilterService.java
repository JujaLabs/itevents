package org.itevents.service;

import org.itevents.model.Filter;
import org.itevents.model.User;

public interface FilterService {
    void addFilter(Filter filter);
    Filter getFilterById(int id);
    Filter getFilterByUser(User user);
    Filter removeFilter(Filter filter);
}
