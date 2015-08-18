package org.itevents.service;

import org.itevents.model.Technology;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by vaa25 on 22.07.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
@Transactional
public class TechnologyServiceTest {

    private final int ID_0 = 0;
    private final int ID_1 = 1;
    private final int ID_2 = 2;
    private final int SIZE_11 = 11;
    private Technology testTechnology = new Technology("testTechnology");
    @Inject
    private TechnologyService technologyService;


    @Test
    public void testGetTechnology1() throws Exception {
        Technology expectedTechnology = new Technology("Java");
        expectedTechnology.setId(ID_1);
        Technology returnedTechnology = technologyService.getTechnology(ID_1);
        assertEquals(expectedTechnology, returnedTechnology);
    }


    @Test
    public void testGetTechnology0() throws Exception {
        Technology returnedTechnology = technologyService.getTechnology(ID_0);
        assertNull(returnedTechnology);
    }

    @Test
    public void testGetAllTechnologies() throws Exception {
        int expectedSize = SIZE_11;
        int returnedSize = technologyService.getAllTechnologies().size();
        assertEquals(expectedSize, returnedSize);
    }

    @Test
    public void testGetSeveralTechnologiesByName() throws Exception {
        String[] names = {"Java", "JavaScript"};
        List<Technology> expectedList = new ArrayList<>();
        expectedList.add(technologyService.getTechnology(ID_1));
        expectedList.add(technologyService.getTechnology(ID_2));
        List<Technology> returnedTecnologies = technologyService.getSeveralTechnologiesByName(names);
        assertEquals(expectedList, returnedTecnologies);
    }

    @Test
    public void testAddTechnology() throws Exception {
        Technology expectedTechnology = testTechnology;
        technologyService.addTechnology(expectedTechnology);
        Technology returnedTechnology = technologyService.getTechnology(expectedTechnology.getId());
        assertEquals(expectedTechnology, returnedTechnology);
        technologyService.removeTechnology(testTechnology);
    }

    @Test(expected = DuplicateKeyException.class)
    public void testAddExistingCity() throws Exception {
        Technology existingTechnology = new Technology("Java");
        technologyService.addTechnology(existingTechnology);
    }

    @Test
    public void testRemoveTechnologySuccess() throws Exception {
        Technology expectedTechnology = testTechnology;
        technologyService.addTechnology(expectedTechnology);
        Technology returnedTechnology = technologyService.removeTechnology(expectedTechnology);
        assertEquals(expectedTechnology, returnedTechnology);
    }

    @Test
    public void testRemoveTechnologyFail() {
        Technology returnedTechnology = technologyService.removeTechnology(testTechnology);
        assertNull(returnedTechnology);
    }
}