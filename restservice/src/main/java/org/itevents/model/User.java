package org.itevents.model;

import org.itevents.parameter.FilteredEventsParameter;

public class User {

    private int id;
    private String login;
    private String password;
    private Role role;
    private FilteredEventsParameter filteredEventsParameter;

    public User() {
    }

    public User(String login, String password, Role role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public User(String login, String password, Role role, FilteredEventsParameter filteredEventsParameter) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.filteredEventsParameter = filteredEventsParameter;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public FilteredEventsParameter getFilteredEventsParameter() {
        return filteredEventsParameter;
    }

    public void setFilteredEventsParameter(FilteredEventsParameter filteredEventsParameter) {
        this.filteredEventsParameter = filteredEventsParameter;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (!login.equals(user.login)) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        return role.equals(user.role);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + login.hashCode();
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + role.hashCode();
        return result;
    }
}

