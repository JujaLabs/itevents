package org.itevents.mybatis.mapper.util;

import org.apache.ibatis.jdbc.SQL;
import org.itevents.parameter.FilteredEventsParameter;

import java.util.Date;

public class SQLBuilder {

    public String selectFilteredEvent(final FilteredEventsParameter params) {
        return new SQL() {{
            SELECT("*");
            FROM("events e");
            if (params.getCity() != null) {
                WHERE("city_id = #{city.id}");
            }
            if (params.getCity() == null && (params.getLatitude() != null)) {
                WHERE("ST_DWithin((point)::geography, ST_MakePoint(#{longitude},#{latitude})::geography, #{radius})");
            }
            if (params.getFree() != null) {
                WHERE("free = #{free}");
            }
            if (params.getTechnologies() != null) {
                JOIN(makeJoin(params));
                WHERE("e.id=et.event_id");
            }
        }}.toString();
    }

    private String makeJoin(FilteredEventsParameter params){
        StringBuilder sb = new StringBuilder();
        sb.append("event_technology et ON ");
        for (int i = 0; i < params.getTechnologies().size(); i++) {
            sb.append("et.technology_id=");
            sb.append(params.getTechnologies().get(i).getId());
            if (i < (params.getTechnologies().size() - 1)){
                sb.append(" or ");
            }
        }
        return sb.toString();
    }

    public String selectFutureFilteredEvents(final FilteredEventsParameter params, final Date dateOfStart){
        StringBuilder sb = new StringBuilder();
        sb.append(" vl.event_id IN (");
        sb.append(selectFilteredEvent(params));
        sb.replace(sb.indexOf("*"), sb.indexOf("*") + 1, "e.id");
        sb.append(" AND event_date BETWEEN '");
        sb.append(dateOfStart);
        sb.append("'::date AND ('");
        sb.append(dateOfStart);
        sb.append("'::date + interval '7 day')");
        sb.append(")");
        final String modificatedQuery = sb.toString();
        String sql = new SQL(){{
            SELECT("vl.event_id");
            FROM("visit_log vl");
            WHERE(modificatedQuery);
            GROUP_BY("vl.event_id");
            ORDER_BY("COUNT(vl.user_id) DESC");
        }}.toString();
        sb = new StringBuilder();
        sb.append(" id IN(");
        sb.append(sql);
        sb.append(")");
        final String preparedWhere = sb.toString();
        return new SQL(){{
            SELECT("*");
            FROM("events");
            WHERE(preparedWhere);
        }}.toString();
    }
}
