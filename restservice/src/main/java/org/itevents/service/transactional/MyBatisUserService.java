package org.itevents.service.transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.itevents.service.converter.FilterConverter;
import org.itevents.controller.wrapper.FilterWrapper;
import org.itevents.dao.UserDao;
import org.itevents.dao.exception.EntityAlreadyExistsDaoException;
import org.itevents.dao.exception.EntityNotFoundDaoException;
import org.itevents.dao.model.Event;
import org.itevents.dao.model.Filter;
import org.itevents.dao.model.User;
import org.itevents.dao.model.builder.UserBuilder;
import org.itevents.service.FilterService;
import org.itevents.service.EventService;
import org.itevents.service.RoleService;
import org.itevents.service.UserService;
import org.itevents.service.exception.*;
import org.itevents.service.sendmail.SendGridMailService;
import org.itevents.util.OneTimePassword.OneTimePassword;
import org.itevents.util.mail.MailBuilderUtil;
import org.itevents.util.time.DateTimeUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

@Service("userService")
@Transactional
public class MyBatisUserService implements UserService {

    private static final Logger LOGGER = LogManager.getLogger();

    @Inject
    private EventService eventService;
    @Inject
    private UserDao userDao;
    @Inject
    private PasswordEncoder passwordEncoder;
    @Inject
    private RoleService roleService;
    @Inject
    private OneTimePassword oneTimePassword;
    @Inject
    private SendGridMailService mailService;
    @Inject
    private MailBuilderUtil mailBuilderUtil;
    @Inject
    private FilterService filterService;
    @Inject
    private FilterConverter filterConverter;
    @Value("${user.activation.otp.lifetime.hours}")
    private int otpLifetime;


    @Override
    public void addSubscriber(String username, String password) throws Exception  {
        String loginInLowerCase = username.toLowerCase();
        User user = UserBuilder.anUser()
                .login(loginInLowerCase)
                .role(roleService.getRoleByName("guest"))
                .build();
        String encodedPassword = passwordEncoder.encode(password);
        addUser(user, encodedPassword);

        OneTimePassword otp = oneTimePassword.generateOtp(otpLifetime);
        setOtpToUser(user, otp);
        sendActivationEmailToUserLogin(user, otp);
    }

    private void addUser(User user, String password) {
        try {
            userDao.addUser(user, password);
        } catch (EntityAlreadyExistsDaoException e) {
            throw new EntityAlreadyExistsServiceException(e.getMessage(), e);
        }
    }

    private void sendActivationEmailToUserLogin(User user, OneTimePassword otp) throws Exception {
        String email = mailBuilderUtil.buildHtmlFromUserOtp(user, otp);
        mailService.sendMail(email, user.getLogin());
    }

    @Override
    public User getUser(int id) {
        try {
            return userDao.getUser(id);
        } catch (EntityNotFoundDaoException e) {
            LOGGER.error(e.getMessage());
            throw new EntityNotFoundServiceException(e.getMessage(), e);
        }
    }

    @Override
    public User getUserByName(String name) {
        try {
            return userDao.getUserByName(name.toLowerCase());
        } catch (EntityNotFoundDaoException e) {
            LOGGER.error(e.getMessage());
            throw new EntityNotFoundServiceException(e.getMessage(), e);
        }
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public User getAuthorizedUser() {
        return getUserByName(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public void activateUserSubscription(FilterWrapper filterWrapper) {
        Filter filter = filterConverter.toFilter(filterWrapper);
        filter.setCreateDate(DateTimeUtil.getNowDate());
        User user = this.getAuthorizedUser();
        user.setSubscribed(true);
        filterService.addFilter(user, filter);
        userDao.updateUser(user);
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public void deactivateUserSubscription() {
        User user = this.getAuthorizedUser();
        user.setSubscribed(false);
        userDao.updateUser(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public List<User> getSubscribedUsers() {
        return userDao.getSubscribedUsers();
    }

    @Override
    public List<User> getUsersByEvent(int eventId) {
        Event event = eventService.getEventById(eventId);
        return userDao.getUsersByEvent(event);
    }

    @Override
    public void setAndEncodeUserPassword(User user, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        userDao.setUserPassword(user, encodedPassword);
    }

    @Override
    public String getUserPassword(User user) {
        return userDao.getUserPassword(user);
    }

    @Override
    public void checkPassword(User user, String password) {
        String encodedPassword = userDao.getUserPassword(user);
        if (!passwordEncoder.matches(password, encodedPassword)) {
            String message = "Wrong login or password";
            throw new AuthenticationServiceException(message);
        }
    }

    @Override
    public void setOtpToUser(User user, OneTimePassword oneTimePassword) {
        userDao.setOtpToUser(user, oneTimePassword);
    }

    @Override
    public void activateUserWithOtp(String password) {
        try {
            OneTimePassword oneTimePassword = userDao.getOtp(password);
            User user = getUserByOtp(oneTimePassword);
//            @TODO:
//            new Date() at line 172 must be refactored, within issue 195
//            https://github.com/JuniorsJava/itevents/pull/195
            if (oneTimePassword.getExpirationDate().after(new Date()) ) {
                user.setRole(roleService.getRoleByName("subscriber"));
                userDao.updateUser(user);
            } else {
                String message = "Password expired";
                throw new OtpExpiredServiceException(message);
            }
        } catch (EntityNotFoundDaoException e) {
            throw new EntityNotFoundServiceException(e.getMessage(),e);
        }
    }

    private User getUserByOtp(OneTimePassword otp) {
        try {
            return userDao.getUserByOtp(otp);
        } catch (EntityNotFoundDaoException e) {
            LOGGER.error(e.getMessage());
            throw new EntityNotFoundServiceException(e.getMessage(), e);
        }
    }
}