package org.itevents.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
import java.util.List;

@RestController
@Api("Users")
public class UserRestController {
    @Inject
    private UserService userService;
    @Inject
    private RoleService roleService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "New subscriber's name", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "New subscriber's password", required = true, dataType = "string", paramType = "query")
    })
    @RequestMapping(method = RequestMethod.POST, value = "/users/register")
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
        return new ResponseEntity(HttpStatus.OK);
    }

    private boolean exists(String username) {
        return userService.getUserByName(username) != null;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/users/delete")
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

    private User getUserFromSecurityContext() {
        return userService.getUserByName(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/users/{userID}")
    public ResponseEntity<User> getUserByID(@PathVariable("userID") int userID) {
        return new ResponseEntity<>(userService.getUser(userID), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/users/{userID}/events")
    public ResponseEntity<List<Event>> myEvents(@PathVariable("userID") int userID){
        User user = userService.getUser(userID);
        if (user == null) return new ResponseEntity(HttpStatus.BAD_REQUEST);
        List<Event> events = userService.getUserEvents(user);
        return new ResponseEntity<>(events,HttpStatus.OK);
    }
}
