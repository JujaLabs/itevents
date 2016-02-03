package org.itevents.controller.wrapper;

/**
 * Created by ramax on 1/16/16.
 */
public class TokenWrapper {
    private String token;

    public TokenWrapper(String token) {
        this.token = token;
    }

    public TokenWrapper() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
