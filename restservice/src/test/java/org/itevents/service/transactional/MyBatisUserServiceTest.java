package org.itevents.service.transactional;

import org.itevents.dao.EventDao;
import org.itevents.dao.UserDao;
import org.itevents.dao.exception.EntityNotFoundDaoException;
import org.itevents.dao.model.Event;
import org.itevents.dao.model.User;
import org.itevents.service.RoleService;
import org.itevents.service.UserService;
import org.itevents.service.exception.EntityAlreadyExistsServiceException;
import org.itevents.service.exception.EntityNotFoundServiceException;
import org.itevents.service.exception.WrongPasswordServiceException;
import org.itevents.test_utils.BuilderUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class MyBatisUserServiceTest {

    @InjectMocks
    @Inject
    private UserService userService;
    @Mock
    private UserDao userDao;
    @Mock
    private EventDao eventDao;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private RoleService roleService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldFindUserById() throws Exception {
        User expectedUser = BuilderUtil.buildUserVlasov();

        when(userDao.getUser(expectedUser.getId())).thenReturn(expectedUser);

        User returnedUser = userService.getUser(expectedUser.getId());

        verify(userDao).getUser(expectedUser.getId());
        assertEquals(expectedUser, returnedUser);

    }

    @Test(expected = EntityNotFoundServiceException.class)
    public void shouldThrowServiceExceptionWhenUserIdIsAbsent() {
        int absentId = 0;

        when(userDao.getUser(absentId)).thenThrow(EntityNotFoundDaoException.class);

        userService.getUser(absentId);
    }

    @Test(expected = EntityNotFoundServiceException.class)
    public void shouldThrowServiceExceptionWhenUserNameIsAbsent() {
        String absentName = "absentName";

        when(userDao.getUserByName(absentName)).thenThrow(EntityNotFoundDaoException.class);

        userService.getUserByName(absentName);
    }

    @Test
    @WithMockUser(username = "testUser", password = "testUserPassword", authorities = "guest")
    public void shouldFindAuthorizedUser() {
        User expectedUser = BuilderUtil.buildUserTest();

        when(userDao.getUserByName(expectedUser.getLogin())).thenReturn(expectedUser);

        User returnedUser = userService.getAuthorizedUser();

        verify(userDao).getUserByName(expectedUser.getLogin());
        assertEquals(expectedUser, returnedUser);
    }

    @Test
    public void shouldAddSubscriber() throws Exception {
        User testUser = BuilderUtil.buildUserVlasov();
        String password = "password";
        String encodedPassword = "encodedPassword";

        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
        when(roleService.getRoleByName("subscriber")).thenReturn(BuilderUtil.buildRoleSubscriber());
        doNothing().when(userDao).addUser(eq(testUser), eq(encodedPassword));

        userService.addSubscriber(testUser.getLogin(), password);

        verify(userDao).addUser(eq(testUser), eq(encodedPassword));
    }

    @Test(expected = EntityAlreadyExistsServiceException.class)
    public void shouldThrowEntityAlreadyExistsServiceExceptionWhenAddExistingSubscriber() throws Exception {
        User testUser = BuilderUtil.buildUserVlasov();
        String password = "password";
        String encodedPassword = "encodedPassword";

        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
        when(roleService.getRoleByName("subscriber")).thenReturn(BuilderUtil.buildRoleSubscriber());
        doThrow(new RuntimeException(new SQLIntegrityConstraintViolationException())).when(userDao).addUser(eq(testUser), eq(encodedPassword));

        userService.addSubscriber(testUser.getLogin(), password);
    }

    @Test
    public void shouldGetAllUsers() throws Exception {
        userService.getAllUsers();

        verify(userDao).getAllUsers();
    }

    @Test
    public void shouldActivateUserSubscription() throws Exception {
        User testUser = BuilderUtil.buildUserTest();
        boolean expectedSubscribed = true;

        doNothing().when(userDao).updateUser(testUser);

        userService.activateUserSubscription(testUser);
        boolean returnedSubscribed = testUser.isSubscribed();

        verify(userDao).updateUser(testUser);
        assertEquals(expectedSubscribed, returnedSubscribed);
    }

    @Test
    public void shouldDeactivateUserSubscription() throws Exception {
        User testSubscriber = BuilderUtil.buildSubscriberTest();
        boolean expectedSubscribed = false;

        doNothing().when(userDao).updateUser(testSubscriber);

        userService.deactivateUserSubscription(testSubscriber);
        boolean returnedSubscribed = testSubscriber.isSubscribed();

        verify(userDao).updateUser(testSubscriber);
        assertEquals(expectedSubscribed, returnedSubscribed);
    }

    @Test
    public void shouldReturnUsersByEvent() throws Exception {
        Event event = BuilderUtil.buildEventJs();
        List users = new ArrayList<>();

        when(userDao.getUsersByEvent(event)).thenReturn(users);
        List returnedUsers = userService.getUsersByEvent(event);

        verify(userDao).getUsersByEvent(event);
        assertEquals(users, returnedUsers);
    }

    @Test
    public void shouldReturnUserPassword() throws Exception {
        User user = BuilderUtil.buildUserAnakin();
        String encodedPassword = "encodedPassword";

        when(userDao.getUserPassword(user)).thenReturn(encodedPassword);
        String returnedPassword = userService.getUserPassword(user);

        verify(userDao).getUserPassword(user);
        assertEquals(encodedPassword, returnedPassword);
    }

    @Test
    public void shouldEncodeAndSaveUserPassword() throws Exception {
        User user = BuilderUtil.buildUserAnakin();
        String password = "password";
        String encodedpassword = "encodedPassword";

        when(passwordEncoder.encode(password)).thenReturn(encodedpassword);
        doNothing().when(userDao).setUserPassword(user, password);

        userService.setAndEncodeUserPassword(user, password);

        verify(userDao).setUserPassword(user, encodedpassword);
    }

    @Test
    public void shouldDoNothingIfCheckPasswordByUserSuccess() throws Exception {
        User testUser = BuilderUtil.buildUserTest();
        String password = "password";
        String encodedPassword = "encodedPassword";

        when(userDao.getUserPassword(testUser)).thenReturn(encodedPassword);
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);

        userService.checkPassword(testUser, password);

        verify(userDao).getUserPassword(testUser);
        verify(passwordEncoder).matches(password, encodedPassword);
    }

    @Test(expected = WrongPasswordServiceException.class)
    public void shouldThrowWrongPasswordServiceExceptionIfCheckPasswordFails() throws Exception {
        User testUser = BuilderUtil.buildUserTest();
        String password = "password";
        String encodedPassword = null;

        when(userDao.getUserPassword(testUser)).thenReturn(encodedPassword);
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(false);

        userService.checkPassword(testUser, password);

        verify(userDao).getUserPassword(testUser);
        verify(passwordEncoder).matches(password, encodedPassword);
    }
}
