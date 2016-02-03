package org.itevents.dao.mybatis.util;

import org.itevents.model.Event;
import org.itevents.model.Technology;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class EventTechnologySqlBuilder {

    public String addEventTechnology(final Event event) {
        List<Technology> technologies = event.getTechnologies();
        if (CollectionUtils.isEmpty(technologies)) {
            return "";
        } else {
            return makeSql(event, technologies);
        }
    }

    private String makeSql(Event event, List<Technology> technologies) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO event_technology (event_id, technology_id) VALUES ");
        for (Technology technology : technologies) {
            sql.append("(")
                    .append(event.getId()).append(", ")
                    .append(technology.getId()).append("), ");
        }
        int lastCommaPositionFromEnd = 2;
        sql.delete(sql.length() - lastCommaPositionFromEnd, sql.length());
        return sql.toString();
    }
}
