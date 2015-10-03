package org.itevents.dao.mybatis.mapper;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.itevents.AbstractDbTest;
import org.itevents.model.User;
import org.itevents.util.BuilderUtil;
import org.junit.Test;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by vaa25 on 21.07.2015.
 */

public class UserMapperTest extends AbstractDbTest {

    private final String TEST_PATH = PATH + "UserMapperTest/";
    @Inject
    private UserMapper userMapper;

    @Test
    @DatabaseSetup(value = TEST_PATH + "UserMapperTest_initial.xml", type = DatabaseOperation.REFRESH)
    public void testGetUserSuccess() throws Exception {
        User expectedUser = BuilderUtil.buildUserGuest();
        User returnedUser = userMapper.getUser(ID_1);
        assertEquals(expectedUser, returnedUser);
    }

    @Test
    @DatabaseSetup(value = TEST_PATH + "UserMapperTest_initial.xml", type = DatabaseOperation.REFRESH)
    public void testGetUserFail() throws Exception {
        User returnedUser = userMapper.getUser(ID_0);
        assertNull(returnedUser);
    }

    @Test
    @DatabaseSetup(value = TEST_PATH + "UserMapperTest_initial.xml", type = DatabaseOperation.REFRESH)
    @ExpectedDatabase(value = TEST_PATH + "testAddUser_expected.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    @DatabaseTearDown(value = TEST_PATH + "UserMapperTest_initial.xml")
    public void testAddUser() throws Exception {
        User testUser = BuilderUtil.buildUserTest();
        userMapper.addUser(testUser);
    }

    @Test
    @DatabaseSetup(value = TEST_PATH + "UserMapperTest_initial.xml", type = DatabaseOperation.REFRESH)
    public void testGetAllUsers() throws Exception {
        int expectedSize = 4;
        int returnedSize = userMapper.getAllUsers().size();
        assertEquals(expectedSize, returnedSize);
    }

    @Test
    @DatabaseSetup(value = TEST_PATH + "testRemoveUser_initial.xml", type = DatabaseOperation.REFRESH)
    @ExpectedDatabase(value = TEST_PATH + "UserMapperTest_initial.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    @DatabaseTearDown(value = TEST_PATH + "UserMapperTest_initial.xml")
    public void testRemoveUser() {
        User testUser = BuilderUtil.buildUserTest();
        userMapper.removeUser(testUser);
    }
}
