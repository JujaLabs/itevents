package org.itevents.dao.mybatis.mapper;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.itevents.AbstractDbTest;
import org.itevents.model.Role;
import org.junit.Test;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by vaa25 on 21.07.2015.
 */

public class RoleMapperTest extends AbstractDbTest {

    private final String TEST_PATH = PATH + "RoleMapperTest/";
    @Inject
    private RoleMapper roleMapper;

    @Test
    @DatabaseSetup(TEST_PATH + "RoleMapperTest_initial.xml")
    public void testGetRoleSuccess() throws Exception {
        Role expectedRole = new Role("guest");
        Role returnedRole = roleMapper.getRole(ID_1);
        assertEquals(expectedRole, returnedRole);
    }

    @Test
    @DatabaseSetup(TEST_PATH + "RoleMapperTest_initial.xml")
    public void testGetRoleFail() throws Exception {
        Role returnedRole = roleMapper.getRole(ID_0);
        assertNull(returnedRole);
    }

    @Test
    @DatabaseSetup(value = TEST_PATH + "RoleMapperTest_initial.xml")
    @ExpectedDatabase(value = TEST_PATH + "testAddRole_expected.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void testAddRole() throws Exception {
        Role testRole = new Role("testRole");
        roleMapper.addRole(testRole);
        System.out.println(roleMapper.getAllRoles());
    }

    @Test
    @DatabaseSetup(TEST_PATH + "testRemoveRole_initial.xml")
    @ExpectedDatabase(value = TEST_PATH + "RoleMapperTest_initial.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void testRemoveRole() {
        Role testRole = new Role("testRole");
        testRole.setId(-4);
        roleMapper.removeRole(testRole);
    }

    @Test
    @DatabaseSetup(TEST_PATH + "RoleMapperTest_initial.xml")
    public void testGetAllRoles() throws Exception {
        int expectedSize = 3;
        int returnedSize = roleMapper.getAllRoles().size();
        assertEquals(expectedSize, returnedSize);
    }
}
