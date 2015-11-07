package org.itevents.dao.mybatis.util;

import org.itevents.model.Filter;
import org.itevents.test_utils.BuilderUtil;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by vaa25 on 06.11.2015.
 */
public class FilterTechnologySqlBuilderTest {

    @Test
    public void shouldBuildSqlQueryToAddFilterTechnologiesOfGivenFilter() throws Exception {
        Filter filter = BuilderUtil.buildFilterTest();
        FilterTechnologySqlBuilder builder = new FilterTechnologySqlBuilder();

        String expectedSql = "INSERT INTO filter_technology (filter_id, technology_id) VALUES (-6, -1), (-6, -2)";

        String returnedSql = builder.addFilterTechnology(filter);

        assertEquals(expectedSql, returnedSql);
    }

}