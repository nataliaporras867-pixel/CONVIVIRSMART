package com.convivir.app.controller;

import com.convivir.app.repository.UsuarioRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/residente")
public class ResidenteDashboardWebController {

    private final UsuarioRepository usuarioRepository;

    public ResidenteDashboardWebController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication) {
        String correo = authentication.getName();
        usuarioRepository.findByCorreo(correo).ifPresent(usuario -> {
            model.addAttribute("nombreUsuario", 
                usuario.getNombres() + " " + usuario.getApellidos());
        });
       
        return "admin/residentes/dashboard"; 
    }

    
    @GetMapping("/noticias")
    public String verNoticias(Model model, Authentication authentication) {
        String correo = authentication.getName();
        
        usuarioRepository.findByCorreo(correo).ifPresent(usuario -> {
            model.addAttribute("nombreUsuario", 
                usuario.getNombres() + " " + usuario.getApellidos());
        });
        
        
        return "admin/residentes/noticias";
    }
}