package com.convivir.app.controller;

import com.convivir.app.model.Pqr;
import com.convivir.app.service.PqrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/residente/pqrs")
public class PqrWebController {

    @Autowired
    private PqrService pqrService;

    @GetMapping("/nueva")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("pqr", new Pqr());
        return "pqr-registro";
    }

    @PostMapping("/guardar")
    public String guardarPqr(@ModelAttribute Pqr pqr, Principal principal) {
        String username = (principal != null) ? principal.getName() : "residente";
        pqrService.registrarPqr(pqr, username);
        return "redirect:/residente/dashboard?successPqr";
    }

    @GetMapping
    public String listarMisPqrs(Model model, Principal principal) {
        String username = (principal != null) ? principal.getName() : "residente";
        List<Pqr> misPqrs = pqrService.listarPorResidente(username);
        model.addAttribute("pqrs", misPqrs);
        return "pqr-lista-residente";
    }

   
    @GetMapping("/detalle/{id}")
    public String verDetallePqr(@PathVariable("id") String id, Model model) {
        Optional<Pqr> pqrOpt = pqrService.buscarPorId(id);
        if (pqrOpt.isPresent()) {
            model.addAttribute("pqr", pqrOpt.get());
            return "pqr-detalle-residente";
        }
        return "redirect:/residente/pqrs";
    }
}