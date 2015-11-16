package org.itevents.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.itevents.model.Event;
import org.itevents.model.Otp;
import org.itevents.model.User;
import org.itevents.model.builder.UserBuilder;
import org.itevents.service.RoleService;
import org.itevents.service.UserService;
import org.itevents.service.sendmail.SendGridMailService;
import org.itevents.util.mail.MailBuilderUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private SendGridMailService mailService;
    @Inject
    MailBuilderUtil mailBuilderUtil;

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
                .password(password)
                .role(roleService.getRoleByName("subscriber"))
                .build();
        userService.addUser(user);
        Otp otp = new Otp();
        otp.generateOtp(1440);
        userService.addOtp(user, otp);
//        SendGrid.Email sendGridMail = mailService.createMail(mailBuilderUtil.buildHtmlFromUserOtp(user,otp),user.getLogin());
//        mailService.send(sendGridMail);
        return new ResponseEntity(HttpStatus.OK);
    }

    private void generateOtp(long lifetime, User user){
        Otp otp = new Otp();
        otp.generateOtp(lifetime);
        userService.addOtp(user, otp);
    }

    private boolean exists(String username) {
        return userService.getUserByName(username) != null;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/delete")
    @ApiOperation(value = "Removes user from database ")
    public ResponseEntity removeUser() {
        User user = getUserFromSecurityContext();
        User removed = userService.removeUser(user);
        if (removed == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(HttpStatus.OK);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/activate/{otp}")
    @ApiOperation(value = "Activates logged in user by OTP")
    public ResponseEntity<User> activateUser(@PathVariable("otp") String password) {
        User user = getUserFromSecurityContext();
        Otp otp = userService.getOtp(user);
        if (!password.equals(otp.getOtp()) || otp.getExpirationDate().after(new Date()))return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            userService.activateUser(user);
            userService.DeleteOtp(user);
            return new ResponseEntity<>(user,HttpStatus.ACCEPTED);
    }
    @RequestMapping(method = RequestMethod.POST, value ="/deactivate")
    @ApiOperation(value = "Generates OTP for deactivation of logged in user")
    public ResponseEntity<User> deactivateUser() {
        User user = getUserFromSecurityContext();
            generateOtp(1440,user);
            return new ResponseEntity<>(user,HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.POST, value ="/deactivate/{otp}")
    @ApiOperation(value = "deactivates logged in user by OTP")
    public ResponseEntity<User> deactivateUser(@PathVariable("otp") String password) {
        User user = getUserFromSecurityContext();
        Otp otp = userService.getOtp(user);
        if (!password.equals(otp.getOtp()) || !otp.getExpirationDate().after(new Date())) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            userService.deactivateUser(user);
            userService.DeleteOtp(user);
            return new ResponseEntity<>(user,HttpStatus.ACCEPTED);
    }

    private User getUserFromSecurityContext() {
        return userService.getUserByName(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") int userId) {
        return new ResponseEntity<>(userService.getUser(userId), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{userId}/events")
    @ApiOperation(value = "Returns list of events, to which user is subscribed")
    public ResponseEntity<List<Event>> myEvents(@PathVariable("userId") int userId){
        User user = userService.getUser(userId);
        if (user == null) return new ResponseEntity(HttpStatus.BAD_REQUEST);
        List<Event> events = userService.getUserEvents(user);
        return new ResponseEntity<>(events,HttpStatus.OK);
    }
}
