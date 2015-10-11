package org.itevents.dao.mybatis.util;

import org.itevents.model.Event;
import org.itevents.model.Technology;

import java.util.Iterator;
import java.util.Set;

public class AddEventTechnologySqlBuilder {

    public String addEventTechnology(final Event event) {
        Set<Technology> technologies = event.getTechnologies();
        StringBuilder sql = new StringBuilder();
        if (technologies != null && technologies.size() > 0) {
            sql.append("INSERT INTO event_technology (event_id, technology_id) VALUES ");
            Iterator<Technology> iterator = technologies.iterator();
            while (iterator.hasNext()) {
                sql.append("(").append(event.getId()).append(", ").append(iterator.next().getId()).append(")");
                if (iterator.hasNext()) {
                    sql.append(", ");
                }
            }
        }
        return sql.toString();
    }
}
