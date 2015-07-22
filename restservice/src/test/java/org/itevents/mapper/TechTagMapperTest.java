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

    @Autowired
    private TechTagMapper techTagMapper;

    @Test
    public void testGetTechTag1() throws Exception {
        assertEquals(new TechTag(1, "Java"), techTagMapper.getTechTag(1));
    }


    @Test
    public void testGetTechTag0() throws Exception {
        assertNull(techTagMapper.getTechTag(0));
    }
}