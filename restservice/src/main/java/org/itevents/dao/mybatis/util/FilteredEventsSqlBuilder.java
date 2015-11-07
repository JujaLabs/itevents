package org.itevents.dao.mybatis.util;

import org.apache.ibatis.jdbc.SQL;
import org.itevents.model.Filter;
import org.itevents.model.Technology;
import org.springframework.util.CollectionUtils;

import java.util.Iterator;
import java.util.List;

public class FilteredEventsSqlBuilder {

    public String getFilteredEvents(final Filter params) {
        return new SQL() {{
            SELECT("*");
            FROM("event e");
            WHERE("e.event_date > NOW()");
            if (params.getCity() != null) {
                WHERE("city_id = #{city.id}");
            }
            if (params.getCity() == null && (params.getLatitude() != null)) {
                WHERE("ST_DWithin((point)::geography, ST_MakePoint(#{longitude},#{latitude})::geography, #{radius})");
            }
            if (params.getFree() != null) {
                if (params.getFree()) {
                    WHERE("(price IS NULL OR price = 0)");
                } else {
                    WHERE("price > 0");
                }
            }
            if (!CollectionUtils.isEmpty(params.getTechnologies())) {
                JOIN(makeJoin(params));
                WHERE("e.id=et.event_id");
            }
        }}.toString() + " ORDER BY event_date LIMIT #{limit} OFFSET #{offset}";
    }

    private String makeJoin(Filter params) {
        StringBuilder sb = new StringBuilder();
        sb.append("event_technology et ON ");
        List<Technology> technologies = params.getTechnologies();
        Iterator<Technology> iterator = technologies.iterator();
        while (iterator.hasNext()) {
            sb.append("et.technology_id=");
            sb.append(iterator.next().getId());
            if (iterator.hasNext()) {
                sb.append(" or ");
            }
        }
        return sb.toString();
    }

}
