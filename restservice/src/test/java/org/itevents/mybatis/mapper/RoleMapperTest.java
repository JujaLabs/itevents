package org.itevents.mybatis.mapper;

import org.itevents.model.Role;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
public class RoleMapperTest {

    private final static int ID_0 = 0;
    private final static int ID_1 = 1;

    @Autowired
    private static RoleMapper roleMapper;
    private static Role testRole;


    @BeforeClass
    public static void setup() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        roleMapper = context.getBean("roleMapper", RoleMapper.class);
        testRole = new Role("testRole");
    }

    @AfterClass
    public static void teardown() {
        roleMapper = null;
        testRole = null;
    }

    @Test
    public void testGetRole1() throws Exception {
        Role expectedRole = new Role("guest");
        expectedRole.setId(ID_1);
        Role returnedRole = roleMapper.getRole(ID_1);
        assertEquals(expectedRole, returnedRole);
    }

    @Test
    public void testGetRole0() throws Exception {
        Role returnedRole = roleMapper.getRole(ID_0);
        assertNull(returnedRole);
    }

    @Test
    public void testAddRole() throws Exception {
        Role expectedRole = testRole;
        roleMapper.addRole(expectedRole);
        Role returnedRole = roleMapper.getRole(expectedRole.getId());
        assertEquals(expectedRole, returnedRole);
        roleMapper.removeRole(testRole);
    }

    @Test
    public void testRemoveRole() {
        Role expectedRole = testRole;
        roleMapper.addRole(expectedRole);
        int expectedSize = roleMapper.getAllRoles().size() - 1;
        roleMapper.removeRole(expectedRole);
        int returnrdSize = roleMapper.getAllRoles().size();
        assertEquals(expectedSize, returnrdSize);
    }

    @Test
    public void testGetAllRoles() throws Exception {
        int expectedSize = 3;
        int returnedSize = roleMapper.getAllRoles().size();
        assertEquals(expectedSize, returnedSize);
    }
}
