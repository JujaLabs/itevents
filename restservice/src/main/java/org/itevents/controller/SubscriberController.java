package org.itevents.controller;

import org.itevents.service.SubscriberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

/**
 * Created by Alex Anakin
 * on 09.11.2015 20:25.
 */
@RestController
@RequestMapping("/user")
public class SubscriberController {

    @Inject
    private SubscriberService subscriberService;

    @RequestMapping(method = RequestMethod.POST, value = "/{user_id}/invite")
    public ResponseEntity<String> inviteFriends(@PathVariable("user_id") int userId, @RequestParam("emails") String emails) {
        String message = subscriberService.inviteFriends(userId, emails);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }


}


