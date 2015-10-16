package org.itevents.controller;

import io.swagger.annotations.Api;
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
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

/**
 * Created by vaa25 on 16.10.2015.
 */
@RestController
@Api("Users")
@RequestMapping("/users")
public class UserRestController {
    @Inject
    private UserService userService;
    @Inject
    private RoleService roleService;

    @RequestMapping(method = RequestMethod.POST, value = "/register")
    @ApiOperation(value = "Registers new Subscriber ")
    public ResponseEntity registerNewSubscriber(@ModelAttribute NewUser newUser) {
        if (exists(newUser)) {
            return new ResponseEntity(HttpStatus.IM_USED);
        }
        final int SUBSCRIBER_ROLE_ID = -3;
        User user = new User();
        user.setLogin(newUser.getLogin());
        user.setPassword(newUser.getPassword());
        user.setRole(roleService.getRole(SUBSCRIBER_ROLE_ID));
        userService.addUser(user);
        return new ResponseEntity(HttpStatus.OK);
    }

    private boolean exists(NewUser newUser) {
        return userService.getUserByName(newUser.getLogin()) != null;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/delete")
    @ApiOperation(value = "Removes user from database ")
    public ResponseEntity removeUser() {
        User user = getUserFromSecurityContext();
        userService.removeUser(user);
        return new ResponseEntity(HttpStatus.OK);
    }

    private User getUserFromSecurityContext() {
        return userService.getUserByName(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
