package org.itevents.service.transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.itevents.dao.UserDao;
import org.itevents.dao.exception.EntityNotFoundDaoException;
import org.itevents.dao.model.Event;
import org.itevents.dao.model.User;
import org.itevents.dao.model.builder.UserBuilder;
import org.itevents.service.RoleService;
import org.itevents.service.UserService;
import org.itevents.service.exception.EntityAlreadyExistsServiceException;
import org.itevents.service.exception.EntityNotFoundServiceException;
import org.itevents.service.exception.OtpExpiredServiceException;
import org.itevents.service.exception.WrongPasswordServiceException;
import org.itevents.service.sendmail.SendGridMailService;
import org.itevents.util.OneTimePassword.OneTimePassword;
import org.itevents.util.mail.MailBuilderUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.List;

@Service("userService")
@Transactional
public class MyBatisUserService implements UserService {

    private static final Logger LOGGER = LogManager.getLogger();

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
    @Value("${user.activation.otp.lifetime.hours}")
    private int otpLifetime;


    private void addUser(User user, String password) {
        try {
            userDao.addUser(user, password);
        } catch (Throwable e) {
            Throwable t = e;
            while (t.getCause() != null) {
                t = t.getCause();
                if (t instanceof SQLIntegrityConstraintViolationException) {
                    throw new EntityAlreadyExistsServiceException("User " + user.getLogin() + " already registered", e);
                }
            }
            String message = e.getMessage() + ". Error when add new user (" + user.getLogin() + ")";
            LOGGER.error(message, e);
            throw new RuntimeException(message, e);
        }
    }

    @Override
    public void addSubscriber(String username, String password) throws Exception  {
        User user = UserBuilder.anUser()
                .login(username)
                .role(roleService.getRoleByName("guest"))
                .build();
        addUser(user, passwordEncoder.encode(password));

        OneTimePassword otp = oneTimePassword.generateOtp(otpLifetime);
        setOtpToUser(user, otp);
        sendActivationEmailToUserLogin(user, otp);
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
            return userDao.getUserByName(name);
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
    public void activateUserSubscription(User user) {
        user.setSubscribed(true);
        userDao.updateUser(user);
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public void deactivateUserSubscription(User user) {
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
    public List<User> getUsersByEvent(Event event) {
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
            String message = "Wrong password '" + password + "' for user '" + user.getLogin() + "'";
            LOGGER.error(message);
            throw new WrongPasswordServiceException(message);
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

            if (oneTimePassword.getExpirationDate().after(new Date()) ) {
                user.setRole(roleService.getRoleByName("subscriber"));
                userDao.updateUser(user);
            } else {
                String message = "Password expired";
                throw new OtpExpiredServiceException(message);
            }
        } catch (EntityNotFoundDaoException e) {
            throw new EntityNotFoundServiceException(e.getMessage(),e.getCause());
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