package org.itevents.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.inject.Inject;
import org.itevents.controller.converter.FilterConverter;
import org.itevents.controller.wrapper.FilterWrapper;
import org.itevents.dao.model.Event;
import org.itevents.dao.model.Filter;
import org.itevents.dao.model.User;
import org.itevents.dao.model.builder.UserBuilder;
import org.itevents.service.EventService;
import org.itevents.service.FilterService;
import org.itevents.service.RoleService;
import org.itevents.service.UserService;
import org.itevents.service.sendmail.SendGridMailService;
import org.itevents.util.OneTimePassword.OneTimePassword;
import org.itevents.util.mail.MailBuilderUtil;
import org.itevents.util.time.DateTimeUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import static org.apache.logging.log4j.LogManager.exists;

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
    @Inject
    private RoleService roleService;
    @Inject
    private OneTimePassword oneTimePassword;
    @Inject
    private SendGridMailService mailService;
    @Inject
    private MailBuilderUtil mailBuilderUtil;

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
    public ResponseEntity registerNewSubscriber(@ModelAttribute("username") String username,
                                                @ModelAttribute("password") String password) throws Exception {
        if (exists(username)) {
            return new ResponseEntity(HttpStatus.IM_USED);
        }
        User user = UserBuilder.anUser()
            .login(username)
            .role(roleService.getRoleByName("subscriber"))
            .build();
        userService.addSubscriber(username, password);
        mailService.sendMail(mailBuilderUtil.buildHtmlFromUserOtp(user, oneTimePassword),user.getLogin());
        return new ResponseEntity(HttpStatus.CREATED);
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

    @RequestMapping(method = RequestMethod.GET, value = "/activate/{otp}")
    @ApiOperation(value = "Activates user by OTP")
    @ResponseStatus(value = HttpStatus.OK)
    public void activateUser(@PathVariable("otp") String otp) {
        userService.activateUserWithOtp(otp);
    }
}