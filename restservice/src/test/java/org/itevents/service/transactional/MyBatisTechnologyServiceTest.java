package org.itevents.service.transactional;

import org.itevents.dao.TechnologyDao;
import org.itevents.model.Technology;
import org.itevents.service.TechnologyService;
import org.itevents.test_utils.BuilderUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

/**
 * Created by vaa25 on 22.07.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class MyBatisTechnologyServiceTest {

    @InjectMocks
    @Inject
    private TechnologyService technologyService;
    @Mock
    private TechnologyDao technologyDao;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldFindTechnology() throws Exception {
        Technology expectedTechnology = BuilderUtil.buildTechnologyJava();

        when(technologyDao.getTechnology(expectedTechnology.getId())).thenReturn(expectedTechnology);

        Technology returnedTechnology = technologyService.getTechnology(expectedTechnology.getId());

        verify(technologyDao).getTechnology(expectedTechnology.getId());
        assertEquals(expectedTechnology, returnedTechnology);
    }

    @Test
    public void shouldFindTechnologiesByNames() throws Exception {
        String[] names = {"Java", "JavaScript"};

        technologyService.getTechnologiesByNames(names);

        verify(technologyDao).getTechnologiesByNames(names);
    }

    @Test
    public void shouldGetAllTechnologies() {
        technologyService.getAllTechnologies();

        verify(technologyDao).getAllTechnologies();
    }

    @Test
    public void shouldAddTechnology() throws Exception {
        Technology testTechnology = BuilderUtil.buildTechnologyTest();

        technologyService.addTechnology(testTechnology);

        verify(technologyDao).addTechnology(testTechnology);
    }

    @Test
    public void shouldRemoveTechnology() {
        Technology expectedTechnology = BuilderUtil.buildTechnologyTest();

        when(technologyDao.getTechnology(expectedTechnology.getId())).thenReturn(expectedTechnology);
        doNothing().when(technologyDao).removeTechnology(expectedTechnology);

        Technology returnedTechnology = technologyService.removeTechnology(expectedTechnology);

        assertEquals(expectedTechnology, returnedTechnology);
    }

    @Test
    public void shouldNotRemoveTechnologyWhenItIsNotExisting() {
        Technology testTechnology = BuilderUtil.buildTechnologyTest();

        when(technologyDao.getTechnology(testTechnology.getId())).thenReturn(null);
        doNothing().when(technologyDao).removeTechnology(testTechnology);

        Technology returnedTechnology = technologyService.removeTechnology(testTechnology);

        assertNull(returnedTechnology);
    }
}