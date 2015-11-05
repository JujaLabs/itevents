package org.itevents.controller.exception;

/**
 * Created by vaa25 on 05.11.2015.
 */
public class ControllerException extends RuntimeException {
    public ControllerException(String message, Throwable cause) {
        super(message, cause);
    }
}
