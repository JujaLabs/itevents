package org.itevents.dao.mybatis.mapper;

import org.itevents.model.Role;
import org.itevents.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by vaa25 on 21.07.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
@Transactional
public class UserMapperTest {

    private final int ID_0 = 0;
    private final int ID_1 = 1;
    @Inject
    private UserMapper userMapper;
    @Inject
    private RoleMapper roleMapper;
    @Inject
    private EventMapper eventMapper;

    @Test
    public void testGetUser1() throws Exception {
        Role role1 = roleMapper.getRole(ID_1);
        User expectedUser = new User("guest", "guest", role1);
        expectedUser.setId(ID_1);
        User returnedUser = userMapper.getUser(ID_1);
        assertEquals(expectedUser, returnedUser);
    }

    @Test
    public void testGetUser0() throws Exception {
        User returnedUser = userMapper.getUser(ID_0);
        assertNull(returnedUser);
    }

    @Test
    public void testAddUser() throws Exception {
        Role role1 = roleMapper.getRole(ID_1);
        User expectedUser = new User("testUser", "testUserPassword", role1);
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
        Role role1 = roleMapper.getRole(ID_1);
        User testUser = new User("testUser", "testUserPassword", role1);
        userMapper.addUser(testUser);
        int expectedSize = userMapper.getAllUsers().size() - 1;
        userMapper.removeUser(testUser);
        int returnedSize = userMapper.getAllUsers().size();
        assertEquals(expectedSize, returnedSize);
    }

    @Test
    public void subscribeToEvent() {
        Role role1 = roleMapper.getRole(ID_1);
        User testUser = new User("testUser", "testUserPassword", role1);
        userMapper.addUser(testUser);
        userMapper.willGoToEvent(userMapper.getUser(3), eventMapper.getEvent(3));
        userMapper.removeUser(testUser);
    }
}
