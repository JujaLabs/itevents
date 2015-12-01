package org.itevents.model.builder;

import org.itevents.model.Role;
import org.itevents.model.User;

/**
 * Created by vaa25 on 29.10.2015.
 */
public class UserBuilder {
    private int id;
    private String login;
    private String password;
    private Role role;
    private boolean subscribed = false;

    private UserBuilder() {
    }

    public static UserBuilder anUser() {
        return new UserBuilder();
    }

    public UserBuilder id(int id) {
        this.id = id;
        return this;
    }

    public UserBuilder login(String login) {
        this.login = login;
        return this;
    }

    public UserBuilder password(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder role(Role role) {
        this.role = role;
        return this;
    }

    public UserBuilder subscribed(boolean subscribed) {
        this.subscribed = subscribed;
        return this;
    }

    public UserBuilder but() {
        return anUser().id(id).login(login).password(password).role(role).subscribed(subscribed);
    }

    public User build() {
        User user = new User();
        user.setId(id);
        user.setLogin(login);
        user.setPassword(password);
        user.setRole(role);
        user.setSubscribed(subscribed);
        return user;
    }
}
