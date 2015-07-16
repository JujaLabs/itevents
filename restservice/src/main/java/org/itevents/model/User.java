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
    private int role;

    public User() {
    }

    public User(int id, String login, String password, int role) {
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

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User ");
        sb.append("id=").append(this.id);
        sb.append(", login=").append(this.login);
        sb.append(", password=").append(this.password);
        sb.append(", role=").append(this.role);
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (this.id != user.id) return false;
        if (login != null ? !login.equals(user.login) : user.login != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        return role == user.role;
    }

    @Override
    public int hashCode() {
        int result = id ^ (id >>> 32) + role ^ (role >>> 32);
        result = 31 * result + login.hashCode();
        result = 31 * result + password.hashCode();
        return result;
    }
}

