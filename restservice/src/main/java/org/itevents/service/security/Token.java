package org.itevents.service.security;

import java.util.Date;

/**
 * Created by ramax on 1/15/16.
 */
public class Token {

    private Date createDate = new Date();
    private String username;
    private String password;

    public Token(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Token() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
