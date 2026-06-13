package com.convivir.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InicioWebController {
	@GetMapping("/")
	public String raiz() {
		return "redirect:/inicio";
		
	}
	
	@GetMapping("/inicio")
	public String inicio() {
		return "inicio";
	}
	
	@GetMapping("/noticias")
	public String verNoticiasPublicas() {
	    
	    return "noticias-inicio";
	}
	
	

	
}
