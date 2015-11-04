package org.itevents.service.transactional;

import org.itevents.dao.UserDao;
import org.itevents.dao.exception.UserNotFoundDaoException;
import org.itevents.model.User;
import org.itevents.service.UserService;
import org.itevents.service.exception.UserNotFoundServiceException;
import org.itevents.test_utils.BuilderUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by vaa25 on 17.10.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@Transactional
public class MyBatisUserServiceTest {

    @InjectMocks
    @Inject
    private UserService userService;
    @Mock(name = "userMapper")
    private UserDao userDao;

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

    @Test(expected = UserNotFoundServiceException.class)
    public void shouldThrowUserNotFoundServiceException() throws Exception {
        int absentId = 0;

        when(userDao.getUser(absentId)).thenThrow(UserNotFoundDaoException.class);

        userService.getUser(absentId);
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

        doNothing().when(userDao).addUser(testUser);

        userService.addUser(testUser);

        verify(userDao).addUser(testUser);
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
}