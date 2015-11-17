package org.itevents.controller;

import org.hibernate.validator.constraints.Email;
import org.springframework.web.bind.annotation.*;


/**
 * Created by Alex Anakin
 * on 09.11.2015 20:25.
 */
@RestController
@RequestMapping("/users")
public class SubscriberController {

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/{user_id}/invites")
    public String inviteFriends(
            @PathVariable("user_id") int userId,
            @RequestParam("email") @Email String email) {
        return "";
    }


}


