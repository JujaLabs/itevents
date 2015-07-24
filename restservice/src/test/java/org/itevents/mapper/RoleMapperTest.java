package org.itevents.mapper;

import org.itevents.model.Role;
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
public class RoleMapperTest {
    @Autowired
    private RoleMapper roleMapper;

    @Test
    public void testGetRole1() throws Exception {
        Role expected = new Role("guest");
        expected.setId(1);
        assertEquals(expected, roleMapper.getRole(1));
    }

    @Test
    public void testGetRole0() throws Exception {
        assertNull(roleMapper.getRole(0));
    }

    @Test
    public void testAddRole() throws Exception {
        Role role = new Role("testRole");
        roleMapper.addRole(role);
        roleMapper.getAllRoles();
    }

    @Test
    public void testGetAllRoles() throws Exception {

    }
}
