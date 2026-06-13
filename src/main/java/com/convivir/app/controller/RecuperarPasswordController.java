package com.convivir.app.controller;

import com.convivir.app.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/recuperar")
public class RecuperarPasswordController {

    private final UsuarioService usuarioService;

    public RecuperarPasswordController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    
    @GetMapping
    public String mostrarFormularioSolicitud() {
        return "recuperar-solicitud"; 
    }

   
    @PostMapping
    public String procesarSolicitud(@RequestParam("correo") String correo, Model model) {
        try {
            String token = usuarioService.generarTokenRecuperacion(correo);
            String enlaceSimulado = "http://localhost:8215/recuperar/restablecer?token=" + token;
            model.addAttribute("enlaceSimulado", enlaceSimulado);
            model.addAttribute("mensaje", "Token generado con éxito para: " + correo);
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "recuperar-solicitud";
    }

    
    @GetMapping("/restablecer")
    public String mostrarFormularioRestablecer(@RequestParam("token") String token, Model model) {
        model.addAttribute("token", token);
        return "recuperar-restablecer";
    }

    
    @PostMapping("/restablecer")
    public String procesarRestablecer(@RequestParam("token") String token,
                                      @RequestParam("password") String password,
                                      Model model) {
        try {
            usuarioService.cambiarPasswordConToken(token, password);
            return "redirect:/login?recuperado"; 
        } catch (RuntimeException e) {
            model.addAttribute("token", token);
            model.addAttribute("error", e.getMessage());
            return "recuperar-restablecer";
        }
    }
}