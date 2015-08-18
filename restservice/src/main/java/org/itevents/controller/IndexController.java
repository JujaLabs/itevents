package org.itevents.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class IndexController {

	@RequestMapping(method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {
		model.addAttribute("message1", "IT Events web application");
		model.addAttribute("message2", "Log in");
		model.addAttribute("message3", "Log out");
		return "index";
	}

	@RequestMapping(value = "login")
	public String loginPage(ModelMap model) {
		model.addAttribute("title", "Spring Security Hello World");
		model.addAttribute("message", "This is protected page!");
		return "login";
	}

	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout(ModelMap model) {
		model.addAttribute("message1", "IT Events web application");
		model.addAttribute("message2", "Log in");
		model.addAttribute("message3", "Log out");
		return "index";
	}

	@RequestMapping(value = "admin")
	public String adminPage(ModelMap model) {
		model.addAttribute("title", "Spring Security Hello World");
		model.addAttribute("message", "This is protected page!");
		return "admin";
	}
}
