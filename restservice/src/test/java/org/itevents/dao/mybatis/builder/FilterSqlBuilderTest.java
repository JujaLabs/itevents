package org.itevents.dao.mybatis.builder;

import org.itevents.dao.model.Filter;
import org.itevents.test_utils.BuilderUtil;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by vaa25 on 06.11.2015.
 */
public class FilterSqlBuilderTest {

    @Test
    public void shouldBuildSqlQueryToAddFilterTechnologiesOfGivenFilter() throws Exception {
        Filter filter = BuilderUtil.buildFilterTest();
        String expectedSql = "INSERT INTO filter_technology (filter_id, technology_id) VALUES (-6, -1), (-6, -2)";

        String returnedSql = new FilterSqlBuilder().addFilterTechnology(filter);

        assertEquals(expectedSql, returnedSql);
    }
}