package org.itevents.controller.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

/**
 * Created by alex-anakin on 17.11.2015.
 */

@ControllerAdvice
public class ExceptionHandler {

    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(ConstraintViolationException.class)
    public String handleConstraintViolationExceptions(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      return "Invalid Email";
    }
}
