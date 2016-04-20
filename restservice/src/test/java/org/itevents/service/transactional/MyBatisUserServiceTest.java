package org.itevents.service.transactional;

import org.itevents.controller.converter.FilterConverter;
import org.itevents.controller.wrapper.FilterWrapper;
import org.itevents.dao.EventDao;
import org.itevents.dao.UserDao;
import org.itevents.dao.exception.EntityAlreadyExistsDaoException;
import org.itevents.dao.exception.EntityNotFoundDaoException;
import org.itevents.dao.model.Event;
import org.itevents.dao.model.Filter;
import org.itevents.dao.model.Role;
import org.itevents.dao.model.User;
import org.itevents.dao.model.builder.UserBuilder;
import org.itevents.service.FilterService;
import org.itevents.service.RoleService;
import org.itevents.service.UserService;
import org.itevents.service.exception.EntityAlreadyExistsServiceException;
import org.itevents.service.exception.EntityNotFoundServiceException;
import org.itevents.service.exception.OtpExpiredServiceException;
import org.itevents.service.exception.WrongPasswordServiceException;
import org.itevents.service.sendmail.SendGridMailService;
import org.itevents.test_utils.BuilderUtil;
import org.itevents.util.OneTimePassword.OneTimePassword;
import org.itevents.util.mail.MailBuilderUtil;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class MyBatisUserServiceTest {

    public static final int OTP_LIFETIME_IN_HOURS = 24;
    public static final String GUEST_ROLE_NAME = "guest";
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
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
    @Mock
    private OneTimePassword oneTimePassword;
    @Mock
    private MailBuilderUtil mailBuilderUtil;
    @Mock
    private SendGridMailService mailService;
    @Mock
    private FilterService filterService;

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
        String testLogin = "test@example.com";
        User testUser = UserBuilder.anUser()
                .login(testLogin)
                .role(BuilderUtil.buildRoleGuest())
                .build();

        String password = "password";
        String encodedPassword = "encodedPassword";
        Role guestRole = BuilderUtil.buildRoleGuest();
        OneTimePassword otp = new OneTimePassword();
        otp.generateOtp(OTP_LIFETIME_IN_HOURS);
        String emailWithOtp = "emailWithOtp";

        when(roleService.getRoleByName(GUEST_ROLE_NAME)).thenReturn(guestRole);
        doNothing().when(userDao).addUser(eq(testUser), eq(encodedPassword));
        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
        when(oneTimePassword.generateOtp(OTP_LIFETIME_IN_HOURS)).thenReturn(otp);
        when(mailBuilderUtil.buildHtmlFromUserOtp(testUser, otp)).thenReturn(emailWithOtp);

        userService.addSubscriber(testLogin, password);

        verify(roleService).getRoleByName(GUEST_ROLE_NAME);
        verify(userDao).addUser(testUser, encodedPassword);
        verify(passwordEncoder).encode(password);
        verify(oneTimePassword).generateOtp(OTP_LIFETIME_IN_HOURS);
        verify(userDao).setOtpToUser(testUser, otp);
        verify(mailService).sendMail(emailWithOtp, testLogin);

    }

    @Test(expected = EntityAlreadyExistsServiceException.class)
    public void shouldThrowEntityAlreadyExistsServiceExceptionWhenAddExistingSubscriber() throws Exception {
        String testLogin = "test@example.com";
        User testUser = UserBuilder.anUser()
                .login(testLogin)
                .role(BuilderUtil.buildRoleGuest())
                .build();
        String password = "password";
        String encodedPassword = "encodedPassword";
        Role guestRole = BuilderUtil.buildRoleGuest();

        when(roleService.getRoleByName(GUEST_ROLE_NAME)).thenReturn(guestRole);
        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
        doThrow(new EntityAlreadyExistsDaoException("message", new SQLException()))
                .when(userDao).addUser(testUser, encodedPassword);

        userService.addSubscriber(testUser.getLogin(), password);
    }

    @Test
    public void shouldGetAllUsers() throws Exception {
        userService.getAllUsers();

        verify(userDao).getAllUsers();
    }

    @Test
    public void shouldActivateUserSubscription() throws Exception {
        FilterWrapper filterWrapper = new FilterWrapper();
        filterWrapper.setCityId(-1);
        filterWrapper.setFree(true);
        filterWrapper.setTechnologiesNames(new String[]{"Java"});

        Filter filter = new FilterConverter().toFilter(filterWrapper);

        User user = BuilderUtil.buildUserAnakin();
//        Filter filter = new FilterConverter().toFilter(wrapper);
//        filter.setCreateDate(DateTimeUtil.getNowDate());
//        User user = userService.getAuthorizedUser();
//        filterService.addFilter(user, filter);
//        userService.activateUserSubscription(user);

        when(userService.getAuthorizedUser()).thenReturn(user);

//        User testUser = BuilderUtil.buildUserTest();
//        boolean expectedSubscribed = true;
//
//        userService.activateUserSubscription(filterWrapper);
//        boolean returnedSubscribed = testUser.isSubscribed();

        verify(filterService).addFilter(user, filter);
        verify(userDao).updateUser(user);
        //assertEquals(expectedSubscribed, returnedSubscribed);
    }

    @Test
    public void shouldDeactivateUserSubscription() throws Exception {
        User testSubscriber = BuilderUtil.buildTestSubscriber();
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

    @Test
    public void shouldSetOtpToUser() throws Exception {
        User user = BuilderUtil.buildUserAnakin();

        userService.setOtpToUser(user, oneTimePassword);

        verify(userDao).setOtpToUser(user, oneTimePassword);
    }

    @Test
    public void shouldUseOtpAndSetRoleToSubscriber() throws Exception {
        User user = BuilderUtil.buildUserGuest();
        OneTimePassword otp = new OneTimePassword().generateOtp(OTP_LIFETIME_IN_HOURS);

        when(userDao.getOtp("otp")).thenReturn(otp);
        when(userDao.getUserByOtp(otp)).thenReturn(user);

        userService.activateUserWithOtp("otp");

        verify(userDao).updateUser(user);
    }

    @Test(expected = OtpExpiredServiceException.class)
    public void shouldNotUseOtpIfExpired() throws Exception {
        User user = BuilderUtil.buildUserGuest();
        OneTimePassword oneTimePassword = new OneTimePassword();
        oneTimePassword.setExpirationDate(new Date());
        String stringOtp = "otp";

        when(userDao.getOtp(stringOtp)).thenReturn(oneTimePassword);
        when(userDao.getUserByOtp(oneTimePassword)).thenReturn(user);

        userService.activateUserWithOtp(stringOtp);
    }

    @Test(expected = EntityNotFoundServiceException.class)
    public void shouldThrowServiceExceptionIfOtpIsInvalid() throws Exception {
        String stringOtp = "otp";

        doThrow(EntityNotFoundDaoException.class)
                .when(userDao).getOtp(stringOtp);

        userService.activateUserWithOtp(stringOtp);
    }
}