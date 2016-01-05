package org.itevents.controller;

import org.itevents.controller.exception.EntityNotFoundControllerException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(EntityNotFoundControllerException.class)
            @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Entity not found")
    public void handleEntityNotFoundControllerException() {
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Constraint violation")
    public void handleConstrainViolationExceptions() {
    }
}
