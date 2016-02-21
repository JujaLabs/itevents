package org.itevents.dao.mybatis.builder;

import org.apache.ibatis.jdbc.SQL;
import org.itevents.dao.model.Event;
import org.itevents.dao.model.Filter;
import org.itevents.dao.model.Technology;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class EventSqlBuilder {

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
            if (params.getRangeInDays() != null) {
                WHERE("e.event_date < NOW() + (#{rangeInDays} || ' DAYS')::INTERVAL");
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