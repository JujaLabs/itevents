package org.itevents.mybatis.mapper.util;

import org.apache.ibatis.jdbc.SQL;
import org.itevents.model.City;
import org.itevents.model.Technology;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class SQLBuilder {

    public String selectAllEvents() {
        return new SQL().
                SELECT("*").
                FROM("events").
                toString();
    }

    public String selectEvent() {
        return new SQL().
                SELECT("*").
                FROM("events").
                WHERE("id = #{id}").
                toString();
    }

    public String selectFilteredEvent(final City city, final Boolean free, final List<Technology> technologies) {
        return new SQL() {{
            SELECT("*");
            FROM("events");
            if (city != null) {
                WHERE("city_id = #{city.id}");
            }
            if (city == null) {
                WHERE("ST_DWithin((point)::geography, ST_MakePoint(#{longitude},#{latitude})::geography, #{radius})");
            }
            if (free != null) {
                WHERE("free = #{free}");
            }
            if (technologies != null) {
                WHERE("id IN").SELECT("event_id FROM event_technology");
                for (Technology technology : technologies) {
                    WHERE("technology_id = #{technology.id}");
                }
            }
        }}.toString();
    }

    public String test(final List<Integer> listId) {
        return new SQL() {{
            SELECT("*");
            FROM("events");
            for (int i = 0; i < listId.size(); i++) {
                if (i == (listId.size() - 1)) {
                    WHERE("id = #{list.get(" + i +")}");
                } else {
                    WHERE("id = #{list.get(" + i +")}").OR();
                }
            }
        }}.toString();
    }
}
