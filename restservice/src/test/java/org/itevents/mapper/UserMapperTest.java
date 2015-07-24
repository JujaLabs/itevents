package org.itevents.mapper;

import org.itevents.model.Role;
import org.itevents.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by vaa25 on 21.07.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
@Transactional
public class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    private Role role1;
    private User testUser;

    @Before
    public void setRole1() {
        role1 = roleMapper.getRole(1);
        testUser = new User("testUser", "testUserPassword", role1);
    }

    @After
    public void dropRole1() {
        role1 = null;
        testUser = null;
    }

    @Test
    public void testGetUser1() throws Exception {
        User expected = new User("guest", "guest", role1);
        expected.setId(1);
        assertEquals(expected, userMapper.getUser(1));
    }

    @Test
    public void testGetUser0() throws Exception {
        assertNull(userMapper.getUser(0));
    }

    @Test
    public void testAddUser() throws Exception {
        userMapper.addUser(testUser);

        assertEquals(testUser, userMapper.getUser(testUser.getId()));

        userMapper.removeUser(testUser);

    }

    @Test
    public void testGetAllUsers() throws Exception {
        assertEquals(4, userMapper.getAllUsers().size());
    }

    @Test
    public void testRemoveUser() {
        userMapper.addUser(testUser);
        int wasSize = userMapper.getAllUsers().size();

        userMapper.removeUser(testUser);

        assertEquals(wasSize - 1, userMapper.getAllUsers().size());
    }
}
