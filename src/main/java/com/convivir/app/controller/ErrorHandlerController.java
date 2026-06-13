package com.convivir.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorHandlerController {

    @GetMapping("/acceso-denegado")
    public String showAccessDenied() {
        // Asegúrate de tener este archivo en: src/main/resources/templates/error/403.html
        return "error/403"; 
    }
}