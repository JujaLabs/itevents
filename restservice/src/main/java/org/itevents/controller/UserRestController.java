package org.itevents.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.itevents.model.User;
import org.itevents.model.builder.UserBuilder;
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

    private User getUserFromSecurityContext() {
        return userService.getUserByName(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
