package org.itevents.controller;

import io.swagger.annotations.Api;
import org.itevents.model.Event;
import org.itevents.model.User;
import org.itevents.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
@Api("Users")
public class UserRestController {

    @Inject
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET, value = "/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") int id) {
        return getUserResponseEntity(userService.getUser(id));
    }
    @RequestMapping(method = RequestMethod.GET, value = "/users/{id}/getMyEvents")
    public List<Event> myEvents(@PathVariable("id") int id){
        return userService.getUserEvents(userService.getUser(id));
    }

    private ResponseEntity<User> getUserResponseEntity(User user) {
        if (user == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
