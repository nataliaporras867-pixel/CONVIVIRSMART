package com.convivir.app.controller;

import com.convivir.app.service.CuotaService;
import com.convivir.app.repository.UsuarioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.security.Principal;

@Controller
public class FinancieroWebController {

    private final CuotaService cuotaService;
    private final UsuarioRepository usuarioRepository;

    public FinancieroWebController(CuotaService cuotaService, UsuarioRepository usuarioRepository) {
        this.cuotaService = cuotaService;
        this.usuarioRepository = usuarioRepository;
        System.err.println("--- EL CONTROLADOR FINANCIERO HA SIDO INICIALIZADO ---");
    }

    @GetMapping("/admin/cuotas")
    public String panelAdmin(Model model) {
        model.addAttribute("listaCuotas", cuotaService.listarTodas());
        return "admin/financiero-gestion";
    }
    
    @GetMapping("/residente/pagos")
    public String listarMisPagos(Model model, Principal principal) {
   
        var usuario = usuarioRepository.findByCorreo(principal.getName())
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        model.addAttribute("misCuotas", cuotaService.listarPorResidente(usuario.getId()));
        
        return "admin/residentes/mis-pagos"; 
    }
    }
    
