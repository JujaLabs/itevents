package org.itevents.dao.mybatis.mapper;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.itevents.AbstractDbTest;
import org.itevents.dao.exception.EntityNotFoundDaoException;
import org.itevents.dao.mybatis.sql_session_dao.UserMyBatisDao;
import org.itevents.model.Event;
import org.itevents.model.User;
import org.itevents.test_utils.BuilderUtil;
import org.junit.Test;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by vaa25 on 21.07.2015.
 */
@DatabaseSetup(value = "file:src/test/resources/dbunit/UserMapperTest/UserMapperTest_initial.xml",
        type = DatabaseOperation.REFRESH)
@DatabaseTearDown(value = "file:src/test/resources/dbunit/UserMapperTest/UserMapperTest_initial.xml",
        type = DatabaseOperation.DELETE_ALL)
public class UserMyBatisDaoDbTest extends AbstractDbTest {

    private final String TEST_PATH = PATH + "UserMapperTest/";
    @Inject
    private UserMyBatisDao userMyBatisDao;

    @Test
    public void shouldFindUserById() throws Exception {
        User expectedUser = BuilderUtil.buildUserGuest();
        User returnedUser = userMyBatisDao.getUser(expectedUser.getId());
        assertEquals(expectedUser, returnedUser);
    }

    @Test
    public void shouldFindUserByName() throws Exception {
        User expectedUser = BuilderUtil.buildUserVlasov();
        User returnedUser = userMyBatisDao.getUserByName(expectedUser.getLogin());
        assertEquals(expectedUser, returnedUser);
    }

    @Test(expected = EntityNotFoundDaoException.class)
    public void shouldThrowEntityNotFoundDaoExceptionWhenUserIsAbsent() throws Exception {
        userMyBatisDao.getUser(ABSENT_ID);
    }

    @Test
    @ExpectedDatabase(value = TEST_PATH + "testAddUser_expected.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void shouldAddUser() throws Exception {
        User testUser = BuilderUtil.buildUserTest();
        userMyBatisDao.addUser(testUser);
    }

    @Test
    public void shouldGetAllUsers() throws Exception {
        int expectedSize = 4;
        int returnedSize = userMyBatisDao.getAllUsers().size();
        assertEquals(expectedSize, returnedSize);
    }

    @Test
    @ExpectedDatabase(value = TEST_PATH + "testUpdateUser_expected.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void shouldUpdateUser() {
        User user = BuilderUtil.buildUserVlasov();
        user.setSubscribed(true);
        userMyBatisDao.updateUser(user);
    }

    @Test
    @DatabaseSetup(value = TEST_PATH + "assignUserEvent_initial.xml", type = DatabaseOperation.REFRESH)
    @DatabaseTearDown(value = TEST_PATH + "assignUserEvent_initial.xml",
            type = DatabaseOperation.DELETE_ALL)
    public void shouldReturnUsersByEvent() throws Exception {
        User user = BuilderUtil.buildUserKuchin();
        List expectedUsers = new ArrayList<>();
        expectedUsers.add(user);
        Event event = BuilderUtil.buildEventPhp();
        List returnedUsers = userMyBatisDao.getUsersByEvent(event);
        assertEquals(expectedUsers,returnedUsers);
    }
}
