package org.itevents.mybatis.mapper;

import org.itevents.model.Technology;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by vaa25 on 22.07.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
@Transactional
public class TechnologyMapperTest {

    private final static int ID_0 = 0;
    private final static int ID_1 = 1;

    @Autowired
    private TechnologyMapper techTagMapper;

    @Test
    public void testGetTechTag1() throws Exception {
        Technology expectedTechnology = new Technology("Java");
        expectedTechnology.setId(ID_1);
        Technology returnedTechnology = techTagMapper.getTechTag(ID_1);
        assertEquals(expectedTechnology, returnedTechnology);
    }


    @Test
    public void testGetTechTag0() throws Exception {
        Technology returnedTechnology = techTagMapper.getTechTag(ID_0);
        assertNull(returnedTechnology);
    }
}