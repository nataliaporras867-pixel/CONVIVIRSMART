package com.convivir.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.convivir.app.dto.RegistroRequest;
import com.convivir.app.service.UsuarioService;

@Controller
@RequestMapping("/registro") 
public class RegistroWebController {

    private final UsuarioService usuarioService;

    public RegistroWebController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String registroPage(Model model) {
        
        model.addAttribute("registroRequest", new RegistroRequest());
        return "auth/registro";
    }

    @PostMapping
    public String procesarRegistro(@ModelAttribute("registroRequest") RegistroRequest request, Model model) {
        try {
            
            if (!request.getPassword().equals(request.getConfirmarPassword())) {
                model.addAttribute("error", "Las contraseñas no coinciden");
                return "auth/registro";
            }

            
            usuarioService.procesarRegistro(request); 
            
           
            return "redirect:/login?registered";

        } catch (Exception e) {
            
            model.addAttribute("error", "Error al registrar: " + e.getMessage());
            
            model.addAttribute("registroRequest", request); 
            return "auth/registro";
        }
    }
}