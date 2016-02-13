package org.itevents.service;

import org.itevents.service.security.Token;

/**
 * Created by ramax on 1/16/16.
 */
public interface CryptTokenService {
    String encrypt(Token token);
    Token decrypt(String token);
}
