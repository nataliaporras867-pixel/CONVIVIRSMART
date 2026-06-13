package com.convivir.app.controller;

import com.convivir.app.model.Unidad;
import com.convivir.app.service.UnidadService; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/propietario")
public class PropietarioController {
	
	

    @Autowired
    private UnidadService unidadService;
    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
        String email = principal.getName();
        
       
        List<Unidad> unidades = unidadService.findByPropietarioEmail(email)
                                             .stream()
                                             .filter(Objects::nonNull)
                                             .collect(Collectors.toList());
                                             
        model.addAttribute("unidades", unidades);
        
        model.addAttribute("totalUnidades", unidades.size());
        
        return "propietario/dashboard";
    }
    
    
}