package com.juannobert.store.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.juannobert.store.dtos.requests.UserRequest;
import com.juannobert.store.services.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {

	
	@Autowired
	private UserService service;
	
	@GetMapping("/login")
	public String login() {
		return "auth/login";
	}
	
	@GetMapping("/register")
	public ModelAndView register() {
		ModelAndView mv = new ModelAndView("auth/register");
		mv.addObject("form", new UserRequest());
		return mv;
	}
	
	@PostMapping("/register")
	public String register(@Valid @ModelAttribute("form") UserRequest request,BindingResult result) {
		if(result.hasErrors()) {
			return "auth/register";
		}
		service.insert(request);
		return "redirect:/auth/login";
	}
}
