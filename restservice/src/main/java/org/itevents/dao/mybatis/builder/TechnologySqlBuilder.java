package org.itevents.dao.mybatis.builder;

import org.apache.ibatis.session.defaults.DefaultSqlSession;

public class TechnologySqlBuilder {

    public String getTechnologiesByNames(DefaultSqlSession.StrictMap strictMap) {
        String[] names = (String[]) strictMap.get("array");

        if (isEmpty(names)) {
            return "";
        } else {
            return getSql(names);
        }
    }

    private boolean isEmpty(String[] names) {
        return names == null || names.length <= 0;
    }

    private String getSql(String[] names) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM technology WHERE name IN (");
        for (String name : names) {
            sql.append("'").append(name).append("'").append(", ");
        }
        int lastCommaPositionFromEnd = 2;
        sql.delete(sql.length() - lastCommaPositionFromEnd, sql.length());
        sql.append(") ORDER BY name");
        return sql.toString();
    }
}
