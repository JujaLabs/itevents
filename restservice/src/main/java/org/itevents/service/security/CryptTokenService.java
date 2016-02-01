package org.itevents.service.security;

/**
 * Created by ramax on 1/16/16.
 */
public interface CryptTokenService {
    String encrypt(Token token);
    Token decrypt(String token);
}
