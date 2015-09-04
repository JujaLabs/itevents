package org.itevents.mybatis.mapper.util;

import org.apache.ibatis.jdbc.SQL;
import org.itevents.parameter.FilteredEventsParameter;

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
            }
        }}.toString();
    }

    private String makeJoin(FilteredEventsParameter params){
        StringBuilder sb = new StringBuilder();
        sb.append("event_technology et ON e.id=et.event_id and (");
        for (int i = 0; i < params.getTechnologies().size(); i++) {
            sb.append("et.technology_id=");
            sb.append(params.getTechnologies().get(i).getId());
            if (i < (params.getTechnologies().size() - 1)){
                sb.append(" or ");
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
