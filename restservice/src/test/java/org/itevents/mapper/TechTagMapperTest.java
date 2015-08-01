package org.itevents.mapper;

import org.itevents.model.TechTag;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by vaa25 on 22.07.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class TechTagMapperTest {

    private final static int ID_0 = 0;
    private final static int ID_1 = 1;

    @Autowired
    private TechTagMapper techTagMapper;

    @Test
    public void testGetTechTag1() throws Exception {
        TechTag expectedTechTag = new TechTag("Java");
        expectedTechTag.setId(ID_1);
        TechTag returnedTechTag = techTagMapper.getTechTag(ID_1);
        assertEquals(expectedTechTag, returnedTechTag);
    }


    @Test
    public void testGetTechTag0() throws Exception {
        TechTag returnedTechTag = techTagMapper.getTechTag(ID_0);
        assertNull(returnedTechTag);
    }
}