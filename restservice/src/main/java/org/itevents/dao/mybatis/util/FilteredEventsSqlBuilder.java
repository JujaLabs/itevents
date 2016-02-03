package org.itevents.dao.mybatis.util;

import org.apache.ibatis.jdbc.SQL;
import org.itevents.model.Filter;
import org.itevents.model.Technology;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class FilteredEventsSqlBuilder {

    public String getFilteredEvents(final Filter params) {
        return getFilteredEventsSQL(params).toString() + " LIMIT #{limit} OFFSET #{offset}";
    }

    private SQL getFilteredEventsSQL(final Filter params) {
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
                WHERE(params.getFree() ? "(price IS NULL OR price = 0)" : "price > 0");
            }
            if (!CollectionUtils.isEmpty(params.getTechnologies())) {
                JOIN(makeJoinSql(params.getTechnologies()));
                WHERE("e.id=et.event_id");
            }
            ORDER_BY("event_date");
        }};
    }

    public String getFilteredEventsInDateRangeWithRating(final Filter params) {
        return getFilteredEventsInDateRangeWithRatingSQL(params).toString() + " LIMIT #{limit}";
    }

    private SQL getFilteredEventsInDateRangeWithRatingSQL(final Filter params) {
        SQL sql = getFilteredEventsSQL(params);
        if (params.getRangeInDays() != null) {
            sql.WHERE("e.event_date < NOW() + (#{rangeInDays} || ' DAYS')::INTERVAL");
        }
        sql.LEFT_OUTER_JOIN(" (SELECT event_id, COUNT(*) as visits FROM visit_log " +
                "GROUP BY event_id) AS visits ON e.id = visits.event_id");
        return sql;
    }

    private String makeJoinSql(List<Technology> technologies) {
        StringBuilder sb = new StringBuilder("event_technology et ON ");
        for (Technology technology : technologies) {
            sb.append("et.technology_id=").append(technology.getId())
                    .append(" or ");
        }
        int lastOrPositionFromEnd = 4;
        sb.delete(sb.length() - lastOrPositionFromEnd, sb.length());
        return sb.toString();
    }
}