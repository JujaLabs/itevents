package org.itevents.mybatis.mapper.util;

import org.apache.ibatis.jdbc.SQL;
import org.itevents.parameter.FilteredEventsParameter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class SQLBuilder {

    public String selectFilteredEvent(Map<String, Object> parameters) {
        final FilteredEventsParameter params = (FilteredEventsParameter) parameters.get("params");
        return new SQL() {{
            SELECT("*");
            FROM("events e");
            if (params.getCity() != null) {
                WHERE("city_id = #{params.city.id}");
            }
            if (params.getCity() == null && (params.getLatitude() != null)) {
                WHERE("ST_DWithin((point)::geography, ST_MakePoint(#{params.longitude},#{params.latitude})::geography, #{params.radius})");
            }
            if (params.getFree() != null) {
                WHERE("free = #{params.free}");
            }
            if (params.getTechnologies() != null) {
                JOIN(makeJoin(params));
                WHERE("e.id=et.event_id");
            }
        }}.toString();
    }

    private String makeJoin(FilteredEventsParameter params) {
        StringBuilder sb = new StringBuilder();
        sb.append("event_technology et ON ");
        for (int i = 0; i < params.getTechnologies().size(); i++) {
            sb.append("et.technology_id=");
            sb.append(params.getTechnologies().get(i).getId());
            if (i < (params.getTechnologies().size() - 1)) {
                sb.append(" or ");
            }
        }
        return sb.toString();
    }

    public String selectFutureFilteredEvents(Map<String, Object> parameters) throws ParseException {
        final FilteredEventsParameter params = (FilteredEventsParameter) parameters.get("params");
        final Date date = (Date) parameters.get("date");
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        sb.append(" vl.event_id IN (");
        sb.append(selectFilteredEvent(parameters));
        sb.replace(sb.indexOf("*"), sb.indexOf("*") + 1, "e.id");
        if (sb.indexOf("events e") == (sb.length() - 8)){
            sb.append(" WHERE ");
        } else{
            sb.append(" AND ");
        }
        sb.append("event_date BETWEEN '" + date + "'::date AND ('" + date + "'::date + interval '7 day')");
        sb.append(")");
        final String modificatedQuery = sb.toString();
        String sql = new SQL() {{
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
        return new SQL() {{
            SELECT("*");
            FROM("events");
            WHERE(preparedWhere);
        }}.toString();
    }

//    public String selectFutureFilteredEvents(Map<String, Object> parameters){
//        return new SQL(){{
//            SELECT("*");
//            FROM("events");
//        }}.toString();
//    }
}
