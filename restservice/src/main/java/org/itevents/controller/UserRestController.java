package org.itevents.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.itevents.model.Otp;
import org.itevents.model.Event;
import org.itevents.model.User;
import org.itevents.model.builder.UserBuilder;
import org.itevents.service.RoleService;
import org.itevents.service.UserService;
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

    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "New subscriber's name", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "New subscriber's password", required = true, dataType = "string", paramType = "query")
    })
    @RequestMapping(method = RequestMethod.POST, value = "/register")
    @ApiOperation(value = "Registers new Subscriber ")
    public ResponseEntity registerNewSubscriber(@ModelAttribute("username") String username,
                                                @ModelAttribute("password") String password) {
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
        userService.addOtp(user,otp);
        return new ResponseEntity(HttpStatus.OK);
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
    @RequestMapping(method = RequestMethod.POST,value = "/activate/{otp}")
    public ResponseEntity<User> activateUser(@PathVariable("otp") String password) {
        User user = getUserFromSecurityContext();
        Otp otp = userService.getOtp(user);
        password = otp.getOtp();
        if (otp!=null && otp.getExpirationDate().after(new Date())) {
            userService.activateUser(user);
            userService.DeleteOtp(user);
            return new ResponseEntity<>(user,HttpStatus.ACCEPTED);
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    private User getUserFromSecurityContext() {
        return userService.getUserByName(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{userID}")
    public ResponseEntity<User> getUserByID(@PathVariable("userID") int userID) {
        return new ResponseEntity<>(userService.getUser(userID), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{userID}/events")
    @ApiOperation(value = "Returns list of events, to which user is subscribed")
    public ResponseEntity<List<Event>> myEvents(@PathVariable("userID") int userID){
        User user = userService.getUser(userID);
        if (user == null) return new ResponseEntity(HttpStatus.BAD_REQUEST);
        List<Event> events = userService.getUserEvents(user);
        return new ResponseEntity<>(events,HttpStatus.OK);
    }
}
