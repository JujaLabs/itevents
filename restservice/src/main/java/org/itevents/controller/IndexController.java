package org.itevents.controller;

import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.apache.logging.log4j.LogManager;


@Controller
@RequestMapping("/")
public class IndexController {

	public static final Logger log = LogManager.getLogger(IndexController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {
		log.info("=====>>>>> TEST LOG4J INTO CONSOLE <<<<<=====");
		model.addAttribute("message1", "IT Events web application");
		model.addAttribute("message2", "Sorry, we are under construction");
		return "index";
	}
}