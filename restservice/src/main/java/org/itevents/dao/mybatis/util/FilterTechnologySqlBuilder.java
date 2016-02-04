package org.itevents.dao.mybatis.util;

import org.itevents.model.Filter;
import org.itevents.model.Technology;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class FilterTechnologySqlBuilder {

    public String addFilterTechnology(Filter filter) {
        List<Technology> technologies = filter.getTechnologies();
        if (CollectionUtils.isEmpty(technologies)) {
            return "";
        } else {
            return makeSql(filter, technologies);
        }
    }

    private String makeSql(Filter filter, List<Technology> technologies) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO filter_technology (filter_id, technology_id) VALUES ");
        for (Technology technology : technologies) {
            sql.append("(")
                    .append(filter.getId()).append(", ")
                    .append(technology.getId()).append("), ");
        }
        int lastCommaPositionFromEnd = 2;
        sql.delete(sql.length() - lastCommaPositionFromEnd, sql.length());
        return sql.toString();
    }
}
