package org.itevents.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.itevents.controller.converter.FilterConverter;
import org.itevents.controller.wrapper.FilterWrapper;
import org.itevents.model.Event;
import org.itevents.model.Filter;
import org.itevents.model.User;
import org.itevents.service.EventService;
import org.itevents.service.FilterService;
import org.itevents.service.UserService;
import org.itevents.util.time.DateTimeUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

@RestController
@Api("Users")
@RequestMapping("/users")
public class UserRestController {
    @Inject
    private UserService userService;
    @Inject
    private EventService eventService;
    @Inject
    private FilterService filterService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "User's name", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "User password", required = true, dataType = "string", paramType = "query")
    })
    @RequestMapping(method = RequestMethod.POST, value = "login")
    public void login() {
    }

    @RequestMapping(method = RequestMethod.POST, value = "logout")
    public void logout() {
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "New subscriber's name", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "New subscriber's password", required = true, dataType = "string", paramType = "query")
    })
    @RequestMapping(method = RequestMethod.POST, value = "/register")
    @ApiOperation(value = "Registers new Subscriber ")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void registerNewSubscriber(@ModelAttribute("username") String username,
                                      @ModelAttribute("password") String password) throws Exception {
        userService.addSubscriber(username, password);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{user_id}")
    @ResponseStatus(value = HttpStatus.OK)
    public User getUserById(@PathVariable("user_id") int userId) {
        return userService.getUser(userId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/subscribe")
    @ApiOperation(value = "Activates authorized user's e-mail subscription with filter")
    @ResponseStatus(value = HttpStatus.OK)
    public void activateSubscription(@ModelAttribute FilterWrapper wrapper) {
        Filter filter = new FilterConverter().toFilter(wrapper);
        filter.setCreateDate(DateTimeUtil.getNowDate());
        User user = userService.getAuthorizedUser();
        filterService.addFilter(user, filter);
        userService.activateUserSubscription(user);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/unsubscribe")
    @ApiOperation(value = "Deactivates authorized user's e-mail subscription")
    @ResponseStatus(value = HttpStatus.OK)
    public void deactivateSubscription() {
        User user = userService.getAuthorizedUser();
        userService.deactivateUserSubscription(user);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{user_id}/events")
    @ApiOperation(value = "Returns list of events, to which user is assigned")
    public List<Event> getEventsByUser(@PathVariable("user_id") int userId) {
        User user = userService.getUser(userId);
        return eventService.getEventsByUser(user);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/activate/{otp}")
    @ApiOperation(value = "Activates logged in user by OTP")
    @ResponseStatus(value = HttpStatus.OK)
    public void activateUser(@PathVariable("otp") String otp) {
        userService.activateUserWithOtp(otp);
    }
}