package org.itevents.service.exception;

/**
 * Created by vaa25 on 06.04.2016.
 */
public class IntegrationException extends RuntimeException {
    public IntegrationException(final String message, final Exception exception) {
        super(message, exception);
    }
}
