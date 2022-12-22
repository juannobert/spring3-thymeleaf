package com.juannobert.store.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.juannobert.store.dtos.requests.ProductRequest;
import com.juannobert.store.services.ProductService;
import com.juannobert.store.utils.FlashMessage;

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
	public String insert(@Valid @ModelAttribute("form") ProductRequest form,BindingResult result,RedirectAttributes attrs) {
		if(result.hasErrors()) {
			return "products/form";
		}
		
		service.insert(form);
		attrs.addFlashAttribute("alert", new FlashMessage("alert-success","Produto inserido com sucesso"));
		return "redirect:/products";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id,RedirectAttributes attrs) {
		service.delete(id);
		
		attrs.addFlashAttribute("alert", new FlashMessage("alert-success","Produto excluido com sucesso"));
		return "redirect:/products";
	}
	
	@GetMapping("/update/{id}")
	public ModelAndView updateForm(@PathVariable Long id) {
		ModelAndView mv = new ModelAndView("products/form");
		mv.addObject("form", service.findById(id));
		
		return mv;
	}
	
	@PostMapping("/update/{id}")
	public String update(@PathVariable Long id,@Valid @ModelAttribute("form") ProductRequest form,BindingResult result,RedirectAttributes attrs) {
		if(result.hasErrors()) {
			return "products/form";
		}
		
		service.update(id, form);
		attrs.addFlashAttribute("alert", new FlashMessage("alert-success","Produto atualizado com sucesso"));
		return "redirect:/products";
	}
	
	@GetMapping("/details/{id}")
	public ModelAndView details(@PathVariable Long id) {
		ModelAndView mv = new ModelAndView("products/details-product");
		mv.addObject("form", service.findById(id));
		
		return mv;
	}
	
	
}
