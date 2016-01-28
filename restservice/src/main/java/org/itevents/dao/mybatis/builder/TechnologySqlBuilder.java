package org.itevents.dao.mybatis.builder;

import org.apache.ibatis.session.defaults.DefaultSqlSession;

public class TechnologySqlBuilder {

    public String getTechnologiesByNames(DefaultSqlSession.StrictMap strictMap) {
        String[] names = (String[]) strictMap.get("array");
        StringBuilder sql = new StringBuilder();
        if (names != null && names.length > 0) {
            sql.append("SELECT * FROM technology WHERE name IN (");
            for (int i = 0; i < names.length; i++) {
                if (i > 0) {
                    sql.append(", ");
                }
                sql.append("'").append(names[i]).append("'");
            }
            sql.append(") ORDER BY name");
        }
        System.out.println(sql.toString());
        return sql.toString();
    }
}
