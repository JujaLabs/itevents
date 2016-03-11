package org.itevents.service.security;

/**
 * Created by ramax on 1/15/16.
 */
public class Token {

    private String username;
    private String role;

    public Token(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public Token() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Token token = (Token) o;

        if (!username.equals(token.username)) return false;
        return role.equals(token.role);

    }

    @Override
    public int hashCode() {
        int result = username.hashCode();
        result = 31 * result + role.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Token{" +
                "username='" + username + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
