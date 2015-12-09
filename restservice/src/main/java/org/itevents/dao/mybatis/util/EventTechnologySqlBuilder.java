package org.itevents.dao.mybatis.util;

import org.itevents.model.Event;
import org.itevents.model.Technology;
import org.springframework.util.CollectionUtils;

import java.util.Iterator;
import java.util.List;

public class EventTechnologySqlBuilder {

    public String addEventTechnology(final Event event) {
        List<Technology> technologies = event.getTechnologies();
        StringBuilder sql = new StringBuilder();
        if (!CollectionUtils.isEmpty(technologies)) {
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
