package org.itevents.controller;

import org.itevents.controller.exception.EntityNotFoundControllerException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;

/**
 * Created by vaa25 on 31.10.2015.
 */
@ControllerAdvice(annotations = RestController.class)
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Database error")
    @ExceptionHandler(SQLException.class)
    public void handleSqlException() {
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Entity not found")
    @ExceptionHandler(EntityNotFoundControllerException.class)
    public void handleEventNotFoundControllerException() {
    }
}
