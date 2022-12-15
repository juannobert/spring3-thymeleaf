package com.juannobert.store.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.juannobert.store.dtos.requests.ProductRequest;
import com.juannobert.store.services.ProductService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService service;
	
	@GetMapping
	public ModelAndView findAll() {
		ModelAndView mv = new ModelAndView("products/list");
		mv.addObject("products", service.findAll());
		return mv;
	}
	
	
	@GetMapping("/insert")
	public ModelAndView insertForm() {
		ModelAndView mv = new ModelAndView("products/form");
		mv.addObject("form", new ProductRequest());
		
		return mv;
	}
	
	@PostMapping("/insert")
	public String insert(@Valid @ModelAttribute("form") ProductRequest form,BindingResult result) {
		if(result.hasErrors()) {
			return "products/form";
		}
		
		service.insert(form);
		return "redirect:/products";
		
		
	}
	
}
