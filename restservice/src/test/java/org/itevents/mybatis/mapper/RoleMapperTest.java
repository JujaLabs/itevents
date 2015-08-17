package org.itevents.mybatis.mapper;

import org.itevents.model.Role;
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
public class RoleMapperTest {

    private final int ID_0 = 0;
    private final int ID_1 = 1;
    @Inject
    private RoleMapper roleMapper;
    private Role testRole = new Role("testRole");

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
        int returnedSize = roleMapper.getAllRoles().size();
        assertEquals(expectedSize, returnedSize);
    }

    @Test
    public void testGetAllRoles() throws Exception {
        int expectedSize = 3;
        int returnedSize = roleMapper.getAllRoles().size();
        assertEquals(expectedSize, returnedSize);
    }
}
