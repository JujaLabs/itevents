package org.itevents.util.builder;

import org.itevents.model.Role;
import org.itevents.model.User;

/**
 * Created by vaa25 on 03.10.2015.
 */
public class UserBuilder {
    private int id;
    private String login;
    private String password;
    private Role role;

    private UserBuilder() {
    }

    public static UserBuilder anUser() {
        return new UserBuilder();
    }

    public UserBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public UserBuilder setLogin(String login) {
        this.login = login;
        return this;
    }

    public UserBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder setRole(Role role) {
        this.role = role;
        return this;
    }

    public UserBuilder but() {
        return anUser().setId(id).setLogin(login).setPassword(password).setRole(role);
    }

    public User build() {
        User user = new User();
        user.setId(id);
        user.setLogin(login);
        user.setPassword(password);
        user.setRole(role);
        return user;
    }
}
