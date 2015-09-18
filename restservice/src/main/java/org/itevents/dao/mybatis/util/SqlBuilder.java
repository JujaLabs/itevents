package org.itevents.dao.mybatis.util;

import org.apache.ibatis.jdbc.SQL;
import org.itevents.parameter.FilteredEventsParameter;

public class SqlBuilder {

    public String getFilteredEvents(final FilteredEventsParameter params) {
        return new SQL() {{
            SELECT("*");
            FROM("events e");
            WHERE("e.event_date > NOW()");
            if (params.getCity() != null) {
                WHERE("city_id = #{city.id}");
            }
            if (params.getCity() == null && (params.getLatitude() != null)) {
                WHERE("ST_DWithin((point)::geography, ST_MakePoint(#{longitude},#{latitude})::geography, #{radius})");
            }
            if (params.getFree() != null) {
                if (params.getFree() == true) {
                    WHERE("(price = null OR price = 0)");
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

}
