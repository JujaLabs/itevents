package org.itevents.model;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(propOrder = {"id", "login", "password", "role"})

public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private int id;
    @XmlElement
    private String login;
    @XmlElement
    private String password;
    @XmlElement
    private Role role;

    public User() {
    }

    public User(int id, String login, String password, Role role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
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

