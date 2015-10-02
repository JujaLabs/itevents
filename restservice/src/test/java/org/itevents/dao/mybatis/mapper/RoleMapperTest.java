package org.itevents.dao.mybatis.mapper;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.itevents.AbstractDbTest;
import org.itevents.model.Role;
import org.itevents.util.BuilderUtil;
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
    @DatabaseSetup(value = TEST_PATH + "RoleMapperTest_initial.xml", type = DatabaseOperation.REFRESH)
    public void testGetRoleSuccess() throws Exception {
        Role expectedRole = BuilderUtil.buildRoleGuest();
        Role returnedRole = roleMapper.getRole(ID_1);
        assertEquals(expectedRole, returnedRole);
    }

    @Test
    @DatabaseSetup(value = TEST_PATH + "RoleMapperTest_initial.xml", type = DatabaseOperation.REFRESH)
    public void testGetRoleFail() throws Exception {
        Role returnedRole = roleMapper.getRole(ID_0);
        assertNull(returnedRole);
    }

    @Test
    @DatabaseSetup(value = TEST_PATH + "RoleMapperTest_initial.xml", type = DatabaseOperation.REFRESH)
    @ExpectedDatabase(value = TEST_PATH + "testAddRole_expected.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    @DatabaseTearDown(value = TEST_PATH + "RoleMapperTest_initial.xml")
    public void testAddRole() throws Exception {
        Role testRole = BuilderUtil.buildRoleTest();
        roleMapper.addRole(testRole);
    }

    @Test
    @DatabaseSetup(value = TEST_PATH + "testRemoveRole_initial.xml", type = DatabaseOperation.REFRESH)
    @ExpectedDatabase(value = TEST_PATH + "RoleMapperTest_initial.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    @DatabaseTearDown(value = TEST_PATH + "RoleMapperTest_initial.xml")
    public void testRemoveRole() {
        Role testRole = BuilderUtil.buildRoleTest();
        roleMapper.removeRole(testRole);
    }

    @Test
    @DatabaseSetup(value = TEST_PATH + "RoleMapperTest_initial.xml", type = DatabaseOperation.REFRESH)
    public void testGetAllRoles() throws Exception {
        int expectedSize = 3;
        int returnedSize = roleMapper.getAllRoles().size();
        assertEquals(expectedSize, returnedSize);
    }
}
