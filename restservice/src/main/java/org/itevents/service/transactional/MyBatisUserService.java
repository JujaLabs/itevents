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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.sql.SQLIntegrityConstraintViolationException;
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

    private void addUser(User user) {
        try {
            userDao.addUser(user);
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
    public void addSubscriber(String username, String password) {
        User user = UserBuilder.anUser()
                .login(username)
                .password(passwordEncoder.encode(password))
                .role(roleService.getRoleByName("subscriber"))
                .build();
        addUser(user);

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
}
