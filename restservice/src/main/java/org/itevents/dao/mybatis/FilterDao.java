package org.itevents.dao.mybatis;

import org.itevents.model.Filter;

import java.util.List;

/**
 * Created by vaa25 on 17.10.2015.
 */
public interface FilterDao {

    Filter getFilter(int id);

    List<Filter> getAllFilters();

    void addFilter(Filter filter);

    void removeFilter(Filter filter);
}
