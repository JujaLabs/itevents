package org.itevents.dao.mybatis.util;

import org.itevents.model.Filter;
import org.itevents.model.Technology;

import java.util.Iterator;
import java.util.List;

public class AddFilterTechnologySqlBuilder {

    public String addFilterTechnology(Filter filter) {
        List<Technology> technologies = filter.getTechnologies();
        StringBuilder sql = new StringBuilder();
        if (technologies != null && technologies.size() > 0) {
            sql.append("INSERT INTO filter_technology (filter_id, technology_id) VALUES ");
            Iterator<Technology> iterator = technologies.iterator();
            while (iterator.hasNext()) {
                sql.append("(").append(filter.getId()).append(", ").append(iterator.next().getId()).append(")");
                if (iterator.hasNext()) {
                    sql.append(", ");
                }
            }
        }
        return sql.toString();
    }
}
