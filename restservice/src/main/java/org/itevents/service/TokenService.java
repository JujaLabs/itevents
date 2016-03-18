package org.itevents.service;

/**
 * Created by ramax on 1/30/16.
 */
public interface TokenService {
    String createToken(String login, String password);
}
