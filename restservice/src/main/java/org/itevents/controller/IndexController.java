package org.itevents.controller;

import io.swagger.annotations.Api;
import org.itevents.model.User;
import org.itevents.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.inject.Inject;

@Controller
@RequestMapping("/")
@Api("Index")
public class IndexController {

	@Inject
	private UserService userService;

	@RequestMapping(method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {
		return "index";
	}

	@RequestMapping(method = RequestMethod.GET, value = "admin")
	public String onlyForAdmin(ModelMap model) {
		return "index";
	}

	@RequestMapping(method = RequestMethod.POST, value = "login")
	public ResponseEntity login(@RequestParam("username") String username, @RequestParam("password") String password) {
		User user = userService.getUserByName(username);
		if (user == null || !user.getPassword().equals(password)) {
			return new ResponseEntity(HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity(HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "logout")
	public ResponseEntity logout() {
		return new ResponseEntity(HttpStatus.OK);
	}
}
