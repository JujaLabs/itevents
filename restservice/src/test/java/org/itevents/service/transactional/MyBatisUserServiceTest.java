package org.itevents.service.transactional;

import org.itevents.dao.EventDao;
import org.itevents.dao.UserDao;
import org.itevents.model.Event;
import org.itevents.model.User;
import org.itevents.service.UserService;
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
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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
    public void shouldAddUser() throws Exception {
        User testUser = BuilderUtil.buildUserTest();
        String password = "testUserPassword";
        doNothing().when(userDao).addUser(testUser, password);

        userService.addUser(testUser, password);

        verify(userDao).addUser(testUser, password);
    }

    @Test
    public void shouldGetAllUsers() {
        userService.getAllUsers();

        verify(userDao).getAllUsers();
    }

    @Test
    public void shouldActivateUserSubscription() {
        User testUser = BuilderUtil.buildUserTest();
        boolean expectedSubscribed = true;

        doNothing().when(userDao).updateUser(testUser);

        userService.activateUserSubscription(testUser);
        boolean returnedSubscribed = testUser.isSubscribed();

        verify(userDao).updateUser(testUser);
        assertEquals(expectedSubscribed, returnedSubscribed);
    }

    @Test
    public void shouldDeactivateUserSubscription() {
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
    public void shouldSetEncodedUserPassword() throws Exception {
        User user = BuilderUtil.buildUserAnakin();
        String password = "password";
        String encodedpassword = "encodedPassword";

        when(passwordEncoder.encode(password)).thenReturn(encodedpassword);
        doNothing().when(userDao).setUserPassword(user, password);

        userService.setAndEncodeUserPassword(user, password);

        verify(userDao).setUserPassword(user, encodedpassword);
    }

    @Test
    public void shouldMatchPasswordByUser() throws Exception {
        User testUser = BuilderUtil.buildUserTest();
        String password = "password";
        String encodedPassword = "encodedPassword";

        when(userDao.getUserPassword(testUser)).thenReturn(encodedPassword);
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);
        boolean isCorrect = userService.matchPasswordByLogin(testUser, password);

        verify(userDao).getUserPassword(testUser);
        assertTrue(isCorrect);
    }
}
