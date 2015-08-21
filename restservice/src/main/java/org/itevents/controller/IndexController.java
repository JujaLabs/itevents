package org.itevents.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class IndexController {

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public String mainPage() {

		return "content/user";

	}

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String adminPage() {

		return "content/admin";

	}

	@RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error,
							  @RequestParam(value = "denied", required = false) String denied,
							  Principal principal) {

		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", "Invalid username or password!");
		}
		if (denied != null) {
			model.addObject("denied", "Access denied to " + principal.getName());
		}

		model.setViewName("login");

		return model;

	}
}
