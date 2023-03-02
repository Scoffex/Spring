package com.progetto.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/")
public class ControllerMVC {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@GetMapping("/home")
	public String show() {
		return "WELCOME TO HOME";
	}

	
 
	@GetMapping("/render")
	public void showForm(HttpServletRequest request) {
		logger.info("ciaooo");
		logger.info(request.getHeader("Authorization"));
		logger.info(request.getParameter("username"));
		logger.info(request.getParameter("password"));

	}

	@GetMapping("/login")
	public String showLogin() {
		return "login";
	}

}
