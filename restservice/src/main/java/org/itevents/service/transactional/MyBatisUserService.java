package org.itevents.service.transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.itevents.dao.UserDao;
import org.itevents.dao.exception.EntityNotFoundDaoException;
import org.itevents.model.Event;
import org.itevents.model.User;
import org.itevents.model.builder.UserBuilder;
import org.itevents.service.RoleService;
import org.itevents.service.UserService;
import org.itevents.service.exception.EntityAlreadyExistsServiceException;
import org.itevents.service.exception.EntityNotFoundServiceException;
import org.itevents.service.exception.OtpExpiredServiceExceprion;
import org.itevents.service.exception.WrongPasswordServiceException;
import org.itevents.service.sendmail.SendGridMailService;
import org.itevents.util.OneTimePassword.OtpGenerator;
import org.itevents.util.mail.MailBuilderUtil;
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
    private OtpGenerator otpGenerator;
    @Inject
    private SendGridMailService mailService;
    @Inject
    private MailBuilderUtil mailBuilderUtil;


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
    public void addSubscriber(String username, String password) throws Exception {
        User user = UserBuilder.anUser()
                .login(username)
                .role(roleService.getRoleByName("guest"))
                .build();
        addUser(user, passwordEncoder.encode(password));

        sendEmailWithActivationLink(user);
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
    public void setOtpToUser(User user, OtpGenerator otpGenerator) {
        userDao.setOtpToUser(user, otpGenerator);
    }

    @Override
    public User getUserByOtp(OtpGenerator otp) {
        try {
            return userDao.getUserByOtp(otp);
        } catch (EntityNotFoundDaoException e) {
            LOGGER.error(e.getMessage());
            throw new EntityNotFoundServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void sendEmailWithActivationLink(User user) throws Exception {
        OtpGenerator otp = otpGenerator.generateOtp(1440);
        setOtpToUser(user, otp);
        String email = mailBuilderUtil.buildHtmlFromUserOtp(user, otp);
        mailService.sendMail(email, user.getLogin());
    }

    @Override
    public void activateUserWithOtp(String password) {
        OtpGenerator otpGenerator = userDao.getOtp(password);
        User user = userDao.getUserByOtp(otpGenerator);

        if (otpGenerator.getExpirationDate().after(new Date()) ) {
        user.setRole(roleService.getRoleByName("subscriber"));
        userDao.updateUser(user);

        } else {
            String message = "Password expired";
            LOGGER.error(message);
            throw new OtpExpiredServiceExceprion(message);
        }
    }
}