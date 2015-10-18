package org.itevents.dao.mybatis.util;

import org.apache.ibatis.jdbc.SQL;
import org.itevents.model.Technology;
import org.itevents.model.Filter;

import java.util.Iterator;
import java.util.List;

public class GetFilteredEventsSqlBuilder {

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
                if (params.getFree() == true) {
                    WHERE("(price IS NULL OR price = 0)");
                } else {
                    WHERE("price > 0");
                }
            }
            if (params.getTechnologies() != null) {
                JOIN(makeJoin(params));
                WHERE("e.id=et.event_id");
            }
        }}.toString() + " LIMIT #{limit} OFFSET #{offset}";
    }

    public String getFilteredEventsInDateRangeWithRating(final Filter params) {
        return new SQL() {{
            SELECT("*");
            FROM("event e");
            WHERE("e.event_date > NOW()");
            if (params.getMaximumDate() != null) {
                WHERE("e.event_date < #{maximumDate}");
            }
            if (params.getCity() != null) {
                WHERE("city_id = #{city.id}");
            }
            if (params.getCity() == null && (params.getLatitude() != null)) {
                WHERE("ST_DWithin((point)::geography, ST_MakePoint(#{longitude},#{latitude})::geography, #{radius})");
            }
            if (params.getFree() != null) {
                if (params.getFree() == true) {
                    WHERE("(price IS NULL OR price = 0)");
                } else {
                    WHERE("price > 0");
                }
            }
            if (params.getTechnologies() != null) {
                JOIN(makeJoin(params));
                WHERE("e.id=et.event_id");
            }
            LEFT_OUTER_JOIN(" (SELECT event_id, COUNT(*) as visits FROM visit_log " +
                    "GROUP BY event_id) AS visits ON e.id = visits.event_id ");
            ORDER_BY("visits DESC NULLS LAST");
        }}.toString() + " LIMIT #{limit} OFFSET #{offset}";
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