package com.progetto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogInController {

	@GetMapping("/login33")
	public String loginForm(Model model) {
		return "login2";
	}

	@GetMapping("/")
	public String home(Model model) {
		return "home";

	}
}
