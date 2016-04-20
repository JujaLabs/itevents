package org.itevents.service;

import org.itevents.controller.wrapper.TokenWrapper;

/**
 * Created by ramax on 1/30/16.
 */
public interface TokenService {
    TokenWrapper createTokenWrapper(String login, String password);
}
