package org.itevents.service.exception;

/**
 * Created by 1 on 05.02.2016.
 */
public class OtpExpiredServiceExceprion extends ServiceException {
    public OtpExpiredServiceExceprion(String message, Throwable cause) {
        super(message, cause);
    }

    public OtpExpiredServiceExceprion(String message) {
        super(message);
    }
}
