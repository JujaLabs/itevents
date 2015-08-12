package org.itevents.mybatis.mapper;

import org.itevents.model.Role;
import org.itevents.model.User;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
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

    private static UserMapper userMapper;
    private static RoleMapper roleMapper;

    private static Role role1;
    private static User testUser;


    @BeforeClass
    public static void setup() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        roleMapper = context.getBean("roleMapper", RoleMapper.class);
        userMapper = context.getBean("userMapper", UserMapper.class);
        role1 = roleMapper.getRole(1);
        testUser = new User("testUser", "testUserPassword", role1);
    }

    @AfterClass
    public static void teardown() {
        userMapper = null;
        roleMapper = null;
        role1 = null;
        testUser = null;
    }

    @Test
    public void testGetUser1() throws Exception {
        User expectedUser = new User("guest", "guest", role1);
        expectedUser.setId(1);

        User returnedUser = userMapper.getUser(1);

        assertEquals(expectedUser, returnedUser);
    }

    @Test
    public void testGetUser0() throws Exception {
        assertNull(userMapper.getUser(0));
    }

    @Test
    public void testAddUser() throws Exception {
        User expectedUser = testUser;
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
        userMapper.addUser(testUser);
        int expectedSize = userMapper.getAllUsers().size() - 1;

        userMapper.removeUser(testUser);
        int returnedSize = userMapper.getAllUsers().size();

        assertEquals(expectedSize, returnedSize);
    }
}
