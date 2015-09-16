package org.itevents.dao.mybatis.mapper;

import org.itevents.model.Technology;
import org.junit.Test;
import org.junit.runner.RunWith;
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
public class TechnologyMapperTest {

    private final int ID_0 = 0;
    private final int ID_1 = 1;
    private final int ID_4 = 4;
    private final int ID_8 = 8;
    private final int ID_9 = 9;

    @Inject
    private TechnologyMapper technologyMapper;

    @Test
    public void testGetTechTag1() throws Exception {
        Technology expectedTechnology = new Technology("Java");
        expectedTechnology.setId(ID_1);
        Technology returnedTechnology = technologyMapper.getTechnology(ID_1);
        assertEquals(expectedTechnology, returnedTechnology);
    }

    @Test
    public void testGetTechnologiesByEventId() throws Exception {
        List<Technology> expectedTechnologies = new ArrayList<>();
        expectedTechnologies.add(technologyMapper.getTechnology(ID_1));
        expectedTechnologies.add(technologyMapper.getTechnology(ID_4));
        expectedTechnologies.add(technologyMapper.getTechnology(ID_8));
        expectedTechnologies.add(technologyMapper.getTechnology(ID_9));
        List<Technology> returnedTechnologies = technologyMapper.getTechnologiesByEventId(ID_1);
        assertEquals(expectedTechnologies, returnedTechnologies);
    }

    @Test
    public void testGetTechTag0() throws Exception {
        Technology returnedTechnology = technologyMapper.getTechnology(ID_0);
        assertNull(returnedTechnology);
    }
}