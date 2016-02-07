package org.itevents.service.exception;

/**
 * Created by vaa25 on 03.11.2015.
 */
public class ServiceException extends RuntimeException {
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(String message) {
        super(message);
    }
}
