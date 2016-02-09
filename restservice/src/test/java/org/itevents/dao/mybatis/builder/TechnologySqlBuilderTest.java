package org.itevents.dao.mybatis.builder;

import org.apache.ibatis.session.defaults.DefaultSqlSession;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by vaa25 on 03.02.2016.
 */
public class TechnologySqlBuilderTest {

    @Test
    public void shouldBuildSqlQueryToSelectTechnologiesByNames() throws Exception {
        DefaultSqlSession.StrictMap strictMap = new DefaultSqlSession.StrictMap();
        String[] technologies = new String[]{"java", "spring"};
        strictMap.put("array", technologies);
        String expectedSql = "SELECT * FROM technology WHERE name IN ('java', 'spring') ORDER BY name";

        String returnedSql = new TechnologySqlBuilder().getTechnologiesByNames(strictMap);

        assertEquals(expectedSql, returnedSql);

    }

    @Test
    public void shouldReturnEmptyStringIfTechnologiesAreAbsent() throws Exception {
        DefaultSqlSession.StrictMap strictMap = new DefaultSqlSession.StrictMap();
        String[] technologies = new String[]{};
        strictMap.put("array", technologies);
        String expectedSql = "";

        String returnedSql = new TechnologySqlBuilder().getTechnologiesByNames(strictMap);

        assertEquals(expectedSql, returnedSql);

    }
}