package com.convivir.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InfoController {

    @GetMapping("/contacto")
    public String mostrarContacto(Model model) {
        model.addAttribute("nombreConjunto", "CONVIVIRSMART");
        model.addAttribute("direccion", "Carrera 27 # 56-12, Bucaramanga");
        model.addAttribute("telefono", "(607) 645-0000");
        model.addAttribute("email", "admin@convivirsmart.com");
        model.addAttribute("horario", "Lunes a Viernes: 8:00 AM - 5:00 PM");
        return "public/contacto";
    }
}