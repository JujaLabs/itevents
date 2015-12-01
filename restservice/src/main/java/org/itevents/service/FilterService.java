package org.itevents.service;

import org.itevents.model.Filter;
import org.itevents.model.User;

import java.util.List;

/**
 * Created by vaa25 on 16.10.2015.
 */
public interface FilterService {

    Filter getFilter(int id);

    List<Filter> getAllFilters();

    Filter getLastFilterByUser(User user);

    void addFilter(User user, Filter filter);
}
