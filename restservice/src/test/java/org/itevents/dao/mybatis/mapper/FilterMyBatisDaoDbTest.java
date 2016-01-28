package org.itevents.dao.mybatis.mapper;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.itevents.AbstractDbTest;
import org.itevents.dao.exception.EntityNotFoundDaoException;
import org.itevents.dao.mybatis.sql_session_dao.FilterMyBatisDao;
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

/**
 * Created by vaa25 on 22.07.2015.
 */
@DatabaseSetup(value = FilterMyBatisDaoDbTest.TEST_PATH + "FilterMapperTest_initial.xml",
        type = DatabaseOperation.REFRESH)
@DatabaseTearDown(value = FilterMyBatisDaoDbTest.TEST_PATH + "FilterMapperTest_initial.xml",
        type = DatabaseOperation.DELETE_ALL)
public class FilterMyBatisDaoDbTest extends AbstractDbTest {

    public static final String TEST_PATH = PATH + "FilterMapperTest/";
    @Inject
    private FilterMyBatisDao filterMyBatisDao;

    @Test
    public void shouldFindFilterById() throws Exception {
        Filter expectedFilter = BuilderUtil.buildFilterFirst();

        Filter returnedFilter = filterMyBatisDao.getFilter(expectedFilter.getId());

        assertEquals(expectedFilter, returnedFilter);
    }

    @Test(expected = EntityNotFoundDaoException.class)
    public void expectNullWhenFilterIsAbsent() {
        filterMyBatisDao.getFilter(ABSENT_ID);
    }

    @Test
    public void shouldFindLastFilterOfUser() {
        Filter expectedFilter = BuilderUtil.buildFilterFifth();
        User user = BuilderUtil.buildUserKuchin();

        Filter returnedFilter = filterMyBatisDao.getLastFilterByUser(user);

        assertEquals(expectedFilter, returnedFilter);
    }

    @Test
    public void shouldGetAllFilters() throws ParseException {
        int expectedSize = 5;

        int returnedSize = filterMyBatisDao.getAllFilters().size();

        Assert.assertEquals(expectedSize, returnedSize);
    }

    @Test
    @ExpectedDatabase(value = TEST_PATH + "testAddFilter_expected.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void shouldAddFilter() throws ParseException {
        Filter addingFilter = BuilderUtil.buildFilterTest();

        filterMyBatisDao.addFilter(addingFilter);
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

        filterMyBatisDao.addFilterTechnology(addingFilter);
    }
}