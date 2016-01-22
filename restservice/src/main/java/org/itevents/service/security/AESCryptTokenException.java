package org.itevents.service.security;

/**
 * Created by ramax on 1/16/16.
 */
public class AESCryptTokenException extends RuntimeException {
    public AESCryptTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
