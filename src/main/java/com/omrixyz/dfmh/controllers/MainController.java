package com.omrixyz.dfmh.controllers;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import com.omrixyz.dfmh.service.UserService;

@Controller
@RequestMapping()
public class MainController {
	 
	@Autowired
	private UserService userService;
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/")
	public String home() {
		return "index";
	}

	@GetMapping("/testi")
	public String test() {
		return "testing";
	}

	@GetMapping("/about")
	public RedirectView about() throws IOException {
		return new RedirectView("https://www.linkedin.com/in/omri-gispan-53b79b1b7");
	}

	
	@ModelAttribute()
	public String whatIsMyName() {
//		if (!userService.checkVerification()) {
//			return "redirect:/validate";
//		}
		return "hello";
	}

}
