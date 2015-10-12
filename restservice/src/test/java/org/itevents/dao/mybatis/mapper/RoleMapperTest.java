package org.itevents.dao.mybatis.mapper;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.itevents.AbstractDbTest;
import org.itevents.model.Role;
import org.itevents_utils.BuilderUtil;
import org.junit.Test;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by vaa25 on 21.07.2015.
 */
@DatabaseSetup(value = "file:src/test/resources/dbunit/RoleMapperTest/RoleMapperTest_initial.xml",
        type = DatabaseOperation.REFRESH)
@DatabaseTearDown(value = "file:src/test/resources/dbunit/RoleMapperTest/RoleMapperTest_initial.xml",
        type = DatabaseOperation.DELETE_ALL)
public class RoleMapperTest extends AbstractDbTest {

    private final String TEST_PATH = PATH + "RoleMapperTest/";
    @Inject
    private RoleMapper roleMapper;

    @Test
    public void testGetRoleSuccess() throws Exception {
        Role expectedRole = BuilderUtil.buildRoleGuest();
        Role returnedRole = roleMapper.getRole(ID_1);
        assertEquals(expectedRole, returnedRole);
    }

    @Test
    public void testGetRoleFail() throws Exception {
        Role returnedRole = roleMapper.getRole(ID_0);
        assertNull(returnedRole);
    }

    @Test
    @ExpectedDatabase(value = TEST_PATH + "testAddRole_expected.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void testAddRole() throws Exception {
        Role testRole = BuilderUtil.buildRoleTest();
        roleMapper.addRole(testRole);
        System.out.println();
    }

    @Test
    @DatabaseSetup(value = TEST_PATH + "testRemoveRole_initial.xml", type = DatabaseOperation.REFRESH)
    @ExpectedDatabase(value = TEST_PATH + "RoleMapperTest_initial.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void testRemoveRole() {
        Role testRole = BuilderUtil.buildRoleTest();
        roleMapper.removeRole(testRole);
    }

    @Test
    public void testGetAllRoles() throws Exception {
        int expectedSize = 3;
        int returnedSize = roleMapper.getAllRoles().size();
        assertEquals(expectedSize, returnedSize);
    }
}
