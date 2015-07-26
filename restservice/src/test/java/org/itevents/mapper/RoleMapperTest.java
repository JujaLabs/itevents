package org.itevents.mapper;

import org.itevents.model.Role;
import org.junit.AfterClass;
import org.junit.BeforeClass;
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
public class RoleMapperTest {
    private static Role expected;
    @Autowired
    private RoleMapper roleMapper;

    @BeforeClass
    public static void setTemplate() {
        expected = new Role("testRole");
    }

    @AfterClass
    public static void clearTemplate() {
        expected = null;
    }

    @Test
    public void testGetRole1() throws Exception {
        Role expected = new Role("guest");
        expected.setId(1);
        Role returned = roleMapper.getRole(1);
        assertEquals(expected, returned);
    }

    @Test
    public void testGetRole0() throws Exception {
        assertNull(roleMapper.getRole(0));
    }

    @Test
    public void testAddRole() throws Exception {
        roleMapper.addRole(expected);

        Role returned = roleMapper.getRole(expected.getId());
        assertEquals(expected, returned);

        roleMapper.removeRole(expected);
    }

    @Test
    public void testRemoveRole() {
        roleMapper.addRole(expected);
        int wasSize = roleMapper.getAllRoles().size();

        roleMapper.removeRole(expected);

        assertEquals(wasSize - 1, roleMapper.getAllRoles().size());
    }

    @Test
    public void testGetAllRoles() throws Exception {
        int expectedSize = 3;
        int returnedSize = roleMapper.getAllRoles().size();
        assertEquals(expectedSize, returnedSize);
    }
}
