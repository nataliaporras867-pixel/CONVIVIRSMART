package com.convivir.app.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.convivir.app.model.Asamblea;
import com.convivir.app.service.AsambleaService;

@Controller
@RequestMapping("/propietario") 
public class AsambleaWebController {

    @Autowired
    private AsambleaService asambleaService;

    @GetMapping("/asambleas") 
    public String listarAsambleas(Model model) {
        System.out.println("¡Entró al controlador!");
        List<Asamblea> todas = asambleaService.listarTodas();
        System.out.println("TOTAL ASAMBLEAS ENCONTRADAS: " + todas.size()); 
        
        model.addAttribute("asambleas", todas);
        return "propietario/asambleas"; 
    }
}