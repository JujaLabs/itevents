package org.itevents.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.itevents.service.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

/**
 * Created by vaa25 on 31.10.2015.
 */
@ControllerAdvice(annotations = RestController.class)
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LogManager.getLogger();

    @ExceptionHandler(EntityNotFoundServiceException.class)
    public ResponseEntity<String> handleEntityNotFoundControllerException(EntityNotFoundServiceException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TimeCollisionServiceException.class)
    public ResponseEntity<String> handleTimeCollisionServiceException(TimeCollisionServiceException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityAlreadyExistsServiceException.class)
    public ResponseEntity<String> handleEntityAlreadyExistsServiceException(EntityAlreadyExistsServiceException ex) {
        LOGGER.error(ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(ActionAlreadyDoneServiceException.class)
    public ResponseEntity<String> handleActionAlreadyDoneServiceException(ActionAlreadyDoneServiceException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String>  handleConstrainViolationExceptions(ConstraintViolationException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationServiceException.class)
    public ResponseEntity<String> handleAuthenticationServiceException(AuthenticationServiceException ex) {
        LOGGER.error(ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }
}
