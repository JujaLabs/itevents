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
        final Date date = (Date) parameters.get("dateStart");
        return new SQL() {{
            SELECT("e.id, e.title, e.event_date, e.create_date, e.reg_link, e.address, e.point, e.contact, e.free," +
                    " e.price, e.currency_id, e.city_id");
            FROM("events e");
            LEFT_OUTER_JOIN("visit_log vl ON e.id = vl.event_id");
            if (params.getCity() != null) {
                WHERE("city_id = #{params.city.id}");
            }
            if (params.getCity() == null && (params.getLatitude() != null)) {
                WHERE("ST_DWithin((point)::geography, " +
                        "ST_MakePoint(#{params.longitude},#{params.latitude})::geography, #{params.radius})");
            }
            if (params.getFree() != null) {
                WHERE("free = #{params.free}");
            }
            if (params.getTechnologies() != null) {
                JOIN(makeJoin(params));
                WHERE("e.id=et.event_id");
            }
            if (date != null) {
                WHERE("event_date BETWEEN '" + date + "'::date AND ('" + date + "'::date + interval '7 day')");
            }
            GROUP_BY("e.id, vl.event_id");
            ORDER_BY("COUNT(vl.user_id) DESC");

        }}.toString();
    }
}
