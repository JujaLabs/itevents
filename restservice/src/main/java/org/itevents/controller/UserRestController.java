package org.itevents.controller;

import io.swagger.annotations.ApiOperation;
import org.itevents.model.NewUser;
import org.itevents.model.User;
import org.itevents.service.RoleService;
import org.itevents.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;

/**
 * Created by vaa25 on 16.10.2015.
 */
@RequestMapping("/users")
public class UserRestController {
    @Inject
    private UserService userService;
    @Inject
    private RoleService roleService;

    @RequestMapping(method = RequestMethod.POST, value = "/register")
    @ApiOperation(value = "Registers new Subscriber ")
    public ResponseEntity registerNewSubscriber(@ModelAttribute NewUser newUser) {
        final int SUBSCRIBER_ROLE_ID = -3;
        User user = new User();
        user.setLogin(newUser.getLogin());
        user.setPassword(newUser.getPassword());
        user.setRole(roleService.getRole(SUBSCRIBER_ROLE_ID));
        userService.addUser(user);
        return new ResponseEntity(HttpStatus.OK);
        //todo if user exists
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/delete")
    @ApiOperation(value = "Removes user from database ")
    public ResponseEntity getFilteredEvents() {
        User user = getUserFromSecurityContext();
        userService.removeUser(user);
        return new ResponseEntity(HttpStatus.OK);
        //todo if user absent
    }

    private User getUserFromSecurityContext() {
        return userService.getUserByName(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
