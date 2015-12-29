package org.itevents.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.itevents.model.Event;
import org.itevents.model.Filter;
import org.itevents.model.User;
import org.itevents.model.builder.UserBuilder;
import org.itevents.service.EventService;
import org.itevents.service.FilterService;
import org.itevents.service.RoleService;
import org.itevents.service.UserService;
import org.itevents.service.converter.FilterConverter;
import org.itevents.service.sendmail.SendGridMailService;
import org.itevents.util.OneTimePassword.OtpGenerator;
import org.itevents.util.mail.BuilderUrl;
import org.itevents.util.mail.MailBuilderUtil;
import org.itevents.util.time.DateTimeUtil;
import org.itevents.wrapper.FilterWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

@RestController
@Api("Users")
@RequestMapping("/users")
public class UserRestController {
    @Inject
    private UserService userService;
    @Inject
    private RoleService roleService;
    @Inject
    private EventService eventService;
    @Inject
    private FilterService filterService;
    @Inject
    private PasswordEncoder passwordEncoder;
    @Inject
    private SendGridMailService mailService;
    @Inject
    private MailBuilderUtil mailBuilderUtil;
    @Inject
    private OtpGenerator otpGenerator;
    @Inject
    private BuilderUrl url;

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
    public ResponseEntity registerNewSubscriber(@ModelAttribute("username") String username,
                                                @ModelAttribute("password") String password) throws Exception{
        if (exists(username)) {
            return new ResponseEntity(HttpStatus.IM_USED);
        }
        User user = UserBuilder.anUser()
                .login(username)
                .password(passwordEncoder.encode(password))
                .role(roleService.getRoleByName("subscriber"))
                .build();
        userService.addUser(user);
        //generateOtp(1440, user);
        //userService.addOtp(user, otpGenerator);
        //SendGrid.Email activationMail = mailService.createMail(mailBuilderUtil.buildHtmlFromUserOtp(user, otpGenerator, url),user.getLogin());
        //mailService.send(activationMail);
        return new ResponseEntity(HttpStatus.OK);
    }

    private boolean exists(String username) {
        return userService.getUserByName(username) != null;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{user_id}")
    public ResponseEntity<User> getUserById(@PathVariable("user_id") int userId) {
        return new ResponseEntity<>(userService.getUser(userId), HttpStatus.OK);
    }

    private void generateOtp(long lifetime, User user){
        otpGenerator.generateOtp(lifetime);
        userService.addOtp(user, otpGenerator);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/subscribe")
    @ApiOperation(value = "Activates authorized user's e-mail subscription with filter")
    public ResponseEntity activateSubscription(@ModelAttribute FilterWrapper wrapper) {
        Filter filter = new FilterConverter().toFilter(wrapper);
        filter.setCreateDate(DateTimeUtil.getNowDate());
        User user = userService.getAuthorizedUser();
        filterService.addFilter(user, filter);
        userService.activateUserSubscription(user);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/unsubscribe")
    @ApiOperation(value = "Deactivates authorized user's e-mail subscription")
    public ResponseEntity deactivateSubscription() {
        User user = userService.getAuthorizedUser();
        userService.deactivateUserSubscription(user);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{user_id}/events")
    @ApiOperation(value = "Returns list of events, to which user is assigned")
    public ResponseEntity<List<Event>> getEventsByUser(@PathVariable("user_id") int userId) {
        User user = userService.getUser(userId);
        if (user == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            List<Event> events = eventService.getEventsByUser(user);
            return new ResponseEntity<>(events, HttpStatus.OK);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/activate/{otp}")
    @ApiOperation(value = "Activates logged in user by OTP")
    public ResponseEntity<User> activateUser(@PathVariable("otp") String password) {
        User user = getUserFromSecurityContext();
        OtpGenerator otpGenerator = userService.getOtp(user);
        if (!password.equals(otpGenerator.getPassword()) || otpGenerator.getExpirationDate().after(new Date())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        userService.activateUser(user);
        userService.DeleteOtp(user);
        return new ResponseEntity<>(user,HttpStatus.ACCEPTED);
    }

    @RequestMapping(method = RequestMethod.POST, value ="/deactivate")
    @ApiOperation(value = "Generates OTP for deactivation of logged in user")
    public ResponseEntity<User> deactivateUser() {
        User user = getUserFromSecurityContext();
        generateOtp(1440, user);
        return new ResponseEntity<>(user,HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.POST, value ="/deactivate/{otp}")
    @ApiOperation(value = "deactivates logged in user by OTP")
    public ResponseEntity<User> deactivateUser(@PathVariable("otp") String password) {
        User user = getUserFromSecurityContext();
        OtpGenerator otpGenerator = userService.getOtp(user);
        if (!password.equals(otpGenerator.getPassword()) || !otpGenerator.getExpirationDate().after(new Date())) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        userService.deactivateUser(user);
        userService.DeleteOtp(user);
        return new ResponseEntity<>(user,HttpStatus.ACCEPTED);
    }

    private User getUserFromSecurityContext() {
        return userService.getUserByName(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}