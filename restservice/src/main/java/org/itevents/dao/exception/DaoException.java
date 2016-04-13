package org.itevents.dao.exception;

/**
 * Created by vaa25 on 03.11.2015.
 */
public class DaoException extends RuntimeException {
    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
