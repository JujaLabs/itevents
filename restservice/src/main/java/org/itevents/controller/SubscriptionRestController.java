package org.itevents.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.itevents.model.Filter;
import org.itevents.model.User;
import org.itevents.service.UserService;
import org.itevents.service.converter.FilterConverter;
import org.itevents.wrapper.FilterWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

/**
 * Created by vaa25 on 16.10.2015.
 */
@RestController
@Api("Subscribe with filter/unsubscribe")
public class SubscriptionRestController {

    @Inject
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET, value = "/unsubscribe")
    @ApiOperation(value = "Reset filter for authorized user")
    public ResponseEntity resetFilter() {
        if (userService.removeFilter(userService.getAuthorizedUser()) == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(HttpStatus.OK);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/subscribe")
    @ApiOperation(value = "Set filter for authorized user")
    public ResponseEntity setFilter(@ModelAttribute FilterWrapper wrapper) {
        Filter filter = new FilterConverter().toFilter(wrapper);
        User user = userService.getAuthorizedUser();
        userService.putFilter(user, filter);
        return new ResponseEntity(HttpStatus.OK);
        //todo try catch for possible exceptions

    }

}
