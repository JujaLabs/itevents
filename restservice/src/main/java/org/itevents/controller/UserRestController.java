package org.itevents.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.itevents.model.Event;
import org.itevents.model.User;
import org.itevents.model.builder.UserBuilder;
import org.itevents.service.EventService;
import org.itevents.service.RoleService;
import org.itevents.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private RoleService roleService;
    @Inject
    private EventService eventService;

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
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{user_id}")
    public ResponseEntity<User> getUserById(@PathVariable("user_id") int userId) {
        return new ResponseEntity<>(userService.getUser(userId), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{user_id}/events")
    @ApiOperation(value = "Returns list of events, to which user is assigned")
    public ResponseEntity<List<Event>> getEventsByUser(@PathVariable("user_id") int userId){
        User user = userService.getUser(userId);
        if (user == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            List<Event> events = eventService.getEventsByUser(user);
            return new ResponseEntity<>(events, HttpStatus.OK);
        }
    }

    private boolean exists(String username) {
        return userService.getUserByName(username) != null;
    }
}
