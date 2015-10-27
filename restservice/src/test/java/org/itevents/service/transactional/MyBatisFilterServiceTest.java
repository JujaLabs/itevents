package org.itevents.service.transactional;

import org.itevents.dao.FilterDao;
import org.itevents.model.Filter;
import org.itevents.model.User;
import org.itevents.service.FilterService;
import org.itevents.test_utils.BuilderUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

/**
 * Created by vaa25 on 27.10.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
@Transactional
public class MyBatisFilterServiceTest {

    @InjectMocks
    @Inject
    private FilterService filterService;
    @Mock
    private FilterDao filterDao;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldFindFilter() throws Exception {
        Filter expectedFilter = BuilderUtil.buildFilterFirst();

        when(filterDao.getFilter(expectedFilter.getId())).thenReturn(expectedFilter);

        Filter returnedFilter = filterService.getFilter(expectedFilter.getId());

        verify(filterDao).getFilter(expectedFilter.getId());
        assertEquals(expectedFilter, returnedFilter);
    }

    @Test
    public void shouldFindLasFilterOfSpecifiedUser() {
        Filter expectedFilter = BuilderUtil.buildFilterFifth();
        User user = BuilderUtil.buildUserKuchin();

        when(filterDao.getLastFilterByUser(user)).thenReturn(expectedFilter);

        Filter returnedFilter = filterService.getLastFilterByUser(user);

        verify(filterDao).getLastFilterByUser(user);
        assertEquals(expectedFilter, returnedFilter);
    }

    @Test
    public void shouldGetAllFilters() {
        filterService.getAllFilters();

        verify(filterDao).getAllFilters();
    }

    @Test
    public void shouldAddFilter() throws Exception {
        Filter testFilter = BuilderUtil.buildFilterFirst();

        filterService.addFilter(testFilter);

        verify(filterDao).addFilter(testFilter);
    }

    @Test
    public void shouldRemoveFilter() {
        Filter expectedFilter = BuilderUtil.buildFilterFirst();

        when(filterDao.getFilter(expectedFilter.getId())).thenReturn(expectedFilter);
        doNothing().when(filterDao).removeFilter(expectedFilter);

        Filter returnedFilter = filterService.removeFilter(expectedFilter);

        assertEquals(expectedFilter, returnedFilter);
    }

    @Test
    public void shouldNotRemoveFilterWhenItIsNotExisting() {
        Filter testFilter = BuilderUtil.buildFilterFirst();

        when(filterDao.getFilter(testFilter.getId())).thenReturn(null);
        doNothing().when(filterDao).removeFilter(testFilter);

        Filter returnedFilter = filterService.removeFilter(testFilter);

        assertNull(returnedFilter);
    }

}