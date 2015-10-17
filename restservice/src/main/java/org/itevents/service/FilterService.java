package org.itevents.service;

import org.itevents.model.Filter;
import org.itevents.model.User;

/**
 * Created by vaa25 on 16.10.2015.
 */
public interface FilterService {

    void putFilter(User user, Filter filter);

    Filter removeFilter(User user);
}
