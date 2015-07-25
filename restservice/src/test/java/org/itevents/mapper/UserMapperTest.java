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
    private User expectedUser;

    @Before
    public void setTemplate() {
        role1 = roleMapper.getRole(1);
        expectedUser = new User("testUser", "testUserPassword", role1);
    }

    @After
    public void clearTemplate() {
        role1 = null;
        expectedUser = null;
    }

    @Test
    public void testGetUser1() throws Exception {
        User expected = new User("guest", "guest", role1);
        expected.setId(1);

        User returned = userMapper.getUser(1);

        assertEquals(expected, returned);
    }

    @Test
    public void testGetUser0() throws Exception {
        assertNull(userMapper.getUser(0));
    }

    @Test
    public void testAddUser() throws Exception {
        userMapper.addUser(expectedUser);

        User returnedUser = userMapper.getUser(expectedUser.getId());
        assertEquals(expectedUser, returnedUser);

        userMapper.removeUser(expectedUser);

    }

    @Test
    public void testGetAllUsers() throws Exception {
        int expectedSize = 4;
        int returnedSize = userMapper.getAllUsers().size();
        assertEquals(expectedSize, returnedSize);
    }

    @Test
    public void testRemoveUser() {
        userMapper.addUser(expectedUser);
        int expectedSize = userMapper.getAllUsers().size() - 1;

        userMapper.removeUser(expectedUser);
        int returnedSize = userMapper.getAllUsers().size();

        assertEquals(expectedSize, returnedSize);
    }
}
