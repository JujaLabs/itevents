package org.itevents.dao.mybatis.mapper;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.itevents.AbstractDbTest;
import org.itevents.model.User;
import org.itevents.test_utils.BuilderUtil;
import org.junit.Test;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by vaa25 on 21.07.2015.
 */
@DatabaseSetup(value = "file:src/test/resources/dbunit/UserMapperTest/UserMapperTest_initial.xml",
        type = DatabaseOperation.REFRESH)
@DatabaseTearDown(value = "file:src/test/resources/dbunit/UserMapperTest/UserMapperTest_initial.xml",
        type = DatabaseOperation.DELETE_ALL)
public class UserMapperDbTest extends AbstractDbTest {

    private final String TEST_PATH = PATH + "UserMapperTest/";
    @Inject
    private UserMapper userMapper;

    @Test
    public void shouldFindUserById() throws Exception {
        User expectedUser = BuilderUtil.buildUserGuest();
        User returnedUser = userMapper.getUser(expectedUser.getId());
        assertEquals(expectedUser, returnedUser);
    }

    @Test
    public void shouldFindUserByName() throws Exception {
        User expectedUser = BuilderUtil.buildUserVlasov();
        User returnedUser = userMapper.getUserByName(expectedUser.getLogin());
        assertEquals(expectedUser, returnedUser);
    }

    @Test
    public void expectNullWhenUserIsAbsent() throws Exception {
        User returnedUser = userMapper.getUser(ID_0);
        assertNull(returnedUser);
    }

    @Test
    @ExpectedDatabase(value = TEST_PATH + "testAddUser_expected.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void shouldAddUser() throws Exception {
        User testUser = BuilderUtil.buildUserTest();
        userMapper.addUser(testUser);
    }

    @Test
    public void shouldGetAllUsers() throws Exception {
        int expectedSize = 4;
        int returnedSize = userMapper.getAllUsers().size();
        assertEquals(expectedSize, returnedSize);
    }

    @Test
    @DatabaseSetup(value = TEST_PATH + "testRemoveUser_initial.xml", type = DatabaseOperation.REFRESH)
    @ExpectedDatabase(value = TEST_PATH + "UserMapperTest_initial.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void shouldRemoveUser() {
        User testUser = BuilderUtil.buildUserTest();
        userMapper.removeUser(testUser);
    }
}
