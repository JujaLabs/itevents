package org.itevents.dao.mybatis.mapper;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.itevents.AbstractDbTest;
import org.itevents.model.Filter;
import org.itevents.model.Technology;
import org.itevents.model.User;
import org.itevents.test_utils.BuilderUtil;
import org.junit.Assert;
import org.junit.Test;

import javax.inject.Inject;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by vaa25 on 22.07.2015.
 */
@DatabaseSetup(value = FilterMapperDbTest.TEST_PATH + "FilterMapperTest_initial.xml",
        type = DatabaseOperation.REFRESH)
@DatabaseTearDown(value = FilterMapperDbTest.TEST_PATH + "FilterMapperTest_initial.xml",
        type = DatabaseOperation.DELETE_ALL)
public class FilterMapperDbTest extends AbstractDbTest {

    public static final String TEST_PATH = PATH + "FilterMapperTest/";
    @Inject
    private FilterMapper filterMapper;

    @Test
    public void shouldFindFilterById() throws Exception {
        Filter expectedFilter = BuilderUtil.buildFilterFirst();

        Filter returnedFilter = filterMapper.getFilter(expectedFilter.getId());

        assertEquals(expectedFilter, returnedFilter);
    }

    @Test
    public void expectNullWhenFilterIsAbsent() {
        Filter returnedFilter = filterMapper.getFilter(ABSENT_ID);

        assertNull(returnedFilter);
    }

    @Test
    public void shouldFindLastFilterOfUser() {
        Filter expectedFilter = BuilderUtil.buildFilterFifth();
        User user = BuilderUtil.buildUserKuchin();

        Filter returnedFilter = filterMapper.getLastFilterByUser(user);

        assertEquals(expectedFilter, returnedFilter);
    }

    @Test
    public void shouldGetAllFilters() throws ParseException {
        int expectedSize = 5;

        int returnedSize = filterMapper.getAllFilters().size();

        Assert.assertEquals(expectedSize, returnedSize);
    }

    @Test
    @ExpectedDatabase(value = TEST_PATH + "testAddFilter_expected.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void shouldAddFilter() throws ParseException {
        Filter addingFilter = BuilderUtil.buildFilterTest();

        filterMapper.addFilter(addingFilter);
    }

    @Test
    @DatabaseSetup(value = TEST_PATH + "testAddFilterTechnology_initial.xml", type = DatabaseOperation.REFRESH)
    @ExpectedDatabase(value = TEST_PATH + "testAddFilterTechnology_expected.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void shouldAddTechnologiesToFilterTechnologyTable() throws ParseException {
        Filter addingFilter = BuilderUtil.buildFilterTest();
        List<Technology> technologies = new ArrayList<>();
        technologies.add(BuilderUtil.buildTechnologyJava());
        technologies.add(BuilderUtil.buildTechnologyJavaScript());
        addingFilter.setTechnologies(technologies);

        filterMapper.addFilterTechnology(addingFilter);
    }
}