package org.itevents.mapper;

import org.itevents.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by vaa25 on 21.07.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Test
    public void testGetUser1() throws Exception {
        assertEquals(new User(1, "guest", "guest", roleMapper.getRole(1)), userMapper.getUserById(1));
    }

    @Test
    public void testGetUser0() throws Exception {
        assertNull(userMapper.getUserById(0));
    }
}
